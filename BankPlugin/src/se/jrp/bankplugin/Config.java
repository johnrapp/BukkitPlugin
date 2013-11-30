package se.jrp.bankplugin;

import java.util.Map.Entry;
import se.jrp.bankplugin.filemanager.CustomProperties;
import se.jrp.bankplugin.filemanager.FileManipulator;
import se.jrp.bankplugin.filemanager.FileSubscriber;
import se.jrp.bankplugin.filemanager.PropertiesFileManipulator;
import se.jrp.bankplugin.resources.Functions;
import se.jrp.bankplugin.resources.Strings;

public class Config implements FileSubscriber {
	private PropertiesFileManipulator manipulator;
	
	private CustomProperties defaultProperties = new CustomProperties();
	private CustomProperties properties;

	public Config() {
		defaultProperties.put(Strings.PROPERTIES_MAX_SLOTS, "54");
		defaultProperties.put(Strings.PROPERTIES_INVERT_ACCEPTED, "false");
		defaultProperties.put(Strings.PROPERTIES_EVERYTHING_ACCEPTED, "false");
	}
	
	public String getString(String key) {
		return properties.getProperty(key);
	}
	
	public Integer getInteger(String key) {
		return Functions.getInteger(properties.getProperty(key),
			defaultProperties.containsKey(key) ? (int) defaultProperties.get(key) : null);
	}
	
	public Boolean getBoolean(String key) {
		return Functions.getBoolean(properties.getProperty(key),
			defaultProperties.containsKey(key) ? (boolean) defaultProperties.get(key) : null);
	}
	
	@Override
	public Object getDefault(String id) {
		CustomProperties prop = generateFile(defaultProperties);
		manipulator.save(prop);
		return prop;
	}
	
	public CustomProperties generateFile(CustomProperties properties) {
		CustomProperties prop = new CustomProperties();
		for(Entry<Object, Object> entry : defaultProperties.entrySet())
			prop.put(entry.getKey(), prop.containsKey(entry.getKey()) ?
					entry.getValue() : defaultProperties.get(entry.getKey()));
		return prop;
	}
	
	@Override
	public void onLoad(String id, Object object) {
		properties = (CustomProperties) object;
		if(properties.size() < defaultProperties.size()) {
			for(Entry<Object, Object> entry : defaultProperties.entrySet())
				if(!properties.containsKey(entry.getKey())) properties.put(entry.getKey(), entry.getValue());
			manipulator.save(properties);
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
