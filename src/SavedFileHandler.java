import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SavedFileHandler {
	File sourceFile, destinationFile, directorySaveFile = new File("shine_generator_data.txt");;

	public SavedFileHandler() {
		loadSavedFiles();
	}

	public void loadSavedFiles() {
		if (directorySaveFile.exists()) {
			try {
				Scanner fileScanner = new Scanner(directorySaveFile);
				ArrayList<String> savedLines = new ArrayList<String>();
				while (fileScanner.hasNextLine()) {
					savedLines.add(fileScanner.nextLine());
				}
				if (savedLines.size() >= 2) {
					sourceFile = new File(savedLines.get(0));
					destinationFile = new File(savedLines.get(1));
				}
				fileScanner.close();
			} catch (IOException e) {

			}
		}
	}

	public void saveFilePaths() {
		File directorySaveFile = new File("shine_generator_data.txt");
		try {
			PrintWriter pr = new PrintWriter(directorySaveFile);
			pr.print(sourceFile.getAbsolutePath() + "\n" + destinationFile.getAbsolutePath());
			pr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void setDestinationFile(File destinationFile) {
		this.destinationFile = destinationFile;
	}

	public void setSourceFile(File sourceFile) {
		this.sourceFile = sourceFile;
	}

	public File getDestinationFile() {
		return destinationFile;
	}

	public File getSourceFile() {
		return sourceFile;
	}
}
