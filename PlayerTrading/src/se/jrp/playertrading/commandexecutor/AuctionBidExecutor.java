package se.jrp.playertrading.commandexecutor;

import org.bukkit.entity.Player;
import se.jrp.playertrading.PlayerTrading;
import se.jrp.playertrading.auction.Auction;
import se.jrp.playertrading.resources.Strings;

public class AuctionBidExecutor extends NestedCommandExecutor {

	public AuctionBidExecutor() {
		super(Strings.USAGE_AUCTION_BID, Strings.HELP_AUCTION_BID, 2, 2);
	}

	@Override
	protected boolean execute(Player player, String[] args) {
		Integer id = PlayerTrading.getInteger(args[0], null);
		Double bid = PlayerTrading.getDouble(args[1], null);
		if(id != null && bid != null) {
			Auction auction = PlayerTrading.auctions.getAuction(id);
			if(auction != null) {
				
			} else player.sendMessage(String.format(Strings.ERROR_AUCTION_DOESNT_EXIST, args[0]));
		} else return false;
		return true;
	}
}