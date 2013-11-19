package se.jrp.marketplugin;

import java.io.File;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import se.jrp.marketplugin.filemanager.FileManager;
import se.jrp.marketplugin.filemanager.FileManipulator;
import se.jrp.marketplugin.nestedcommandexecutor.BuyExecutor;
import se.jrp.marketplugin.nestedcommandexecutor.NestedCommandExecutor;
import se.jrp.marketplugin.nestedcommandexecutor.SellExecutor;
import se.jrp.marketplugin.resources.Strings;
import se.jrp.marketplugin.resources.Values;

public class MarketPlugin extends JavaPlugin implements CommandExecutor {
	public HashMap<String, NestedCommandExecutor> commandExecutors = new HashMap<>();
	public static MarketPrices prices = new MarketPrices();

    public static void main(String[] args) {
    }
    @Override
    public void onEnable() {
		Values.init();
		commandExecutors.put(Strings.COMMAND_BUY, new BuyExecutor());
		commandExecutors.put(Strings.COMMAND_SELL, new SellExecutor());
		FileManager.onEnable(getDataFolder() + File.separator, new FileManipulator[] {
			prices.getManipulator(Strings.FILE_PRICES)});
////        Values.init();
//        HashMap<String, FileSubscriber> map = new HashMap<>();
//        map.put(Strings.FILE_SAPLING_WALK, new SaplingWalk());
//        FileManager.onEnable(getDataFolder() + File.separator, map);
////    	listener = new LogBlockBreakListener();
////    	commandExecutor = new TestPluginCommandExecutor();
    }
 
    @Override
    public void onDisable() {
    	FileManager.onDisable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Strings.ERROR_NON_PLAYER);
			return true;
		}
		return true;		
    }
	
	public static int totalGold(PlayerInventory inventory) {
		int gold = 0;
		for(ItemStack nugget : inventory.all(Material.GOLD_NUGGET).values()) {
			gold += nugget.getAmount();
		}
		for(ItemStack ingot : inventory.all(Material.GOLD_INGOT).values()) {
			gold += ingot.getAmount() * 9;
		}
		for(ItemStack block : inventory.all(Material.GOLD_BLOCK).values()) {
			gold += block.getAmount() * 81;
		}
		return gold;
	}
	
	public static int totalGold(int nuggets, int ingots, int blocks) {
		return nuggets + ingots * 9 + blocks * 81;
	}
}
