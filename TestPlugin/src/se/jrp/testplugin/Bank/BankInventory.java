package se.jrp.testplugin.Bank;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import se.jrp.testplugin.Resources.Functions;
import se.jrp.testplugin.Resources.Strings;
import se.jrp.testplugin.Resources.Values;

public class BankInventory extends HashMap<String, ArrayList<ItemStack>> {
	
	public ArrayList<ItemStack> all(String key, Material mat) {
		ArrayList<ItemStack> result = new ArrayList<ItemStack>();
		for(ItemStack item : get(key)) {
			if(item.getType() == mat)
				result.add(item);
		}
		return result;
	}
	
	public boolean full(String key) {
		return get(key).size() >= Values.BANK_MAX_SLOTS;
	}
	
	public boolean addItem(Player player, ItemStack item) {
		//TODO kolla igenom och förbättra
		//om man tar något ifrån en sack längre bort tas det från en tidigare
		Material mat = item.getType();
		ArrayList<ItemStack> all = all(player.getName(), mat);
		boolean remove = false;
		while(item.getAmount() > 0 && !remove) {
			boolean full = full(player.getName());
			if(all.size() > 0) {
				ItemStack is = all.get(0);
				if(is.getAmount() >= mat.getMaxStackSize()) {
					all.remove(is);
				} else {
					if((item.getAmount() + is.getAmount()) <= mat.getMaxStackSize()) {
						is.setAmount(item.getAmount() + is.getAmount());
						remove = true;
					} else {
						item.setAmount(item.getAmount() - (mat.getMaxStackSize() - is.getAmount()));
						is.setAmount(mat.getMaxStackSize());
					}
				}
			} else if(!full) {
				get(player.getName()).add(new ItemStack(mat, item.getAmount()));
				remove = true;
			} else if(full) {
				player.sendMessage(Strings.ERROR_BANK_STORE_NOT_EVERYTHING);
				break;
			}
		}
		if(remove) player.getInventory().removeItem(item);
		return remove;
	}
	
	public void getItem(Player player, ItemStack item) {
		Material mat = item.getType();
		PlayerInventory inventory = player.getInventory();
		ArrayList<ItemStack> depositBox = get(player.getName());
		ArrayList<ItemStack> all = all(player.getName(), mat);
		if(all.size() < 1) {
			player.sendMessage(Strings.ERROR_BANK_NONE_OF_MATERIAL1 + Functions.parseMaterial(mat)
				+ Strings.ERROR_BANK_NONE_OF_MATERIAL2);
		}
		while(all.size() > 0) {
			if(!Functions.full(inventory)) {
				ItemStack least = all.get(0);
				for(int i = 1; i < all.size(); i++) {
					ItemStack is = all.get(i);
					if(is.getAmount() < least.getAmount())
						least = is;
				}
				if(least.getAmount() >= item.getAmount()) {
					least.setAmount(least.getAmount() - item.getAmount());
					if(least.getAmount() <= 0)
						depositBox.remove(least);
					inventory.addItem(item);
					return;
				} else {
					item.setAmount(item.getAmount() - least.getAmount());
					inventory.addItem(least);
					depositBox.remove(least);
					all.remove(least);
				}
			} else {
				player.sendMessage(Strings.ERROR_BANK_GET_NOT_EVERYTHING);
				return;
			}
		}
		
		player.sendMessage(Strings.ERROR_BANK_GET_NOT_ENOUGH1 + Functions.parseMaterial(mat)
				+ Strings.ERROR_BANK_GET_NOT_ENOUGH2);
	}
	
	/*public void notifyChanges(String player) {
		ArrayList<ItemStack> depositBox = get(player);
		HashMap<Material, ArrayList<ItemStack>> materials = new HashMap<Material, ArrayList<ItemStack>>();
		for(ItemStack is : depositBox) {
			if(is.getAmount() < is.getType().getMaxStackSize()) {
				if(!materials.containsKey(is.getType()))
					materials.put(is.getType(), new ArrayList<ItemStack>());
				materials.get(is.getType()).add(is);
			}
		}
		for(ArrayList<ItemStack> array : materials.values()) {
			int amount = 0;
			for(ItemStack is : array) {
				amount += is.getAmount();
			}
		}
	}*/
	
	public boolean contains(String player, Material material) {
		for(ItemStack item : get(player)) {
			if(item.getType() == material) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean accepted(Material mat) {
		for(Material item : Values.BANK_ACCEPTED_ITEMS) {
			if(mat == item)
				return true;
		}
		return false;
	}
}
