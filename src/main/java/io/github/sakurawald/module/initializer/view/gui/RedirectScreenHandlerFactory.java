package io.github.sakurawald.module.initializer.view.gui;

import com.mojang.authlib.GameProfile;
import io.github.sakurawald.core.auxiliary.minecraft.GuiHelper;
import io.github.sakurawald.core.auxiliary.minecraft.RegistryHelper;
import io.github.sakurawald.core.auxiliary.minecraft.ServerHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.c2s.common.SyncedClientOptions;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class RedirectScreenHandlerFactory {

    private static final String DIMENSION = "Dimension";

    private final String targetPlayerName;
    private final Text title;

    private boolean onlineEditMode;
    private ServerPlayerEntity targetPlayer;

    protected ServerPlayerEntity getTargetPlayer() {
        return this.targetPlayer;
    }

    public RedirectScreenHandlerFactory(String targetPlayerName, Text title) {
        this.targetPlayerName = targetPlayerName;
        this.title = title;

        // load
        this.loadTargetPlayer();
    }

    private void setDimension(ServerPlayerEntity player, @Nullable NbtCompound root) {
        if (root == null) return;
        if (root.contains(DIMENSION)) {
            String dimensionId = root.getString(DIMENSION);
            ServerWorld world = RegistryHelper.ofServerWorld(Identifier.of(dimensionId));
            if (world != null) {
                player.setServerWorld(world);
            }
        }
    }

    private void loadTargetPlayer() {
        ServerPlayerEntity player = ServerHelper.getDefaultServer().getPlayerManager().getPlayer(targetPlayerName);
        if (player != null) {
            onlineEditMode = true;
            targetPlayer = player;
        } else {
            Optional<GameProfile> gameProfile = ServerHelper.getGameProfileByName(targetPlayerName);
            if (gameProfile.isEmpty()) {
                throw new IllegalArgumentException("Can't find player %s in usercache.json".formatted(targetPlayerName));
            }

            targetPlayer = ServerHelper.getPlayerManager().createPlayer(gameProfile.get(), SyncedClientOptions.createDefault());

            /*
             the default dimension for ServerPlayerEntity instance if minecraft:overworld.
             in order to keep original dimension after modify inv, here we should set dimension for the loaded player entity.
             */
            Optional<NbtCompound> playerDataOpt = ServerHelper.getPlayerManager().loadPlayerData(targetPlayer);
            setDimension(targetPlayer, playerDataOpt.orElse(null));
        }
    }

    // the redirect will invalid if online-offline or offline-online.
    private boolean isRedirectValid() {
        if (onlineEditMode) {
            return !targetPlayer.isRemoved();
        }
        return !ServerHelper.isPlayerOnline(targetPlayerName);
    }

    protected abstract Inventory getTargetInventory();

    protected abstract ScreenHandlerType<GenericContainerScreenHandler> getTargetInventorySize();

    protected abstract boolean canClick(int i);

    private void savePlayerData() {
        ServerHelper.getDefaultServer().saveHandler.savePlayerData(targetPlayer);
    }

    private GenericContainerScreenHandler makeGenericContainerScreenHandler(int syncId, PlayerInventory sourceInventory, PlayerEntity source) {
        int rows = GuiHelper.getRows(getTargetInventorySize());

        return new GenericContainerScreenHandler(getTargetInventorySize(), syncId, sourceInventory, getTargetInventory(), rows) {

            @Override
            public void onSlotClick(int i, int j, SlotActionType slotActionType, PlayerEntity playerEntity) {
                if (!canClick(i)) return;

                // save player data in time, in keep sync if player gets online.
                if (!onlineEditMode) {
                    savePlayerData();
                }
                super.onSlotClick(i, j, slotActionType, playerEntity);
            }

            @Override
            public boolean canUse(PlayerEntity playerEntity) {
                return isRedirectValid();
            }
        };
    }

    public SimpleNamedScreenHandlerFactory makeFactory() {
        return new SimpleNamedScreenHandlerFactory(
                this::makeGenericContainerScreenHandler, this.title);
    }

}
