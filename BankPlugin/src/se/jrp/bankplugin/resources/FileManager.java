package se.jrp.bankplugin.resources;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;

public class FileManager {
        public final static String FILE_EXTENSION = ".bin";
        public static String path;
        public static HashMap<String, FileSubscriber> subscribers;
        
        public static void onEnable(String path, HashMap<String, FileSubscriber> subscribers) {
                FileManager.subscribers = subscribers;
                FileManager.path = path;
				File dir = new File(path);
				if (!dir.exists()) dir.mkdir();
                for(Entry<String, FileSubscriber> entry : subscribers.entrySet()) {
					entry.getValue().onLoad(entry.getKey(), load(entry.getKey()));
                }
        }
        
        public static void onDisable() {
                for(Entry<String, FileSubscriber> entry : subscribers.entrySet()) {
					save(entry.getValue().onSave(entry.getKey()), entry.getKey());
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