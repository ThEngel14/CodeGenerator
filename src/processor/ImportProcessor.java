package processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import context.Context;
import model.Field;
import model.ItemDescriptor;
import model.Method;
import model.Package;
import model.Parameter;
import model.Type;
import model.imports.ClassImport;
import model.imports.ImportItem;
import model.util.EqualsUtil;
import model.writable.Class;

public class ImportProcessor implements Processor {
	private Context context;
	private HashMap<String, ItemDescriptor> importMap;
	private Collection<ItemDescriptor> existingClasses;

	public ImportProcessor(Context context) {
		this.context = context;
		initializeImportMap();
		initializeExistingClasses();
	}

	private void initializeImportMap() {
		Package java_util = new Package("java", "util");
		Package javax_swing = new Package("javax", "swing");
		Package java_awt = new Package("java", "awt");

		importMap = new HashMap<>();
		importMap.put("List<.+>", new ItemDescriptor(java_util, "List"));
		importMap.put("Collection<.+>", new ItemDescriptor(java_util, "Collection"));
		importMap.put("ArrayList<.+>", new ItemDescriptor(java_util, "ArrayList"));
		importMap.put("Set<.+>", new ItemDescriptor(java_util, "Set"));
		importMap.put("HashSet<.+>", new ItemDescriptor(java_util, "HashSet"));

		importMap.put("JFrame", new ItemDescriptor(javax_swing, "JFrame"));
		importMap.put("JPanel", new ItemDescriptor(javax_swing, "JPanel"));
		importMap.put("JComponent", new ItemDescriptor(javax_swing, "JComponent"));

		importMap.put("Component", new ItemDescriptor(java_awt, "Component"));
	}

	private void initializeExistingClasses() {
		existingClasses = new ArrayList<>();
		try {
			for (File file : findSrcFiles()) {
				Package _package = Package.createPackage(context.getOutputDirectory(), file);
				String name = file.getName().substring(0,
						file.getName().length() - context.getFileExtension().length());
				existingClasses.add(new ItemDescriptor(_package, name));
			}
		} catch (IOException e) {
			System.out.println("ImportProcessor: Error while initializing the existing classes: " + e.getMessage());
		}
	}

	private Collection<File> findSrcFiles() throws IOException {
		Path path = Paths.get(context.getOutputDirectory().getAbsolutePath());

		Collection<File> files = new ArrayList<>();
		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				if (!attrs.isDirectory() && file.toFile().getName().endsWith(context.getFileExtension())) {
					files.add(file.toFile());
				}
				return FileVisitResult.CONTINUE;
			}
		});

		return files;
	}

	@Override
	public void processClass(Class _class) {
		// Superclass
		handleItemDescriptor(_class, _class.getSuperClass());

		// Interfaces
		for (ItemDescriptor itemDescriptor : _class.getInterfaces()) {
			handleItemDescriptor(_class, itemDescriptor);
		}

		// Fields
		for (Field f : _class.getFields()) {
			handleType(_class, f.getType());
		}

		// Methods
		for (Method m : _class.getMethods()) {
			handleType(_class, m.getReturnType());
			if (m.getParameter() != null) {
				for (Parameter param : m.getParameter()) {
					handleType(_class, param.getType());
				}
			}
		}

		if (_class.getImportItems().size() >= 2) {
			ImportItem i1 = new ArrayList<>(_class.getImportItems()).get(0);
			ImportItem i2 = new ArrayList<>(_class.getImportItems()).get(1);

			System.out.println("Import items equals: " + i1.equals(i2));
		}
	}

	private void handleItemDescriptor(Class _class, ItemDescriptor itemDescriptor) {
		if (itemDescriptor == null || itemDescriptor.getPackage() == null
				|| itemDescriptor.getPackage().equals(_class.getPackage())) {
			return;
		}

		_class.getImportItems().add(new ClassImport(itemDescriptor));
	}

	private void handleType(Class _class, Type type) {
		if (type == null) {
			return;
		}

		// Import from java library
		for (String key : importMap.keySet()) {
			if (type.getName().matches(key)) {
				_class.getImportItems().add(new ClassImport(importMap.get(key)));
			}
		}

		String matchingPattern = "(.+<)?%s(>.*)?";
		int matches = 0;
		ImportItem importItem = null;
		// Import from generated class
		for (Class c : context.getClasses()) {
			if (type.getName().matches(String.format(matchingPattern, c.getName()))
					|| type.getName().matches(String.format(matchingPattern, c.getFullName()))) {
				ImportItem current = new ClassImport(new ItemDescriptor(c.getPackage(), c.getName()));
				if (!current.equals(importItem)) {
					matches++;
					importItem = current;
				}
			}
		}
		// Import from existing class
		for (ItemDescriptor desc : existingClasses) {
			if (type.getName().matches(String.format(matchingPattern, desc.getName()))
					|| type.getName().matches(String.format(matchingPattern, desc.getFullName()))) {
				ImportItem current = new ClassImport(desc);
				if (!current.equals(importItem)) {
					matches++;
					importItem = current;
				}
			}
		}

		// Add import
		if (importItem != null && matches == 1) {
			if (!EqualsUtil.equals(_class.getPackage(), importItem.getPackage())) {
				_class.getImportItems().add(importItem);
			}
		}
	}
}
