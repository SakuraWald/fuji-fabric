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

    private static CommandRequirementDescriptor deriveCommandRequirementForInsertPoint(List<Argument> arguments, int insertIndex) {
        /* try to copy the requirement from next level */
        if (insertIndex < arguments.size()) {
            return arguments.get(insertIndex).getRequirement();
        }

        /* try to copy the requirement from prev level */
        return arguments.getLast().getRequirement();
    }

    private static List<Argument> insertOthersArguments(List<Argument> arguments) {
        List<Argument> ret = new ArrayList<>(arguments);

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

            int totalValue = CommandHelper.Return.SUCCESS;
            for (ServerPlayerEntity target : targets.getValue()) {

                List<Object> unboxedArgs = args.subList(1, args.size());
                unboxedArgs.set(this.commandTargetAnnotationIndex, target);

                LogUtil.debug("invoke command method {} in class {}: target = {}, args = {}"
                    , this.method.getName()
                    , this.method.getDeclaringClass().getSimpleName()
                    , target.getGameProfile().getName()
                    , unboxedArgs);

                try {
                    // if one of the execution if failed, then it's considered the whole return value is failed.
                    int singleValue = (int) this.method.invoke(null, unboxedArgs.toArray());

                    if (singleValue != CommandHelper.Return.SUCCESS) {
                        totalValue = CommandHelper.Return.FAIL;
                    }

                } catch (Exception wrappedOrUnwrappedException) {
                    return handleException(ctx, this.method, wrappedOrUnwrappedException);
                }
            }

            return totalValue;
        };
    }

    public static Optional<RetargetCommandDescriptor> make(CommandDescriptor commandDescriptor) {

        /* filter */
        Optional<Integer> commandTargetAnnotationIndexOpt = findCommandTargetAnnotationIndex(commandDescriptor.method);
        if (commandTargetAnnotationIndexOpt.isEmpty()) {
            return Optional.empty();
        }
        int commandTargetAnnotationIndex = commandTargetAnnotationIndexOpt.get();

        /* make retarget command descriptor */
        Method delegateMethod = commandDescriptor.getMethod();
        List<Argument> argsWithOthers = insertOthersArguments(commandDescriptor.getArguments());

        RetargetCommandDescriptor retargetCommandDescriptor = new RetargetCommandDescriptor(delegateMethod, argsWithOthers, commandTargetAnnotationIndex);

        return Optional.of(retargetCommandDescriptor);
    }

}
