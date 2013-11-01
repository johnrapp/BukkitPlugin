package se.jrp.testplugin;

import java.util.HashMap;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
 
public final class TestPlugin extends JavaPlugin {
	public static TestPlugin instance;
	LogBlockBreakListener listener;
	SaplingWalk saplingWalk;
	TestPluginCommandExecutor commandExecutor;
	
	@Override
    public void onEnable(){
		instance = this;
		HashMap<String, FileListener> map = new HashMap<String, FileListener>();
		map.put(FileManager.BANK, new Bank());
		map.put(FileManager.SAPLING_WALK, new SaplingWalk());
		FileManager.onEnable(map);
    	listener = new LogBlockBreakListener();
    	commandExecutor = new TestPluginCommandExecutor();
    }
 
    @Override
    public void onDisable() {
    	FileManager.onDisable();
    }
    
    public void addEventListener(Listener listener) {
    	getServer().getPluginManager().registerEvents(listener, this);
    }
    
    public void addCommandExecutor(CommandExecutor executor, String command) {
    	getCommand(command).setExecutor(executor);
    }
    
    public static void debug(String text) {
    	instance.getLogger().info(text);
    }
    
}