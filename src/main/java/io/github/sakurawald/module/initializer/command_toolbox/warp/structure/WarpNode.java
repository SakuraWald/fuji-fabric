package io.github.sakurawald.module.initializer.command_toolbox.warp.structure;

import io.github.sakurawald.core.structure.SpatialPose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarpNode {

    public SpatialPose position;

    public String name = "<blue>Display Name";
    public String item = "minecraft:painting";
    public List<String> lore = new ArrayList<>();

    public WarpNode(SpatialPose position) {
        this.position = position;
    }
}
