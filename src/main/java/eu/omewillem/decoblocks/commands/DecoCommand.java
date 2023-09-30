package eu.omewillem.decoblocks.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import eu.omewillem.decoblocks.DecoBlocks;
import eu.omewillem.decoblocks.utils.ColourUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

@CommandAlias("decoblocks|deco")
@CommandPermission("decoblocks.command")
public class DecoCommand extends BaseCommand {

    @HelpCommand
    @Default
    public void onDefault(CommandSender sender) {
        sender.sendMessage(ColourUtils.parse("&2/" + "decoblocks" + " <subcommand> <arg>..."));
        getSubCommands().forEach((string, registeredCommand) -> {
            if (registeredCommand.getSyntaxText() == null || registeredCommand.getSyntaxText().isEmpty() || registeredCommand.getSyntaxText().equals(""))
                return;
            sender.sendMessage(ColourUtils.parse("&a/" + "decoblocks" + " " + registeredCommand.getSyntaxText()));
        });
    }

    @Subcommand("convert")
    @CommandPermission("decoblocks.convert")
    @CommandAlias("c|get")
    @Syntax("convert <texture,mojang,nothing>")
    public void onConvert(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!item.getType().equals(Material.PLAYER_WALL_HEAD) && !item.getType().equals(Material.PLAYER_HEAD)) {
            player.sendMessage("You must hold a player skull.");
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();
        NamespacedKey blockKey = DecoBlocks.getInstance().getBlockKey();

        if (persistentDataContainer.has(blockKey, PersistentDataType.BOOLEAN)) {
            player.sendMessage("This is already a Deco Block");
            return;
        }

        persistentDataContainer.set(blockKey, PersistentDataType.BOOLEAN, true);
        meta.setLore(List.of(ColourUtils.parse("&7This is a DecoBlocks block.")));

        item.setItemMeta(meta);

        player.sendMessage("Succesfully converted your skull into a deco block!");
    }

    @Subcommand("decoy")
    @CommandPermission("decoblocks.decoy")
    @CommandAlias("fake")
    @Syntax("decoy")
    public void onDecoy(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();
        NamespacedKey decoyKey = DecoBlocks.getInstance().getDecoyKey();

        if (persistentDataContainer.has(decoyKey, PersistentDataType.BOOLEAN)) {
            player.sendMessage("This is already a Decoy Block");
            return;
        }

        persistentDataContainer.set(decoyKey, PersistentDataType.BOOLEAN, true);
        meta.setLore(List.of(ColourUtils.parse("&2THIS IS A DECOY BLOCK USED WITH DECOBLOCKS!")));

        item.setItemMeta(meta);

        player.sendMessage("Succesfully converted your block into a decoy block!");
    }

}
