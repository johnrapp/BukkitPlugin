package se.jrp.bankplugin;

import se.jrp.bankplugin.commandexecutors.BankStoreCommandExecutor;
import se.jrp.bankplugin.commandexecutors.BankHelpCommandExecutor;
import se.jrp.bankplugin.commandexecutors.BankListCommandExecutor;
import se.jrp.bankplugin.commandexecutors.BankAcceptedCommandExecutor;
import se.jrp.bankplugin.commandexecutors.BankCommandExecutor;
import se.jrp.bankplugin.commandexecutors.BankGetCommandExecutor;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import se.jrp.bankplugin.filemanager.HumanReadableFileManipulator;
import se.jrp.bankplugin.filemanager.FileManager;
import se.jrp.bankplugin.filemanager.FileManipulator;
import se.jrp.bankplugin.filemanager.FileSubscriber;
import se.jrp.bankplugin.filemanager.ObjectFileManipulator;
import se.jrp.bankplugin.resources.Functions;
import se.jrp.bankplugin.resources.Strings;
import se.jrp.bankplugin.resources.Values;

public class BankPlugin extends JavaPlugin implements CommandExecutor, FileSubscriber {
	public BankInventory inventory = new BankInventory();
	public HashMap<String, BankCommandExecutor> commandExecutors = new HashMap<>();
	public HashMap<String, HashMap<Material, Integer>> defaultObject = new HashMap<>();
	
	public static void main(String[] args) {
    }
	
	@Override
    public void onEnable() {
        Values.init();
        FileManager.onEnable(getDataFolder() + File.separator, new FileManipulator[] {
				//getManipulator(Strings.FILE_BANK), :))) <-- rolig smily
			inventory.getManipulator(Strings.FILE_ACCEPTED)});
        getCommand(Strings.COMMAND_BANK).setExecutor(this);
		commandExecutors.put(Strings.COMMAND_BANK_GET, new BankGetCommandExecutor(this));
        commandExecutors.put(Strings.COMMAND_BANK_STORE, new BankStoreCommandExecutor(this));
        commandExecutors.put(Strings.COMMAND_BANK_LIST, new BankListCommandExecutor(this));
		commandExecutors.put(Strings.COMMAND_BANK_ACCEPTED, new BankAcceptedCommandExecutor(this));
		commandExecutors.put(Strings.COMMAND_BANK_HELP, new BankHelpCommandExecutor(this));
	}
 
    @Override
    public void onDisable() {
    	FileManager.onDisable();
    }
	
	@Override
	public void onLoad(String id, Object object) {
		HashMap<String, HashMap<Material, Integer>> serializedMap = (HashMap<String, HashMap<Material, Integer>>) object;
		for(Entry entry : serializedMap.entrySet()) {
			inventory.put((String) entry.getKey(), deSerialize((ArrayList<HashMap<Material, Integer>>) entry.getValue()));
		}
	}
	@Override
	public HashMap onSave(String id) {
		HashMap<String, ArrayList<HashMap<Material, Integer>>> serializedMap = new HashMap<>();
		for(Entry<String, ArrayList<ItemStack>> entry : inventory.entrySet()) {
			serializedMap.put(entry.getKey(), serialize(entry.getValue()));
		}
		return serializedMap;
	}
	
	public ArrayList<HashMap<Material, Integer>> serialize(ArrayList<ItemStack> items) {
		ArrayList<HashMap<Material, Integer>> result = new ArrayList<>();
		for(ItemStack item : items) {
            HashMap<Material, Integer> map = new HashMap<>();
            map.put(item.getType(), item.getAmount());
            result.add(map);
		}
		return result;
	}
	
	public ArrayList<ItemStack> deSerialize(ArrayList<HashMap<Material, Integer>> items) {
		ArrayList<ItemStack> result = new ArrayList<>();
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

	@Override
	public Object getDefault(String id) {
		return defaultObject;
	}

	@Override
	public boolean isSaving(String id) {
		return true;
	}

	@Override
	public FileManipulator getManipulator(String id) {
		return new ObjectFileManipulator(this, id);
	}
}