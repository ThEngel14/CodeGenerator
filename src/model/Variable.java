package model;

public class Variable {
	private String name;

	public Variable(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Variable) {
			return name.equals(((Variable) obj).getName());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
