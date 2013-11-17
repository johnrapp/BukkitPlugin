package se.jrp.testplugin.Resources;

import java.util.HashMap;
import org.bukkit.Material;

public class Values {
	public final static int BOOK_MAX_COLUMNS = 19;
	public final static int BOOK_MAX_ROWS = 14;
	
	public final static HashMap<Material, String> MATERIAL_DISPLAY_EXCEPTIONS =	new HashMap<Material, String>();
	public final static HashMap<String, Material> MATERIAL_WRITE_EXCEPTIONS = new HashMap<String, Material>();
	
	public static void init() {
		MATERIAL_DISPLAY_EXCEPTIONS.put(Material.TNT, "TNT");
		MATERIAL_DISPLAY_EXCEPTIONS.put(Material.NETHER_STALK, "Nether warts");
		MATERIAL_WRITE_EXCEPTIONS.put("NETHER_WARTS", Material.NETHER_STALK);
		//MATERIAL_WRITE_EXCEPTIONS.put("COOCKED_PORKSHOP", Material.GRILLED_PORK);
		//MATERIAL_EXCEPTIONS.put(Material.GRILLED_PORK, "Coocked Porkchop");
	}
}
