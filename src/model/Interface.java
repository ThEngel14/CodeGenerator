package model;

import model.util.EqualsUtil;

public class Interface {
	private Package _package;
	private String name;
	private Interface[] interfaces;
	private Method[] methods;

	public Interface(Package _package, String name) {
		this._package = _package;
		this.name = name;
		interfaces = null;
		methods = null;
	}

	public Package getPackage() {
		return _package;
	}

	public void setPackage(Package _package) {
		this._package = _package;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Interface[] getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(Interface[] interfaces) {
		this.interfaces = interfaces;
	}

	public Method[] getMethods() {
		return methods;
	}

	public void setMethods(Method[] methods) {
		this.methods = methods;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Interface) {
			Interface i = (Interface) obj;
			return _package.equals(i.getPackage()) && name.equals(i.getName())
					&& EqualsUtil.equals(interfaces, i.getInterfaces()) && EqualsUtil.equals(methods, i.getMethods());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return _package.hashCode() * name.hashCode();
	}
}
