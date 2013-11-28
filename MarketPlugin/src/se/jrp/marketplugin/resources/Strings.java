package se.jrp.marketplugin.resources;

import org.bukkit.ChatColor;

public class Strings {
	private final static String CURRENCY = "Dollars";
	private final static String NAME = "Market";
	
	public final static String PRICES = "prices";
	public final static String TEST = "test";
	
	public final static String FILE_PRICES = PRICES;
	public static String FILE_TEST = TEST;

	private final static String COMMAND_BASIC = "/market";
	public final static String COMMAND_BUY = "buy";
	public final static String COMMAND_SELL = "sell";
	public final static String COMMAND_PRICE = "price";
	public final static String COMMAND_PRICES = PRICES;
	public final static String COMMAND_HELP = "help";
	public final static String COMMAND_TEST = TEST;
	
	public final static String COMMENT_PRICES = "The first value is the buy price and the second the sell price.";
	
	public final static String ERROR_INVENTORY_FULL = ChatColor.RED + "Your inventory is full.";
	public final static String ERROR_INCORRECT_USAGE = ChatColor.RED + "Incorrect usage, instead use:\n";
	public final static String ERROR_NON_PLAYER = ChatColor.RED + "You must be a player to issue this command.";
	public final static String ERROR_NOT_SELLABLE = ChatColor.RED + "The " + NAME + " is not interested in buying %s.";
	public final static String ERROR_NOT_FOR_SALE = ChatColor.RED + "%s is not for sale in the " + NAME + ".";
	public final static String ERROR_TOO_POOR = ChatColor.RED + "You don't have enough money to buy that.";
	public final static String ERROR_NOTHING_IN_HAND = ChatColor.RED + "You don't have anything in your hand.";
	public final static String ERROR_NON_MATERIAL = ChatColor.RED + "%s is not a known material.";

	public final static String INFO_BOOK_ADDED = "A book was added to your inventory.";
	
	public final static String INFO_BOUGHT = "You bought %s for %s " + CURRENCY + ".";
	public final static String INFO_ONLY_BOUGHT = "You could only buy %s for %s " + CURRENCY + ".";
	public final static String INFO_SOLD = "You sold %s for %s " + CURRENCY + ".";
	public final static String INFO_BUY_PRICE = "%s buy price: %s";
	public final static String INFO_SELL_PRICE = "%s sell price: %s";
	
	public final static String SELL_USAGE = COMMAND_BASIC + " sell";
	public final static String SELL_HELP = "Sell the item in hand for the market price.\n";
	
	public final static String BUY_USAGE = COMMAND_BASIC + " buy <material> <amount> ...";
	public final static String BUY_HELP = "Buy one or several items from the market.\n"
			+ "You can buy several items at once by stacking materials and amounts.\n"
			+ "Example: " + COMMAND_BASIC + " buy iron_ingot 5 cake 2";
	
	public final static String PRICE_USAGE = COMMAND_BASIC + " price <material> ...";
	public final static String PRICE_HELP = "Get the price of one or severeal materials.";
	
	public final static String PRICES_USAGE = COMMAND_BASIC + " prices";
	public final static String PRICES_HELP = "Get a book containing all the prices.";
}