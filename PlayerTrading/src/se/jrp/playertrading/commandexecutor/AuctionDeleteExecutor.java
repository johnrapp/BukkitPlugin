package se.jrp.playertrading.commandexecutor;

import org.bukkit.entity.Player;
import se.jrp.playertrading.PlayerTrading;
import se.jrp.playertrading.resources.Strings;

public class AuctionDeleteExecutor extends NestedCommandExecutor {

	public AuctionDeleteExecutor() {
		super(Strings.USAGE_AUCTION_DELETE, Strings.HELP_AUCTION_DELETE, 1, -1);
	}

	@Override
	protected boolean execute(Player player, String[] args) {
		for(String arg : args) {
			Integer id = PlayerTrading.getInteger(arg, null);
			if(id != null && PlayerTrading.auctions.deleteAuction(player.getName(), PlayerTrading.auctions.getAuction(id))) {
				player.sendMessage(String.format(Strings.INFO_AUCTION_DELETED, id));
			} else player.sendMessage(String.format(Strings.ERROR_AUCTION_CANT_DELETE, arg));
		}
		return true;
	}

}