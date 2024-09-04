package io.github.sakurawald.module.mixin.common.low_level.accessor;

import io.github.sakurawald.core.accessor.PlayerCombatStateAccessor;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements PlayerCombatStateAccessor {

    @Unique
    public boolean fuji$inCombat;

    @Inject(method = "enterCombat", at = @At("RETURN"))
    public void $enterCombat(CallbackInfo ci) {
        fuji$inCombat = true;
    }

    @Inject(method = "endCombat", at = @At("RETURN"))
    public void $leaveCombat(CallbackInfo ci) {
        fuji$inCombat = false;
    }

    @Override
    public boolean fuji$inCombat() {
        return fuji$inCombat;
    }

}
