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
	public BankInventory inventory = new BankInventory();
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
			inventory.put((String) entry.getKey(), deSerialize((ArrayList<HashMap<Material, Integer>>) entry.getValue()));
		}
	}
	@Override
	public HashMap onSave() {
		HashMap<String, ArrayList<HashMap<Material, Integer>>> serializedMap = new HashMap<String, ArrayList<HashMap<Material, Integer>>>();
		for(Entry<String, ArrayList<ItemStack>> entry : inventory.entrySet()) {
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
		if(!(sender instanceof Player)) {
			sender.sendMessage(Strings.ERROR_NON_PLAYER);
			return true;
		} else if(args.length < 1) {
			return false;
		}
		if(commandExecutors.containsKey(args[0])) {
			commandExecutors.get(args[0]).onCommand((Player) sender, Functions.cutFirstIndex(args));
			return true;
		} 
		
		return false;
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