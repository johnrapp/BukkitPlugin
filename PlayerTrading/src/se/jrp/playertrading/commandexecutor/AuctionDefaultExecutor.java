package se.jrp.playertrading.commandexecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import se.jrp.playertrading.PlayerTrading;
import static se.jrp.playertrading.PlayerTrading.auctions;
import static se.jrp.playertrading.PlayerTrading.getInteger;
import se.jrp.playertrading.auction.Auction;
import se.jrp.playertrading.resources.Strings;

public class AuctionDefaultExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1) {
			Player player = (Player) sender;
			Integer id = getInteger(args[0], null);
			if(id != null) {
				Auction auction = auctions.getAuction(id);
				if(auction != null) {
					PlayerTrading.sendAsMultipleMessages(player, auction.toString());
				} else {
					player.sendMessage(String.format(Strings.ERROR_AUCTION_DOESNT_EXIST, id));
				}
				return true;
			}
		}
		return false;
	}

}