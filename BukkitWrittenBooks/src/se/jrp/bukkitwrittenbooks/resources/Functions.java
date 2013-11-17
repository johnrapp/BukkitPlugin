package se.jrp.bukkitwrittenbooks.resources;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Functions {
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
}