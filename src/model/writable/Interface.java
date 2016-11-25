package model.writable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import model.ItemDescriptor;
import model.Method;
import model.Modifier;
import model.Package;
import model.imports.ImportItem;
import model.util.EqualsUtil;

public class Interface implements Writable {
	private Package _package;
	private Set<ImportItem> importItems;
	private Modifier modifier;
	private String name;
	private Collection<ItemDescriptor> interfaces;
	private Collection<Method> methods;

	public Interface(Package _package, String name) {
		this._package = _package;
		this.name = name;
		importItems = new HashSet<>();
		modifier = Modifier.PUBLIC;
		interfaces = new ArrayList<>();
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

	public Collection<ItemDescriptor> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(ItemDescriptor... interfaces) {
		this.interfaces = new ArrayList<>(Arrays.asList(interfaces));
	}

	public Collection<Method> getMethods() {
		return methods;
	}

	public void setMethods(Method... methods) {
		this.methods = new ArrayList<>(Arrays.asList(methods));
	}

	public boolean equalContent(Interface i) {
		return EqualsUtil.equals(_package, i.getPackage()) && EqualsUtil.equals(importItems, i.getImportItems())
					&& modifier.equals(i.getModifier()) && name.equals(i.getName())
					&& EqualsUtil.equals(interfaces, i.getInterfaces()) && EqualsUtil.equals(methods, i.getMethods());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Interface) {
			return getFullName().equals(((Interface) obj).getFullName());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return getFullName().hashCode();
	}
}
