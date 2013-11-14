package se.jrp.testplugin.Bank;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import se.jrp.testplugin.Book;
import se.jrp.testplugin.Resources.Functions;
import se.jrp.testplugin.Resources.Strings;
import se.jrp.testplugin.Resources.Values;

public class BankHelpCommandExecutor extends BankCommandExecutor  {

	public BankHelpCommandExecutor(Bank bank) {
		super(bank);
	}
	
	public void onCommand(Player player, String[] args) {
		Book.giveBook(player, new Book(Strings.BANK_HELP_BOOK_TITLE,
			new ArrayList<String>(Arrays.asList(Values.BANK_HELP_PAGES)), null));
	}
}
