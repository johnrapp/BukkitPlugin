package se.jrp.marketplugin.resources;

import org.bukkit.ChatColor;

public class Strings {
	public final static String FILE_PRICES = "prices";

	public final static String COMMAND_BUY = "buy";
	public final static String COMMAND_SELL = "sell";
	public final static String COMMAND_PRICE = "price";
	
	public final static String ERROR_INCORRECT_USAGE = ChatColor.RED + "Incorrect usage, instead use:\n";
	public final static String ERROR_NON_PLAYER = ChatColor.RED + "You must be a player to issue this command.";
	public final static String ERROR_NOT_FOR_SALE = ChatColor.RED + "This item is not for sale.";
	public final static String ERROR_TOO_POOR = ChatColor.RED + "You don't have enough money to buy that.";
	public final static String ERROR_NON_MATERIAL = "is not a known material.";

	public final static String INFO_NOT_EVERYTHING_ADDED = "Your inventory was too full or you didn't have enough money to buy everything you wanted.";
	
	public final static String SELL_USAGE = "/market sell";
	public final static String SELL_HELP = "Sell the item in hand for the market price.\n";
	
	public final static String BUY_USAGE = "/market buy <material> <amount> ...";
	public final static String BUY_HELP = "Buy an item from the market.\n"
			+ "You can buy several items at once by stacking materials and amounts.\n"
			+ "Example: /market buy iron_ingot 5 cake 2";
	
	public final static String PRICE_USAGE = "/market price <material> ...";
	public final static String PRICE_HELP = "Get the price of a material.";
	
	public final static String PRICES_USAGE = "/market prices";
	public final static String PRICES_HELP = "Get a book containing all the prices.";
}