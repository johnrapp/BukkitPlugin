package se.jrp.marketplugin.nestedcommandexecutor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import se.jrp.marketplugin.MarketPrices;
import se.jrp.marketplugin.resources.MaterialParser;
import se.jrp.marketplugin.resources.Strings;

public class PriceExecutor extends NestedCommandExecutor {

	public PriceExecutor() {
		super(Strings.PRICE_USAGE, Strings.PRICE_HELP, 1, -1);
	}

	@Override
	public boolean execute(Player player, String[] args) {
		for(String arg : args) {
			Material material = MaterialParser.instance().getMaterial(arg);
			if(material != null) {
				String name = MaterialParser.instance().getParsedName(material);
				double buyPrice = MarketPrices.getBuyPrice(material);
				double sellPrice = MarketPrices.getSellPrice(material);
				player.sendMessage(buyPrice > 0 ? String.format(Strings.INFO_BUY_PRICE, name, buyPrice)
						: String.format(Strings.ERROR_NOT_FOR_SALE, name));
				player.sendMessage(sellPrice > 0 ? String.format(Strings.INFO_SELL_PRICE, name, sellPrice)
						: String.format(Strings.ERROR_NOT_SELLABLE, name));
			} else player.sendMessage(String.format(Strings.ERROR_NON_MATERIAL, arg));
		}
		return true;
	}

}