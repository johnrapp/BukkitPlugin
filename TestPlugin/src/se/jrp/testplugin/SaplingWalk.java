package se.jrp.testplugin;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
//import se.jrp.bukkitfilemanager.FileSubscriber;

public class SaplingWalk implements Listener, CommandExecutor, FileSubscriber {
	private String command = "saplingwalk";
	private HashMap<String, Boolean> toggles;
	
	public SaplingWalk() {
		TestPlugin.instance.addEventListener(this);
		TestPlugin.instance.addCommandExecutor(this, command);
	}
	
	@Override
	public void onLoad(String id, HashMap<? extends Object, ? extends Object> map) {
		toggles = (HashMap<String, Boolean>) map;
		
	}
	@Override
	public HashMap<? extends Object, ? extends Object> onSave(String id) {
		return toggles;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(toggles.containsKey(player.getName()) && !toggles.get(player.getName())) return;
		
		PlayerInventory inventory = player.getInventory();
		if(inventory.contains(Material.SAPLING)) {
			boolean place = false;
			Location loc = player.getLocation();
			World w = loc.getWorld();
			Material under = w.getBlockAt(new Location(w, loc.getX(), loc.getY() - 1, loc.getZ())).getType();
			if(under == Material.GRASS || under == Material.DIRT) {
				place = true;
				int range = 3;
				for(int x = -range; x <= range; x++) {
					for(int z = -range; z <= range; z++) {
						for(int y = -range; y <= range; y++) {
							Location newLoc = new Location(w, loc.getX() + x, loc.getY() + y,loc.getZ() + z);
							Material mat = w.getBlockAt(new Location(w, newLoc.getX(), newLoc.getY() - 1, newLoc.getZ())).getType();
							if(mat == Material.SAPLING || mat == Material.LOG) {
								place = false;
								break;
							}
						}
					}
				}
			}
			
			if(place) {
				ItemStack sapling = (ItemStack) inventory.all(Material.SAPLING).values().toArray()[0];
				w.getBlockAt(loc).setType(Material.SAPLING);
				w.getBlockAt(loc).setData(sapling.getData().getData());
				if(sapling.getAmount() > 1)
					sapling.setAmount(sapling.getAmount() - 1);
				else 
					inventory.remove(sapling);
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("enable")) {
					toggles.put(sender.getName(), true);
					return true;
				}
				if(args[0].equalsIgnoreCase("disable")) {
					toggles.put(sender.getName(), false);
					return true;
				}
			}
		} else {
			sender.sendMessage("You must be a player to issue this command.");
			return true;
		}
		
		return false;
	}
}
