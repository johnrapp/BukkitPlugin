package se.jrp.marketplugin;

import java.util.HashMap;
import java.util.regex.Pattern;
import org.bukkit.Material;
import se.jrp.marketplugin.filemanager.FileManipulator;
import se.jrp.marketplugin.filemanager.FileSubscriber;
import se.jrp.marketplugin.filemanager.CustomProperties;
import se.jrp.marketplugin.filemanager.PropertiesFileManipulator;
import se.jrp.marketplugin.resources.MaterialParser;
import se.jrp.marketplugin.resources.Strings;

public class MarketPrices extends HashMap<Material, Double[]> implements FileSubscriber {
	private static final HashMap<Material, Double[]> DEFAULT_PRICES = new HashMap<>();
	private final static String DIVISION_SYMBOL = "|";
	private PropertiesFileManipulator manipulator;

	
	public MarketPrices() {
		DEFAULT_PRICES.put(Material.DIAMOND, new Double[] {324.0, 162.0});
		DEFAULT_PRICES.put(Material.IRON_INGOT, new Double[] {8.0, 4.0});
		DEFAULT_PRICES.put(Material.LOG, new Double[] {2.0, 1.0});
		DEFAULT_PRICES.put(Material.WOOD, new Double[] {1.0, 0.0});
		DEFAULT_PRICES.put(Material.COAL, new Double[] {4.0, 2.0});
		DEFAULT_PRICES.put(Material.PAPER, new Double[] {3.0, 1.0});
		DEFAULT_PRICES.put(Material.WATCH, new Double[] {42.0, 21.0});
		DEFAULT_PRICES.put(Material.BOOKSHELF, new Double[] {33.0, 16.0});
		
	}
	
	@Override
	public void onLoad(String id, Object object) {
		CustomProperties prop = (CustomProperties) object;
		for(String key : prop.stringPropertyNames()) {
			String[] prices = prop.getProperty(key).split(Pattern.quote(DIVISION_SYMBOL));
			put(MaterialParser.instance().getMaterial(key), new Double[] {Double.parseDouble(prices[0]), Double.parseDouble(prices[1])});
		}
		if(prop.size() < Material.values().length) {
			manipulator.save(generateFile(this));
		}
	}

	@Override
	public Object onSave(String id) {
		return null;
	}

	@Override
	public Object getDefault(String id) {
		CustomProperties prop = generateFile(DEFAULT_PRICES);
		manipulator.save(prop);
		return prop;
	}

	@Override
	public FileManipulator getManipulator(String id) {
		if(manipulator == null) manipulator = new PropertiesFileManipulator(this, MarketPlugin.directory, id);
		return manipulator;
	}

	@Override
	public boolean isAutoSaving(String id) {
		return false;
	}
	
	public CustomProperties generateFile(HashMap<Material, Double[]> prices) {
		CustomProperties prop = new CustomProperties();
		prop.setComment(Strings.COMMENT_PRICES);
		for(Material material : Material.values()) {
			prop.put(MaterialParser.instance().getName(material), getBuyPrice(material, prices)
				+ DIVISION_SYMBOL + getSellPrice(material, prices));
		}
		return prop;
	}
	
	//buyPrice first then sellPrice
	public static boolean isForSale(Material material, HashMap<Material, Double[]> prices) {
		return getBuyPrice(material, prices) > 0;
	}
	public static boolean isForSale(Material material) {
		return isForSale(material, MarketPlugin.prices);
	}
	public static double getBuyPrice(Material material, HashMap<Material, Double[]> prices) {
		return prices.containsKey(material) ? prices.get(material)[0] : 0.;
	}
	public static double getBuyPrice(Material material) {
		return getBuyPrice(material, MarketPlugin.prices);
	}
	public static double getSellPrice(Material material, HashMap<Material, Double[]> prices) {
		return prices.containsKey(material) ? prices.get(material)[1] : 0;
	}
	public static double getSellPrice(Material material) {
		return getSellPrice(material, MarketPlugin.prices);
	}
	
}
