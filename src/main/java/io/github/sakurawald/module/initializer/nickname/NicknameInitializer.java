package io.github.sakurawald.module.initializer.nickname;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.github.sakurawald.config.handler.ConfigHandler;
import io.github.sakurawald.config.handler.ObjectConfigHandler;
import io.github.sakurawald.config.model.NicknameModel;
import io.github.sakurawald.module.initializer.ModuleInitializer;
import io.github.sakurawald.util.minecraft.CommandHelper;
import io.github.sakurawald.util.minecraft.MessageHelper;
import lombok.Getter;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class NicknameInitializer extends ModuleInitializer {

    @Getter
    private static final ConfigHandler<NicknameModel> nicknameHandler = new ObjectConfigHandler<>("nickname.json", NicknameModel.class);

    @Override
    public void onInitialize() {
        nicknameHandler.loadFromDisk();
    }

    @Override
    public void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(
                literal("nickname")
                        .then(literal("reset")
                                .executes(this::$reset)
                        )
                        .then(literal("set")
                                .then(argument("format", StringArgumentType.greedyString())
                                        .executes(this::$set)
                                )
                        )
        );
    }

    private int $set(CommandContext<ServerCommandSource> ctx) {
        return CommandHelper.playerOnlyCommand(ctx, (player) -> {
            String name = player.getGameProfile().getName();
            String format = StringArgumentType.getString(ctx, "format");
            nicknameHandler.model().format.player2format.put(name, format);
            nicknameHandler.saveToDisk();

            MessageHelper.sendMessage(player,"nickname.set");
            return Command.SINGLE_SUCCESS;
        });
    }

    private int $reset(CommandContext<ServerCommandSource> ctx) {
        return CommandHelper.playerOnlyCommand(ctx, (player) -> {
            String name = player.getGameProfile().getName();
            nicknameHandler.model().format.player2format.remove(name);
            nicknameHandler.saveToDisk();

            MessageHelper.sendMessage(player,"nickname.unset");
            return Command.SINGLE_SUCCESS;
        });
    }
}
