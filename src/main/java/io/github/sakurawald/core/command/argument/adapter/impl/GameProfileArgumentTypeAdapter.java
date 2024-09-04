package io.github.sakurawald.core.command.argument.adapter.impl;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.github.sakurawald.core.command.argument.adapter.abst.BaseArgumentTypeAdapter;
import io.github.sakurawald.core.command.argument.wrapper.GameProfileCollection;
import lombok.SneakyThrows;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.server.command.ServerCommandSource;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

@SuppressWarnings("unused")
public class GameProfileArgumentTypeAdapter extends BaseArgumentTypeAdapter {

    @Override
    public boolean match(Type type) {
        return GameProfileCollection.class.equals(type);
    }

    @Override
    protected ArgumentType<?> makeArgumentType() {
        return GameProfileArgumentType.gameProfile();
    }

    @SneakyThrows
    @Override
    public Object makeArgumentObject(CommandContext<ServerCommandSource> context, Parameter parameter) {
        return new GameProfileCollection(GameProfileArgumentType.getProfileArgument(context,parameter.getName()));
    }
}
