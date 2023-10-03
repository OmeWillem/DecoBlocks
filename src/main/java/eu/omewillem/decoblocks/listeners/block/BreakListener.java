package eu.omewillem.decoblocks.listeners.block;

import eu.omewillem.decoblocks.DecoBlocks;
import eu.omewillem.decoblocks.utils.general.BlockUtils;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakListener implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!event.getBlock().getType().equals(Material.BARRIER)) return;

        Block block = event.getBlock();
        if (!BlockUtils.hasUUID(block)) return;

        Entity entity = DecoBlocks.getInstance().getServer().getEntity(BlockUtils.getUUID(block));
        entity.remove();

        BlockUtils.removeData(block);

        block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, Material.PLAYER_HEAD);
    }
}
