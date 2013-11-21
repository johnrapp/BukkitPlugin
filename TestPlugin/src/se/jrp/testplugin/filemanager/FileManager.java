package se.jrp.testplugin.filemanager;

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
}