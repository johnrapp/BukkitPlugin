package se.jrp.bukkitfilemanager;

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
	
	public void autoSave(String path) {
		if(subscriber.isSaving(file)) onSave(path + file + fileExtension, subscriber.onSave(file));
	}
	
	public void load(String path) {
		String completePath = path + file + fileExtension;
		Object load;
		File f = new File(completePath);
		File dir = new File(path);
		if (f.exists()) {
			load = onLoad(completePath);
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
	
	public abstract void onSave(String path, Object object);
	public abstract Object onLoad(String path);

}