package se.jrp.bankplugin.filemanager;

public interface FileSubscriber {
	void onLoad(String id, Object object);
	Object onSave(String id);
	Object getDefault(String id);
	FileManipulator getManipulator(String id);
	boolean isAutoSaving(String id);
}