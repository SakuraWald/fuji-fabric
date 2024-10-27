package io.github.sakurawald.module.mixin.chat.replace;

import io.github.sakurawald.core.auxiliary.LogUtil;
import io.github.sakurawald.module.initializer.chat.replace.model.ChatReplaceInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = ServerPlayNetworkHandler.class, priority = 1000 + 1000)
public abstract class ServerPlayNetworkHandlerMixin {

    @Shadow
    public ServerPlayerEntity player;

    @Unique
    private Text replaceDisplayText(PlayerEntity player, Text original) {
        Text newValue = ChatReplaceInitializer.rewriteChatText(player, original);
        LogUtil.debug("replace chat text: old = {}, new = {}", original, newValue);
        return newValue;
    }

    @ModifyVariable(method = "sendChatMessage", at = @At(value = "HEAD"), argsOnly = true)
    public SignedMessage modifyChatMessageSentByPlayers(SignedMessage original) {
        Text newText = replaceDisplayText(player, original.getContent());
        return original.withUnsignedContent(newText);
    }

    /* some chat-related mods will encode the content into the sender, or vice versa.
     * For this reason, we have to parse the display text twice.
     */
    @ModifyVariable(method = "sendChatMessage", at = @At(value = "HEAD"), argsOnly = true)
    public MessageType.Parameters modifyChatMessageSentByPlayers(MessageType.Parameters original) {
        Text newText = replaceDisplayText(player, original.comp_920());
        return new MessageType.Parameters(original.type(), newText, original.comp_921());
    }
}
