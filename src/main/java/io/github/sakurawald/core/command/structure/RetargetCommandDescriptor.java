package io.github.sakurawald.core.command.structure;

import com.mojang.brigadier.Command;
import io.github.sakurawald.core.auxiliary.LogUtil;
import io.github.sakurawald.core.auxiliary.minecraft.CommandHelper;
import io.github.sakurawald.core.command.annotation.CommandTarget;
import io.github.sakurawald.core.command.argument.structure.Argument;
import io.github.sakurawald.core.command.argument.wrapper.impl.PlayerCollection;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RetargetCommandDescriptor extends CommandDescriptor {

    private static final int COMMAND_TARGET_DUMMY_PARAMETER_INDEX = 1024;

    private final int commandTargetAnnotationIndex;

    private RetargetCommandDescriptor(Method method, List<Argument> arguments, int commandTargetAnnotationIndex) {
        super(method, arguments);
        this.commandTargetAnnotationIndex = commandTargetAnnotationIndex;
    }

    private static List<Argument> transformWithOthersArguments(List<Argument> arguments, int commandTargetAnnotationIndex) {
        List<Argument> ret = new ArrayList<>(arguments
            .stream()
            .filter(it ->
                /*
                 remove the argument that is annotated with @CommandTarget and is not annotated with @CommandSource,
                 so that this argument will not be registered in the command tree.
                 */
                it.isCommandSource() ||
                    it.getMethodParameterIndex() != commandTargetAnnotationIndex
            )
            .toList());

        for (int argumentIndex = 0; argumentIndex < arguments.size(); argumentIndex++) {
            Argument argument = arguments.get(argumentIndex);

            /* ensure the `others` args are the `first required argument`, so that the `makeCommandFunctionArgs()` can extract the targets in the first arg */
            if (argument.isRequiredArgument() || argumentIndex == ret.size() - 1) {

                /* all retarget commands require level 4 permission to use */
                CommandRequirementDescriptor requirement = new CommandRequirementDescriptor(4, null);

                ret.add(argumentIndex, Argument.makeLiteralArgument("others", requirement));
                ret.add(argumentIndex + 1, Argument.makeRequiredArgument(PlayerCollection.class, "others", COMMAND_TARGET_DUMMY_PARAMETER_INDEX, false, requirement));
                break;
            }

        }

        return ret;
    }

    private static Optional<Integer> findCommandTargetAnnotationIndex(Method method) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (parameter.isAnnotationPresent(CommandTarget.class)) {
                // verify
                if (!parameter.getType().equals(ServerPlayerEntity.class)) {
                    throw new IllegalArgumentException("the annotation @CommandTarget can only be used in a parameter whose type is ServerPlayerEntity: class = %s, method = %s".formatted(method.getDeclaringClass().getSimpleName(), method.getName()));
                }

                return Optional.of(i);
            }
        }

        return Optional.empty();
    }

    @Override
    protected Command<ServerCommandSource> makeCommandFunctionClosure() {
        return (ctx) -> {

            /* verify command source */
            if (!verifyCommandSource(ctx, this)) {
                return CommandHelper.Return.FAIL;
            }

            LogUtil.debug("execute retarget command: initialing command source = {}", ctx.getSource().getName());

            /* invoke the command function */
            List<Object> args = makeCommandFunctionArgs(ctx);

            /* apply the command execution for each target. */
            PlayerCollection targets = (PlayerCollection) args.getFirst();
            LogUtil.debug("unbox the first argument and get the targets: {}", targets.getValue().stream().map(it -> it.getGameProfile().getName()).toList());

            int finalValue = CommandHelper.Return.SUCCESS;
            for (ServerPlayerEntity target : targets.getValue()) {

                List<Object> unboxedArgs = args.subList(1, args.size());
                /*
                 if the @CommandSource and @CommandTarget are both annotated in the same parameter:
                 1. The @CommandSource will still be used to verify the type of `initialing command source`.
                 2. After that, the command source passed to the command method will be overridden by the @CommandTarget.
                 3. Any exceptions thrown during the execution of the command method, will be reported to the `initialing command source`.
                 */
                if (this.commandTargetAnnotationIndex < unboxedArgs.size()) {
                    unboxedArgs.set(this.commandTargetAnnotationIndex, target);
                } else {
                    // if the commandTargetAnnotationIndex < unboxedArgs, then it means the argument annotated with @CommandTarget is filtered.
                    unboxedArgs.add(this.commandTargetAnnotationIndex, target);
                }

                LogUtil.debug("invoke command method {} in class {}: target = {}, args = {}"
                    , this.method.getName()
                    , this.method.getDeclaringClass().getSimpleName()
                    , target.getGameProfile().getName()
                    , unboxedArgs);

                try {
                    // if one of the execution if failed, then it's considered the whole return value is failed.
                    int singleValue = (int) this.method.invoke(null, unboxedArgs.toArray());
                    LogUtil.debug("the return value of command method is {}: target = {}, args = {}"
                        , singleValue
                        , target.getGameProfile().getName()
                        , unboxedArgs);

                    if (singleValue != CommandHelper.Return.SUCCESS) {
                        finalValue = CommandHelper.Return.FAIL;
                    }

                } catch (Exception wrappedOrUnwrappedException) {
                    return handleException(ctx, this.method, wrappedOrUnwrappedException);
                }
            }

            return finalValue;
        };
    }

    public static Optional<RetargetCommandDescriptor> make(CommandDescriptor commandDescriptor) {
        /* filter: the method that contains @CommandTarget */
        Optional<Integer> commandTargetAnnotationIndexOpt = findCommandTargetAnnotationIndex(commandDescriptor.method);
        if (commandTargetAnnotationIndexOpt.isEmpty()) {
            return Optional.empty();
        }
        int commandTargetAnnotationIndex = commandTargetAnnotationIndexOpt.get();

        /* make retarget command descriptor */
        Method delegateMethod = commandDescriptor.getMethod();
        List<Argument> transformedArgs = transformWithOthersArguments(commandDescriptor.getArguments(), commandTargetAnnotationIndex);

        RetargetCommandDescriptor retargetCommandDescriptor = new RetargetCommandDescriptor(delegateMethod, transformedArgs, commandTargetAnnotationIndex);
        return Optional.of(retargetCommandDescriptor);
    }

}
