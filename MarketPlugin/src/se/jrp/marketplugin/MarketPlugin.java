package se.jrp.marketplugin;

import net.milkbowl.vault.economy.Economy;

import java.io.File;
import java.util.Arrays;
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
import se.jrp.marketplugin.nestedcommandexecutor.PriceExecutor;
import se.jrp.marketplugin.nestedcommandexecutor.SellExecutor;
import se.jrp.marketplugin.nestedcommandexecutor.TestExecutor;
import se.jrp.marketplugin.resources.Strings;

public class MarketPlugin extends JavaPlugin implements CommandExecutor {
	public static MarketPlugin instance;
	public static MarketPrices prices = new MarketPrices();
	public static Economy economy = null;
	public static String directory;
	public static String pluginName;
	public HashMap<String, NestedCommandExecutor> commandExecutors = new HashMap<>();

    public static void main(String[] args) {
    }
    @Override
    public void onEnable() {
		instance = this;
		directory = getDataFolder() + File.separator;
		pluginName = getDescription().getName();
		if (!setupEconomy() ) {
            getLogger().severe("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		TestExecutor test = new TestExecutor();
		commandExecutors.put(Strings.COMMAND_BUY, new BuyExecutor());
		commandExecutors.put(Strings.COMMAND_SELL, new SellExecutor());
		commandExecutors.put(Strings.COMMAND_PRICE, new PriceExecutor());
		commandExecutors.put(Strings.COMMAND_TEST, test);

		FileManager.onEnable(new FileManipulator[] {
			prices.getManipulator(Strings.FILE_PRICES),
			test.getManipulator(Strings.FILE_TEST)});
    }
 
    @Override
    public void onDisable() {
		if(economy != null)
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
		} else if(args.length > 0 && commandExecutors.containsKey(args[0])) {
			commandExecutors.get(args[0]).onCommand((Player) sender, Arrays.copyOfRange(args, 1, args.length));
		} else {
			return false;		
		}
		return true;
	}
	
	public static Integer getInteger(String s, Integer defaultValue) {
	    try { return Integer.parseInt(s); }
	    catch(NumberFormatException e) { return defaultValue; }
	}
}
