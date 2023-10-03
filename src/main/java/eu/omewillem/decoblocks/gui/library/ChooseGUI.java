package eu.omewillem.decoblocks.gui.library;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import eu.omewillem.decoblocks.DecoBlocks;
import eu.omewillem.decoblocks.managers.ConvertManager;
import eu.omewillem.decoblocks.utils.general.ItemUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class ChooseGUI {
    private final Gui gui;
    private final DecoBlocks instance;
    public ChooseGUI() {
        this.instance = DecoBlocks.getInstance();
        this.gui = Gui.gui()
                .title(Component.text("Choose a category!"))
                .rows(3)
                .create();

        gui.setDefaultClickAction(event -> {
            event.setCancelled(true);
        });
        gui.setOpenGuiAction(openEvent -> {
            if (!instance.getHistoryMap().containsKey(openEvent.getPlayer().getUniqueId())) return;
            GuiItem historyCategory = ItemBuilder
                    .from(Material.FILLED_MAP)
                    .name(Component.text("History"))
                    .asGuiItem(event -> {
                        new HistoryGUI().open((Player) event.getWhoClicked());
                    });
            gui.updateItem(26, historyCategory);
        });
        prepare();
    }

    public void open(Player player) {
        gui.open(player);
    }

    private void prepare() {
        FileConfiguration libraryConfig = instance.getLibraryConfig().getConfig();

        ConvertManager convertManager = instance.getConvertManager();
        GuiItem lettersCategory = ItemBuilder
                .from(convertManager.convertMojang(libraryConfig.getString("library.gui.letters")))
                .name(Component.text("Letters (Alphabet)"))
                .asGuiItem(event -> {
                    new PageGUI("letters").open((Player) event.getWhoClicked());
                });
        GuiItem furnitureCategory = ItemBuilder
                .from(convertManager.convertMojang(libraryConfig.getString("library.gui.furniture")))
                .name(Component.text("Furniture"))
                .asGuiItem(event -> {
                    new PageGUI("furniture").open((Player) event.getWhoClicked());
                });
        GuiItem blocksCategory = ItemBuilder
                .from(convertManager.convertMojang(libraryConfig.getString("library.gui.blocks")))
                .name(Component.text("Blocks"))
                .asGuiItem(event -> {
                    new PageGUI("blocks").open((Player) event.getWhoClicked());
                });
        GuiItem historyCategory = ItemBuilder
                .from(Material.MAP)
                .name(Component.text("History"))
                .asGuiItem(event -> {
                    new HistoryGUI().open((Player) event.getWhoClicked());
                });

        gui.setItem(11, lettersCategory);
        gui.setItem(13, furnitureCategory);
        gui.setItem(15, blocksCategory);
        gui.setItem(26, historyCategory);
    }
}
