package se.jrp.playertrading.auction;

public class Bid {
	public Auction auction;
	public String bidder;
	public double bid;

	public Bid(Auction auction, String bidder, double bid) {
		this.auction = auction;
		this.bidder = bidder;
		this.bid = bid;
	}
	
}