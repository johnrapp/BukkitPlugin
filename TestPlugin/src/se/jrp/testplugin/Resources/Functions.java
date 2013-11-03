package se.jrp.testplugin.Resources;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Functions {
	public static String getTimeStamp() {
		return (new SimpleDateFormat("dd/MM HH:mm:ss")).format(new Date());
	}

	public static String parseMaterial(Material material) {
		String name = material.toString().toLowerCase();
		String result = Character.toUpperCase(name.charAt(0)) + name.substring(1);
		return result.replaceAll("_", " ");
	}
	
	public static boolean isInteger(String s) {
	    try { Integer.parseInt(s); }
	    catch(NumberFormatException e) { return false; }
	    return true;
	}
	
	public static int usedSlots(PlayerInventory inventory) {
		int used = 0;
		for(ItemStack item : inventory.getContents()) {
			if(item != null && item.getAmount() > 0)
				used++;
		}
		return used;
	}
}