package se.jrp.playertrading.auction;

import java.io.Serializable;
import se.jrp.playertrading.resources.CardboardBox;

/*
* A serializable Auction
*/

public class SerializedAuction implements Serializable {
	private static final long serialVersionUID = 123321123321007133L;
	public int id;
	public String creator;
	public CardboardBox item;
	public double min;
	public double desired;
	public Bid currentBid;

	public SerializedAuction(int id, String creator, CardboardBox item, double min, double desired, Bid currentBid) {
		this.id = id;
		this.creator = creator;
		this.item = item;
		this.min = min;
		this.desired = desired;
		this.currentBid = currentBid;
	}
	
	public Auction deSerialize() {
		return new Auction(id, creator, item.unbox(), min, desired, currentBid);
	}

}