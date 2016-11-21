package writer;

import java.io.File;
import java.io.PrintWriter;

import generator.CodeGenerator;
import model.Class;

public abstract class Writer {
	public static void writeFile(File baseDirectory, Class _class, CodeGenerator generator) {
		String baseName = baseDirectory + File.separator + String.join(File.separator, _class.getPackage().getParts())
				+ File.separator + _class.getName();
		File file = new File(baseName + generator.getFileExtension());

		// Create directories
		file.mkdirs();
		
		if (file.exists()) {
			// Rename old file
			File tmpFile = new File(baseName + ".tmp" + generator.getFileExtension());
			if (tmpFile.exists()) {
				tmpFile.delete();
			}
			file.renameTo(tmpFile);
		}

		// Write class
		try {
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			writer.println(generator.generateClassCode(0, _class));
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
