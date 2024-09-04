package io.github.sakurawald.module.mixin.common.low_level.accessor;

import io.github.sakurawald.core.accessor.UserCacheAccessor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import net.minecraft.util.UserCache;

@Mixin(UserCache.class)
public class UserCacheMixin implements UserCacheAccessor {

    @Final
    @Shadow
    private Map<String, UserCache.Entry> byName;

    @Override
    public @NotNull Collection<String> fuji$getNames() {
        ArrayList<String> ret = new ArrayList<>();
        byName.values().forEach(o -> ret.add(o.getProfile().getName()));
        return ret;
    }
}
