package se.jrp.marketplugin.nestedcommandexecutor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import se.jrp.marketplugin.resources.Strings;

public class SellExecutor extends NestedCommandExecutor {

	public SellExecutor() {
		super(Strings.SELL_USAGE, Strings.SELL_HELP, 0, 0);
	}

	@Override
	public void execute(Player player, String[] args) {
		
	}

}