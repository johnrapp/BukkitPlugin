package se.jrp.bankplugin.commandexecutors;

import org.bukkit.entity.Player;

public interface BankCommandExecutor {
	public void onCommand(Player player, String[] args);
}