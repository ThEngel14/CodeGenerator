package model;

public class Package {
	private String[] parts;

	public Package(String... parts) {
		this.parts = parts;
	}

	public String[] getParts() {
		return parts;
	}

	public void setParts(String... parts) {
		this.parts = parts;
	}

	public String getCompleteString() {
		return String.join(".", parts);
	}
}
