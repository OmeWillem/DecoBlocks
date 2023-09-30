package eu.omewillem.decoblocks.listeners.block.placers;

import com.jeff_media.customblockdata.CustomBlockData;
import eu.omewillem.decoblocks.DecoBlocks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
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

import java.util.Map;

public class PlaceListener implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (!block.getType().equals(Material.PLAYER_HEAD) && !block.getType().equals(Material.PLAYER_WALL_HEAD)) return;

        ItemStack item = event.getItemInHand();
        ItemMeta meta = item.getItemMeta();

        PersistentDataContainer itemDataContainer = meta.getPersistentDataContainer();
        if (!itemDataContainer.has(DecoBlocks.getInstance().getBlockKey(), PersistentDataType.BOOLEAN)) return;

        Player player = event.getPlayer();
        Skull skull = (Skull) block.getState();
        Location location = skull.getLocation().clone().add(0.5,1,0.5);

        ItemDisplay display = (ItemDisplay) location.getWorld().spawnEntity(location, EntityType.ITEM_DISPLAY);
        Transformation transformation = display.getTransformation();

        transformation.getScale().set(2,2,2);
        transformation.getRightRotation().setAngleAxis(getAngle(player.getFacing()), 0, 1, 0);

        display.setItemStack(item);
        display.setTransformation(transformation);

        PersistentDataContainer blockDataContainer = new CustomBlockData(block, DecoBlocks.getInstance());
        blockDataContainer.set(DecoBlocks.getInstance().getUuidKey(),
                PersistentDataType.STRING,
                display.getUniqueId().toString());
        blockDataContainer.set(DecoBlocks.getInstance().getBlockKey(),
                PersistentDataType.BOOLEAN,
                true);

        block.setType(Material.BARRIER);
    }

    private double getAngle(BlockFace face) {
        return Map.of(
                BlockFace.NORTH, Math.toRadians(180),
                BlockFace.WEST, Math.toRadians(270),
                BlockFace.SOUTH, 0.0,
                BlockFace.EAST, Math.toRadians(90)
        ).getOrDefault(face, 0.0);
    }
}
