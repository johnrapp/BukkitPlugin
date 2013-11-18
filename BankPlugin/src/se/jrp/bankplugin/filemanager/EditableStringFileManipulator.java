package se.jrp.bankplugin.filemanager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditableStringFileManipulator extends FileManipulator {
	
	final static Charset ENCODING = StandardCharsets.UTF_8;
	

	public EditableStringFileManipulator(FileSubscriber subscriber, String file) {
		super(subscriber, file, ".txt");
	}

	@Override
	public void save(String path, Object object) {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), ENCODING)){
			for(String line : (ArrayList<String>)object){
				writer.write(line);
				writer.newLine();
			}
	  }	catch (IOException ex) {
		}
	}

	@Override
	public Object load(String path) {
		try (Scanner scanner =  new Scanner(Paths.get(path), ENCODING.name())){
			ArrayList<String> lines = new ArrayList<>();
			while (scanner.hasNextLine()){
				lines.add(scanner.nextLine());
			}
			return lines;
		} catch (IOException ex) {
			return subscriber.getDefault(file);
		}
	}

}