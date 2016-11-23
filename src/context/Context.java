package context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import generator.CodeGenerator;
import generator.JavaGenerator;
import input.JSONLoader;
import io.writer.Writer;
import model.writable.Class;
import processor.Processor;

public class Context {
	private CodeGenerator generator;
	private TreeSet<Class> classes;
	private Collection<Processor> processors;

	public Context() {
		generator = new JavaGenerator();
		classes = new TreeSet<>();
		processors = new ArrayList<>();
	}

	public void loadClasses(File directory) throws IOException {
		classes.addAll(JSONLoader.loadClassesFromJSON(directory));
	}

	public void processClasses() {
		for (Class c : classes) {
			System.out.println(String.format("Processing class %s ...", c.getFullName()));
			for (Processor p : processors) {
				p.processClass(c);
			}
			System.out.println(String.format("%s processed", c.getFullName()));
		}
	}

	public void writeClasses(File directory) {
		for (Class c : classes) {
			Writer.writeFile(directory, c, generator);
		}
	}

	public Class getClassByName(String name) {
		Class result = null;
		int counter = 0;

		for (Class c : classes) {
			if (c.getName().equals(name)) {
				counter++;
				result = c;
			}
		}

		if (counter > 1) {
			throw new IllegalArgumentException("There are more than one class with the given name!");
		}

		return result;
	}

	public Class getClassByFullName(String fullName) {
		for (Class c : classes) {
			if (c.getFullName().equals(fullName)) {
				return c;
			}
		}

		return null;
	}

}
