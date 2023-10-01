package eu.omewillem.decoblocks.utils;

import com.jeff_media.customblockdata.CustomBlockData;
import eu.omewillem.decoblocks.DecoBlocks;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;
import java.util.UUID;

@UtilityClass
public class Utils {
    public String parse(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    public boolean isPlayerHeadBlock(Material type) {
        return type == Material.PLAYER_HEAD || type == Material.PLAYER_WALL_HEAD;
    }

    public boolean isDecoItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.getPersistentDataContainer().has(DecoBlocks.getInstance().getBlockKey(), PersistentDataType.BOOLEAN);
    }

    public boolean isDecoyItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.getPersistentDataContainer().has(DecoBlocks.getInstance().getDecoyKey(), PersistentDataType.BOOLEAN);
    }


    private PersistentDataContainer getCustomBlockData(Block block) {
        return new CustomBlockData(block, DecoBlocks.getInstance());
    }

    public boolean isDecoBlock(Block block) {
        PersistentDataContainer dataContainer = getCustomBlockData(block);
        return dataContainer.has(DecoBlocks.getInstance().getBlockKey(), PersistentDataType.BOOLEAN);
    }

    public boolean hasUUID(Block block) {
        PersistentDataContainer dataContainer = getCustomBlockData(block);
        return dataContainer.has(DecoBlocks.getInstance().getUuidKey(), PersistentDataType.STRING);
    }

    public UUID getUUID(Block block) {
        PersistentDataContainer dataContainer = getCustomBlockData(block);
        String uuidString = dataContainer.get(DecoBlocks.getInstance().getUuidKey(), PersistentDataType.STRING);
        return uuidString != null ? UUID.fromString(uuidString) : null;
    }

    public double getAngle(BlockFace face) {
        return Map.of(
                BlockFace.NORTH, Math.toRadians(180),
                BlockFace.WEST, Math.toRadians(270),
                BlockFace.SOUTH, 0.0,
                BlockFace.EAST, Math.toRadians(90)
        ).getOrDefault(face, 0.0);
    }
}
