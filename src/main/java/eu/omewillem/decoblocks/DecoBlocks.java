package eu.omewillem.decoblocks;

import co.aikar.commands.PaperCommandManager;
import com.jeff_media.customblockdata.CustomBlockData;
import eu.omewillem.decoblocks.commands.DecoCommand;
import eu.omewillem.decoblocks.listeners.block.BreakListener;
import eu.omewillem.decoblocks.listeners.block.PlaceListener;
import eu.omewillem.decoblocks.managers.BlockManager;
import eu.omewillem.decoblocks.managers.ConvertManager;
import eu.omewillem.decoblocks.utils.Metrics;
import eu.omewillem.decoblocks.utils.files.PluginConfig;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class DecoBlocks extends JavaPlugin {

    private static @Getter DecoBlocks instance;
    private @Getter PaperCommandManager commandManager;
    private @Getter BlockManager blockManager;
    private @Getter ConvertManager convertManager;

    // keys
    private @Getter NamespacedKey blockKey;
    private @Getter NamespacedKey uuidKey;

    // configs
    private @Getter PluginConfig libraryConfig;

    // storage shit ig
    private @Getter Map<UUID, ArrayList<ItemStack>> historyMap;


    @Override
    public void onEnable() {
        new Metrics(this, 19946);
        instance = this;
        CustomBlockData.registerListener(this);

        libraryConfig = new PluginConfig(this, "library.yml", true);

        this.commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new DecoCommand());

        this.blockManager = new BlockManager(this);
        this.convertManager = new ConvertManager(this);

        getServer().getPluginManager().registerEvents(new PlaceListener(), this);
        getServer().getPluginManager().registerEvents(new BreakListener(), this);

        this.blockKey = new NamespacedKey(this, "block");
        this.uuidKey = new NamespacedKey(this, "uuid");

        this.historyMap = new HashMap<>();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
