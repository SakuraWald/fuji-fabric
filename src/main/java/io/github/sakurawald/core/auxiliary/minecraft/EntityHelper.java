package io.github.sakurawald.core.auxiliary.minecraft;

import com.mojang.authlib.GameProfile;
import lombok.experimental.UtilityClass;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.UserCache;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@UtilityClass
public class EntityHelper {

    private static final String DIMENSION_NBT_KEY = "Dimension";

    public static boolean isRealPlayer(@NotNull ServerPlayerEntity player) {
        return player.getClass() == ServerPlayerEntity.class;
    }

    private static void applyPlayerDataNbt(ServerPlayerEntity player, @Nullable NbtCompound playerDataNbt) {
        if (playerDataNbt == null) return;

        /* apply saved dimension */
        if (playerDataNbt.contains(DIMENSION_NBT_KEY)) {
            String dimensionId = playerDataNbt.getString(DIMENSION_NBT_KEY);

            ServerWorld world = RegistryHelper.ofServerWorld(dimensionId);
            if (world != null) {
                player.setServerWorld(world);
            }
        }
    }

    public static ServerPlayerEntity loadOfflinePlayer(String playerName) {
        Optional<GameProfile> gameProfile = getGameProfileByName(playerName);
        if (gameProfile.isEmpty()) {
            throw new IllegalArgumentException("can't find player %s in usercache.json".formatted(playerName));
        }

        ServerPlayerEntity player = ServerHelper.getPlayerManager().createPlayer(gameProfile.get());

            /*
             the default dimension for ServerPlayerEntity instance is minecraft:overworld.
             in order to keep original dimension, here we should set dimension for the loaded player entity.
             */
        NbtCompound playerDataOpt = ServerHelper.getPlayerManager().loadPlayerData(player);
        applyPlayerDataNbt(player, playerDataOpt);
        return player;
    }

    private static Optional<GameProfile> getGameProfileByName(String playerName) {
        UserCache userCache = ServerHelper.getServer().getUserCache();
        if (userCache == null) return Optional.empty();

        return userCache.findByName(playerName);
    }
}
