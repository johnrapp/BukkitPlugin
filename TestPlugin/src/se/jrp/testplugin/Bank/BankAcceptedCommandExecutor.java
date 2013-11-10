package se.jrp.testplugin.Bank;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import se.jrp.testplugin.Book;
import se.jrp.testplugin.Resources.Functions;
import se.jrp.testplugin.Resources.Strings;
import se.jrp.testplugin.Resources.Values;

public class BankAcceptedCommandExecutor extends BankCommandExecutor {

	public BankAcceptedCommandExecutor(Bank bank) {
		super(bank);
	}

	@Override
	public void onCommand(Player player, String[] args) {
		ArrayList<String> rows = new ArrayList<String>();
		rows.add(Strings.BANK_ACCEPTED_ITEMS);
		rows.add("");
		for(Material mat : Values.BANK_ACCEPTED_ITEMS) {
			rows.add(Functions.parseMaterial(mat));
		}
		ArrayList<String> pages = Book.fitToBook(rows);
		pages.add(0, Strings.BANK_ACCEPTED_GUIDLINES);
		Book.giveBook(player, new Book(Strings.BANK_ACCEPTED_ITEMS, pages, null));
	}

}
