package eu.omewillem.decoblocks.listeners.block;

import eu.omewillem.decoblocks.DecoBlocks;
import eu.omewillem.decoblocks.utils.general.BlockUtils;
import eu.omewillem.decoblocks.utils.general.ItemUtils;
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
        if (!BlockUtils.isPlayerHeadBlock(block.getType())) return;

        ItemStack item = event.getItemInHand();
        if (!ItemUtils.isDecoItem(item)) return;

        Player player = event.getPlayer();
        DecoBlocks.getInstance().getBlockManager().createBlock(block, item, player.getFacing());
        block.setType(Material.BARRIER);
    }
}
