package se.jrp.marketplugin.resources;

import org.bukkit.ChatColor;

public class Strings {
	public final static String COMMAND_BUY = "buy";
	public final static String COMMAND_SELL = "sell";
	public final static String COMMAND_PRICE = "price";
	
	public final static String ERROR_INCORRECT_USAGE = ChatColor.RED + "Incorrect usage, instead use:\n";
	public final static String ERROR_NON_PLAYER = ChatColor.RED + "You must be a player to issue this command.";
	
	public final static String SELL_USAGE = "/market sell";
	public final static String SELL_HELP = "Sell the item in hand for the market price.";
}