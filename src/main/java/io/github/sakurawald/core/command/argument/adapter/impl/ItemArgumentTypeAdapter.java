package io.github.sakurawald.core.command.argument.adapter.impl;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.github.sakurawald.core.command.argument.adapter.abst.BaseArgumentTypeAdapter;
import io.github.sakurawald.core.command.argument.structure.Argument;
import io.github.sakurawald.core.command.processor.CommandAnnotationProcessor;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.item.Item;
import net.minecraft.server.command.ServerCommandSource;

import java.lang.reflect.Type;

public class ItemArgumentTypeAdapter extends BaseArgumentTypeAdapter {
    @Override
    public boolean match(Type type) {
        return Item.class.equals(type);
    }

    @Override
    protected ArgumentType<?> makeArgumentType() {
        return ItemStackArgumentType.itemStack(CommandAnnotationProcessor.getRegistryAccess());
    }

    @Override
    public Object makeArgumentObject(CommandContext<ServerCommandSource> context, Argument argument) {
        return ItemStackArgumentType.getItemStackArgument(context, argument.getArgumentName()).getItem();
    }
}
