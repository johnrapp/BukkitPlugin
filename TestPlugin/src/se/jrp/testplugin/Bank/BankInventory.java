package se.jrp.testplugin.Bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import se.jrp.testplugin.Resources.Strings;
import se.jrp.testplugin.Resources.Values;

public class BankInventory extends HashMap<String, ArrayList<ItemStack>> {
	
	public ArrayList<ItemStack> all(ArrayList<ItemStack> inventory, Material mat) {
		ArrayList<ItemStack> result = new ArrayList<ItemStack>();
		for(ItemStack item : inventory) {
			if(item.getType() == mat)
				result.add(item);
		}
		return result;
	}
	
	public boolean addItem(ArrayList<ItemStack> inventory, ItemStack item, Player player) {
		Material mat = item.getType();
		ArrayList<ItemStack> all = all(inventory, mat);
		boolean remove = false;
		while(item.getAmount() > 0 && !remove) {
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
			} else if(inventory.size() < Values.BANK_MAX_SLOTS) {
				inventory.add(new ItemStack(mat, item.getAmount()));
				remove = true;
			} else if(inventory.size() >= Values.BANK_MAX_SLOTS) {
				player.sendMessage(Strings.ERROR_BANK_STORE_NOT_EVERYTHING);
				break;
			}
		}
		if(remove) player.getInventory().removeItem(item);
		return remove;
	}
	
	public ArrayList<HashMap<Material, Integer>> serialize(ArrayList<ItemStack> items) {
		ArrayList<HashMap<Material, Integer>> result = new ArrayList<HashMap<Material, Integer>>();
		for(ItemStack item : items) {
			HashMap<Material, Integer> map = new HashMap<Material, Integer>();
			map.put(item.getType(), item.getAmount());
			result.add(map);
		}
		return result;
	}
	
	public ArrayList<ItemStack> deSerialize(ArrayList<HashMap<Material, Integer>> items) {
		ArrayList<ItemStack> result = new ArrayList<ItemStack>();
		for(HashMap<Material, Integer> map : items) {
			for(Entry<Material, Integer> entry : map.entrySet()) {
				result.add(new ItemStack(entry.getKey(), entry.getValue()));
			}
		}
		return result;
	}
}
