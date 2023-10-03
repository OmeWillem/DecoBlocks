package eu.omewillem.decoblocks.utils.general;

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

    public double getAngle(BlockFace face) {
        return Map.of(
                BlockFace.NORTH, Math.toRadians(180),
                BlockFace.WEST, Math.toRadians(270),
                BlockFace.SOUTH, 0.0,
                BlockFace.EAST, Math.toRadians(90)
        ).getOrDefault(face, 0.0);
    }
}
