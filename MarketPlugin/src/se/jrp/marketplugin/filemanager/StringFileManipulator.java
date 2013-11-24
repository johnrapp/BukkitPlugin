package se.jrp.marketplugin.filemanager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class StringFileManipulator extends FileManipulator {
	
	final static Charset ENCODING = StandardCharsets.UTF_8;
	

	public StringFileManipulator(FileSubscriber subscriber, String directory, String file) {
		super(subscriber, directory, file, ".txt");
	}

	@Override
	public void onSave(String path, Object object) {
		try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), ENCODING)){
			for(String line : (ArrayList<String>) object){
				writer.write(line);
				writer.newLine();
			}
		} catch(IOException ex) {
		}
	}

	@Override
	public Object onLoad(String path) {
		try(Scanner scanner =  new Scanner(Paths.get(path), ENCODING.name())){
			ArrayList<String> lines = new ArrayList<>();
			while(scanner.hasNextLine()){
				lines.add(scanner.nextLine());
			}
			return lines;
		} catch(IOException ex) {
			return subscriber.getDefault(file);
		}
	}

}