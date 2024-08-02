package io.github.sakurawald.module.initializer.command_toolbox.nickname;

import io.github.sakurawald.command.argument.wrapper.GreedyString;
import io.github.sakurawald.command.annotation.Command;
import io.github.sakurawald.command.annotation.CommandSource;
import io.github.sakurawald.config.handler.interfaces.ConfigHandler;
import io.github.sakurawald.config.handler.ObjectConfigHandler;
import io.github.sakurawald.module.initializer.command_toolbox.nickname.model.NicknameModel;
import io.github.sakurawald.module.initializer.ModuleInitializer;
import io.github.sakurawald.util.minecraft.CommandHelper;
import io.github.sakurawald.util.minecraft.MessageHelper;
import lombok.Getter;
import net.minecraft.server.network.ServerPlayerEntity;

@Command("nickname")
public class NicknameInitializer extends ModuleInitializer {

    @Getter
    private static final ConfigHandler<NicknameModel> nicknameHandler = new ObjectConfigHandler<>("nickname.json", NicknameModel.class);

    @Override
    public void onInitialize() {
        nicknameHandler.loadFromDisk();
    }

    @Command("set")
    private int $set(@CommandSource ServerPlayerEntity player, GreedyString format) {
            String name = player.getGameProfile().getName();
            nicknameHandler.model().format.player2format.put(name, format.getString());
            nicknameHandler.saveToDisk();

            MessageHelper.sendMessage(player, "nickname.set");
            return CommandHelper.Return.SUCCESS;
    }

    @Command("reset")
    private int $reset(@CommandSource ServerPlayerEntity player) {
        String name = player.getGameProfile().getName();
        nicknameHandler.model().format.player2format.remove(name);
        nicknameHandler.saveToDisk();

        MessageHelper.sendMessage(player, "nickname.unset");
        return CommandHelper.Return.SUCCESS;
    }
}
