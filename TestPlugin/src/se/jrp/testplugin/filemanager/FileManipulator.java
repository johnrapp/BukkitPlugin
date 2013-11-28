package se.jrp.testplugin.filemanager;

import java.io.File;

public abstract class FileManipulator {
	FileSubscriber subscriber;
	String directory;
	String file;
	String fileExtension;
	String path;

	public FileManipulator(FileSubscriber subscriber, String directory, String file, String fileExtension) {
		this.subscriber = subscriber;
		this.directory = directory;
		this.file = file;
		this.fileExtension = fileExtension;
		this.path = directory + file + fileExtension;
	}
	
	public void save(Object object) {
		onSave(path, object);
	}
	
	public void autoSave() {
		if(subscriber.isAutoSaving(file)) onSave(path, subscriber.onSave(file));
	}
	
	public void autoLoad() {
		Object load;
		File f = new File(path);
		File dir = new File(directory);
		if (f.exists()) {
			load = onLoad(path);
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