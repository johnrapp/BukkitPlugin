package se.jrp.playertrading.commandexecutor;

import org.bukkit.entity.Player;
import se.jrp.playertrading.resources.Strings;

public abstract class NestedCommandExecutor {
    public String usage;
    public String help;
    public int min;
    public int max;

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
	protected abstract boolean execute(Player player, String[] args);
}
