package se.jrp.testplugin.filemanager;

public class FileManager {
    public static FileManipulator[] manipulators;

    public static void onEnable(FileManipulator[] manipulators) {
        FileManager.manipulators = manipulators;
        for(FileManipulator manipulator : manipulators) {
			manipulator.autoLoad();
        }
    }

    public static void onDisable() {
        for(FileManipulator manipulator : manipulators) {
            manipulator.autoSave();
        }
    }
}