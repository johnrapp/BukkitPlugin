package se.jrp.testplugin.filemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class FileManager {
	public final static String FILE_EXTENSION = ".bin";
	public static String path;
	public static FileManipulator[] manipulators;

	public static void onEnable(String path, FileManipulator[] manipulators) {
		FileManager.path = path;
		FileManager.manipulators = manipulators;
		for(FileManipulator manipulator : manipulators) {
			manipulator.load(path);
		}
	}

	public static void onDisable() {
		for(FileManipulator manipulator : manipulators) {
			manipulator.autoSave(path);
		}
	}

	public static HashMap<? extends Object, ? extends Object> load(String fileName) {
		File dir = new File(path);
		if (!dir.exists()) dir.mkdir();
		if(!(new File(path + fileName + FILE_EXTENSION).exists())) return new HashMap<>();
		try {
			FileInputStream fis = new FileInputStream(path + fileName + FILE_EXTENSION);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object result = ois.readObject();
			ois.close();
			return (HashMap<? extends Object, ? extends Object>) result;
		}
		catch(Exception e) {
		}
		return null;
	}

	public static void save(HashMap<? extends Object, ? extends Object> map, String fileName) {
		try {
			FileOutputStream fos = new FileOutputStream(path + fileName + FILE_EXTENSION);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(map);
			oos.flush();
			oos.close();
		}
		catch(Exception e) {
		}
	}
}