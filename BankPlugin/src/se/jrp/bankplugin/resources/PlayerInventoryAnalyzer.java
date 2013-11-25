package se.jrp.bankplugin.resources;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerInventoryAnalyzer {
	public static int usedSlots(PlayerInventory inventory) {
		int used = 0;
		for(ItemStack item : inventory.getContents()) {
			if(item != null && item.getAmount() > 0)
				used++;
		}
		return used;
	}
	
	public static int emptySlots(PlayerInventory inventory) {
		return inventory.getSize() - usedSlots(inventory);
	}
	
	public static boolean full(PlayerInventory inventory) {
		return emptySlots(inventory) <= 0;
	}
	
	public static boolean full(PlayerInventory inventory, Material material) {
		return spaceFor(inventory, material) <= 0;
	}
	
	public static int spaceFor(PlayerInventory inventory, Material material) {
		int space = 0;
		for(ItemStack is : inventory.all(material).values()) {
			space += is.getType().getMaxStackSize() - is.getAmount();
		}
		return space + emptySlots(inventory) * material.getMaxStackSize();
	}
	
	public static boolean enoughSpace(PlayerInventory inventory, ItemStack item) {
		return spaceFor(inventory, item.getType()) >= item.getAmount();
	}
}
