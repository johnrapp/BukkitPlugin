package se.jrp.marketplugin.nestedcommandexecutor;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import se.jrp.marketplugin.MarketPlugin;
import se.jrp.marketplugin.MarketPrices;
import se.jrp.marketplugin.resources.Strings;

public class SellExecutor extends NestedCommandExecutor {

	public SellExecutor() {
		super(Strings.SELL_USAGE, Strings.SELL_HELP, 0, 0);
	}

	@Override
	public boolean execute(Player player, String[] args) {
		ItemStack item = player.getItemInHand();
		if(MarketPrices.isForSale(item.getType())) {
			MarketPlugin.economy.depositPlayer(player.getName(), MarketPrices.getSellPrice(item.getType()) * item.getAmount());
			player.getInventory().remove(item);
		} else {
			player.sendMessage(Strings.ERROR_NOT_FOR_SALE);
		}
		return false;
	}

}