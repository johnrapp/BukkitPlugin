package se.jrp.bankplugin.commandexecutors;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import se.jrp.bankplugin.BankPlugin;

import se.jrp.bankplugin.resources.Functions;
import se.jrp.bankplugin.resources.MaterialParser;
import se.jrp.bankplugin.resources.PlayerInventoryAnalyzer;
import se.jrp.bankplugin.resources.Strings;

public class BankGetCommandExecutor implements BankCommandExecutor {
	
	@Override
	public void onCommand(Player player, String[] args) {
		PlayerInventory inventory = player.getInventory();
		ArrayList<ItemStack> depositBox = BankPlugin.inventory.get(player.getName());
		if(args.length < 1) {
			player.sendMessage(Strings.ERROR_BANK_GET_NO_ARGUMENTS);
		} else if(PlayerInventoryAnalyzer.full(inventory)) {
			player.sendMessage(Strings.ERROR_INVENTORY_FULL);
			return;
		} else if(Functions.getInteger(args[0], null) != null) {
			getByIndex(player, depositBox, args);
		} else {
			getByName(player, depositBox, args);
		}
	}
	
	public void getByIndex(Player player, ArrayList<ItemStack> depositBox, String [] args) {
		PlayerInventory inventory = player.getInventory();
		ArrayList<Integer> remove = new ArrayList<>();
		for(int i = 0; i < args.length; i++) {
			if(Functions.getInteger(args[i], null) != null) {
				int index = Integer.parseInt(args[i]);
				if(PlayerInventoryAnalyzer.usedSlots(inventory) < inventory.getSize()){
					if(depositBox.size() > index) {
						inventory.addItem(depositBox.get(index));
						remove.add(index);
					} else {
						player.sendMessage(ChatColor.RED + "Slot " + index + Strings.ERROR_DONT_EXIST);
					}
				} else {
					player.sendMessage(Strings.ERROR_BANK_GET_NOT_EVERYTHING);
					break;
				}
			} else {
				player.sendMessage(ChatColor.RED + args[i] + Strings.ERROR_NON_NUMBER);
			}
		}
		Collections.sort(remove, Collections.reverseOrder());
		for (int i : remove)
		    depositBox.remove(i);
	}
	
	public void getByName(Player player, ArrayList<ItemStack> depositBox, String [] args) {
		PlayerInventory inventory = player.getInventory();
		for(int i = 0; i < args.length; i += 2) {
			if(!PlayerInventoryAnalyzer.full(inventory)) {
				Material mat = MaterialParser.instance().getMaterial(args[i]);
				if(mat != null && BankPlugin.inventory.contains(player.getName(), mat)) {
					String amount = args[i + 1];
					if(Functions.getInteger(amount, null) != null) {
						BankPlugin.inventory.getItem(player, new ItemStack(mat, Integer.parseInt(amount)));
					} else {
						player.sendMessage(ChatColor.RED + amount + Strings.ERROR_NON_NUMBER);
						break;
					}
				} else {
					player.sendMessage(ChatColor.RED + args[i] + Strings.ERROR_BANK_NON_ITEM);
				}
			} else {
				player.sendMessage(Strings.ERROR_BANK_GET_NOT_EVERYTHING);
				break;
			}
		}
	}

}
