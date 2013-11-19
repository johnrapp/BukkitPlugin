package se.jrp.bankplugin.filemanager;

import java.io.File;

public abstract class FileManipulator {
	FileSubscriber subscriber;
	String file;
	String fileExtension;

	public FileManipulator(FileSubscriber subscriber, String file, String fileExtension) {
		this.subscriber = subscriber;
		this.file = file;
		this.fileExtension = fileExtension;
	}
	
	public void onSave(String path) {
		if(subscriber.isSaving(file))
			save(path + file + fileExtension, subscriber.onSave(file));
	}
	
	public void onLoad(String path) {
		String completePath = path + file + fileExtension;
		Object load;
		File f = new File(completePath);
		File dir = new File(path);
		if (f.exists()) {
			load = load(completePath);
		} else {
			load = subscriber.getDefault(file);
			if(!dir.exists()) dir.mkdir();
			try {
				f.createNewFile();
			} catch (Exception ex) {
			}
		}
		subscriber.onLoad(file, load);
	}
	
	public abstract void save(String path, Object object);
	public abstract Object load(String path);

}