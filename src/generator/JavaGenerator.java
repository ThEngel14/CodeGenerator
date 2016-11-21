package generator;

import java.util.ArrayList;
import java.util.Collection;

import model.Class;
import model.Field;
import model.Interface;
import model.Method;
import model.Package;
import model.Parameter;
import model.imports.ImportItem;

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

		result += method.getModifier().getName() + " ";
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

		if (method.isAbstract()) {
			result += GeneratorUtil.SEMICOLON;
		} else {
			result += " {" + GeneratorUtil.NEW_LINE;
			result += generateMethodBodyCode(level + 1, method.getBody());
			result += GeneratorUtil.generateIndent(level) + "}";
		}

		return result;
	}

	private String generateMethodBodyCode(int level, String... body) {
		if (body == null) {
			return GeneratorUtil.generateIndent(level) + GeneratorUtil.TODO_PLACEHOLDER + GeneratorUtil.NEW_LINE;
		}

		String bodyString = "";
		for (String b : body) {
			bodyString += GeneratorUtil.generateIndent(level) + b + GeneratorUtil.NEW_LINE;
		}

		return bodyString;
	}

	@Override
	public String generateClassCode(int level, Class _class) {
		// Package
		String result = GeneratorUtil.generateIndent(level);
		result += GeneratorUtil.PACKAGE + " " + _class.getPackage().getCompleteString() + GeneratorUtil.SEMICOLON
				+ GeneratorUtil.NEW_LINE;

		result += GeneratorUtil.NEW_LINE;

		// Imports
		if (_class.getImportItems() != null) {
			for (ImportItem i : _class.getImportItems()) {
				result += GeneratorUtil.generateIndent(level) + GeneratorUtil.IMPORT + " " + i.getCompleteImport()
						+ GeneratorUtil.SEMICOLON + GeneratorUtil.NEW_LINE;
			}
		}
		
		result += GeneratorUtil.NEW_LINE;
		
		// Class
		result += _class.getModifier().getName() + " " + GeneratorUtil.CLASS + " " + _class.getName() + " ";
		if(_class.getSuperClass() != null) {
			result += GeneratorUtil.EXTENDS + " " + _class.getSuperClass().getName() + " ";
		}
		if(_class.getInterfaces() != null && _class.getInterfaces().length > 0) {
			Collection<String> prepInterfaces = new ArrayList<>(_class.getInterfaces().length);
			for(Interface i : _class.getInterfaces()) {
				prepInterfaces.add(i.getName());
			}
			
			result += GeneratorUtil.IMPLEMENTS + " " + String.join(", ", prepInterfaces) + " ";
		}
		result += "{" + GeneratorUtil.NEW_LINE;
		
		// Fields
		if(_class.getFields() != null && _class.getFields().length > 0) {
			for(Field f : _class.getFields()) {
				result += generateFieldCode(level + 1, f) + GeneratorUtil.NEW_LINE;
			}
		}

		result += GeneratorUtil.NEW_LINE;
		
		// Methods
		if (_class.getMethods() != null && _class.getMethods().length > 0) {
			for (Method m : _class.getMethods()) {
				result += generateMethodCode(level + 1, m) + GeneratorUtil.NEW_LINE;
				result += GeneratorUtil.NEW_LINE;
			}
		}
		
		result += GeneratorUtil.generateIndent(level) + "}";
		// end Class

		return result;
	}
}
