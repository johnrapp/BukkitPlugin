package se.jrp.playertrading;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import se.jrp.playertrading.auction.AuctionManager;
import se.jrp.playertrading.commandexecutor.AuctionAllExecutor;
import se.jrp.playertrading.commandexecutor.AuctionBidExecutor;
import se.jrp.playertrading.commandexecutor.AuctionDefaultExecutor;
import se.jrp.playertrading.commandexecutor.AuctionDeleteExecutor;
import se.jrp.playertrading.commandexecutor.AuctionNewExecutor;
import se.jrp.playertrading.commandexecutor.NestedCommandExecutor;
import se.jrp.playertrading.filemanager.FileManager;
import se.jrp.playertrading.filemanager.FileManipulator;
import se.jrp.playertrading.resources.Strings;

public class PlayerTrading extends JavaPlugin implements CommandExecutor {
	public static PlayerTrading instance;
	public static String directory;
	public static Economy economy = null;
	public static AuctionManager auctions = new AuctionManager();
	private final HashMap<String, HashMap<String, NestedCommandExecutor>> executors = new HashMap<>();
	private final HashMap<String, CommandExecutor> defaultExecutors = new HashMap<>();

	public static void main(String[] args) {
	}

	@Override
	public void onEnable() {
		if(!setupEconomy() ) {
            getLogger().severe(Strings.ERROR_NO_VAULT);
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		instance = this;
		directory = getDataFolder() + File.separator;
		FileManager.onEnable(new FileManipulator[] {auctions.getManipulator(Strings.FILE_AUCTIONS)});
		
		HashMap<String, NestedCommandExecutor> auctionExecutors = new HashMap<>();
		executors.put(Strings.COMMAND_AUCTION, auctionExecutors);
		auctionExecutors.put(Strings.COMMAND_AUCTION_NEW, new AuctionNewExecutor());
		auctionExecutors.put(Strings.COMMAND_AUCTION_ALL, new AuctionAllExecutor());
		auctionExecutors.put(Strings.COMMAND_AUCTION_DELETE, new AuctionDeleteExecutor());
		auctionExecutors.put(Strings.COMMAND_AUCTION_BID, new AuctionBidExecutor());
		
		defaultExecutors.put(Strings.COMMAND_AUCTION, new AuctionDefaultExecutor());
	}

	@Override
	public void onDisable() {
		if(economy != null)	FileManager.onDisable();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length > 0 && executors.containsKey(label) && executors.get(label).containsKey(args[0])) {
				executors.get(label).get(args[0]).onCommand(player, Arrays.copyOfRange(args, 1, args.length));
				return true;
			} else if(defaultExecutors.containsKey(label)) {
				return defaultExecutors.get(label).onCommand(sender, command, label, args);
			}
		} else {
			sender.sendMessage(Strings.ERROR_NON_PLAYER);
			return true;
		}
		return false;
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
	
	public static void sendAsMultipleMessages(Player player, String messages) {
		for(String message : messages.split("\n"))
			player.sendMessage(message);
	}
	
	public static Integer getInteger(String s, Integer defaultValue) {
	    try { return Integer.parseInt(s); }
	    catch(NumberFormatException e) { return defaultValue; }
	}
	
	public static Double getDouble(String s, Double defaultValue) {
	    try { return Double.parseDouble(s); }
	    catch(NumberFormatException e) { return defaultValue; }
	}
	
	public static Boolean getBoolean(String s, Boolean defaultValue) {
	    try { return Boolean.parseBoolean(s); }
	    catch(NumberFormatException e) { return defaultValue; }
	}
}
