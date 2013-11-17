package se.jrp.bankplugin.commandexecutors;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.entity.Player;
import se.jrp.bankplugin.BankPlugin;

import se.jrp.bankplugin.resources.Book;
import se.jrp.bankplugin.resources.Strings;
import se.jrp.bankplugin.resources.Values;

public class BankHelpCommandExecutor extends BankCommandExecutor  {

	public BankHelpCommandExecutor(BankPlugin bank) {
		super(bank);
	}
	
    @Override
	public void onCommand(Player player, String[] args) {
		Book.giveBook(player, new Book(Strings.BANK_HELP_BOOK_TITLE,
			new ArrayList<>(Arrays.asList(Values.BANK_HELP_PAGES)), null));
	}
}