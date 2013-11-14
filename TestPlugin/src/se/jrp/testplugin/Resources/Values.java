package se.jrp.testplugin.Resources;

import java.util.HashMap;

import org.bukkit.Material;

public class Values {
	public final static int BOOK_MAX_COLUMNS = 19;
	public final static int BOOK_MAX_ROWS = 14;
	
	public final static int BANK_MAX_SLOTS = 54;
	
	public final static Material[] BANK_ACCEPTED_ITEMS = {Material.LAPIS_BLOCK, Material.GOLD_BLOCK, Material.IRON_BLOCK,
		Material.TNT, Material.OBSIDIAN, Material.DIAMOND_BLOCK, Material.PUMPKIN, Material.MELON_BLOCK, Material.MYCEL, Material.NETHER_BRICK,
		Material.ENDER_CHEST, Material.EMERALD_BLOCK, Material.BEACON, Material.REDSTONE_BLOCK, Material.QUARTZ_BLOCK, Material.COAL_BLOCK,
		Material.APPLE, Material.COAL, Material.DIAMOND, Material.IRON_INGOT, Material.GOLD_INGOT, Material.MUSHROOM_SOUP, Material.GLOWSTONE,
		Material.ARROW, Material.SULPHUR, Material.BREAD, Material.GRILLED_PORK, Material.GOLDEN_APPLE, Material.SADDLE, Material.REDSTONE,
		Material.LEATHER, Material.COMPASS, Material.WATCH, Material.COOKED_FISH, Material.CAKE, Material.BED, Material.COOKIE, Material.MELON,
		Material.COOKED_BEEF, Material.COOKED_CHICKEN, Material.ENDER_PEARL, Material.BLAZE_ROD, Material.GHAST_TEAR, Material.NETHER_WARTS,
		Material.EYE_OF_ENDER, Material.EMERALD, Material.CARROT, Material.BAKED_POTATO, Material.GOLDEN_CARROT, Material.PUMPKIN_PIE};
	
	public final static String[] BANK_HELP_PAGES = {Strings.BANK_HELP_GET, Strings.BANK_HELP_STORE};
	
	public final static HashMap<Material, String> MATERIAL_DISPLAY_EXCEPTIONS =	new HashMap<Material, String>();
	public final static HashMap<String, Material> MATERIAL_WRITE_EXCEPTIONS = new HashMap<String, Material>();
	
	public static void init() {
		MATERIAL_DISPLAY_EXCEPTIONS.put(Material.TNT, "TNT");
		//MATERIAL_WRITE_EXCEPTIONS.put("COOCKED_PORKSHOP", Material.GRILLED_PORK);
		//MATERIAL_EXCEPTIONS.put(Material.GRILLED_PORK, "Coocked Porkchop");
	}
}
