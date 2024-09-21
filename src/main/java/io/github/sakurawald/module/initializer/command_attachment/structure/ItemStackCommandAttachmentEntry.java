package io.github.sakurawald.module.initializer.command_attachment.structure;

import io.github.sakurawald.module.initializer.command_attachment.command.argument.wrapper.ExecuteAsType;
import io.github.sakurawald.module.initializer.command_attachment.command.argument.wrapper.InteractType;
import lombok.Getter;

@Getter
public class ItemStackCommandAttachmentEntry extends CommandAttachmentEntry {
    public boolean destroyItem;

    public ItemStackCommandAttachmentEntry(String command, InteractType interactType, ExecuteAsType executeAsType, int maxUseTimes, int useTimes, boolean destroyItem) {
        super(CommandAttackmentType.ITEMSTACK,command, interactType, executeAsType, maxUseTimes, useTimes);
        this.destroyItem = destroyItem;
    }
}
