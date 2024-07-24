package io.github.sakurawald.module.initializer.command_toolbox.bed;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import io.github.sakurawald.Fuji;
import io.github.sakurawald.module.initializer.ModuleInitializer;
import io.github.sakurawald.util.minecraft.CommandHelper;
import io.github.sakurawald.util.minecraft.MessageHelper;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class BedInitializer extends ModuleInitializer {

    @Override
    public void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("bed").executes(this::$bed));
    }

    private int $bed(CommandContext<ServerCommandSource> ctx) {
        return CommandHelper.playerOnlyCommand(ctx, (player) -> {
            BlockPos respawnPosition = player.getSpawnPointPosition();
            RegistryKey<World> respawnDimension = player.getSpawnPointDimension();
            ServerWorld serverLevel = Fuji.SERVER.getWorld(respawnDimension);
            if (respawnPosition == null || serverLevel == null) {
                MessageHelper.sendMessage(player, "bed.not_found");
                return Command.SINGLE_SUCCESS;
            }

            player.teleport(serverLevel, respawnPosition.getX(), respawnPosition.getY(), respawnPosition.getZ(), player.getYaw(), player.getPitch());
            MessageHelper.sendMessage(player, "bed.success");
            return Command.SINGLE_SUCCESS;
        });
    }
}
