package se.jrp.marketplugin;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Material;
import se.jrp.marketplugin.filemanager.FileManipulator;
import se.jrp.marketplugin.filemanager.FileSubscriber;
import se.jrp.marketplugin.filemanager.HumanReadableFileManipulator;
import se.jrp.marketplugin.resources.Values;

public class MarketPrices extends HashMap<Material, Integer[]> implements FileSubscriber {
	public final static String TYPE_DIVISION_SYMBOL = ":";
	public final static String PRICE_DIVISION_SYMBOL = ",";
	public boolean saving = false;
	
	@Override
	public void onLoad(String id, Object object) {
		ArrayList<String> lines = (ArrayList<String>) object;
		for(String line : lines) {
			String[] types = line.split(TYPE_DIVISION_SYMBOL);
			String[] prices = types[1].split(PRICE_DIVISION_SYMBOL);
			put(Material.getMaterial(types[0]),
					new Integer[] {Integer.parseInt(prices[0]), Integer.parseInt(prices[1])});
		}
		if(lines.size() < Material.values().length) {
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
		return new HumanReadableFileManipulator(this, id);
	}

	@Override
	public boolean isSaving(String id) {
		return saving;
	}
	
	public ArrayList<String> generateFile(HashMap<Material, Integer[]> prices) {
		ArrayList<String> lines = new ArrayList<>();
		for(Material material : Material.values()) {
			lines.add(material.name() + TYPE_DIVISION_SYMBOL + getBuyPrice(material, prices)
					+ PRICE_DIVISION_SYMBOL + getSellPrice(material, prices));
		}
		return lines;
	}
	
	//buyPrice first then sellPrice
	public int getBuyPrice(Material material, HashMap<Material, Integer[]> prices) {
		return prices.containsKey(material) ? prices.get(material)[0] : -1;
	}
	public int getBuyPrice(Material material) {
		return getBuyPrice(material, this);
	}
	
	public int getSellPrice(Material material, HashMap<Material, Integer[]> prices) {
		return prices.containsKey(material) ? prices.get(material)[1] : 0;
	}
	public int getSellPrice(Material material) {
		return getSellPrice(material, this);
	}
	
	
}
