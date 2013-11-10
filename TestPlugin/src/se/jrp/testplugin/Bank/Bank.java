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
	
	public Bank() {
		TestPlugin.instance.addCommandExecutor(this, Strings.COMMAND_BANK);
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
		//String[] params = {};
		//if(args.length > 1)
			//params = Functions.cutFirstIndex(args);
		String[] params = Functions.cutFirstIndex(args);		
		if(args[0].equalsIgnoreCase(Strings.COMMAND_BANK_GET)) {
			get(player, params);
		} else if(args[0].equalsIgnoreCase(Strings.COMMAND_BANK_STORE)) {
			store(player, params);
		} else if(args[0].equalsIgnoreCase(Strings.COMMAND_BANK_LIST)) {
			list(player, params);
		} else if(args[0].equalsIgnoreCase(Strings.COMMAND_BANK_ACCEPTED)) {
			accepted(player, params);
		} else if(args[0].equalsIgnoreCase("clear")) {
			bankInventory.remove(player.getName());
		} else if(args[0].equalsIgnoreCase("debug")) {
			player.sendMessage(player.getItemInHand().getType().name());
		} else {
			return false;
		}
		
		return true;
		
	}
	
	public void get(Player player, String[] args) {
		PlayerInventory inventory = player.getInventory();
		ArrayList<ItemStack> bank = bankInventory.get(player.getName());
		if(args.length < 1) {
			player.sendMessage(Strings.ERROR_BANK_GET_NO_ARGUMENTS);
		} else if(Functions.usedSlots(inventory) >= inventory.getSize()) {
			player.sendMessage(Strings.ERROR_INVENTORY_FULL);
			return;
		} else if(Functions.isInteger(args[0])) {
			getByIndex(player, args, bank);
		} else {
			getByName(player, args, bank);
		}
	}
	
	public void getByIndex(Player player, String [] args, ArrayList<ItemStack> bank) {
		PlayerInventory inventory = player.getInventory();
		ArrayList<Integer> remove = new ArrayList<Integer>();
		for(int i = 0; i < args.length; i++) {
			if(Functions.isInteger(args[i])) {
				int index = Integer.parseInt(args[i]);
				if(Functions.usedSlots(inventory) < inventory.getSize()) {
					if(bank.size() > index) {
						inventory.addItem(bank.get(index));
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
		    bank.remove(i);
	}
	
	public void getByName(Player player, String [] args, ArrayList<ItemStack> bank) {
		if(args.length > 1) {
			PlayerInventory inventory = player.getInventory();
			ArrayList<Integer> remove = new ArrayList<Integer>();
			for(int i = 0; i < args.length; i++) {
				if(Functions.isInteger(args[i])) {
					int index = Integer.parseInt(args[i]);
					if(Functions.usedSlots(inventory) < inventory.getSize()) {
						if(bank.size() > index) {
							inventory.addItem(bank.get(index));
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
			    bank.remove(i);
		} else {
			player.sendMessage(Strings.ERROR_BANK_GET_NO_ARGUMENTS);
		}
	}
	
	public void store(Player player, String[] args) {
		if(!(bankInventory.containsKey(player.getName())))
			bankInventory.put(player.getName(), new ArrayList<ItemStack>());
		ArrayList<ItemStack> inventory =  bankInventory.get(player.getName());
		if(args.length > 0) {
			player.sendMessage(Strings.ERROR_BANK_STORE);
			return;
		} else if(inventory.size() >= Values.BANK_MAX_SLOTS) {
			player.sendMessage(Strings.ERROR_BANK_FULL);
			return;
		}
		ItemStack item = player.getItemInHand();
		if(item.getAmount() <= 0) {
			player.sendMessage(Strings.ERROR_BANK_NOTHING_IN_HAND);
		} else {
			boolean accepted = itemAccepted(item.getType());
			if(accepted && addItem(inventory, item, player)) {
	 			player.sendMessage(Strings.BANK_ADDED_ITEM);
			} else if(!accepted) {
				player.sendMessage(Strings.ERROR_BANK_NOT_ACCEPTED);
			}
		}
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
	
	public void list(Player player, String[] args) {
		ArrayList<ItemStack> inventory;
		if(!bankInventory.containsKey(player.getName()) || (inventory = bankInventory.get(player.getName())).size() < 1) {
			player.sendMessage(Strings.ERROR_BANK_NOTHING_STORED);
			return;
		}
		ArrayList<String> timestamp = new ArrayList<String>();
		timestamp.add(Functions.getTimeStamp());
		ArrayList<String> rows = new ArrayList<String>();
		rows.add(player.getName() + Strings.BANK_PLAYER_ACCOUNT);
		rows.add("");
		for(int i = 0; i < inventory.size(); i++) {
			ItemStack item = inventory.get(i);
			rows.add(i + ". " + Functions.parseMaterial(item.getType()) + " " + item.getAmount());
		}
		Book.giveBook(player, new Book(player.getName() + Strings.BANK_PLAYER_ACCOUNT,
				Book.fitToBook(rows), timestamp));
	}
	
	public void accepted(Player player, String[] args) {
		ArrayList<String> rows = new ArrayList<String>();
		rows.add(Strings.BANK_ACCEPTED_ITEMS);
		rows.add("");
		for(Material mat : Values.BANK_ACCEPTED_ITEMS) {
			rows.add(Functions.parseMaterial(mat));
		}
		ArrayList<String> pages = Book.fitToBook(rows);
		pages.add(0, Strings.BANK_ACCEPTED_GUIDLINES);
		Book.giveBook(player, new Book(Strings.BANK_ACCEPTED_ITEMS, pages, null));
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