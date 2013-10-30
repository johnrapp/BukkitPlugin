package se.jrp.testplugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
 
public final class TestPlugin extends JavaPlugin implements Listener {
	LogBlockBreakListener listener;
	SaplingWalk saplingWalk;
	TestPluginCommandExecutor commandExecutor;
	Bank bank;
	@Override
    public void onEnable(){
    	listener = new LogBlockBreakListener(this);
    	commandExecutor = new TestPluginCommandExecutor(this);
		saplingWalk = new SaplingWalk(this);
		bank = new Bank(this);
    }
 
    @Override
    public void onDisable() {
    	saplingWalk.onDisable(this);
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // Get the player's location.
        Location loc = event.getPlayer().getLocation();
        // Sets loc to five above where it used to be. Note that this doesn't change the player's position.
        loc.setY(loc.getY() + 5);
        World w = loc.getWorld();
        // Gets the block at the new location.
        Block b = w.getBlockAt(loc);
        // Sets the block to type id 1 (stone).
        b.setType(Material.STONE);;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
        evt.getPlayer().sendMessage("Welcome! You seem to be reeeally rich, so we gave you some more diamonds!");
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
    	event.setCancelled(true);
    }
    
}