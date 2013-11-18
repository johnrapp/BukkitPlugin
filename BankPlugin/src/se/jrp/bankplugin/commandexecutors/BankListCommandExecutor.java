package se.jrp.bankplugin.commandexecutors;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import se.jrp.bankplugin.BankPlugin;

import se.jrp.bankplugin.resources.Book;
import se.jrp.bankplugin.resources.Functions;
import se.jrp.bankplugin.resources.Strings;

public class BankListCommandExecutor extends BankCommandExecutor {

	public BankListCommandExecutor(BankPlugin bank) {
		super(bank);
	}

	@Override
	public void onCommand(Player player, String[] args) {
		ArrayList<ItemStack> inventory;
		if(!bank.inventory.containsKey(player.getName()) || (inventory = bank.inventory.get(player.getName())).size() < 1) {
			player.sendMessage(Strings.ERROR_BANK_NOTHING_STORED);
			return;
		}
		if(args.length > 0) {
			ArrayList<ItemStack> depositBox = bank.inventory.get(player.getName());
			for(String arg : args) {
				Material mat = Functions.getMaterialFromName(arg);
				if(mat != null && bank.inventory.contains(player.getName(), mat)) {
					int amount = 0;
					for(ItemStack is : depositBox) {
						if(is.getType() == mat) {
							amount += is.getAmount();
						}
					}
					player.sendMessage(Strings.BANK_LIST_MATERIAL1 + amount + " "
					+ (amount > 1 ? Functions.parseMaterialPlural(mat) : Functions.parseMaterial(mat)) + Strings.BANK_LIST_MATERIAL2);
				} else {
					player.sendMessage(ChatColor.RED + arg + Strings.ERROR_BANK_NON_ITEM);
				}
			}
		} else {
			ArrayList<String> timestamp = new ArrayList<>();
			timestamp.add(Functions.getTimeStamp());
			ArrayList<String> rows = new ArrayList<>();
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

}
