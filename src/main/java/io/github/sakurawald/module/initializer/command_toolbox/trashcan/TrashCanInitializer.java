package io.github.sakurawald.module.initializer.command_toolbox.trashcan;

import io.github.sakurawald.core.auxiliary.minecraft.CommandHelper;
import io.github.sakurawald.core.auxiliary.minecraft.LocaleHelper;
import io.github.sakurawald.core.command.annotation.CommandNode;
import io.github.sakurawald.core.command.annotation.CommandSource;
import io.github.sakurawald.module.initializer.ModuleInitializer;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;

public class TrashCanInitializer extends ModuleInitializer {

    @CommandNode("trashcan")
    private static int $trashcan(@CommandSource ServerPlayerEntity player) {
        int rows = 3;
        SimpleInventory simpleInventory = new SimpleInventory(rows * 9);

        player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, inventory, p) -> new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X3, i, inventory, simpleInventory, rows), LocaleHelper.getTextByKey(player, "trashcan.gui.title")));
        player.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
        return CommandHelper.Return.SUCCESS;
    }
}
