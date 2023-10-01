package eu.omewillem.decoblocks.listeners.block.placers;

import eu.omewillem.decoblocks.DecoBlocks;
import eu.omewillem.decoblocks.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class PlaceListener implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (!Utils.isPlayerHeadBlock(block.getType())) return;

        ItemStack item = event.getItemInHand();
        if (!Utils.isDecoItem(item)) return;

        Player player = event.getPlayer();
        DecoBlocks.getInstance().getBlockManager().createBlock(block, item, player.getFacing());
        block.setType(Material.BARRIER);
    }
}
