package yang.utility.java;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteFile {

	private String path;
	private boolean appendToFile = false;
	
	public WriteFile(String filePath) {
		path = filePath;
	}
	
	public WriteFile(String filePath, boolean append) {
		path = filePath;
		appendToFile = append;
	}
	
	public void writeFile(String txtLine) throws IOException {
		FileWriter writer = new FileWriter(path, appendToFile);
		PrintWriter pWriter = new PrintWriter(writer);
		
		pWriter.println(txtLine);
		
		pWriter.close();
	}
	
	public static void main(String[] args) throws IOException {
		String file = WriteFile.class.getResource("test.txt").getPath();
		
		WriteFile writeFile = new WriteFile(file, true);
		writeFile.writeFile("I am a boy!");
	}
}
