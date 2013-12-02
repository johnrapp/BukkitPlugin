package se.jrp.playertrading.auction;

import org.bukkit.inventory.ItemStack;
import se.jrp.playertrading.resources.CardboardBox;
import se.jrp.playertrading.resources.MaterialParser;
import se.jrp.playertrading.resources.Strings;

public class Auction {
	public int id;
	public String creator;
	public ItemStack item;
	double min;
	double desired;
	public Bid currentBid = null;

	public Auction(int id, String creator, ItemStack item, double min, double desired) {
		this.id = id;
		this.creator = creator;
		this.item = item;
		this.min = min;
		this.desired = desired;
	}
	
	public Auction(int id, String creator, ItemStack item, double min, double desired, Bid currentBid) {
		this.id = id;
		this.creator = creator;
		this.item = item;
		this.min = min;
		this.desired = desired;
		this.currentBid = currentBid;
	}
	
	public SerializedAuction serialize() {
		return new SerializedAuction(id, creator, new CardboardBox(item), min, desired, currentBid);
	}
	
	@Override
	public String toString() {
		return (String.format(Strings.FORMAT_AUCTION_SEGMENT_INFO, id, creator, MaterialParser.instance().getFullParsedName(item))) +
			(currentBid == null ? String.format(Strings.FORMAT_AUCTION_SEGMENT_NO_BID, min) :
			String.format(Strings.FORMAT_AUCTION_SEGMENT_BID, currentBid.bidder, currentBid.bid, currentBid.bid / item.getAmount())) +
			(desired > 0 ? String.format(Strings.FORMAT_AUCTION_SEGMENT_DESIRED, desired) : Strings.FORMAT_AUCTION_SEGMENT_NO_DESIRED);
	}
}

//return String.format(Strings.FORMAT_AUCTION_SEGMENT_INFO, id, creator, MaterialParser.instance().getFullParsedName(item)) +
//			currentBid == null ? String.format(Strings.FORMAT_AUCTION_SEGMENT_NO_BID, min) :
//			String.format(Strings.FORMAT_AUCTION_SEGMENT_BID, currentBid.bidder, currentBid.bid, currentBid.bid / item.getAmount()) +
//			(desired > 0 ? String.format(Strings.FORMAT_AUCTION_SEGMENT_DESIRED, desired) : Strings.FORMAT_AUCTION_SEGMENT_NO_DESIRED);

//	#23 *gray*
//	Johnrapp
//	5 Diamonds
//	Current bid:
//		Cotfsm
//		100 Dollars
//		(20 per item) *gray*
//	No bid:
//		Min bid 10 Dollars
//	Desired price:
//		200 Dollars
//	Desired price:
//		none