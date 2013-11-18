package se.jrp.bankplugin.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;

public class Values {
	public final static int BOOK_MAX_COLUMNS = 19;
	public final static int BOOK_MAX_ROWS = 14;
	
	public final static int BANK_MAX_SLOTS = 54;
	
	public final static List<Material> DEFAULT_ACCEPTED_ITEMS = Arrays.asList(new Material[] {Material.LAPIS_BLOCK, Material.GOLD_BLOCK, Material.IRON_BLOCK,
		Material.TNT, Material.OBSIDIAN, Material.DIAMOND_BLOCK, Material.PUMPKIN, Material.MELON_BLOCK, Material.MYCEL, Material.NETHER_BRICK,
		Material.ENDER_CHEST, Material.EMERALD_BLOCK, Material.BEACON, Material.REDSTONE_BLOCK, Material.QUARTZ_BLOCK, Material.COAL_BLOCK,
		Material.APPLE, Material.COAL, Material.DIAMOND, Material.IRON_INGOT, Material.GOLD_INGOT, Material.MUSHROOM_SOUP, Material.GLOWSTONE,
		Material.ARROW, Material.SULPHUR, Material.BREAD, Material.GRILLED_PORK, Material.GOLDEN_APPLE, Material.SADDLE, Material.REDSTONE,
		Material.LEATHER, Material.COMPASS, Material.WATCH, Material.COOKED_FISH, Material.CAKE, Material.BED, Material.COOKIE, Material.MELON,
		Material.COOKED_BEEF, Material.COOKED_CHICKEN, Material.ENDER_PEARL, Material.BLAZE_ROD, Material.GHAST_TEAR, Material.NETHER_STALK,
		Material.EYE_OF_ENDER, Material.EMERALD, Material.CARROT, Material.BAKED_POTATO, Material.GOLDEN_CARROT, Material.PUMPKIN_PIE});
	
	public final static String[] HELP_PAGES = {Strings.BANK_HELP_PAGE1, Strings.BANK_HELP_PAGE2,Strings.BANK_HELP_PAGE3, Strings.BANK_HELP_PAGE4};
	
	public final static HashMap<Material, String> MATERIAL_DISPLAY_EXCEPTIONS = new HashMap<>();
	public final static HashMap<String, Material> MATERIAL_WRITE_EXCEPTIONS = new HashMap<>();
	
	public static void init() {
		MATERIAL_DISPLAY_EXCEPTIONS.put(Material.TNT, "TNT");
		MATERIAL_DISPLAY_EXCEPTIONS.put(Material.NETHER_STALK, "Nether warts");
		MATERIAL_WRITE_EXCEPTIONS.put("NETHER_WARTS", Material.NETHER_STALK);
		//MATERIAL_WRITE_EXCEPTIONS.put("COOCKED_PORKSHOP", Material.GRILLED_PORK);
		//MATERIAL_EXCEPTIONS.put(Material.GRILLED_PORK, "Coocked Porkchop");
	}
}
