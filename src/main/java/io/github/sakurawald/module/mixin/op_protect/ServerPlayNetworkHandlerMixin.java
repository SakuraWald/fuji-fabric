package io.github.sakurawald.module.mixin.op_protect;


import io.github.sakurawald.Fuji;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.DisconnectionInfo;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

    @Shadow
    public ServerPlayerEntity player;

    @Inject(at = @At(value = "HEAD"), method = "onDisconnected")
    private void $onDisconnected(DisconnectionInfo disconnectionInfo, CallbackInfo ci) {
        if (Fuji.SERVER.getPlayerManager().isOperator(player.getGameProfile())
        && !FabricLoader.getInstance().isDevelopmentEnvironment()) {
            Fuji.LOGGER.info("op protect -> deop {}", player.getGameProfile().getName());
            Fuji.SERVER.getPlayerManager().removeFromOperators(player.getGameProfile());
        }
    }
}
