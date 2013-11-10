package se.jrp.testplugin.Bank;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import se.jrp.testplugin.Book;
import se.jrp.testplugin.Resources.Functions;
import se.jrp.testplugin.Resources.Strings;

public class BankListCommandExecutor extends BankCommandExecutor {

	public BankListCommandExecutor(Bank bank) {
		super(bank);
	}

	@Override
	public void onCommand(Player player, String[] args) {
		ArrayList<ItemStack> inventory;
		if(!bank.inventory.containsKey(player.getName()) || (inventory = bank.inventory.get(player.getName())).size() < 1) {
			player.sendMessage(Strings.ERROR_BANK_NOTHING_STORED);
			return;
		}
		ArrayList<String> timestamp = new ArrayList<String>();
		timestamp.add(Functions.getTimeStamp());
		ArrayList<String> rows = new ArrayList<String>();
		rows.add(player.getName() + Strings.BANK_PLAYER_ACCOUNT);
		rows.add("");
		for(int i = 0; i < inventory.size(); i++) {
			ItemStack item = inventory.get(i);
			rows.add(i + ". " + Functions.parseMaterial(item.getType()) + " " + item.getAmount());
		}
		Book.giveBook(player, new Book(player.getName() + Strings.BANK_PLAYER_ACCOUNT,
				Book.fitToBook(rows), timestamp));
	}

}
