package eu.omewillem.decoblocks.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import eu.omewillem.decoblocks.DecoBlocks;
import eu.omewillem.decoblocks.utils.Utils;
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
        sender.sendMessage(Utils.parse("&2/" + "decoblocks" + " <subcommand> <arg>..."));
        getSubCommands().forEach((string, registeredCommand) -> {
            if (registeredCommand.getSyntaxText() == null || registeredCommand.getSyntaxText().isEmpty() || registeredCommand.getSyntaxText().equals(""))
                return;
            sender.sendMessage(Utils.parse("&a/" + "decoblocks" + " " + registeredCommand.getSyntaxText()));
        });
    }

    @Subcommand("convert")
    @CommandPermission("decoblocks.convert")
    @Syntax("convert <mojang,nothing/skull_in_hand>")
    public void onConvert(Player player, @Optional String url) {
        if (url == null) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (!Utils.isPlayerHeadBlock(item.getType())) {
                player.sendMessage("You must hold a player skull.");
                return;
            }

            ItemStack convertedItem = DecoBlocks.getInstance().getConvertManager().convertSkull(item);
            if (convertedItem == null) {
                player.sendMessage("Something went wrong!");
                return;
            }

            player.getInventory().setItemInMainHand(convertedItem);
        } else {
            ItemStack convertedItem = DecoBlocks.getInstance().getConvertManager().convertMojang(url);
            if (convertedItem == null) {
                player.sendMessage("Something went wrong!");
            }

            player.getInventory().addItem(convertedItem);
        }

        player.sendMessage("Succesfully converted!");
    }

    @Subcommand("decoy")
    @CommandPermission("decoblocks.decoy")
    @Syntax("decoy")
    public void onDecoy(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemStack convertedItem = DecoBlocks.getInstance().getConvertManager().convertDecoy(item);

        if (convertedItem == null) {
            player.sendMessage("Something went wrong!");
            return;
        }

        player.getInventory().setItemInMainHand(convertedItem);
        player.sendMessage("Succesfully converted your block into a decoy block!");
    }

}
