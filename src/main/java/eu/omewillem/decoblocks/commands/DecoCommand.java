package eu.omewillem.decoblocks.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import eu.omewillem.decoblocks.DecoBlocks;
import eu.omewillem.decoblocks.gui.library.ChooseGUI;
import eu.omewillem.decoblocks.utils.general.BlockUtils;
import eu.omewillem.decoblocks.utils.general.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CommandAlias("decoblocks|deco")
@CommandPermission("decoblocks.command")
public class DecoCommand extends BaseCommand {

    private DecoBlocks instance;
    public DecoCommand() {
        instance = DecoBlocks.getInstance();
    }

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
        ItemStack convertedItem;
        if (url == null) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (!BlockUtils.isPlayerHeadBlock(item.getType())) {
                player.sendMessage("You must hold a player skull.");
                return;
            }

            convertedItem = instance.getConvertManager().convertSkull(item);
            if (convertedItem == null) {
                player.sendMessage("Something went wrong!");
                return;
            }


            player.getInventory().setItemInMainHand(convertedItem);
        } else {
            convertedItem = instance.getConvertManager().convertMojang(url);
            if (convertedItem == null) {
                player.sendMessage("Something went wrong!");
                return;
            }

            player.getInventory().addItem(convertedItem);
        }

        convertedItem.setAmount(1);
        if (instance.getHistoryMap().containsKey(player.getUniqueId())) {
            instance.getHistoryMap().get(player.getUniqueId()).add(convertedItem);
        } else {
            ArrayList<ItemStack> stacks = new ArrayList<>();
            stacks.add(convertedItem);
            DecoBlocks.getInstance().getHistoryMap().put(player.getUniqueId(), stacks);
        }
        player.sendMessage("Succesfully converted!");
    }

    @Subcommand("library")
    @CommandPermission("decoblocks.library")
    @Syntax("library")
    public void onLibrary(Player player) {
        new ChooseGUI().open(player);
    }

}
