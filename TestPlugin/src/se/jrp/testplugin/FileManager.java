package se.jrp.testplugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.event.Listener;

public class FileManager {
	public final static String FILE_EXTENSION = ".bin";
	public static String path;
	public static HashMap<String, FileListener> listeners;
	
	public final static String SAPLING_WALK = "saplingwalk";
	public final static String BANK = "bank";
	
	public static void onEnable(HashMap<String, FileListener> listeners) {
		FileManager.listeners = listeners;
		path = TestPlugin.instance.getDataFolder() + File.separator;
		for(Entry<String, FileListener> entry : listeners.entrySet()) {
			entry.getValue().onLoad(load(entry.getKey()));
		}
	}
	
	public static void onDisable() {
		for(Entry<String, FileListener> entry : listeners.entrySet()) {
			save(entry.getValue().onSave(), entry.getKey());
		}
	}

	public static HashMap<? extends Object, ? extends Object> load(String fileName) {
		if(!(new File(path + fileName + FILE_EXTENSION).exists())) return new HashMap<Object, Object>();
		try {
			FileInputStream fis = new FileInputStream(path + fileName + FILE_EXTENSION);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object result = ois.readObject();
			ois.close();
			return (HashMap<? extends Object, ? extends Object>) result;
		}
		catch(Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
	}
}