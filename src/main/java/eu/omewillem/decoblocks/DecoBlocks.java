package eu.omewillem.decoblocks;

import co.aikar.commands.PaperCommandManager;
import eu.omewillem.decoblocks.commands.DecoCommand;
import eu.omewillem.decoblocks.listeners.block.*;
import eu.omewillem.decoblocks.listeners.block.placers.DecoyPlaceListener;
import eu.omewillem.decoblocks.listeners.block.placers.PlaceListener;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class DecoBlocks extends JavaPlugin {

    private static @Getter DecoBlocks instance;
    private @Getter PaperCommandManager commandManager;

    // keys
    private @Getter NamespacedKey blockKey;
    private @Getter NamespacedKey uuidKey;
    private @Getter NamespacedKey decoyKey;

    @Override
    public void onEnable() {
        instance = this;

        this.commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new DecoCommand());

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
