package se.jrp.bankplugin.filemanager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PropertiesFileManipulator extends FileManipulator {

	public PropertiesFileManipulator(FileSubscriber subscriber, String directory, String file) {
		super(subscriber, directory, file, ".properties");
	}

	@Override
	public void onSave(String path, Object object) {
		try {
			CustomProperties prop = (CustomProperties) object;
			FileOutputStream fos = new FileOutputStream(path);
			prop.store(fos);
			fos.close();
		} catch (IOException ex) { }
	}

	@Override
	public Object onLoad(String path) {
		CustomProperties properties = new CustomProperties();
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
