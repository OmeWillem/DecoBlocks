package eu.omewillem.decoblocks.listeners.block.placers;

import eu.omewillem.decoblocks.DecoBlocks;
import eu.omewillem.decoblocks.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class DecoyPlaceListener implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        ItemStack item = event.getItemInHand();

        if (!Utils.isDecoyItem(item)) return;

        Block underBlock = block.getLocation().clone().subtract(0,1,0).getBlock();
        if (!Utils.isDecoBlock(underBlock)) return;

        DecoBlocks.getInstance().getBlockManager().createDecoy(block);
        block.setType(Material.BARRIER);
    }
}
