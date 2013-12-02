package se.jrp.playertrading.auction;

import java.util.ArrayList;
import org.bukkit.inventory.ItemStack;
import se.jrp.playertrading.PlayerTrading;
import se.jrp.playertrading.filemanager.FileManipulator;
import se.jrp.playertrading.filemanager.FileSubscriber;
import se.jrp.playertrading.filemanager.ObjectFileManipulator;

public class AuctionManager implements FileSubscriber {
	private final ArrayList<Auction> auctions = new ArrayList<>();
	
	public int addAuction(String creator, ItemStack item, double min, double desired) {
		int id = generateId();
		auctions.add(new Auction(id, creator, item, min, desired));
		return id;
	}
	public Auction getAuction(Integer id) {
		for(Auction auction : auctions) {
			if(auction.id == id) return auction;
		}
		return null;
	}
	
	public boolean deleteAuction(String player, Auction auction) {
		boolean delete = auction != null && auction.creator.equals(player);
		if(delete) auctions.remove(auction);
		return delete;
	}
	
	public boolean bid(String player, Auction auction, double bid) {
		if(bid >= auction.desired) return true;
		else auction.currentBid = new Bid(auction, player, bid);
		return false;
	}
	
	public int size() {
		return auctions.size();
	}
	
	private int generateId() {
		if(auctions.size() < 1) return 1;
		boolean found = false;
		int id = 0;
		while(!found) {
			++id;
			for(Auction auction : auctions) {
				found = auction.id != id && !found;
			}
		}
		return id;
	}

	@Override
	public void onLoad(String id, Object object) {
		ArrayList<SerializedAuction> serializedAuctions = (ArrayList<SerializedAuction>) object;
		for(SerializedAuction auction : serializedAuctions) {
			auctions.add(auction.deSerialize());
		}
	}

	@Override
	public Object onSave(String id) {
		ArrayList<SerializedAuction> serializedAuctions = new ArrayList<>();
		for(Auction auction : auctions) {
			serializedAuctions.add(auction.serialize());
		}
		return serializedAuctions;
	}

	@Override
	public Object getDefault(String id) {
		return new ArrayList<>();
	}

	@Override
	public FileManipulator getManipulator(String id) {
		return new ObjectFileManipulator(this, PlayerTrading.directory, id);
	}

	@Override
	public boolean isAutoSaving(String id) {
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sz = new StringBuilder();
		for(Auction s : auctions) {
			sz.append(s.toString()).append("\n");
		}
		return sz.toString();
	}

}