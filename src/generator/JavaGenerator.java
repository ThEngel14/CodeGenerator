package generator;

import java.util.ArrayList;
import java.util.Collection;

import model.Field;
import model.ItemDescriptor;
import model.Method;
import model.Package;
import model.Parameter;
import model.imports.ImportItem;
import model.writable.Class;
import model.writable.Interface;
import model.writable.Writable;

public class JavaGenerator implements CodeGenerator {

	@Override
	public String getFileExtension() {
		return ".java";
	}

	@Override
	public String generatePackageCode(int level, Package _package) {
		return String.format("%s%s %s;", GeneratorUtil.generateIndent(level), GeneratorUtil.PACKAGE,
				_package.getCompleteString());
	}

	@Override
	public String generateImportCode(int level, ImportItem importItem) {
		return String.format("%s%s %s;", GeneratorUtil.generateIndent(level), GeneratorUtil.IMPORT,
				importItem.getCompleteImport());
	}

	@Override
	public String generateParameterCode(int level, Parameter param) {
		return String.format("%s%s %s", GeneratorUtil.generateIndent(level), param.getType().getName(),
				param.getVariable().getName());
	}

	@Override
	public String generateFieldCode(int level, Field field) {
		String result = GeneratorUtil.generateIndent(level);
		if (field.getModifier().getName().length() > 0) {
			result += field.getModifier().getName() + " ";
		}
		if (field.isStatic()) {
			result += GeneratorUtil.STATIC + " ";
		}
		if (field.isFinal()) {
			result += GeneratorUtil.FINAL + " ";
		}
		result += field.getType().getName() + " ";
		result += field.getVariable().getName();
		result += GeneratorUtil.SEMICOLON;

		return result;
	}

	@Override
	public String generateMethodCode(int level, Method method) {
		String result = GeneratorUtil.generateIndent(level);

		if (method.getModifier() != null) {
			result += method.getModifier().getName() + " ";
		}
		if (method.isAbstract()) {
			result += GeneratorUtil.ABSTRACT + " ";
		}
		if (method.isStatic()) {
			result += GeneratorUtil.STATIC + " ";
		}
		if (method.isFinal()) {
			result += GeneratorUtil.FINAL + " ";
		}
		if (method.getReturnType() != null) {
			result += method.getReturnType().getName() + " ";
		}

		Collection<String> prepParameters = new ArrayList<>(method.getParameter().length);
		for (Parameter p : method.getParameter()) {
			prepParameters.add(generateParameterCode(0, p));
		}
		result += String.format("%s(%s)", method.getName(),
				String.join(", ", prepParameters));

		if (method.isAbstract() || !method.hasBody()) {
			result += GeneratorUtil.SEMICOLON;
		} else {
			result += " {" + GeneratorUtil.NEW_LINE;
			if (method.getBody() != null && !method.getBody().isEmpty()) {
				result += generateMethodBodyCode(level + 1, method.getBody());
			}
			result += GeneratorUtil.generateIndent(level) + "}";
		}

		return result;
	}

	private String generateMultipleMethodCode(int level, Collection<Method> methods) {
		if (methods == null || methods.isEmpty()) {
			return null;
		}

		Collection<String> methodsString = new ArrayList<>(methods.size());
		for (Method m : methods) {
			methodsString.add(generateMethodCode(level, m));
		}

		return String.join(GeneratorUtil.NEW_LINE + GeneratorUtil.NEW_LINE, methodsString);
	}

	@Override
	public String generateClassCode(int level, Class _class) {
		String result = generateWritableHeader(0, _class);
		
		// Class
		result += _class.getModifier().getName() + " " + GeneratorUtil.CLASS + " " + _class.getName() + " ";
		if(_class.getSuperClass() != null) {
			result += GeneratorUtil.EXTENDS + " " + _class.getSuperClass().getName() + " ";
		}
		String prepInterfaces = generatePreparedInterfacesString(_class.getInterfaces());
		if (prepInterfaces != null) {
			result += GeneratorUtil.IMPLEMENTS + " " + prepInterfaces + " ";
		}
		result += "{" + GeneratorUtil.NEW_LINE;
		
		// Fields
		if (_class.getFields() != null && !_class.getFields().isEmpty()) {
			for(Field f : _class.getFields()) {
				result += generateFieldCode(level + 1, f) + GeneratorUtil.NEW_LINE;
			}
		}

		result += GeneratorUtil.NEW_LINE;
		
		// Methods
		result += generateMultipleMethodCode(level + 1, _class.getMethods());
		result += GeneratorUtil.NEW_LINE;
		
		result += GeneratorUtil.generateIndent(level) + "}";
		// end Class

		return result;
	}

	@Override
	public String generateInterfaceCode(int level, Interface _interface) {
		String result = generateWritableHeader(0, _interface);

		// Interface
		result += _interface.getModifier().getName() + " " + GeneratorUtil.INTERFACE + " " + _interface.getName() + " ";
		String prepInterfaces = generatePreparedInterfacesString(_interface.getInterfaces());
		if (prepInterfaces != null) {
			result += GeneratorUtil.EXTENDS + " " + prepInterfaces + " ";
		}
		result += "{" + GeneratorUtil.NEW_LINE;

		// Methods
		result += generateMultipleMethodCode(level + 1, _interface.getMethods());
		result += GeneratorUtil.NEW_LINE;

		result += GeneratorUtil.generateIndent(level) + "}";
		// end Interface

		return result;
	}

	private String generateWritableHeader(int level, Writable writable) {
		// Package
		String result = GeneratorUtil.generateIndent(level);

		if (writable.getPackage() != null) {
			result += GeneratorUtil.PACKAGE + " " + writable.getPackage().getCompleteString() + GeneratorUtil.SEMICOLON
				+ GeneratorUtil.NEW_LINE;
			result += GeneratorUtil.NEW_LINE;
		}


		// Imports
		if (writable.getImportItems() != null && !writable.getImportItems().isEmpty()) {
			for (ImportItem i : writable.getImportItems()) {
				result += GeneratorUtil.generateIndent(level) + GeneratorUtil.IMPORT + " " + i.getCompleteImport()
						+ GeneratorUtil.SEMICOLON + GeneratorUtil.NEW_LINE;
			}
			result += GeneratorUtil.NEW_LINE;
		}


		return result;
	}

	private String generatePreparedInterfacesString(Collection<ItemDescriptor> interfaces) {
		if (interfaces == null || interfaces.isEmpty()) {
			return null;
		}
		
		Collection<String> prepInterfaces = new ArrayList<>(interfaces.size());
		for (ItemDescriptor i : interfaces) {
			prepInterfaces.add(i.getName());
		}

		return String.join(", ", prepInterfaces);
	}

	private String generateMethodBodyCode(int level, Collection<String> body) {
		String bodyString = "";
		for (String b : body) {
			bodyString += GeneratorUtil.generateIndent(level) + b + GeneratorUtil.NEW_LINE;
		}

		return bodyString;
	}
}
