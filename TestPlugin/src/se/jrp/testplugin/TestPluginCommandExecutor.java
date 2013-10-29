package se.jrp.testplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class TestPluginCommandExecutor implements CommandExecutor {
	 
		private TestPlugin plugin; // pointer to your main class, unrequired if you don't need methods from the main class
	 
		public TestPluginCommandExecutor(TestPlugin plugin) {
			this.plugin = plugin;
			plugin.getCommand("test").setExecutor(this);
		}
	 
		@Override
		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if(cmd.getName().equalsIgnoreCase("test")) {
				if (args.length > 0) {
					for(String arg : args) {
						Player target = Bukkit.getServer().getPlayer(arg);
				        if (target != null) {
							PlayerInventory inventory = target.getInventory();
							if(inventory.contains(Material.TNT))
								target.setHealth(Math.max(target.getHealth() - 6, 0));
							else
								sender.sendMessage(arg + " has no tnt");
				        } else {
				        	sender.sendMessage(arg + " is not online!");
				        }
					}
					return true;
		        }
			}
	    	return false; 
		}		

	}