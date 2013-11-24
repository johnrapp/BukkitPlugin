package se.jrp.marketplugin.nestedcommandexecutor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import se.jrp.marketplugin.MarketPlugin;
import se.jrp.marketplugin.MarketPrices;
import se.jrp.marketplugin.resources.MaterialParser;
import se.jrp.marketplugin.resources.PlayerInventoryAnalyzer;
import se.jrp.marketplugin.resources.Strings;

public class BuyExecutor extends NestedCommandExecutor {

	public BuyExecutor() {
		super(Strings.BUY_USAGE, Strings.BUY_HELP, 2, -1);
	}

	@Override
	public boolean execute(Player player, String[] args) {
		for(int i = 0; i < args.length; i += 2) {
			Material material = MaterialParser.instance().getMaterial(args[i]);
			double price = MarketPrices.getBuyPrice(material);
			if(price > 0) {
				if(MarketPlugin.economy.has(player.getName(), price)) {
					Integer amount = MarketPlugin.getInteger(args[i + 1], null);
					if(amount != null) {
						double added = Math.min(MarketPlugin.economy.getBalance(player.getName()) / price,
								Math.min(amount, PlayerInventoryAnalyzer.spaceFor(player.getInventory(), material)));
						player.getInventory().addItem(new ItemStack(material, (int) added));
						MarketPlugin.economy.withdrawPlayer(player.getName(), added * price);
						if(added < amount) {
							player.sendMessage(Strings.INFO_NOT_EVERYTHING_ADDED);
						}
						player.sendMessage(String.format(Strings.INFO_BALANCE, MarketPlugin.economy.getBalance(player.getName())));
					} else return false;
				} else player.sendMessage(Strings.ERROR_TOO_POOR);
			} else player.sendMessage(String.format(Strings.ERROR_NOT_FOR_SALE, args[i]));
		}
		return true;
	}
	
}