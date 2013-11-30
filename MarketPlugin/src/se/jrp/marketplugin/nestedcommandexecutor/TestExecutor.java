package se.jrp.marketplugin.nestedcommandexecutor;

import java.util.HashMap;
import java.util.Map.Entry;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import se.jrp.marketplugin.MarketPlugin;
import se.jrp.marketplugin.filemanager.FileManager;
import se.jrp.marketplugin.filemanager.FileManipulator;
import se.jrp.marketplugin.filemanager.FileSubscriber;
import se.jrp.marketplugin.filemanager.ObjectFileManipulator;
import se.jrp.marketplugin.resources.MaterialParser;

public class TestExecutor extends NestedCommandExecutor implements FileSubscriber {
	public HashMap<String, Boolean> testers = new HashMap<>();

	public TestExecutor() {
		super("/market test <checktype:get:iwannatest:testers>", "", 1, -1);
	}

	@Override
	protected boolean execute(Player player, String[] args) {
		switch(args[0]) {
			case "checktype":
				player.sendMessage(MaterialParser.instance().getName(player.getItemInHand().getType()));
				break;
			case "get":
				Material mat = MaterialParser.instance().getMaterial(args[1]);
				if(mat != null) {
					player.getInventory().addItem(new ItemStack(mat));
				} else
					return false;
				break;
			case "iwannatest":
				testers.put(player.getName(), true);
				break;
			case "idontwannatest":
				testers.put(player.getName(), false);
				break;
			case "testers":
				for(Entry<String, Boolean> entry : testers.entrySet()) {
					player.sendMessage(entry.getKey() + " " + entry.getValue());
				}
				break;
			default:
				return false;
		}
		return true;
	}

	@Override
	public void onLoad(String id, Object object) {
		testers = (HashMap<String, Boolean>) object;
	}

	@Override
	public Object onSave(String id) {
		return testers;
	}

	@Override
	public Object getDefault(String id) {
		return new HashMap<>();
	}

	@Override
	public FileManipulator getManipulator(String id) {
		return new ObjectFileManipulator(this, MarketPlugin.directory, id);
	}

	@Override
	public boolean isAutoSaving(String id) {
		return true;
	}
	
}
