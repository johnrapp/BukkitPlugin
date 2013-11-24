package se.jrp.marketplugin.nestedcommandexecutor;

import org.bukkit.entity.Player;

public class PriceExecutor extends NestedCommandExecutor {

	public PriceExecutor() {
		super("", "", 0, -1);
	}

	@Override
	public boolean execute(Player player, String[] args) {
		return false;
	}

}