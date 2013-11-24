package se.jrp.marketplugin.resources;

import java.util.HashMap;
import java.util.Map.Entry;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialParser {
	private static MaterialParser instance;
	public static HashMap<Material, String> exceptions = new HashMap<>();

	public MaterialParser() {
		exceptions.put(Material.NETHER_STALK, "nether_warts");
		exceptions.put(Material.GRILLED_PORK, "cooked_porkshop");
		exceptions.put(Material.WATCH, "clock");
	}
	
	public String getName(Material material) {
		String name;
		if(exceptions.containsKey(material)) {
			name = exceptions.get(material);
		} else {
			name = material.name();
		}
		StringBuilder sz = new StringBuilder();
		String[] words = name.split("_");
		for(int i = 0; i < words.length; i++) {
			sz.append(Character.toUpperCase(words[i].charAt(0)))
					.append(words[i].substring(1))
					.append(i < words.length - 1 ? " " : "");
		}
		return sz.toString();
	}
	public String getName(ItemStack item) {
		String name = getName(item.getType());
		return name.substring(name.length() - 1).equalsIgnoreCase("s") ? name : name + "s";
	}
	
	public Material getMaterial(String name) {
		Material material = null;
		for(Entry<Material, String> entry : exceptions.entrySet()) {
			if(entry.getValue().equalsIgnoreCase(name)) {
				material = entry.getKey();
			}
		}
		if(material == null) material = Material.getMaterial(name);
		return material;
	}
	
	public static MaterialParser instance() {
		if(instance == null) instance = new MaterialParser();
		return instance;
	}
}
