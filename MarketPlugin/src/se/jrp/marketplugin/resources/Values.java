package se.jrp.marketplugin.resources;

import java.util.HashMap;
import org.bukkit.Material;

public class Values {
	public static HashMap<Material, Integer[]> DEFAULT_PRICES = new HashMap<>();
	public static void init() {
		DEFAULT_PRICES.put(Material.DIAMOND, new Integer[] {324, 162});
		DEFAULT_PRICES.put(Material.IRON_INGOT, new Integer[] {8, 4});
		DEFAULT_PRICES.put(Material.LOG, new Integer[] {2, 1});
	}
}
