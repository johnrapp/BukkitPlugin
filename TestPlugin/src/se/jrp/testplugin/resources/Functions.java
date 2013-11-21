package se.jrp.testplugin.resources;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Functions {
	public static String getTimeStamp() {
		return (new SimpleDateFormat("dd/MM HH:mm:ss")).format(new Date());
	}

	public static String parseMaterial(Material material) {
		if(Values.MATERIAL_DISPLAY_EXCEPTIONS.containsKey(material))
			return Values.MATERIAL_DISPLAY_EXCEPTIONS.get(material);
		String name = material.toString().toLowerCase();
		String result = Character.toUpperCase(name.charAt(0)) + name.substring(1);
		return result.replaceAll("_", " ");
	}
	
	public static String parseMaterialPlural(Material material) {
		String name = parseMaterial(material);
		return name.substring(name.length() - 1).equalsIgnoreCase("s") ? name : name + "s";
	}
	
	public static Material getMaterialFromName(String name) {
		name = name.toUpperCase();
		if(Values.MATERIAL_WRITE_EXCEPTIONS.containsKey(name))
			return Values.MATERIAL_WRITE_EXCEPTIONS.get(name);
		
		return Material.getMaterial(name);
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
	
	public static boolean full(PlayerInventory inventory) {
		return Functions.usedSlots(inventory) >= inventory.getSize();
	}
	
	public static String[] cutFirstIndex(String[] array) {
		return Arrays.copyOfRange(array, 1, array.length);
	}
}