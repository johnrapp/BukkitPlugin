package se.jrp.testplugin.filemanager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileManipulator extends FileManipulator {

	public PropertiesFileManipulator(FileSubscriber subscriber, String file) {
		super(subscriber, file, ".properties");
	}

	@Override
	public void onSave(String path, Object object) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			((Properties) object).store(fos, null);
			fos.flush();
			fos.close();
		} catch (IOException ex) {
		}
	}

	@Override
	public Object onLoad(String path) {
		LinkedProperties properties = new LinkedProperties();
		try {
			FileInputStream fis = new FileInputStream(path);
			properties.load(fis);
			fis.close();
			return properties;
		} catch (IOException e) {
			return subscriber.getDefault(file);
		}
	}
	
}
