package eu.omewillem.decoblocks.managers;

import eu.omewillem.decoblocks.DecoBlocks;
import eu.omewillem.decoblocks.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public class ConvertManager {
    private final DecoBlocks decoBlocks;
    public ConvertManager(DecoBlocks decoBlocks) {
        this.decoBlocks = decoBlocks;
    }

    public ItemStack convertSkull(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        setBlockData(meta);
        meta.setLore(List.of(Utils.parse("&7This is a DecoBlocks block.")));
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack convertMojang(String texture) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        PlayerProfile profile = Bukkit.getServer().createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();
        try {
            textures.setSkin(new URL("http://textures.minecraft.net/texture/" + texture));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        profile.setTextures(textures);
        meta.setOwnerProfile(profile);
        setBlockData(meta);
        meta.setDisplayName("A cool block!");
        meta.setLore(List.of(Utils.parse("&7This is a DecoBlocks block.")));
        head.setItemMeta(meta);
        return head;
    }

    public ItemStack convertDecoy(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();
        NamespacedKey decoyKey = DecoBlocks.getInstance().getDecoyKey();

        persistentDataContainer.set(decoyKey, PersistentDataType.BOOLEAN, true);
        meta.setLore(List.of(Utils.parse("&2THIS IS A DECOY BLOCK USED WITH DECOBLOCKS!")));

        item.setItemMeta(meta);
        return item;
    }

    private void setBlockData(ItemMeta meta) {
        PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();
        NamespacedKey blockKey = DecoBlocks.getInstance().getBlockKey();

        persistentDataContainer.set(blockKey, PersistentDataType.BOOLEAN, true);
    }
}
