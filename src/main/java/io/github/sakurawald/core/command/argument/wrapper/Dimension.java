package io.github.sakurawald.core.command.argument.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.server.world.ServerWorld;

@Data
@AllArgsConstructor
public class Dimension {
    ServerWorld world;
}
