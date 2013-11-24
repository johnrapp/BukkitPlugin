package se.jrp.marketplugin.nestedcommandexecutor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import se.jrp.marketplugin.resources.MaterialParser;

public class TEST extends NestedCommandExecutor {

	public TEST() {
		super("/market test <1:2>", "", 0, -1);
	}

	@Override
	protected boolean execute(Player player, String[] args) {
		switch(args[0]) {
			case "1":
				player.sendMessage(MaterialParser.instance().getName(player.getItemInHand().getType()));
				player.sendMessage(MaterialParser.instance().getParsedName(player.getItemInHand().getType()));

				break;
			case "2":
				Material mat = MaterialParser.instance().getMaterial(args[1]);
				if(mat != null) {
					player.getInventory().addItem(new ItemStack(mat));
				} else
					return false;
				break;
			default:
				return false;
		}
		return true;
	}
	
}
