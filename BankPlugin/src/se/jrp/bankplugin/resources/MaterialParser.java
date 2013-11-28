package se.jrp.bankplugin.resources;

import java.util.HashMap;
import java.util.Map.Entry;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialParser {
	private static MaterialParser instance;
	public static HashMap<Material, String> exceptions = new HashMap<>();

	public MaterialParser() {
		exceptions.put(Material.NETHER_STALK, "NETHER_WARTS");
		exceptions.put(Material.GRILLED_PORK, "COOKED_PORKSHOP");
		exceptions.put(Material.WATCH, "CLOCK");
		exceptions.put(Material.SULPHUR, "GUN_POWDER");
		exceptions.put(Material.DIODE, "REDSTONE_REPEATER");
	}
	
	public String getName(Material material) {
		return exceptions.containsKey(material) ? exceptions.get(material) : material.name();
	}
	
	public String getName(ItemStack item) {
		String name = getName(item.getType());
		return item.getAmount() > 1 ? addPlural(name) : name;
	}
	
	public String getParsedName(Material material) {
		StringBuilder sz = new StringBuilder();
		String[] words = getName(material).toLowerCase().split("_");
		for(int i = 0; i < words.length; i++) {
			sz.append(Character.toUpperCase(words[i].charAt(0)))
					.append(words[i].substring(1))
					.append(i < words.length - 1 ? " " : "");
		}
		return sz.toString();
	}
	
	public String getParsedName(ItemStack item) {
		String name = getParsedName(item.getType());
		return item.getAmount() > 1 ? addPlural(name) : name;
	}
	
	public String getFullParsedName(ItemStack item) {
		String name = getParsedName(item.getType());
		return item.getAmount() + " " + (item.getAmount() > 1 ? addPlural(name) : name);
	}
	
	private String addPlural(String name) {
		return name.substring(name.length() - 1).equalsIgnoreCase("s") ? name : name + "s";
	}
	
	public Material getMaterial(String name) {
		name = name.toUpperCase();
		Material material = null;
		for(Entry<Material, String> entry : exceptions.entrySet()) {
			if(entry.getValue().equals(name)) {
				material = entry.getKey();
			}
		}
		return material != null ? material : Material.getMaterial(name);
	}
	
	public static MaterialParser instance() {
		if(instance == null) instance = new MaterialParser();
		return instance;
	}
}
