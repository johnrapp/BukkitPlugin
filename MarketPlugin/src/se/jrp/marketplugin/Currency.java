package se.jrp.marketplugin;

import org.bukkit.Material;
import org.bukkit.inventory.PlayerInventory;

public class Currency {
	
	public static Material currency;
	
	public static void init() {
		currency = Material.GOLD_NUGGET;
	}
	
	public static boolean add(PlayerInventory inventory, int amount) {
		
		return false;
	}
}
