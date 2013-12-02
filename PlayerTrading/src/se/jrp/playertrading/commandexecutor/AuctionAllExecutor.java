package se.jrp.playertrading.commandexecutor;

import org.bukkit.entity.Player;
import se.jrp.playertrading.PlayerTrading;
import se.jrp.playertrading.resources.Strings;

public class AuctionAllExecutor extends NestedCommandExecutor {

	public AuctionAllExecutor() {
		super(Strings.USAGE_AUCTION_ALL, Strings.HELP_AUCTION_ALL, 0, 0);
	}

	@Override
	protected boolean execute(Player player, String[] args) {
		PlayerTrading.sendAsMultipleMessages(player, PlayerTrading.auctions.toString());
		return true;
	}

}