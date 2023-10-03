package eu.omewillem.decoblocks.gui.library;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import eu.omewillem.decoblocks.DecoBlocks;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class HistoryGUI {
    private final PaginatedGui gui;
    public HistoryGUI() {
        this.gui = Gui.paginated()
                .title(Component.text("History"))
                .rows(3)
                .create();

        gui.setDefaultClickAction(event -> {
            event.setCancelled(true);
        });
        gui.setOpenGuiAction(event -> {
            setHistory((Player) event.getPlayer());
        });
        prepare();
    }

    public void open(Player player) {
        gui.open(player);
    }

    private void prepare() {
        gui.getFiller().fillBetweenPoints(1, 1, 1, 9, ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).asGuiItem());
        gui.getFiller().fillBetweenPoints(3, 1, 3, 9, ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(18, ItemBuilder.from(Material.ARROW).setName("Previous").asGuiItem(event -> gui.previous()));
        gui.setItem(26, ItemBuilder.from(Material.ARROW).setName("Next").asGuiItem(event -> gui.next()));
        gui.setItem(22, ItemBuilder.from(Material.BARRIER).setName("Go back")
                .asGuiItem(event -> new ChooseGUI().open((Player) event.getWhoClicked())));
    }

    private void setHistory(Player player) {
        Map<UUID, ArrayList<ItemStack>> historyMap = DecoBlocks.getInstance().getHistoryMap();
        if (!historyMap.containsKey(player.getUniqueId())) return;
        List<ItemStack> stacks = historyMap.get(player.getUniqueId());
        Collections.reverse(stacks);
        stacks.forEach(stack -> gui.addItem(ItemBuilder.from(stack).asGuiItem(event -> event.getWhoClicked().getInventory().addItem(stack))));
        gui.update();
    }

}
