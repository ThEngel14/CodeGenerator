package context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

import generator.CodeGenerator;
import generator.JavaGenerator;
import input.JSONLoader;
import io.writer.Writer;
import model.writable.Class;
import processor.ConstructorProcessor;
import processor.GetterSetterProcessor;
import processor.ImportProcessor;
import processor.MethodReturnProcessor;
import processor.Processor;

public class Context {
	private File inputDirectory;
	private File outputDirectory;
	private CodeGenerator generator;
	private TreeSet<Class> classes;
	private Collection<Processor> processors;

	public Context(File inputDir, File outputDir) {
		inputDirectory = inputDir;
		outputDirectory = outputDir;

		generator = new JavaGenerator();
		classes = new TreeSet<>();
		processors = new ArrayList<>();

		initializeProcessors();
	}

	private void initializeProcessors() {
		processors.add(new MethodReturnProcessor());
		processors.add(new ConstructorProcessor());
		processors.add(new GetterSetterProcessor());
		processors.add(new ImportProcessor(this));
	}

	public File getInputDirectory() {
		return inputDirectory;
	}

	public File getOutputDirectory() {
		return outputDirectory;
	}

	public Collection<Class> getClasses() {
		return Collections.unmodifiableCollection(classes);
	}

	public String getFileExtension() {
		return generator.getFileExtension();
	}

	public void loadClasses() throws IOException {
		classes.addAll(JSONLoader.loadClassesFromJSON(inputDirectory));
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

	public void writeClasses() {
		for (Class c : classes) {
			Writer.writeFile(outputDirectory, c, generator);
		}
	}
}
