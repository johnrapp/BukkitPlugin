package se.jrp.bankplugin.resources;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;

import se.jrp.bankplugin.resources.Functions;
import se.jrp.bankplugin.resources.Strings;
import se.jrp.bankplugin.resources.Values;

public class Book extends ItemStack {
	private String author;
	private String title;
	private ArrayList<String> pages;
	private ArrayList<String> lore;
	private BookMeta meta;
	
	public Book(String title, ArrayList<String> pages, ArrayList<String> lore) {
		super(Material.WRITTEN_BOOK);
		meta = (BookMeta) getItemMeta();
		meta.setAuthor(Strings.PLUGIN_NAME);
		meta.setTitle(title);
		meta.setPages(pages);
		meta.setLore(lore);
		setItemMeta(meta);
	}
	
	public static ArrayList<String> fitToBook(ArrayList<String> rows) {
		ArrayList<String> pages = new ArrayList<String>();
		StringBuilder sz = new StringBuilder();
		for(int i = 0; i < rows.size(); i++) {
			if(pages.size() < (i / Values.BOOK_MAX_ROWS)) {
				pages.add(sz.toString());
				sz.delete(0, sz.length());
			}
				sz.append(rows.get(i) + "\n");
		}
		pages.add(sz.toString());
		return pages;
	}
	
	public static void giveBook(Player player, Book book) {
		PlayerInventory inventory = player.getInventory();
		if(!Functions.full(inventory)) {
			inventory.addItem(book);
			player.sendMessage(Strings.BOOK_ADDED);
		} else {
			player.sendMessage(Strings.ERROR_INVENTORY_FULL);
		}
	}
}
