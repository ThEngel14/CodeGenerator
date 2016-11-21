package model;

public class Type implements Comparable<Type> {
	private String name;
	
	public Type(String name) {
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
		if(obj instanceof Type) {
			return name.equals(((Type) obj).getName());
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public int compareTo(Type o) {
		return name.compareTo(o.getName());
	}
}
