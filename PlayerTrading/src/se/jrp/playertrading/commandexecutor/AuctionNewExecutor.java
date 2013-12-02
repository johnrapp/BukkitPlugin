package se.jrp.playertrading.commandexecutor;

import org.bukkit.entity.Player;
import se.jrp.playertrading.PlayerTrading;
import se.jrp.playertrading.resources.Strings;

public class AuctionNewExecutor extends NestedCommandExecutor {

	public AuctionNewExecutor() {
		super(Strings.USAGE_AUCTION_NEW, Strings.HELP_AUCTION_NEW, 0, 2);
	}

	@Override
	protected boolean execute(Player player, String[] args) {
		if(player.getItemInHand().getAmount() > 0) {
			int id = PlayerTrading.auctions.addAuction(player.getName(), player.getItemInHand(),
				args.length > 0 ? PlayerTrading.getDouble(args[0], 0.0) : 0.0,
				args.length > 1 ? PlayerTrading.getDouble(args[1], 0.0) : 0.0);
			player.setItemInHand(null);
			player.sendMessage(String.format(Strings.INFO_AUCTION_CREATED, id));
		} else player.sendMessage(Strings.ERROR_NOTHING_IN_HAND);
		return true;
	}

}