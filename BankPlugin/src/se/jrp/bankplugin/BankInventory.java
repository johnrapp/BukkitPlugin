package se.jrp.bankplugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import se.jrp.bankplugin.filemanager.CustomProperties;
import se.jrp.bankplugin.filemanager.FileManipulator;
import se.jrp.bankplugin.filemanager.FileSubscriber;
import se.jrp.bankplugin.filemanager.PropertiesFileManipulator;

import se.jrp.bankplugin.resources.MaterialParser;
import se.jrp.bankplugin.resources.PlayerInventoryAnalyzer;
import se.jrp.bankplugin.resources.Strings;
import se.jrp.bankplugin.resources.Values;

public class BankInventory extends HashMap<String, ArrayList<ItemStack>> implements FileSubscriber {
	public final static String DIVISION_SYMBOL = ":";
	public static ArrayList<Material> acceptedItems = new ArrayList<>();
	private PropertiesFileManipulator manipulator;
	
	public ArrayList<ItemStack> all(String key, Material mat) {
		ArrayList<ItemStack> result = new ArrayList<>();
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
		String name = MaterialParser.instance().getParsedName(mat);
		PlayerInventory inventory = player.getInventory();
		ArrayList<ItemStack> depositBox = get(player.getName());
		ArrayList<ItemStack> all = all(player.getName(), mat);
		if(all.size() < 1) {
			player.sendMessage(Strings.ERROR_BANK_NONE_OF_MATERIAL1 + name
				+ Strings.ERROR_BANK_NONE_OF_MATERIAL2);
		}
		while(all.size() > 0) {
			if(!PlayerInventoryAnalyzer.full(inventory, mat)) {
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
		
		player.sendMessage(Strings.ERROR_BANK_GET_NOT_ENOUGH1 + name
				+ Strings.ERROR_BANK_GET_NOT_ENOUGH2);
	}
	
	public boolean contains(String player, Material material) {
		for(ItemStack item : get(player)) {
			if(item.getType() == material) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean accepted(Material material) {
		for(Material mat : acceptedItems) if(mat == material) return true;
		return false;
	}
	
	@Override
	public void onLoad(String id, Object object) {
		CustomProperties prop = (CustomProperties) object;
		for(String key : prop.stringPropertyNames()) {
			if(Boolean.parseBoolean(prop.getProperty(key)))
				acceptedItems.add(MaterialParser.instance().getMaterial(key));
		}
		if(prop.size() < Material.values().length) {
			manipulator.save(acceptedItems);
		}
	}

	@Override
	public Object onSave(String id) {
		return null;
	}
	
	@Override
	public boolean isAutoSaving(String id) {
		return false;
	}

	@Override
	public Object getDefault(String id) {
		CustomProperties prop = generateFile(Values.DEFAULT_ACCEPTED_ITEMS);
		manipulator.save(prop);
		return prop;
	}
	
	public CustomProperties generateFile(List<Material> accepted) {
		CustomProperties prop = new CustomProperties();
		for(Material material : Material.values()) {
			prop.put(MaterialParser.instance().getName(material), Boolean.toString(accepted.contains(material)));
		}
		return prop;
	}

	@Override
	public FileManipulator getManipulator(String id) {
		if(manipulator == null) manipulator = new PropertiesFileManipulator(this, BankPlugin.directory, id);
		return manipulator;
	}
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
	
	/*public boolean enoughSpace(ArrayList<ItemStack> inventory, ItemStack item) {
		Material mat = item.getType();
		ArrayList<ItemStack> all = all(inventory, mat);
		int busy = 0;
		for(ItemStack itm : all) {
			busy += itm.getAmount();
		} 
		return ((all.size() * mat.getMaxStackSize() - busy) >= item.getAmount()) || inventory.size() < MAX_SLOTS;
	}*/