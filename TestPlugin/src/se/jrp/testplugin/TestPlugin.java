package se.jrp.testplugin;

import java.io.File;
import java.util.HashMap;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
//import se.jrp.bukkitfilemanager.FileManager;
//import se.jrp.bukkitfilemanager.FileSubscriber;

import se.jrp.testplugin.Bank.Bank;
import se.jrp.testplugin.Resources.Strings;
import se.jrp.testplugin.Resources.Values;
 
public final class TestPlugin extends JavaPlugin {
    public static TestPlugin instance;
    LogBlockBreakListener listener;
    SaplingWalk saplingWalk;
    TestPluginCommandExecutor commandExecutor;
    
    public static void main(String[] args) {
    }
    
    @Override
    public void onEnable() {
		instance = this;
        Values.init();
        HashMap<String, FileSubscriber> map = new HashMap<String, FileSubscriber>();
        map.put(Strings.FILE_BANK, new Bank());
        map.put(Strings.FILE_SAPLING_WALK, new SaplingWalk());
        FileManager.onEnable(getDataFolder() + File.separator, map);
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