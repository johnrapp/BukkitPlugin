package se.jrp.testplugin;

import java.io.File;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import se.jrp.testplugin.resources.Strings;
import se.jrp.testplugin.resources.Values;
import se.jrp.testplugin.filemanager.FileManager;
import se.jrp.testplugin.filemanager.FileManipulator;
 
public final class TestPlugin extends JavaPlugin {
	public static String directory;
    public static TestPlugin instance;
    public LogBlockBreakListener listener;
    public SaplingWalk saplingWalk;
    public TestPluginCommandExecutor commandExecutor;
    
    public static void main(String[] args) {
    }
    
    @Override
    public void onEnable() {
		instance = this;
		directory = getDataFolder() + File.separator;
        Values.init();
        FileManager.onEnable(new FileManipulator[]
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