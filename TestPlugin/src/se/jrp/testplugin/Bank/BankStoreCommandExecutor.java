package se.jrp.testplugin.Bank;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import se.jrp.testplugin.Resources.Strings;
import se.jrp.testplugin.Resources.Values;

public class BankStoreCommandExecutor extends BankCommandExecutor {

	public BankStoreCommandExecutor(Bank bank) {
		super(bank);
	}

	public void onCommand(Player player, String[] args) {
		if(!(bank.bankInventory.containsKey(player.getName())))
			bank.bankInventory.put(player.getName(), new ArrayList<ItemStack>());
		ArrayList<ItemStack> inventory = bank.bankInventory.get(player.getName());
		if(args.length > 0) {
			player.sendMessage(Strings.ERROR_BANK_STORE);
			return;
		} else if(inventory.size() >= Values.BANK_MAX_SLOTS) {
			player.sendMessage(Strings.ERROR_BANK_FULL);
			return;
		}
		ItemStack item = player.getItemInHand();
		if(item.getAmount() <= 0) {
			player.sendMessage(Strings.ERROR_BANK_NOTHING_IN_HAND);
		} else {
			boolean accepted = Bank.itemAccepted(item.getType());
			if(accepted && bank.addItem(inventory, item, player)) {
	 			player.sendMessage(Strings.BANK_ADDED_ITEM);
			} else if(!accepted) {
				player.sendMessage(Strings.ERROR_BANK_NOT_ACCEPTED);
			}
		}
		
	}
	
}
