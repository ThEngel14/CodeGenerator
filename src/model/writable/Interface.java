package model.writable;

import model.ItemDescriptor;
import model.Method;
import model.Modifier;
import model.Package;
import model.imports.ImportItem;
import model.util.EqualsUtil;

public class Interface implements Writable {
	private Package _package;
	private ImportItem[] importItems;
	private Modifier modifier;
	private String name;
	private ItemDescriptor[] interfaces;
	private Method[] methods;

	public Interface(Package _package, String name) {
		this._package = _package;
		this.name = name;
		importItems = null;
		modifier = Modifier.PUBLIC;
		interfaces = null;
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

	public ItemDescriptor[] getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(ItemDescriptor... interfaces) {
		this.interfaces = interfaces;
	}

	public Method[] getMethods() {
		return methods;
	}

	public void setMethods(Method[] methods) {
		this.methods = methods;
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
