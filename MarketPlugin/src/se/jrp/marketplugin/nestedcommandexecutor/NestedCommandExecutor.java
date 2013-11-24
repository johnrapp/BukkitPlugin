package se.jrp.marketplugin.nestedcommandexecutor;

import org.bukkit.entity.Player;
import se.jrp.marketplugin.resources.Strings;

public abstract class NestedCommandExecutor {
    String usage;
    String help;
    int min;
    int max;

	public NestedCommandExecutor(String usage, String help, int min, int max) {
		this.usage = usage;
		this.min = min;
		this.max = max;
	}
		
    public void onCommand(Player player, String[] args) {
		boolean success = args.length >= min && (args.length <= max || max == -1);
        if(!success || !execute(player, args)) {
			player.sendMessage(Strings.ERROR_INCORRECT_USAGE + usage);
		}
	}
	public abstract boolean execute(Player player, String[] args);
}
