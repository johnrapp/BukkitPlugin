package se.jrp.testplugin.Resources;

import org.bukkit.ChatColor;

public class Strings {
	public final static String PLUGIN_NAME = "TestPlugin";
	
	public final static String FILE_BANK = "bank";
	public final static String FILE_SAPLING_WALK = "saplingwalk";
	
	public final static String COMMAND_BANK = "bank";
	public final static String COMMAND_BANK_GET = "get";
	public final static String COMMAND_BANK_STORE = "store";
	public final static String COMMAND_BANK_LIST = "list";
	public final static String COMMAND_BANK_ACCEPTED = "accepted";
	public final static String COMMAND_BANK_SEND = "send";
	
	public final static String COMMAND_SAPLING_WALK = "saplingwalk";
	
	public final static String ERROR_NON_PLAYER = ChatColor.RED + "You must be a player to issue this command.";
	public final static String ERROR_INVENTORY_FULL = ChatColor.RED + "Your inventory is full.";
	
	public final static String ERROR_BANK_NOTHING_STORED = ChatColor.RED + "You don't have anything in the Bank.";
	public final static String ERROR_BANK_NOTHING_IN_HAND = ChatColor.RED + "You have nothing in your hand to store.";
	public final static String ERROR_BANK_NO_SLOTS = ChatColor.RED + "Your Bank is too full to store everything.";
	public final static String ERROR_BANK_NOT_ACCEPTED = ChatColor.RED + "The Bank does not accept this item.\nType \"/bank accepted\" to see a list of accepted items.";
	
	public final static String BOOK_ADDED = ChatColor.GREEN + "A book was added to your inventory.";
	
	public final static String BANK_PLAYER_ACCOUNT = "'s Bank account:";
	public final static String BANK_ADDED_ITEM = ChatColor.GREEN + "Item added successfully!";
}
