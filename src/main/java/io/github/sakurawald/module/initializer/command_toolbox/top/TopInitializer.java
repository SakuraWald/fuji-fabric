package io.github.sakurawald.module.initializer.command_toolbox.top;

import io.github.sakurawald.core.annotation.Document;
import io.github.sakurawald.core.auxiliary.minecraft.CommandHelper;
import io.github.sakurawald.core.auxiliary.minecraft.TextHelper;
import io.github.sakurawald.core.command.annotation.CommandNode;
import io.github.sakurawald.core.command.annotation.CommandSource;
import io.github.sakurawald.core.command.annotation.CommandTarget;
import io.github.sakurawald.core.structure.SpatialPose;
import io.github.sakurawald.module.initializer.ModuleInitializer;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Set;

public class TopInitializer extends ModuleInitializer {

    @CommandNode("top")
    @Document("Teleport to the top of your current position.")
    private static int top(@CommandSource @CommandTarget ServerPlayerEntity player) {
        World world = player.getWorld();
        BlockPos topPosition = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, player.getBlockPos());

        Set<PositionFlag> flags = EnumSet.noneOf(PositionFlag.class);
        SpatialPose spatialPose = SpatialPose.of(player).withY(topPosition.getY());
        spatialPose.teleport(player,flags);

        TextHelper.sendMessageByKey(player, "top");
        return CommandHelper.Return.SUCCESS;
    }

}
