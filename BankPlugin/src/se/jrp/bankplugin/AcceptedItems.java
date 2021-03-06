package se.jrp.bankplugin;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import se.jrp.bankplugin.filemanager.CustomProperties;
import se.jrp.bankplugin.filemanager.FileManipulator;
import se.jrp.bankplugin.filemanager.FileSubscriber;
import se.jrp.bankplugin.filemanager.PropertiesFileManipulator;
import se.jrp.bankplugin.resources.MaterialParser;
import se.jrp.bankplugin.resources.Strings;
import se.jrp.bankplugin.resources.Values;

public class AcceptedItems extends ArrayList<Material> implements FileSubscriber {
	private PropertiesFileManipulator manipulator;

	public static boolean accepted(Material material, AcceptedItems acceptedItems) {
		if(acceptedItems.size() <= 0) return true;
		for(Material mat : acceptedItems) if(mat == material) return true;
		return false;
	}
	
	public static boolean accepted(Material material) {
		return accepted(material, BankPlugin.acceptedItems);
	}
	
	
	@Override
	public void onLoad(String id, Object object) {
		if(BankPlugin.config.getBoolean(Strings.PROPERTIES_EVERYTHING_ACCEPTED)) return;
		
		CustomProperties prop = (CustomProperties) object;
		boolean invert = BankPlugin.config.getBoolean(Strings.PROPERTIES_INVERT_ACCEPTED);
		for(String key : prop.stringPropertyNames()) {
			boolean accepted = Boolean.parseBoolean(prop.getProperty(key));
			if((accepted && !invert) || (!accepted && invert))
				add(MaterialParser.instance().getMaterial(key));
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
	public boolean isAutoSaving(String id) {
		return false;
	}

	@Override
	public Object getDefault(String id) {
		CustomProperties prop = generateFile(Values.DEFAULT_ACCEPTED_ITEMS);
		manipulator.save(prop);
		return prop;
	}
	
	public CustomProperties generateFile(List<Material> accepted) {
		CustomProperties prop = new CustomProperties();
		for(Material material : Material.values()) {
			prop.put(MaterialParser.instance().getName(material), Boolean.toString(accepted.contains(material)));
		}
		return prop;
	}

	@Override
	public FileManipulator getManipulator(String id) {
		if(manipulator == null) manipulator = new PropertiesFileManipulator(this, BankPlugin.directory, id);
		return manipulator;
	}
	
}
