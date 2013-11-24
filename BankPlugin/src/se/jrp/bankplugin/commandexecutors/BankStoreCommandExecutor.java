package se.jrp.bankplugin.commandexecutors;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import se.jrp.bankplugin.BankPlugin;
import se.jrp.bankplugin.BankInventory;

import se.jrp.bankplugin.resources.Strings;

public class BankStoreCommandExecutor extends BankCommandExecutor {

	public BankStoreCommandExecutor(BankPlugin bank) {
		super(bank);
	}

	@Override
	public void onCommand(Player player, String[] args) {
		if(!(bank.inventory.containsKey(player.getName())))
			bank.inventory.put(player.getName(), new ArrayList<ItemStack>());
		if(args.length > 0) {
			player.sendMessage(Strings.ERROR_BANK_STORE);
			return;
		} else if(bank.inventory.full(player.getName())) {
			player.sendMessage(Strings.ERROR_BANK_FULL);
			return;
		}
		ItemStack item = player.getItemInHand();
		if(item.getAmount() > 0) {
			boolean accepted = BankInventory.accepted(item.getType());
			if(accepted && bank.inventory.addItem(player, item)) {
	 			player.sendMessage(Strings.BANK_ADDED_ITEM);
			} else if(!accepted) player.sendMessage(Strings.ERROR_BANK_NOT_ACCEPTED);
		} else player.sendMessage(Strings.ERROR_BANK_NOTHING_IN_HAND);
	}
	
}
