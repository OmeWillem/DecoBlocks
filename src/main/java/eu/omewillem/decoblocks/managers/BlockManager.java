package eu.omewillem.decoblocks.managers;

import com.jeff_media.customblockdata.CustomBlockData;
import eu.omewillem.decoblocks.DecoBlocks;
import eu.omewillem.decoblocks.utils.Utils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Transformation;

public class BlockManager {
    private final DecoBlocks decoBlocks;

    public BlockManager(DecoBlocks decoBlocks) {
        this.decoBlocks = decoBlocks;
    }

    public void createBlock(Block block, ItemStack item, BlockFace face) {
        ItemDisplay display = spawnBlock(block, item, face);
        PersistentDataContainer blockDataContainer = getCustomBlockData(block);

        setUUID(blockDataContainer, display);
        setBlock(blockDataContainer);
    }

    public void createDecoy(Block block) {
        BlockDisplay display = spawnDecoy(block);
        PersistentDataContainer blockDataContainer = getCustomBlockData(block);

        setUUID(blockDataContainer, display);
        setDecoy(blockDataContainer);
    }

    private ItemDisplay spawnBlock(Block block, ItemStack item, BlockFace face) {
        Location location = block.getLocation().clone().add(0.5, 1, 0.5);
        ItemDisplay display = (ItemDisplay) block.getWorld().spawnEntity(location, EntityType.ITEM_DISPLAY);

        Transformation transformation = display.getTransformation();
        transformation.getScale().set(2, 2, 2);
        transformation.getRightRotation().setAngleAxis(Utils.getAngle(face), 0, 1, 0);

        display.setTransformation(transformation);
        display.setItemStack(item);
        return display;
    }

    private BlockDisplay spawnDecoy(Block block) {
        BlockDisplay display = (BlockDisplay) block.getWorld().spawnEntity(block.getLocation(), EntityType.BLOCK_DISPLAY);
        display.setBlock(block.getBlockData());
        return display;
    }

    private PersistentDataContainer getCustomBlockData(Block block) {
        return new CustomBlockData(block, decoBlocks);
    }

    private void setUUID(PersistentDataContainer dataContainer, Display display) {
        dataContainer.set(decoBlocks.getUuidKey(), PersistentDataType.STRING, display.getUniqueId().toString());
    }

    private void setBlock(PersistentDataContainer dataContainer) {
        dataContainer.set(decoBlocks.getBlockKey(), PersistentDataType.BOOLEAN, true);
    }

    private void setDecoy(PersistentDataContainer dataContainer) {
        dataContainer.set(decoBlocks.getDecoyKey(), PersistentDataType.BOOLEAN, true);
    }
}
