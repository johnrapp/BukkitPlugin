package se.jrp.bankplugin.resources;

import org.bukkit.ChatColor;

public class Strings {
	public final static String PLUGIN_NAME = "BankPlugin";
	
	public final static String FILE_BANK = "bank";
	
	public final static String COMMAND_BANK = "bank";
	public final static String COMMAND_BANK_GET = "get";
	public final static String COMMAND_BANK_GET_INDEX = "index";
	public final static String COMMAND_BANK_STORE = "store";
	public final static String COMMAND_BANK_LIST = "list";
	public final static String COMMAND_BANK_ACCEPTED = "accepted";
	public final static String COMMAND_BANK_HELP = "help";
	
	
	public final static String ERROR_NON_PLAYER = ChatColor.RED + "You must be a player to issue this command.";
	public final static String ERROR_INVENTORY_FULL = ChatColor.RED + "Your inventory is full.";
	public final static String ERROR_NON_NUMBER = " is not a number.";
	public final static String ERROR_DONT_EXIST = " does not exist.";
	
	public final static String ERROR_BANK_GET_NO_ARGUMENTS = ChatColor.RED + "You didn't write what to get.\nType \"/bank help\" for help.";
	public final static String ERROR_BANK_NO_ARGUMENTS = ChatColor.RED + "You didn't write which bank command you wanted to use.\nType \"/bank help\" for help.";
	public final static String ERROR_BANK_GET_NOT_EVERYTHING = ChatColor.RED + "You don't have enough space in your inventory to store everything.";
	public final static String ERROR_BANK_GET_NON_NUMBER = " is not a number.\nIf you want to get an item by its name, type \"/bank get (example_item) (amount)\".\n"
			+ "If you want to know more, type \"/bank help\"";
	public final static String ERROR_BANK_GET_NOT_ENOUGH1 = ChatColor.RED + "You didn't have all the ";
	public final static String ERROR_BANK_GET_NOT_ENOUGH2 = " you asked for.";
	public final static String ERROR_BANK_STORE = ChatColor.RED + "You can only store what's in your hand. Just type \"/bank store\"";
	public final static String ERROR_BANK_STORE_NOT_EVERYTHING = ChatColor.RED + "Your Bank is too full to store everything.";
	public final static String ERROR_BANK_NOTHING_STORED = ChatColor.RED + "You don't have anything in the Bank.";
	public final static String ERROR_BANK_NOTHING_IN_HAND = ChatColor.RED + "You have nothing in your hand to store.";
	public final static String ERROR_BANK_FULL = ChatColor.RED + "Your Bank is full.";
	public final static String ERROR_BANK_NOT_ACCEPTED = ChatColor.RED + "The Bank does not accept this item.\nType \"/bank accepted\" to see a list of accepted items.";
	public final static String ERROR_BANK_NON_ITEM = " is not an item in your Bank inventory.\ntype \"/bank list\" to see your Bank inventory\n"
			+ "If you want to know more, type \"/bank help\"";
	public final static String ERROR_BANK_NONE_OF_MATERIAL1 = ChatColor.RED + "You have no ";
	public final static String ERROR_BANK_NONE_OF_MATERIAL2 = " in your inventory";
	
	public final static String BOOK_ADDED = "A book was added to your inventory.";
	
	public final static String BANK_LIST_MATERIAL1 = "You have ";
	public final static String BANK_LIST_MATERIAL2 = " in your Bank inventory.";
	public final static String BANK_HELP_BOOK_TITLE = "Bank tutorial";
	public final static String BANK_PLAYER_ACCOUNT = "'s Bank account";
	public final static String BANK_ADDED_ITEM = ChatColor.GREEN + "Item added successfully!";
	public final static String BANK_ACCEPTED_ITEMS = "List of accepted items";
	public final static String BANK_ACCEPTED_GUIDLINES = ChatColor.BLUE + "What's accepted in the Bank?\n\n" + 
			ChatColor.BLACK + "General rules:\n" + ChatColor.GRAY + "*All cooked food.\n" +
			"*Ingots and gems.\n" + "*No ores, tools or armor.\n" + "*Most rare stuff worth keeping.";
	
	
	public final static String BANK_HELP_PAGE1 = ChatColor.BLUE + "How to use the Bank\n\n" 
			+ ChatColor.BLACK + "/bank get (slot) ...\n"
				+ ChatColor.GRAY + "- Get a item by its slot.\n\n"
			+ ChatColor.BLACK + "/bank get (material) (amount) ...\n"
				+ ChatColor.GRAY + "- Get a item by its name.\n\n"
				+ "Use underscore instead of space in item names.";

	public final static String BANK_HELP_PAGE2 = ChatColor.GRAY + "Three dots after eachother means that you can stack arguments.\n\n"
			+ "Examples\n/bank get 0 1 5\n/bank get diamond 34 iron_ingot 54";
	
	public final static String BANK_HELP_PAGE3 = ChatColor.BLACK + "/bank store\n"
			+ ChatColor.GRAY + "- Store the item in your hand.\n\n"
			+ ChatColor.BLACK + "/bank accepted\n"
					+ ChatColor.GRAY + "- Get a book showing items accepted by the Bank.\n\n"
			+ ChatColor.BLACK + "/bank list\n"
					+ ChatColor.GRAY + "- Get a book showing your Bank inventory.";
	
	public final static String BANK_HELP_PAGE4 = ChatColor.BLACK + "/bank list (material) ...\n"
			+ ChatColor.GRAY + "- See how much of a perticular material you have.";
}
