package se.jrp.marketplugin.nestedcommandexecutor;

import java.util.HashMap;
import org.bukkit.entity.Golem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import se.jrp.marketplugin.Currency;
import se.jrp.marketplugin.MarketPrices;
import se.jrp.marketplugin.resources.Strings;

public class SellExecutor extends NestedCommandExecutor {

	public SellExecutor() {
		super(Strings.SELL_USAGE, Strings.SELL_HELP, 0, 0);
	}

	@Override
	public void execute(Player player, String[] args) {
		HashMap<Integer, ItemStack> leftover = player.getInventory().
				addItem(new ItemStack(Currency.currency, player.getItemInHand().getAmount() * MarketPrices.));
	}

}