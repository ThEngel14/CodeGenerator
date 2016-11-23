package model;

public class ItemDescriptor {
	private Package _package;
	private String name;

	public ItemDescriptor(Package _package, String name) {
		this._package = _package;
		this.name = name;
	}

	public ItemDescriptor(String name) {
		this(null, name);
	}

	public Package getPackage() {
		return _package;
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		if (_package == null) {
			return name;
		}

		return String.format("%s.%s", _package.getCompleteString(), name);
	}
}
