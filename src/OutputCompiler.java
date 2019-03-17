import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class OutputCompiler {
	public OutputCompiler() {

	}

	public FileData getData(File currentSourceFile) {
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> fileNames = new ArrayList<String>();
		try {
			Scanner s = new Scanner(currentSourceFile);
			while (s.hasNextLine()) {
				String thisLine = s.nextLine();
				if (thisLine.contains("name = ")) {
					String[] parts = thisLine.split("name = ");
					String thisname = parts[1].replaceAll("\"", "");
					names.add(thisname);
				}
				if (thisLine.contains("texturefile = ")) {
					String[] parts = thisLine.split("texturefile = ");
					String thisname = parts[1].replaceAll("\"", "");
					fileNames.add(thisname);
				}
			}
			s.close();
		} catch (Exception e) {
			System.out.println("Error reading file " + currentSourceFile + ": " + e);
		}
		return new FileData(names, fileNames);
	}

	public String compileOutputString(FileData fileData) {
		ArrayList<String> names = fileData.names;
		ArrayList<String> filenames = fileData.fileNames;
		String outputString = "";
		outputString += "# Shiny version of icons setup by Mechano via script\n";
		outputString += "spriteTypes = {\n";
		try {
			for (int i = 0; i < names.size(); i++) {
				System.out.println("Writing File #" + i);
				outputString += "\tspriteType = {\n";
				outputString += "\t\tname = \"" + names.get(i) + "_shine\"\n";
				outputString += "\t\ttexturefile = \"" + filenames.get(i) + "\"\n";
				outputString += "\t\teffectFile = \"gfx/FX/buttonstate.lua\"\n";
				outputString += "\t\tanimation = {\n";
				outputString += "\t\t\tanimationmaskfile = \"" + filenames.get(i) + "\"\n";
				outputString += "\t\t\tanimationtexturefile = \"gfx/interface/goals/shine_overlay.dds\" 	# <- the animated file\n";
				outputString += "\t\t\tanimationrotation = -90.0		# -90 clockwise 90 counterclockwise(by default)\n";
				outputString += "\t\t\tanimationlooping = no			# yes or no ;)\n";
				outputString += "\t\t\tanimationtime = 0.75				# in seconds\n";
				outputString += "\t\t\tanimationdelay = 0			# in seconds\n";
				outputString += "\t\t\tanimationblendmode = \"add\"       #add, multiply, overlay\n";
				outputString += "\t\t\tanimationtype = \"scrolling\"      #scrolling, rotating, pulsing\n";
				outputString += "\t\t\tanimationrotationoffset = { x = 0.0 y = 0.0 }\n";
				outputString += "\t\t\tanimationtexturescale = { x = 1.0 y = 1.0 }\n";

				outputString += "\t\t}\n";
				outputString += "\t\tanimation = {\n";
				outputString += "\t\t\tanimationmaskfile = \"" + filenames.get(i) + "\"\n";
				outputString += "\t\t\tanimationtexturefile = \"gfx/interface/goals/shine_overlay.dds\" 	# <- the animated file\n";
				outputString += "\t\t\tanimationrotation = 90.0		# -90 clockwise 90 counterclockwise(by default)\n";
				outputString += "\t\t\tanimationlooping = no			# yes or no ;)\n";
				outputString += "\t\t\tanimationtime = 0.75				# in seconds\n";
				outputString += "\t\t\tanimationdelay = 0			# in seconds\n";
				outputString += "\t\t\tanimationblendmode = \"add\"       #add, multiply, overlay\n";
				outputString += "\t\t\tanimationtype = \"scrolling\"      #scrolling, rotating, pulsing\n";
				outputString += "\t\t\tanimationrotationoffset = { x = 0.0 y = 0.0 }\n";
				outputString += "	\t\t\tanimationtexturescale = { x = 1.0 y = 1.0 }\n";

				outputString += "\t\t}\n";
				outputString += "\t\tlegacy_lazy_load = no\n";
				outputString += "\t}\n";
			}
			outputString += "}\n";
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return outputString;
	}

	public String getAndCompile(File currentSourceFile) {
		return compileOutputString(getData(currentSourceFile));
	}
}
