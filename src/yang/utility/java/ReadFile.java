package yang.utility.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.sound.sampled.Line;

public class ReadFile {

	private String path;
	
	
	public ReadFile(String file_path) {
		path = file_path;
	}
	
	public Vector<String> openFile() throws IOException {
		FileReader fr = new FileReader(path);
		BufferedReader textReader = new BufferedReader(fr);
		
		Vector<String> textData = new Vector<>(3);	// Capability
		
		String sLine;
		while ((sLine = textReader.readLine()) != null) {
			textData.add(sLine);
		}
		
		textReader.close();
		return textData;
	}
		
	public static void main(String[] args) throws IOException {
		String path = ReadFile.class.getResource("test.txt").getPath();

		ReadFile rf = new ReadFile(path);
		Vector<String> strings = rf.openFile();
		
		for(int i=0; i<strings.size(); i++) {
			System.out.println(strings.get(i));
		}

	}
}
