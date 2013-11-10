package se.jrp.testplugin.Bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import se.jrp.testplugin.Book;
import se.jrp.testplugin.FileListener;
import se.jrp.testplugin.TestPlugin;
import se.jrp.testplugin.Resources.Functions;
import se.jrp.testplugin.Resources.Strings;
import se.jrp.testplugin.Resources.Values;

public class Bank implements CommandExecutor, FileListener {
	public HashMap<String, ArrayList<ItemStack>> bankInventory = new HashMap<String, ArrayList<ItemStack>>();
	public HashMap<String, BankCommandExecutor> commandExecutors = new HashMap<String, BankCommandExecutor>();
	
	public Bank() {
		TestPlugin.instance.addCommandExecutor(this, Strings.COMMAND_BANK);
		commandExecutors.put(Strings.COMMAND_BANK_GET, new BankGetCommandExecutor(this));
		commandExecutors.put(Strings.COMMAND_BANK_STORE, new BankStoreCommandExecutor(this));
		commandExecutors.put(Strings.COMMAND_BANK_LIST, new BankListCommandExecutor(this));
		commandExecutors.put(Strings.COMMAND_BANK_ACCEPTED, new BankAcceptedCommandExecutor(this));
	}
	
	@Override
	public void onLoad(HashMap<? extends Object, ? extends Object> serializedMap) {
		for(Entry entry : serializedMap.entrySet()) {
			bankInventory.put((String) entry.getKey(), deSerialize((ArrayList<HashMap<Material, Integer>>) entry.getValue()));
		}
	}
	@Override
	public HashMap onSave() {
		HashMap<String, ArrayList<HashMap<Material, Integer>>> serializedMap = new HashMap<String, ArrayList<HashMap<Material, Integer>>>();
		for(Entry<String, ArrayList<ItemStack>> entry : bankInventory.entrySet()) {
			serializedMap.put(entry.getKey(), serialize(entry.getValue()));
		}
		return serializedMap;
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
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length < 1) {
			return false;
		} else if(!(sender instanceof Player)) {
			sender.sendMessage(Strings.ERROR_NON_PLAYER);
			return true;
		}
		Player player = (Player) sender;
		String[] params = Functions.cutFirstIndex(args);
		if(commandExecutors.containsKey(args[0])) {
			commandExecutors.get(args[0]).onCommand(player, params);
			return true;
		} 
		
		return false;
	}
	
	public static boolean itemAccepted(Material mat) {
		for(Material item : Values.BANK_ACCEPTED_ITEMS) {
			if(mat == item)
				return true;
		}
		return false;
	}
	
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

	
}

/*public boolean enoughSpace(ArrayList<ItemStack> inventory, ItemStack item) {
Material mat = item.getType();
ArrayList<ItemStack> all = all(inventory, mat);
int busy = 0;
for(ItemStack itm : all) {
	busy += itm.getAmount();
} 
return ((all.size() * mat.getMaxStackSize() - busy) >= item.getAmount()) || inventory.size() < MAX_SLOTS;
}*/