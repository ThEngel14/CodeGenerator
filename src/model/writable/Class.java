package model.writable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import model.Field;
import model.ItemDescriptor;
import model.Method;
import model.Modifier;
import model.Package;
import model.imports.ImportItem;
import model.util.EqualsUtil;

public class Class implements Writable, Comparable<Class> {
	private Package _package;
	private Set<ImportItem> importItems;
	private Modifier modifier;
	private String name;
	private ItemDescriptor superClass;
	private Collection<ItemDescriptor> interfaces;
	private Collection<Field> fields;
	private Collection<Method> methods;

	public Class(Package _package, Modifier modifier, String name) {
		this._package = _package;
		this.modifier = modifier;
		this.name = name;
		importItems = new HashSet<>();
		superClass = null;
		interfaces = new ArrayList<>();
		fields = new ArrayList<>();
		methods = new ArrayList<>();
	}

	@Override
	public Package getPackage() {
		return _package;
	}

	public void setPackage(Package _package) {
		this._package = _package;
	}

	@Override
	public Set<ImportItem> getImportItems() {
		return importItems;
	}

	public void setImportItems(ImportItem... importItems) {
		this.importItems = new HashSet<>(Arrays.asList(importItems));
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

	public ItemDescriptor getSuperClass() {
		return superClass;
	}

	public void setSuperClass(ItemDescriptor superClass) {
		this.superClass = superClass;
	}

	public Collection<ItemDescriptor> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(ItemDescriptor... interfaces) {
		this.interfaces = new ArrayList<>(Arrays.asList(interfaces));
	}

	public Collection<Field> getFields() {
		return fields;
	}

	public void setFields(Field... fields) {
		this.fields = new ArrayList<>(Arrays.asList(fields));
	}

	public Collection<Method> getMethods() {
		return methods;
	}

	public void setMethods(Method... methods) {
		this.methods = new ArrayList<>(Arrays.asList(methods));
	}

	public boolean equalContent(Class c) {
		return EqualsUtil.equals(_package, c.getPackage()) && name.equals(c.getName())
					&& EqualsUtil.equals(importItems, c.getImportItems())
					&& modifier.equals(c.getModifier())
					&& EqualsUtil.equals(superClass, c.getSuperClass())
					&& EqualsUtil.equals(interfaces, c.getInterfaces()) && EqualsUtil.equals(fields, fields)
					&& EqualsUtil.equals(methods, c.getMethods());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Class) {
			return getFullName().equals(((Class) obj).getFullName());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return getFullName().hashCode();
	}

	@Override
	public int compareTo(Class o) {
		return getFullName().compareTo(o.getFullName());
	}

	@Override
	public String toString() {
		return getFullName();
	}
}
