package se.jrp.testplugin;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class FileManager {
	public static void save(HashMap<? extends Object, ? extends Object> map, String path) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(map);
			oos.flush();
			oos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
		 
	public static HashMap<? extends Object, ? extends Object> load(String path) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
			Object result = ois.readObject();
			return (HashMap<? extends Object, ? extends Object>) result;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}