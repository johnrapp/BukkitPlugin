package se.jrp.playertrading.resources;

import org.bukkit.ChatColor;

public class Strings {	
	public final static String FILE_AUCTIONS = "auctions";
	
	private final static String DEFAULT_COMMAND_AUCTION = "/auction ";
	
	private final static String CURRENCY = "Dollars";
	
	public final static String COMMAND_AUCTION = "auction";
	public final static String COMMAND_AUCTION_NEW = "new";
	public final static String COMMAND_AUCTION_ALL = "all";
	public final static String COMMAND_AUCTION_DELETE = "delete";
	public final static String COMMAND_AUCTION_BID = "bid";
	
	public final static String FORMAT_AUCTION_SEGMENT_INFO = "#%d\n" + "%s\n%s\n";
	public final static String FORMAT_AUCTION_SEGMENT_BID = "Current bid:\n	%s\n  %s " + CURRENCY + "\n"
			+ "  (%s per item)\n";
	public final static String FORMAT_AUCTION_SEGMENT_NO_BID = "No bid yet:\n  Min bid:\n  %s " + CURRENCY + "\n";
	public final static String FORMAT_AUCTION_SEGMENT_DESIRED = "Desired price:\n  %s " + CURRENCY;
	public final static String FORMAT_AUCTION_SEGMENT_NO_DESIRED = "Desired price:\n  none";

	public final static String ERROR_NO_VAULT = "Disabled due to no Vault dependency found!";
	public final static String ERROR_INCORRECT_USAGE = ChatColor.RED + "Incorrect usage, instead use:\n";
	public final static String ERROR_NON_PLAYER = ChatColor.RED + "You must be a player to issue this command.";
	public final static String ERROR_NOTHING_IN_HAND = ChatColor.RED + "You have nothing in your hand.";

	public final static String ERROR_AUCTION_DOESNT_EXIST = ChatColor.RED + "Auction #%s doesn't exist.";
	public final static String ERROR_AUCTION_CANT_DELETE = ChatColor.RED + "Auction #%s doesn't exist or is not yours.";

	public final static String INFO_AUCTION_CREATED = "Your auction was created successfully as #%s.";
	public final static String INFO_AUCTION_DELETED = "Auction #%s was deleted successfully.";
	
	public final static String USAGE_AUCTION_NEW = DEFAULT_COMMAND_AUCTION + COMMAND_AUCTION_NEW
			+ " (start price) (desired price)";
	public final static String HELP_AUCTION_NEW = "Create an auction for the item in your hand.";
	
	public final static String USAGE_AUCTION_ALL = DEFAULT_COMMAND_AUCTION + COMMAND_AUCTION_ALL;
	public final static String HELP_AUCTION_ALL = "View all auctions.";
	
	public final static String USAGE_AUCTION_DELETE = DEFAULT_COMMAND_AUCTION + COMMAND_AUCTION_DELETE + " <auction number> ...";
	public final static String HELP_AUCTION_DELETE = "Delete one or several auctions.";
	
	public final static String USAGE_AUCTION_BID = DEFAULT_COMMAND_AUCTION + COMMAND_AUCTION_BID + " <auction number> <bid>";
	public final static String HELP_AUCTION_BID = "Bid on an auction.";
}