package se.jrp.testplugin;

import java.io.File;
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
import org.bukkit.plugin.java.JavaPlugin;

public class SaplingWalk implements Listener, CommandExecutor {
	public HashMap<String, Boolean> enables = new HashMap<>();
	private String command = "saplingwalk";
	private String path;
	
	public SaplingWalk(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		plugin.getCommand(command).setExecutor(this);
		path = plugin.getDataFolder() + File.separator + "saplingwalk.bin";
		if((new File(path)).exists()) // check if file exists before loading to avoid errors!
			enables = (HashMap<String, Boolean>) FileManager.load(path);
	} 
	
	public void onDisable(JavaPlugin plugin) {
		FileManager.save(enables, path);
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(enables.containsKey(player.getName()) && !enables.get(player.getName())) return;
		
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
					enables.put(sender.getName(), true);
					return true;
				}
				if(args[0].equalsIgnoreCase("disable")) {
					enables.put(sender.getName(), false);
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
