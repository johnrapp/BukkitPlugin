package se.jrp.testplugin;

import java.io.File;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import se.jrp.testplugin.Resources.Strings;
import se.jrp.testplugin.Resources.Values;
import se.jrp.testplugin.filemanager.FileManager;
import se.jrp.testplugin.filemanager.FileManipulator;
 
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
        FileManager.onEnable(getDataFolder() + File.separator, new FileManipulator[]
			{(new SaplingWalk()).getManipulator(Strings.FILE_SAPLING_WALK)});
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