package eu.omewillem.decoblocks.gui.library;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import eu.omewillem.decoblocks.DecoBlocks;
import eu.omewillem.decoblocks.managers.ConvertManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PageGUI {
    private final PaginatedGui gui;
    private final String category;
    public PageGUI(String category) {
        this.category = category;
        this.gui = Gui.paginated()
                .title(Component.text(category))
                .rows(3)
                .create();

        gui.setDefaultClickAction(event -> {
            event.setCancelled(true);
        });
        prepare();
        getBlocks();
    }

    public void open(Player player) {
        gui.open(player);
    }

    private void prepare() {
        gui.getFiller().fillBetweenPoints(3, 2, 3, 8, ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).asGuiItem());
        gui.setItem(18, ItemBuilder.from(Material.ARROW).setName("Previous").asGuiItem(event -> gui.previous()));
        gui.setItem(26, ItemBuilder.from(Material.ARROW).setName("Next").asGuiItem(event -> gui.next()));
        gui.setItem(22, ItemBuilder.from(Material.BARRIER).setName("Go back")
                .asGuiItem(event -> new ChooseGUI().open((Player) event.getWhoClicked())));
    }

    private void getBlocks() {
        DecoBlocks instance = DecoBlocks.getInstance();
        FileConfiguration libraryConfig = instance.getLibraryConfig().getConfig();
        ConfigurationSection categorySection = libraryConfig.getConfigurationSection("library." + category);
        if (categorySection == null) {
            Bukkit.getLogger().warning("Could not find the library category: " + category);
            return;
        }

        for (String key : categorySection.getKeys(false)) {
            ItemStack raw = instance.getConvertManager()
                    .convertMojang(categorySection.getString(key + ".texture"));

            GuiItem guiItem = ItemBuilder.from(raw)
                    .name(Component.text(categorySection.getString(key + ".name", "Unknown")))
                    .asGuiItem(event -> event.getWhoClicked().getInventory().addItem(event.getCurrentItem()));

            gui.addItem(guiItem);
        }
    }
}
