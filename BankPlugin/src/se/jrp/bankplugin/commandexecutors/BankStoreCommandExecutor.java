package se.jrp.bankplugin.commandexecutors;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import se.jrp.bankplugin.AcceptedItems;
import se.jrp.bankplugin.BankPlugin;

import se.jrp.bankplugin.resources.Strings;

public class BankStoreCommandExecutor implements BankCommandExecutor {

	@Override
	public void onCommand(Player player, String[] args) {
		if(!(BankPlugin.inventory.containsKey(player.getName())))
			BankPlugin.inventory.put(player.getName(), new ArrayList<ItemStack>());
		if(args.length > 0) {
			player.sendMessage(Strings.ERROR_BANK_STORE);
			return;
		} else if(BankPlugin.inventory.full(player.getName())) {
			player.sendMessage(Strings.ERROR_BANK_FULL);
			return;
		}
		ItemStack item = player.getItemInHand();
		if(item.getAmount() > 0) {
			boolean accepted = AcceptedItems.accepted(item.getType());
			if(accepted && BankPlugin.inventory.addItem(player, item)) {
	 			player.sendMessage(Strings.BANK_ADDED_ITEM);
			} else if(!accepted) player.sendMessage(Strings.ERROR_BANK_NOT_ACCEPTED);
		} else player.sendMessage(Strings.ERROR_BANK_NOTHING_IN_HAND);
	}
	
}
