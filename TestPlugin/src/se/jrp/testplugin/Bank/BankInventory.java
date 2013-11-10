package se.jrp.testplugin.Bank;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
		Material mat = item.getType();
		ArrayList<ItemStack> all = all(player.getName(), mat);
		boolean remove = false;
		while(item.getAmount() > 0 && !remove) {
			boolean full = this.full(player.getName());
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
	
	public static boolean accepted(Material mat) {
		for(Material item : Values.BANK_ACCEPTED_ITEMS) {
			if(mat == item)
				return true;
		}
		return false;
	}
}
