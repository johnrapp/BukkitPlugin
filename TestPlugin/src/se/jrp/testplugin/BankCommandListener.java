package se.jrp.testplugin;

import org.bukkit.entity.Player;

public interface BankCommandListener {
	void onCommand(Player player, String[] args);
}
