package io.github.sakurawald.module.initializer.head.gui;

import io.github.sakurawald.core.auxiliary.minecraft.MessageHelper;
import io.github.sakurawald.core.gui.InputSignGui;
import io.github.sakurawald.module.initializer.head.privoder.HeadProvider;
import io.github.sakurawald.module.initializer.head.structure.Head;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class SearchHeadsInputGui extends InputSignGui {

    private final @NotNull HeadGui parentGui;

    public SearchHeadsInputGui(@NotNull HeadGui parentGui) {
        super(parentGui.player);
        this.parentGui = parentGui;
    }

    @Override
    public void onClose() {
        String keywords = reduceInputOrEmpty();

        if (keywords.isEmpty()) {
            parentGui.open();
            return;
        }

        List<Head> entities = HeadProvider.getHeads().values().stream()
                .filter(head -> head.name.toLowerCase().contains(keywords.toLowerCase())
                        || head.getTagsOrEmpty().toLowerCase().contains(keywords.toLowerCase()))
                .collect(Collectors.toList());

        Text title = MessageHelper.ofText(player, "gui.search.title", keywords);
        new PagedHeadGui(this.parentGui, player, title, entities, 0).open();
    }
}
