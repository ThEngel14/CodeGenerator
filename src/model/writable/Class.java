package model.writable;

import model.Field;
import model.Method;
import model.Modifier;
import model.Package;
import model.imports.ImportItem;
import model.util.EqualsUtil;

public class Class implements Writable {
	private Package _package;
	private ImportItem[] importItems;
	private Modifier modifier;
	private String name;
	private Class superClass;
	private Interface[] interfaces;
	private Field[] fields;
	private Method[] methods;

	public Class(Package _package, Modifier modifier, String name) {
		this._package = _package;
		this.modifier = modifier;
		this.name = name;
		importItems = null;
		superClass = null;
		interfaces = null;
		fields = null;
		methods = null;
	}

	@Override
	public Package getPackage() {
		return _package;
	}

	public void setPackage(Package _package) {
		this._package = _package;
	}

	@Override
	public ImportItem[] getImportItems() {
		return importItems;
	}

	public void setImportItems(ImportItem... importItems) {
		this.importItems = importItems;
	}

	public Modifier getModifier() {
		return modifier;
	}

	public void setModifier(Modifier modifier) {
		this.modifier = modifier;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class getSuperClass() {
		return superClass;
	}

	public void setSuperClass(Class superClass) {
		this.superClass = superClass;
	}

	public Interface[] getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(Interface... interfaces) {
		this.interfaces = interfaces;
	}

	public Field[] getFields() {
		return fields;
	}

	public void setFields(Field... fields) {
		this.fields = fields;
	}

	public Method[] getMethods() {
		return methods;
	}

	public void setMethods(Method... methods) {
		this.methods = methods;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Class) {
			Class c = (Class) obj;
			return _package.equals(c.getPackage()) && name.equals(c.getName())
					&& EqualsUtil.equals(importItems, c.getImportItems())
					&& modifier.equals(c.getModifier())
					&& EqualsUtil.equals(superClass, c.getSuperClass())
					&& EqualsUtil.equals(interfaces, c.getInterfaces()) && EqualsUtil.equals(fields, fields)
					&& EqualsUtil.equals(methods, c.getMethods());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return _package.hashCode() * name.hashCode();
	}

	public String getFullName() {
		return String.format("%s.%s", _package.getCompleteString(), name);
	}
}
