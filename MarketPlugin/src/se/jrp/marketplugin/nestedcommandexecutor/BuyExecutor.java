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
			if(MarketPrices.isForSale(material)) {
				double price = MarketPrices.getBuyPrice(material);
				if(MarketPlugin.economy.has(player.getName(), price)) {
					Integer amount = MarketPlugin.getInteger(args[i + 1], null);
					if(amount != null) {
						int added = (int) Math.min(MarketPlugin.economy.getBalance(player.getName()) / price,
								Math.min(amount, PlayerInventoryAnalyzer.spaceFor(player.getInventory(), material)));
						ItemStack item = new ItemStack(material, added);
						player.getInventory().addItem(item);
						double cost = added * price;
						MarketPlugin.economy.withdrawPlayer(player.getName(), cost);
						
						String info = added == amount ? Strings.INFO_BOUGHT : Strings.INFO_ONLY_BOUGHT;
						player.sendMessage(String.format(info, MaterialParser.instance().getFullParsedName(item), cost));
					} else return false;
				} else player.sendMessage(Strings.ERROR_TOO_POOR);
			} else player.sendMessage(String.format(Strings.ERROR_NOT_FOR_SALE, args[i]));
		}
		return true;
	}
	
}