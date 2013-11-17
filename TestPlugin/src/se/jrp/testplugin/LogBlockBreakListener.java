package se.jrp.testplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class LogBlockBreakListener implements Listener {
	public LogBlockBreakListener() {
		TestPlugin.instance.addEventListener(this);
	}
	
	@EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		if(block.getType() == Material.LOG) {
			Location loc = block.getLocation();
	        World w = loc.getWorld();
			if(event.getPlayer().getItemInHand().getType() == Material.DIAMOND_AXE) {
		        removeConnected(Material.LOG, loc, w, loc.getY());
			}
			Material under = w.getBlockAt(new Location(w, loc.getX(), loc.getY() - 1, loc.getZ())).getType();
	        if(under == Material.DIRT || under == Material.GRASS) {
	        	block.setType(Material.SAPLING);
	        	block.setData(block.getData());
	        	event.setCancelled(true);
	        }
		}
	}
	
	public void removeConnected(Material mat, Location loc, World w, double originY) {
		for(int x = -1; x <= 1; x++) {
			for(int z = -1; z <= 1; z++) {
				for(int y = -1; y <= 1; y++) {
					Location newLoc = new Location(w, loc.getX() + x, loc.getY() + y,loc.getZ() + z);
					if(newLoc.getY() >= originY) {
						Block block = w.getBlockAt(newLoc);
						if(block.getType() == mat) {
							block.breakNaturally();
							removeConnected(mat, newLoc, w, originY);
						} else if(block.getType() == Material.LEAVES) {
							block.breakNaturally();
						}
					}
				}
			}
		}
	}
}
