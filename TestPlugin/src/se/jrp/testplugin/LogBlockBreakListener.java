package se.jrp.testplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class LogBlockBreakListener implements Listener {
	public LogBlockBreakListener(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		if(block.getType() == Material.LOG && event.getPlayer().getItemInHand().getType() == Material.DIAMOND_AXE) {
			Location loc = block.getLocation();
	        World w = loc.getWorld();
	        removeConnected(Material.LOG, loc, w, loc.getY());
		}
	}
	
	public void removeConnected(Material mat, Location loc, World w, double origin) {
		for(int x = -1; x <= 1; x++) {
			for(int z = -1; z <= 1; z++) {
				for(int y = -1; y <= 1; y++) {
					Location newLoc = new Location(w, loc.getX() + x, loc.getY() + y,loc.getZ() + z);
					if(newLoc.getY() >= origin) {
						Block block = w.getBlockAt(newLoc);
						if(block.getType() == mat) {
							block.breakNaturally();
							removeConnected(mat, newLoc, w, origin);
						} else if(block.getType() == Material.LEAVES) {
							block.breakNaturally();
						}
					}
				}
			}
		}
	}
}
