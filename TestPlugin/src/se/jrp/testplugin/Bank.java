package se.jrp.testplugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Bank implements CommandExecutor {
	private String command = "bank";
	private HashMap<String, ArrayList<ItemStack>> bankMap;
	public Bank(JavaPlugin plugin) {
		plugin.getCommand(command).setExecutor(this);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length < 1) {
			if(!(sender instanceof Player))
				sender.sendMessage("Only players can use the Bank.");
			return false;
		}
		
		Player player = (Player) sender;
		String[] params = Arrays.copyOfRange(args, 1, args.length - 1);
		if(args[0].equalsIgnoreCase("get")) {
			get(player, params);
		}
		return false;
	}
	
	public void get(Player player, String[] args) {
		
	}
	
	public void store(Player player, String[] args) {
		
	}
	public void list(Player player) {
		
	}
	public void send(Player playerFrom, String[] args) {
		
	}
}