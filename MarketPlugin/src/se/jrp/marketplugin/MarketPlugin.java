package se.jrp.marketplugin;

import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import se.jrp.marketplugin.nestedcommandexecutor.BuyExecutor;
import se.jrp.marketplugin.nestedcommandexecutor.NestedCommandExecutor;
import se.jrp.marketplugin.nestedcommandexecutor.SellExecutor;
import se.jrp.marketplugin.resources.Strings;

public class MarketPlugin extends JavaPlugin implements CommandExecutor {
    public static MarketPlugin instance;
	public HashMap<String, NestedCommandExecutor> commandExecutors = new HashMap<>();

    public static void main(String[] args) {
    }
    @Override
    public void onEnable() {
		instance = this;
		commandExecutors.put(Strings.COMMAND_BUY, new BuyExecutor());
		commandExecutors.put(Strings.COMMAND_SELL, new SellExecutor());
////        Values.init();
//        HashMap<String, FileSubscriber> map = new HashMap<>();
//        map.put(Strings.FILE_SAPLING_WALK, new SaplingWalk());
//        FileManager.onEnable(getDataFolder() + File.separator, map);
////    	listener = new LogBlockBreakListener();
////    	commandExecutor = new TestPluginCommandExecutor();
    }
 
    @Override
    public void onDisable() {
//    	FileManager.onDisable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Strings.ERROR_NON_PLAYER);
			return true;
		}
		return true;		
    }
	
	public int getSellPrice(Material material) {
		return 1;
	}
	
	public int getBuyPrice(Material material) {
		return 1;
	}
    
    public static void debug(String text) {
    	instance.getLogger().info(text);
    }
}
