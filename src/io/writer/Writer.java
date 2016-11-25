package io.writer;

import java.io.File;
import java.io.PrintWriter;

import generator.CodeGenerator;
import generator.GeneratorUtil;
import io.reader.CustomFileReader;
import model.Field;
import model.Method;
import model.imports.ImportItem;
import model.writable.Class;
import model.writable.Interface;
import model.writable.Writable;

public abstract class Writer {

	public enum WriteStatus {
		GENERATED, APPENDED, NOT_GENERATED;
	}

	public static void writeFile(File srcDirectory, Class _class, CodeGenerator generator) {
		File dir = buildDirectory(srcDirectory, _class);
		WriteStatus status = writeFile(dir, _class.getName(), generator.getFileExtension(),
				generator.generateClassCode(0, _class), false);
		if (status == WriteStatus.NOT_GENERATED) {
			status = appendClassToFile(dir, _class, generator);
		}
		writeLog(_class, status);
	}

	public static void writeFile(File srcDirectory, Interface _interface, CodeGenerator generator) {
		File dir = buildDirectory(srcDirectory, _interface);
		WriteStatus status = writeFile(dir, _interface.getName(), generator.getFileExtension(),
				generator.generateInterfaceCode(0, _interface), true);
		writeLog(_interface, status);
	}

	private static void writeLog(Writable writable, WriteStatus status) {
		if (status == WriteStatus.GENERATED) {
			System.out.println(
					String.format("%s %s generated", writable.getClass().getSimpleName(), writable.getFullName()));
		} else if (status == WriteStatus.APPENDED) {
			System.out.println(
					String.format("%s %s appended", writable.getClass().getSimpleName(),
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

	private static WriteStatus writeFile(File directory, String fileName, String fileExtension, String content,
			boolean override) {
		// Create directories
		directory.mkdirs();

		File file = new File(directory.getAbsoluteFile() + File.separator + fileName + fileExtension);

		if (file.exists()) {
			if (!override) {
				return WriteStatus.NOT_GENERATED;
			}
			File originalFile = new File(
					directory.getAbsoluteFile() + File.separator + fileName + fileExtension + ".original");
			file.renameTo(originalFile);
		}

		// Write class
		WriteStatus status = WriteStatus.NOT_GENERATED;
		try {
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			writer.println(content);
			writer.flush();
			status = WriteStatus.GENERATED;

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return status;
	}

	private static WriteStatus appendClassToFile(File directory, Class _class, CodeGenerator generator) {
		File file = new File(
				directory.getAbsolutePath() + File.separator + _class.getName() + generator.getFileExtension());
		if (!file.exists()) {
			return WriteStatus.NOT_GENERATED;
		}

		WriteStatus status = WriteStatus.NOT_GENERATED;
		try {
			String newFileContent = "";
			String fileContent = CustomFileReader.readFile(file);

			int endPackageIndex = fileContent.indexOf(GeneratorUtil.SEMICOLON);
			int closingIndex = fileContent.lastIndexOf("}");

			newFileContent += fileContent.substring(0, endPackageIndex + 1);

			// Imports
			if (!_class.getImportItems().isEmpty()) {
				newFileContent += GeneratorUtil.NEW_LINE + GeneratorUtil.NEW_LINE;
				newFileContent += GeneratorUtil.TODO_APPEND_PLACEHOLDER + GeneratorUtil.NEW_LINE;
				for (ImportItem importItem : _class.getImportItems()) {
					newFileContent += generator.generateImportCode(0, importItem) + GeneratorUtil.NEW_LINE;
				}
				newFileContent += GeneratorUtil.END_PLACEHOLDER + GeneratorUtil.NEW_LINE;
			}

			newFileContent += fileContent.substring(endPackageIndex + 1, closingIndex - 1);

			// Fields and methods
			if (!_class.getFields().isEmpty() || !_class.getMethods().isEmpty()) {
				newFileContent += GeneratorUtil.NEW_LINE + GeneratorUtil.NEW_LINE;
				newFileContent += GeneratorUtil.generateIndent(1) + GeneratorUtil.TODO_APPEND_PLACEHOLDER
						+ GeneratorUtil.NEW_LINE;

				for (Field f : _class.getFields()) {
					newFileContent += generator.generateFieldCode(1, f) + GeneratorUtil.NEW_LINE;
				}
				if (!_class.getFields().isEmpty()) {
					newFileContent += GeneratorUtil.NEW_LINE;
				}

				for (Method m : _class.getMethods()) {
					newFileContent += generator.generateMethodCode(1, m) + GeneratorUtil.NEW_LINE;
				}

				newFileContent += GeneratorUtil.generateIndent(1) + GeneratorUtil.END_PLACEHOLDER
						+ GeneratorUtil.NEW_LINE;
			}

			newFileContent += fileContent.substring(closingIndex);

			status = writeFile(directory, _class.getName(), generator.getFileExtension(), newFileContent, true);
			if (status == WriteStatus.GENERATED) {
				status = WriteStatus.APPENDED;
			}
		} catch (Exception e) {
			System.err.println("Writer: Error while appending Code to " + file.getAbsolutePath());
			e.printStackTrace();
		}

		return status;
	}
}
