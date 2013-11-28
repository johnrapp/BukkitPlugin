package se.jrp.testplugin.filemanager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectFileManipulator extends FileManipulator {

	public ObjectFileManipulator(FileSubscriber subscriber, String directory, String file) {
		super(subscriber, directory, file, ".bin");
	}

	@Override
	public void onSave(String path, Object object) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
			oos.close();
		}
		catch(Exception e) {
		}
	}

	@Override
	public Object onLoad(String path) {
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object result = ois.readObject();
			ois.close();
			return result;
		}
		catch(Exception e) {
			return subscriber.getDefault(file);
		}
	}

}