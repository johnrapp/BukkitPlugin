package se.jrp.testplugin.Bank;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import se.jrp.testplugin.Resources.Functions;
import se.jrp.testplugin.Resources.Strings;

public class BankGetCommandExecutor extends BankCommandExecutor {

	public BankGetCommandExecutor(Bank bank) {
		super(bank);
	}
	public void onCommand(Player player, String[] args) {
		PlayerInventory inventory = player.getInventory();
		ArrayList<ItemStack> depositBox = bank.inventory.get(player.getName());
		if(args.length < 1) {
			player.sendMessage(Strings.ERROR_BANK_GET_NO_ARGUMENTS);
		} else if(Functions.usedSlots(inventory) >= inventory.getSize()) {
			player.sendMessage(Strings.ERROR_INVENTORY_FULL);
			return;
		} else if(Functions.isInteger(args[0])) {
			getByIndex(player, depositBox, args);
		} else {
			getByName(player, depositBox, args);
		}
	}
	
	public void getByIndex(Player player, ArrayList<ItemStack> depositBox, String [] args) {
		PlayerInventory inventory = player.getInventory();
		ArrayList<Integer> remove = new ArrayList<Integer>();
		for(int i = 0; i < args.length; i++) {
			if(Functions.isInteger(args[i])) {
				int index = Integer.parseInt(args[i]);
				if(Functions.usedSlots(inventory) < inventory.getSize()){
					if(depositBox.size() > index) {
						inventory.addItem(depositBox.get(index));
						remove.add(index);
					} else {
						player.sendMessage(ChatColor.RED + "Index " + index + Strings.ERROR_DONT_EXIST);
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
		ArrayList<Integer> remove = new ArrayList<Integer>();
		for(int i = 0; i < args.length; i++) {
			if(Functions.isInteger(args[i])) {
				int index = Integer.parseInt(args[i]);
				if(Functions.usedSlots(inventory) < inventory.getSize()){
					if(depositBox.size() > index) {
						inventory.addItem(depositBox.get(index));
						remove.add(index);
					} else {
						player.sendMessage(ChatColor.RED + "Index " + index + Strings.ERROR_DONT_EXIST);
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

}