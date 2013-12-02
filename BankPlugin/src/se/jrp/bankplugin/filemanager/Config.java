package se.jrp.bankplugin.filemanager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import se.jrp.bankplugin.BankPlugin;
import se.jrp.bankplugin.resources.Functions;

public class Config implements FileSubscriber {
	private PropertiesFileManipulator manipulator;
	
	private final HashMap<String, String> defaultProperties = new HashMap<>();
	private CustomProperties properties;

	public String getString(String key) {
		return properties.getProperty(key);
	}
	
	public Integer getInteger(String key) {
		return Functions.getInteger(properties.getProperty(key),
				Functions.getInteger(defaultProperties.get(key), null));
	}
	
	public Boolean getBoolean(String key) {
		return Functions.getBoolean(properties.getProperty(key),
				Functions.getBoolean(defaultProperties.get(key), null));
	}
	
	public void addProperty(String key, String value) {
		defaultProperties.put(key, value);
	}
	
	@Override
	public Object getDefault(String id) {
		CustomProperties prop = generateFile(defaultProperties);
		manipulator.save(prop);
		return prop;
	}
	
	public CustomProperties generateFile(Map map) {
		CustomProperties prop = new CustomProperties();
		for(Entry<String, String> entry : defaultProperties.entrySet())
			prop.put(entry.getKey(), prop.containsKey(entry.getKey()) ?
					entry.getValue() : defaultProperties.get(entry.getKey()));
		return prop;
	}
	
	@Override
	public void onLoad(String id, Object object) {
		properties = (CustomProperties) object;
		if(properties.size() < defaultProperties.size()) {
			manipulator.save(generateFile(properties));
		}
	}

	@Override
	public Object onSave(String id) {
		return null;
	}

	@Override
	public FileManipulator getManipulator(String id) {
		if(manipulator == null) manipulator = new PropertiesFileManipulator(this, BankPlugin.directory, id);
		return manipulator;
	}

	@Override
	public boolean isAutoSaving(String id) {
		return false;
	}
	
}
