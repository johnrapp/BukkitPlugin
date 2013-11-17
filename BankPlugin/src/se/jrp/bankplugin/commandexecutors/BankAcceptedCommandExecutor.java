package se.jrp.bankplugin.commandexecutors;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import se.jrp.bankplugin.BankPlugin;

import se.jrp.bankplugin.resources.Book;
import se.jrp.bankplugin.resources.Functions;
import se.jrp.bankplugin.resources.Strings;
import se.jrp.bankplugin.resources.Values;

public class BankAcceptedCommandExecutor extends BankCommandExecutor {

	public BankAcceptedCommandExecutor(BankPlugin bank) {
		super(bank);
	}

	@Override
	public void onCommand(Player player, String[] args) {
		/*if(args.length < 1) {
			player.sendMessage(Strings.ERROR_BANK_NO_ARGUMENTS);*/
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