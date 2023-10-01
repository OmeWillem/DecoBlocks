package eu.omewillem.decoblocks;

import co.aikar.commands.PaperCommandManager;
import eu.omewillem.decoblocks.commands.DecoCommand;
import eu.omewillem.decoblocks.listeners.block.BreakListener;
import eu.omewillem.decoblocks.listeners.block.placers.DecoyPlaceListener;
import eu.omewillem.decoblocks.listeners.block.placers.PlaceListener;
import eu.omewillem.decoblocks.managers.BlockManager;
import eu.omewillem.decoblocks.managers.ConvertManager;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class DecoBlocks extends JavaPlugin {

    private static @Getter DecoBlocks instance;
    private @Getter PaperCommandManager commandManager;
    private @Getter BlockManager blockManager;
    private @Getter ConvertManager convertManager;

    // keys
    private @Getter NamespacedKey blockKey;
    private @Getter NamespacedKey uuidKey;
    private @Getter NamespacedKey decoyKey;

    @Override
    public void onEnable() {
        instance = this;

        this.commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new DecoCommand());

        this.blockManager = new BlockManager(this);
        this.convertManager = new ConvertManager(this);

        getServer().getPluginManager().registerEvents(new PlaceListener(), this);
        getServer().getPluginManager().registerEvents(new DecoyPlaceListener(), this);

        getServer().getPluginManager().registerEvents(new BreakListener(), this);

        this.blockKey = new NamespacedKey(this, "block");
        this.uuidKey = new NamespacedKey(this, "uuid");
        this.decoyKey = new NamespacedKey(this, "decoy");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
