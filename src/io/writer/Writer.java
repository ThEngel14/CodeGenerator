package io.writer;

import java.io.File;
import java.io.PrintWriter;

import generator.CodeGenerator;
import model.writable.Class;
import model.writable.Interface;
import model.writable.Writable;

public abstract class Writer {

	public enum WriteStatus {
		GENERATED, GEN_GENERATED, NOT_GENERATED;
	}

	public static void writeFile(File srcDirectory, Class _class, CodeGenerator generator) {
		File dir = buildDirectory(srcDirectory, _class);
		WriteStatus status = writeFile(dir, _class.getName(), generator.getFileExtension(),
				generator.generateClassCode(0, _class));
		writeLog(_class, status);
	}

	public static void writeFile(File srcDirectory, Interface _interface, CodeGenerator generator) {
		File dir = buildDirectory(srcDirectory, _interface);
		WriteStatus status = writeFile(dir, _interface.getName(), generator.getFileExtension(),
				generator.generateInterfaceCode(0, _interface));
		writeLog(_interface, status);
	}

	private static void writeLog(Writable writable, WriteStatus status) {
		if (status == WriteStatus.GENERATED) {
			System.out.println(
					String.format("%s %s generated", writable.getClass().getSimpleName(), writable.getFullName()));
		} else if (status == WriteStatus.GEN_GENERATED) {
			System.out.println(
					String.format("%s %s generated as .gen", writable.getClass().getSimpleName(),
							writable.getFullName()));
		}
	}

	private static File buildDirectory(File srcDirectory, Writable writable) {
		String dir = srcDirectory.getAbsolutePath();
		if (writable.getPackage() != null) {
			dir += File.separator + String.join(File.separator, writable.getPackage().getParts());
		}
		return new File(dir);
	}

	private static WriteStatus writeFile(File directory, String fileName, String fileExtension, String content) {
		WriteStatus status = WriteStatus.NOT_GENERATED;
		// Create directories
		directory.mkdirs();

		File file = new File(directory.getAbsoluteFile() + File.separator + fileName + fileExtension);

		boolean fileExists = false;
		if (file.exists()) {
			fileExists = true;
			file = new File(directory.getAbsoluteFile() + File.separator + fileName + ".gen" + fileExtension);
		}

		// Write class
		try {
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			writer.println(content);
			writer.flush();
			status = fileExists ? WriteStatus.GEN_GENERATED : WriteStatus.GENERATED;

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}
}
