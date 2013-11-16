package se.jrp.bankplugin.commandexecutors;

import org.bukkit.entity.Player;
import se.jrp.bankplugin.BankPlugin;

public abstract class BankCommandExecutor {
	BankPlugin bank;
	public BankCommandExecutor(BankPlugin bank) {
		this.bank = bank;
	}
	
	public abstract void onCommand(Player player, String[] args);
}