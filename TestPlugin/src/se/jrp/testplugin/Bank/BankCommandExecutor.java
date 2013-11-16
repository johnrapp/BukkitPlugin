package se.jrp.testplugin.Bank;

import org.bukkit.entity.Player;

public abstract class BankCommandExecutor {
	Bank bank;
	public BankCommandExecutor(Bank bank) {
		this.bank = bank;
	}
	
	public abstract void onCommand(Player player, String[] args);
}