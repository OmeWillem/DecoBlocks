package eu.omewillem.decoblocks.listeners.block;

import com.jeff_media.customblockdata.CustomBlockData;
import eu.omewillem.decoblocks.DecoBlocks;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class BreakListener implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!event.getBlock().getType().equals(Material.BARRIER)) return;

        Block block = event.getBlock();
        PersistentDataContainer customBlockData = new CustomBlockData(block, DecoBlocks.getInstance());
        NamespacedKey uuidKey = DecoBlocks.getInstance().getUuidKey();

        if (!customBlockData.has(uuidKey, PersistentDataType.STRING)) return;

        UUID uuid = UUID.fromString(customBlockData.get(uuidKey, PersistentDataType.STRING));
        Entity entity = DecoBlocks.getInstance().getServer().getEntity(uuid);
        entity.remove();

        block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, Material.PLAYER_HEAD);
    }
}
