package se.jrp.marketplugin;

import java.util.ArrayList;
import se.jrp.marketplugin.filemanager.FileManager;
import se.jrp.marketplugin.filemanager.FileManipulator;
import se.jrp.marketplugin.filemanager.FileSubscriber;
import se.jrp.marketplugin.filemanager.StringFileManipulator;

public class PriceSpreadSheetParser implements FileSubscriber {
	@Override
	public void onLoad(String id, Object object) {
	}

	@Override
	public Object onSave(String id) {
		String[] lines = SpreadSheet.sheet.split("\n");
		ArrayList<String> result = new ArrayList<>();
		for(String line : lines) {
			String[] types = line.split("	");
			String material = types[0];
			double buyPrice = Double.parseDouble(types[1]);
			result.add(material + "=" + buyPrice + "|" + buyPrice / 2);
		}
		return result;
	}

	@Override
	public Object getDefault(String id) {
		return null;
	}

	@Override
	public FileManipulator getManipulator(String id) {
		return new StringFileManipulator(this, MarketPlugin.directory, id);
	}

	@Override
	public boolean isAutoSaving(String id) {
		return true;
	}
}