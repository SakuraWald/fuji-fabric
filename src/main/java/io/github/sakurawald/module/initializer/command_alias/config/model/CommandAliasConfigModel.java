package io.github.sakurawald.module.initializer.command_alias.config.model;

import io.github.sakurawald.core.structure.CommandPathMappingEntry;

import java.util.ArrayList;
import java.util.List;

public class CommandAliasConfigModel {
    public List<CommandPathMappingEntry> alias = new ArrayList<>() {
        {
            this.add(new CommandPathMappingEntry(List.of("r"), List.of("reply")));
            this.add(new CommandPathMappingEntry(List.of("sudo"), List.of("run", "as", "fake-op")));
            this.add(new CommandPathMappingEntry(List.of("i", "want", "to", "modify", "chat"), List.of("chat", "format")));
        }
    };
}
