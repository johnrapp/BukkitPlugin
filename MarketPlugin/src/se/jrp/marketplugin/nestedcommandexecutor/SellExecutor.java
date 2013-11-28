package se.jrp.marketplugin.nestedcommandexecutor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import se.jrp.marketplugin.MarketPlugin;
import se.jrp.marketplugin.MarketPrices;
import se.jrp.marketplugin.resources.MaterialParser;
import se.jrp.marketplugin.resources.Strings;

public class SellExecutor extends NestedCommandExecutor {

	public SellExecutor() {
		super(Strings.SELL_USAGE, Strings.SELL_HELP, 0, 0);
	}

	@Override
	public boolean execute(Player player, String[] args) {
		ItemStack item = player.getItemInHand();
		if(item.getAmount() > 0) {
			if(MarketPrices.isPurshasable(item.getType())) {
				double earned = MarketPrices.getSellPrice(item.getType()) * item.getAmount();
				MarketPlugin.economy.depositPlayer(player.getName(), earned);
				player.getInventory().setItemInHand(null);
				player.sendMessage(String.format(Strings.INFO_SOLD, MaterialParser.instance().getFullParsedName(item), earned));
			} else player.sendMessage(String.format(Strings.ERROR_NOT_SELLABLE, MaterialParser.instance().getParsedName(item.getType())));
		} else player.sendMessage(Strings.ERROR_NOTHING_IN_HAND);
		return true;
	}

}