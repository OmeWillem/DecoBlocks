package eu.omewillem.decoblocks.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

@UtilityClass
public class ColourUtils {
    public String parse(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
