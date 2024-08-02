package io.github.sakurawald.command.argument.adapter.impl;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.github.sakurawald.command.argument.adapter.interfaces.AbstractArgumentTypeAdapter;
import net.minecraft.server.command.ServerCommandSource;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

@SuppressWarnings("unused")
public class BooleanArgumentTypeAdapter extends AbstractArgumentTypeAdapter {
    @Override
    public boolean match(Type type) {
        return  boolean.class.equals(type) || Boolean.class.equals(type);
    }

    @Override
    protected ArgumentType<?> makeArgumentType() {
        return BoolArgumentType.bool();
    }

    @Override
    public Object makeArgumentObject(CommandContext<ServerCommandSource> context, Parameter parameter) {
        return BoolArgumentType.getBool(context, parameter.getName());
    }
}
