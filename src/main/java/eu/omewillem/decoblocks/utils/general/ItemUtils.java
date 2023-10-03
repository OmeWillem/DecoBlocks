package eu.omewillem.decoblocks.utils.general;

import eu.omewillem.decoblocks.DecoBlocks;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

@UtilityClass
public class ItemUtils {
    public boolean isDecoItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.getPersistentDataContainer().has(DecoBlocks.getInstance().getBlockKey(), PersistentDataType.BOOLEAN);
    }
}
