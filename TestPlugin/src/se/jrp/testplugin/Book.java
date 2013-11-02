package se.jrp.testplugin;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class Book extends ItemStack {
	private String author;
	private String title;
	private ArrayList<String> pages;
	private ArrayList<String> lore;
	private BookMeta meta;
	
	public Book(String author, String title, ArrayList<String> pages, ArrayList<String> lore) {
		super(Material.WRITTEN_BOOK);
		meta = (BookMeta) getItemMeta();
		meta.setAuthor(author);
		meta.setTitle(title);
		meta.setPages(pages);
		meta.setLore(lore);
		setItemMeta(meta);
	}
	
}
