package eu.omewillem.decoblocks.utils.general;

import com.jeff_media.customblockdata.CustomBlockData;
import eu.omewillem.decoblocks.DecoBlocks;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

@UtilityClass
public class BlockUtils {
    private PersistentDataContainer getCustomBlockData(Block block) {
        return new CustomBlockData(block, DecoBlocks.getInstance());
    }

    public boolean isPlayerHeadBlock(Material type) {
        return type == Material.PLAYER_HEAD || type == Material.PLAYER_WALL_HEAD;
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

    public void removeData(Block block) {
        PersistentDataContainer dataContainer = getCustomBlockData(block);
        dataContainer.remove(DecoBlocks.getInstance().getUuidKey());
        dataContainer.remove(DecoBlocks.getInstance().getBlockKey());
    }
}
