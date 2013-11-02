package se.jrp.testplugin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import se.jrp.testplugin.Resources.Strings;
import se.jrp.testplugin.Resources.Values;

public class Bank implements CommandExecutor, FileListener {
	private HashMap<String, ArrayList<ItemStack>> bankInventory = new HashMap<String, ArrayList<ItemStack>>();
	
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
		String[] params = {};
		if(args.length > 1)
			params = Arrays.copyOfRange(args, 1, args.length - 1);
		if(args[0].equalsIgnoreCase(Strings.COMMAND_BANK_GET)) {
			get(player, params);
			return true;
		} else if(args[0].equalsIgnoreCase(Strings.COMMAND_BANK_STORE)) {
			store(player, params);
			return true;
		} else if(args[0].equalsIgnoreCase(Strings.COMMAND_BANK_LIST)) {
			list(player, params);
			return true;
		} else if(args[0].equalsIgnoreCase(Strings.COMMAND_BANK_ACCEPTED)) {
			accepted(player, params);
			return true;
		} else if(args[0].equalsIgnoreCase(Strings.COMMAND_BANK_SEND)) {
			send(player, params);
			return true;			
		} else if(args[0].equalsIgnoreCase("clear")) {
			bankInventory.remove(player.getName());
			return true;
		}
		
		return false;
	}
	
	public void get(Player player, String[] args) {
		
	}
	
	public void store(Player player, String[] args) {
		if(!(bankInventory.containsKey(player.getName())))
			bankInventory.put(player.getName(), new ArrayList<ItemStack>());
		ItemStack item = player.getItemInHand();
		if(item.getAmount() <= 0) {
			player.sendMessage(Strings.ERROR_BANK_NOTHING_IN_HAND);
		} else {
			boolean accepted = itemAccepted(item.getType());
			if(accepted && addItem(bankInventory.get(player.getName()), item, player)) {
	 			player.sendMessage(Strings.BANK_ADDED_ITEM);
			} else if(!accepted) {
				player.sendMessage(Strings.ERROR_BANK_NOT_ACCEPTED);
			}
		}
	}
	
	public boolean itemAccepted(Material mat) {
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
				player.sendMessage(Strings.ERROR_BANK_NO_SLOTS);
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
		timestamp.add(getTimeStamp());
		ArrayList<String> pages = new ArrayList<String>();
		StringBuilder sz = new StringBuilder();
		int rows = 0;
		int page = 0;
		for(int i = 0; i < inventory.size(); i++) {
			ItemStack item = inventory.get(i);
			String s = i + ". " + item.getAmount() + "x " + item.getType().toString();
			int height = (int) Math.floor(s.length() / Values.BOOK_MAX_COLUMNS);
			rows += height;
			if(rows > Values.BOOK_MAX_ROWS) {
				pages.add(s);
				page++;
				rows = height;
			} else {
				
			}
			
		}
		pages.add(sz.toString());
		giveBook(player, new Book(Strings.PLUGIN_NAME, player.getName() + Strings.BANK_PLAYER_ACCOUNT, pages, timestamp));
	}
	
	public void accepted(Player player, String[] args) {
		
	}
	
	public void send(Player playerFrom, String[] args) {
		
	}
	
	public void giveBook(Player player, Book book) {
		if(player.getInventory().getSize() < player.getInventory().getMaxStackSize()) {
			player.getInventory().addItem(book);
			player.sendMessage(Strings.BOOK_ADDED);
		} else {
			player.sendMessage(Strings.ERROR_INVENTORY_FULL);
		}
	}
	
	public String getTimeStamp() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM HH:mm:ss");
		return format.format(new Date());
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