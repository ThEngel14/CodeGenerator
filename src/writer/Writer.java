package writer;

import java.io.File;
import java.io.PrintWriter;

import generator.CodeGenerator;
import model.writable.Class;
import model.writable.Interface;
import model.writable.Writable;

public abstract class Writer {

	public static void writeFile(File srcDirectory, Class _class, CodeGenerator generator) {
		File dir = buildDirectory(srcDirectory, _class);
		writeFile(dir, _class.getName() + generator.getFileExtension(), generator.generateClassCode(0, _class));
	}

	public static void writeFile(File srcDirectory, Interface _interface, CodeGenerator generator) {
		File dir = buildDirectory(srcDirectory, _interface);
		writeFile(dir, _interface.getName() + generator.getFileExtension(),
				generator.generateInterfaceCode(0, _interface));
	}

	private static File buildDirectory(File srcDirectory, Writable writable) {
		String dir = srcDirectory + File.separator + String.join(File.separator, writable.getPackage().getParts());
		return new File(dir);
	}

	private static void writeFile(File directory, String fileName, String content) {
		// Create directories
		directory.mkdirs();

		File file = new File(directory.getAbsoluteFile() + File.separator + fileName);

		if (file.exists()) {
			// Rename old file
			File tmpFile = new File(file.getAbsoluteFile() + ".tmp");
			if (!tmpFile.exists()) {
				file.renameTo(tmpFile);
			}
		}

		// Write class
		try {
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			writer.println(content);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
