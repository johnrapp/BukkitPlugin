package se.jrp.marketplugin;

import java.util.HashMap;
import java.util.Properties;
import org.bukkit.Material;
import se.jrp.marketplugin.filemanager.FileManipulator;
import se.jrp.marketplugin.filemanager.FileSubscriber;
import se.jrp.marketplugin.filemanager.LinkedProperties;
import se.jrp.marketplugin.filemanager.PropertiesFileManipulator;
import se.jrp.marketplugin.resources.Values;

public class MarketPrices extends HashMap<Material, Integer[]> implements FileSubscriber {
	public final static String DIVISION_SYMBOL = ",";
	public boolean saving = false;
	
	@Override
	public void onLoad(String id, Object object) {
		LinkedProperties prop = (LinkedProperties) object;
		for(String key : prop.stringPropertyNames()) {
			String[] prices = prop.getProperty(key).split(DIVISION_SYMBOL);
			put(Material.getMaterial(key), new Integer[] {Integer.parseInt(prices[0]), Integer.parseInt(prices[1])});
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
		return generateFile(Values.DEFAULT_PRICES);
	}

	@Override
	public FileManipulator getManipulator(String id) {
		return new PropertiesFileManipulator(this, id);
	}

	@Override
	public boolean isSaving(String id) {
		return saving;
	}
	
	public Properties generateFile(HashMap<Material, Integer[]> prices) {
		LinkedProperties prop = new LinkedProperties();
		for(Material material : Material.values()) {
			prop.put(material.name(), getBuyPrice(material, prices)
				+ DIVISION_SYMBOL + getSellPrice(material, prices));
		}
		return prop;
	}
	
	//buyPrice first then sellPrice
	
	public static boolean isForSale(Material material, HashMap<Material, Integer[]> prices) {
		return getBuyPrice(material, prices) > 0;
	}
	public static boolean isForSale(Material material) {
		return isForSale(material, MarketPlugin.prices);
	}
	public static int getBuyPrice(Material material, HashMap<Material, Integer[]> prices) {
		return prices.containsKey(material) ? prices.get(material)[0] : 0;
	}
	public static int getBuyPrice(Material material) {
		return getBuyPrice(material, MarketPlugin.prices);
	}
	
	public static int getSellPrice(Material material, HashMap<Material, Integer[]> prices) {
		return prices.containsKey(material) ? prices.get(material)[1] : 0;
	}
	public static int getSellPrice(Material material) {
		return getSellPrice(material, MarketPlugin.prices);
	}
	
}
