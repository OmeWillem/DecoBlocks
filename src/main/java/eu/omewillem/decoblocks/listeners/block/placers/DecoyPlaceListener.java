package eu.omewillem.decoblocks.listeners.block.placers;

import com.jeff_media.customblockdata.CustomBlockData;
import eu.omewillem.decoblocks.DecoBlocks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Transformation;

public class DecoyPlaceListener implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        ItemStack item = event.getItemInHand();
        ItemMeta meta = item.getItemMeta();

        PersistentDataContainer itemDataContainer = meta.getPersistentDataContainer();
        if (!itemDataContainer.has(DecoBlocks.getInstance().getDecoyKey(), PersistentDataType.BOOLEAN)) return;

        Block underBlock = event.getBlock().getLocation().clone().subtract(0,1,0).getBlock();
        PersistentDataContainer underDataContainer = new CustomBlockData(underBlock, DecoBlocks.getInstance());
        if (!underDataContainer.has(DecoBlocks.getInstance().getBlockKey(), PersistentDataType.BOOLEAN)) return;

        Location location = block.getLocation();

        BlockDisplay display = (BlockDisplay) location.getWorld().spawnEntity(location, EntityType.BLOCK_DISPLAY);
        display.setBlock(block.getBlockData());

        PersistentDataContainer blockDataContainer = new CustomBlockData(block, DecoBlocks.getInstance());
        blockDataContainer.set(DecoBlocks.getInstance().getUuidKey(),
                PersistentDataType.STRING,
                display.getUniqueId().toString());

        block.setType(Material.BARRIER);
    }
}
