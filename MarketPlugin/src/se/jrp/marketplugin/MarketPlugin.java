package se.jrp.marketplugin;

import net.milkbowl.vault.economy.Economy;

import java.io.File;
import java.util.HashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import se.jrp.marketplugin.filemanager.FileManager;
import se.jrp.marketplugin.filemanager.FileManipulator;
import se.jrp.marketplugin.nestedcommandexecutor.BuyExecutor;
import se.jrp.marketplugin.nestedcommandexecutor.NestedCommandExecutor;
import se.jrp.marketplugin.nestedcommandexecutor.SellExecutor;
import se.jrp.marketplugin.resources.Strings;
import se.jrp.marketplugin.resources.Values;

public class MarketPlugin extends JavaPlugin implements CommandExecutor {
	public static MarketPlugin instance;
	public static MarketPrices prices = new MarketPrices();
	public static Economy economy = null;
	public HashMap<String, NestedCommandExecutor> commandExecutors = new HashMap<>();

    public static void main(String[] args) {
    }
    @Override
    public void onEnable() {
		instance = this;
//		if (!setupEconomy() ) {
//            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
//            getServer().getPluginManager().disablePlugin(this);
//            return;
//        }
		Values.init();
		commandExecutors.put(Strings.COMMAND_BUY, new BuyExecutor());
		commandExecutors.put(Strings.COMMAND_SELL, new SellExecutor());
		FileManager.onEnable(getDataFolder() + File.separator, new FileManipulator[] {
			prices.getManipulator(Strings.FILE_PRICES)});
    }
 
    @Override
    public void onDisable() {
    	FileManager.onDisable();
    }
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Strings.ERROR_NON_PLAYER);
			return true;
		}
		return true;		
    }
}
