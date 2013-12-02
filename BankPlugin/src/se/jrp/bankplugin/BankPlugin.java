package se.jrp.bankplugin;

import se.jrp.bankplugin.filemanager.Config;
import se.jrp.bankplugin.commandexecutors.BankStoreCommandExecutor;
import se.jrp.bankplugin.commandexecutors.BankHelpCommandExecutor;
import se.jrp.bankplugin.commandexecutors.BankListCommandExecutor;
import se.jrp.bankplugin.commandexecutors.BankAcceptedCommandExecutor;
import se.jrp.bankplugin.commandexecutors.BankCommandExecutor;
import se.jrp.bankplugin.commandexecutors.BankGetCommandExecutor;
import java.io.File;
import java.util.HashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import se.jrp.bankplugin.filemanager.FileManager;
import se.jrp.bankplugin.filemanager.FileManipulator;
import se.jrp.bankplugin.resources.Functions;
import se.jrp.bankplugin.resources.Strings;

public class BankPlugin extends JavaPlugin implements CommandExecutor {
	public static String directory;
	public static BankInventory inventory = new BankInventory();
	public static AcceptedItems acceptedItems = new AcceptedItems();
	public static Config config = new Config();
	public HashMap<String, BankCommandExecutor> commandExecutors = new HashMap<>();
	
	public static void main(String[] args) {
    }
	
	@Override
    public void onEnable() {
		directory = getDataFolder() + File.separator;
		
		config.addProperty(Strings.PROPERTIES_MAX_SLOTS, "54");
		config.addProperty(Strings.PROPERTIES_INVERT_ACCEPTED, "false");
		config.addProperty(Strings.PROPERTIES_EVERYTHING_ACCEPTED, "false");
		
		commandExecutors.put(Strings.COMMAND_BANK_GET, new BankGetCommandExecutor());
        commandExecutors.put(Strings.COMMAND_BANK_STORE, new BankStoreCommandExecutor());
        commandExecutors.put(Strings.COMMAND_BANK_LIST, new BankListCommandExecutor());
		commandExecutors.put(Strings.COMMAND_BANK_ACCEPTED, new BankAcceptedCommandExecutor());
		commandExecutors.put(Strings.COMMAND_BANK_HELP, new BankHelpCommandExecutor());
		
        FileManager.onEnable(new FileManipulator[] {
			config.getManipulator(Strings.FILE_CONFIG), acceptedItems.getManipulator(Strings.FILE_ACCEPTED),
			inventory.getManipulator(Strings.FILE_BANK)});
	}
 
    @Override
    public void onDisable() {
    	FileManager.onDisable();
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