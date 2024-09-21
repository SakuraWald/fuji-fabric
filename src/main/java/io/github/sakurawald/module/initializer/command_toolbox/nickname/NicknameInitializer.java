package io.github.sakurawald.module.initializer.command_toolbox.nickname;

import io.github.sakurawald.core.auxiliary.minecraft.CommandHelper;
import io.github.sakurawald.core.auxiliary.minecraft.LocaleHelper;
import io.github.sakurawald.core.command.annotation.CommandNode;
import io.github.sakurawald.core.command.annotation.CommandSource;
import io.github.sakurawald.core.command.argument.wrapper.impl.GreedyString;
import io.github.sakurawald.core.config.handler.abst.BaseConfigurationHandler;
import io.github.sakurawald.core.config.handler.impl.ObjectConfigurationHandler;
import io.github.sakurawald.core.config.transformer.impl.MoveFileIntoModuleConfigDirectoryTransformer;
import io.github.sakurawald.module.initializer.ModuleInitializer;
import io.github.sakurawald.module.initializer.command_toolbox.nickname.config.model.NicknameModel;
import lombok.Getter;
import net.minecraft.server.network.ServerPlayerEntity;

@CommandNode("nickname")
public class NicknameInitializer extends ModuleInitializer {

    @Getter
    private static final BaseConfigurationHandler<NicknameModel> nicknameHandler = new ObjectConfigurationHandler<>("nickname.json", NicknameModel.class)
        .addTransformer(new MoveFileIntoModuleConfigDirectoryTransformer(NicknameInitializer.class));

    @CommandNode("set")
    private int $set(@CommandSource ServerPlayerEntity player, GreedyString format) {
            String name = player.getGameProfile().getName();
            nicknameHandler.getModel().format.player2format.put(name, format.getValue());
            nicknameHandler.writeStorage();

            LocaleHelper.sendMessageByKey(player, "nickname.set");
            return CommandHelper.Return.SUCCESS;
    }

    @CommandNode("reset")
    private int $reset(@CommandSource ServerPlayerEntity player) {
        String name = player.getGameProfile().getName();
        nicknameHandler.getModel().format.player2format.remove(name);
        nicknameHandler.writeStorage();

        LocaleHelper.sendMessageByKey(player, "nickname.unset");
        return CommandHelper.Return.SUCCESS;
    }
}
