package se.jrp.marketplugin;

import java.util.HashMap;
import java.util.Properties;
import org.bukkit.Material;
import se.jrp.marketplugin.filemanager.FileManipulator;
import se.jrp.marketplugin.filemanager.FileSubscriber;
import se.jrp.marketplugin.filemanager.LinkedProperties;
import se.jrp.marketplugin.filemanager.PropertiesFileManipulator;

public class MarketPrices extends HashMap<Material, Double[]> implements FileSubscriber {
	private static final HashMap<Material, Double[]> DEFAULT_PRICES = new HashMap<>();
	public final static String DIVISION_SYMBOL = "|";
	public boolean saving = false;

	
	public MarketPrices() {
		DEFAULT_PRICES.put(Material.DIAMOND, new Double[] {324., 162.});
		DEFAULT_PRICES.put(Material.IRON_INGOT, new Double[] {8., 4.});
		DEFAULT_PRICES.put(Material.LOG, new Double[] {2., 1.});
		DEFAULT_PRICES.put(Material.WOOD, new Double[] {1., 0.});
		DEFAULT_PRICES.put(Material.COAL, new Double[] {4., 2.});
		DEFAULT_PRICES.put(Material.PAPER, new Double[] {3., 1.});
		DEFAULT_PRICES.put(Material.WATCH, new Double[] {42., 21.});
		DEFAULT_PRICES.put(Material.BOOKSHELF, new Double[] {33., 16.});
		
	}
	
	@Override
	public void onLoad(String id, Object object) {
		LinkedProperties prop = (LinkedProperties) object;
		for(String key : prop.stringPropertyNames()) {
			String[] prices = prop.getProperty(key).split(DIVISION_SYMBOL);
			put(Material.getMaterial(key), new Double[] {Double.parseDouble(prices[0]), Double.parseDouble(prices[1])});
		}
		if(prop.size() < Material.values().length) {
			saving = true;
		}
	}

	@Override
	public Object onSave(String id) {
		return generateFile(this);
	}

	@Override
	public Object getDefault(String id) {
		saving = true;
		return generateFile(DEFAULT_PRICES);
	}

	@Override
	public FileManipulator getManipulator(String id) {
		return new PropertiesFileManipulator(this, id);
	}

	@Override
	public boolean isSaving(String id) {
		return saving;
	}
	
	public Properties generateFile(HashMap<Material, Double[]> prices) {
		LinkedProperties prop = new LinkedProperties();
		for(Material material : Material.values()) {
			prop.put(material.name(), getBuyPrice(material, prices)
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
