package input;

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
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import generator.GeneratorUtil;
import io.reader.CustomFileReader;
import model.Field;
import model.ItemDescriptor;
import model.Method;
import model.Modifier;
import model.Package;
import model.Parameter;
import model.Type;
import model.Variable;
import model.writable.Class;

public class JSONLoader {
	public static Collection<Class> loadClassesFromJSON(File directory) throws IOException {
		Collection<Class> classes = new ArrayList<>();

		for (File f : findJSONFiles(directory)) {
			Package _package = Package.createPackage(directory, f);

			JSONArray arr = CustomFileReader.readJSON(f);
			for (int i = 0; i < arr.length(); i++) {
				Class _class = createClassFromJSON(_package, arr.getJSONObject(i));
				classes.add(_class);
			}
		}

		return classes;
	}

	private static Collection<File> findJSONFiles(File directory) throws IOException {
		Path path = Paths.get(directory.getAbsolutePath());

		Collection<File> files = new ArrayList<>();
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				if (!attrs.isDirectory() && file.toFile().getName().endsWith(".json")) {
						files.add(file.toFile());
					}
					return FileVisitResult.CONTINUE;
				}
			});

		return files;
	}

	private static Class createClassFromJSON(Package _package, JSONObject obj) {
		// Class name
		String[] nameParts = obj.getString("class").split("\\.");

		Package classPackage = _package;
		String name = nameParts[nameParts.length - 1];
		if (nameParts.length > 1) {
			String[] parts = new String[(_package != null ? _package.getParts().length : 0) + nameParts.length - 1];
			int p_counter = 0;
			if (_package != null) {
				for (String p : _package.getParts()) {
					parts[p_counter++] = p;
				}
			}
			for (int i = 0; i < nameParts.length - 1; i++) {
				parts[p_counter++] = nameParts[i];
			}
			classPackage = new Package(parts);
		}

		// Modifier
		Modifier modifier = Modifier.PUBLIC;
		if (obj.has("modifier")) {
			modifier = Modifier.getModifierByName(obj.getString("modifier"));
		}

		Class _class = new Class(classPackage, modifier, name);

		// Superclass
		if (obj.has("superclass")) {
			String superclass = obj.getString("superclass");
			_class.setSuperClass(new ItemDescriptor(superclass));
		}

		// Interfaces
		if (obj.has("interfaces")) {
			JSONArray jsoninterfaces = obj.getJSONArray("interfaces");
			ItemDescriptor[] interfaces = new ItemDescriptor[jsoninterfaces.length()];
			for (int i = 0; i < jsoninterfaces.length(); i++) {
				interfaces[i] = new ItemDescriptor(jsoninterfaces.getString(i));
			}
			_class.setInterfaces(interfaces);
		}

		// Fields
		if (obj.has("fields")) {
			JSONObject jsonFields = obj.getJSONObject("fields");
			Field[] fields = new Field[jsonFields.length()];
			int f_counter = 0;
			for (String key : jsonFields.keySet()) {
				fields[f_counter++] = new Field(Modifier.PRIVATE, new Type(jsonFields.getString(key)),
						new Variable(key));
			}
			_class.setFields(fields);
		}
		
		List<Method> methods = new ArrayList<>();
		
		// Constructors
		if (obj.has("constructors")) {
			JSONArray jsonConstr = obj.getJSONArray("constructors");
			for(int i = 0; i < jsonConstr.length(); i++) {
				JSONObject constr = jsonConstr.getJSONObject(i);
				Method m = createMethodFromJSON(_class.getName(), constr);
				methods.add(m);
			}
		}

		// Methods
		if (obj.has("methods")) {
			JSONObject jsonMethods = obj.getJSONObject("methods");
			for (String key : jsonMethods.keySet()) {
				JSONObject jsonm = jsonMethods.getJSONObject(key);
				Method m = createMethodFromJSON(key, jsonm);
				methods.add(m);
			}
		}

		// Set methods to class
		if (!methods.isEmpty()) {
			Method[] arrayMethods = new Method[methods.size()];
			for (int i = 0; i < methods.size(); i++) {
				arrayMethods[i] = methods.get(i);
			}

			_class.setMethods(arrayMethods);
		}

		System.out.println(String.format("Class %s loaded", _class.getFullName()));

		return _class;
	}

	private static Method createMethodFromJSON(String name, JSONObject obj) {
		Modifier modifier = Modifier.PUBLIC;
		if(obj.has("modifiers")) {
			JSONArray jsonModifier = obj.getJSONArray("modifiers");
			modifier = Modifier.getModifierByName(jsonModifier.getString(0));
		}
		Type returnType = null;
		if(obj.has("returnType")) {
			returnType = new Type(obj.getString("returnType"));
		}
		
		Method method = new Method(modifier, returnType, name);

		if (obj.has("parameters")) {
			JSONArray jsonParams = obj.getJSONArray("parameters");
			Parameter[] params = new Parameter[jsonParams.length()];
			HashMap<String, Integer> typeCount = new HashMap<>();
			for (int i = 0; i < jsonParams.length(); i++) {
				String type = jsonParams.getString(i);

				String variable = type.toLowerCase();
				if (variable.equals(type)) {
					variable = "_" + variable;
				}
				if (typeCount.containsKey(type)) {
					variable += (typeCount.get(type) + 1);
				}

				params[i] = new Parameter(new Type(type), new Variable(variable));

				int count = 0;
				if (typeCount.containsKey(type)) {
					count = typeCount.get(type);
				}
				typeCount.put(type, count + 1);
			}
			method.setParameter(params);
		}

		method.setBody(GeneratorUtil.TODO_PLACEHOLDER);

		return method;
	}
}
