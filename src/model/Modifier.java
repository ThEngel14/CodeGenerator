package model;

public enum Modifier {
	PRIVATE("private"), PROTECTED("protected"), PUBLIC("public"), DEFAULT("");

	private final String name;

	private Modifier(String value) {
		this.name = value;
	}

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : name.equals(otherName);
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return this.name;
	}
}
