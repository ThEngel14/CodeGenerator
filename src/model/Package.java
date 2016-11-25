package model;

import java.io.File;
import java.util.regex.Pattern;

public class Package {
	private String[] parts;

	public Package(String... parts) {
		this.parts = parts;
	}

	public static Package createPackage(File directory, File file) {
		if (file.getParentFile().getAbsolutePath().length() <= directory.getAbsolutePath().length()) {
			return null;
		}

		String relevant = file.getParentFile().getAbsolutePath().substring(directory.getAbsolutePath().length() + 1);
		String separator = Pattern.quote(System.getProperty("file.separator"));
		String[] parts = relevant.split(separator);
		return new Package(parts);
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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Package) {
			return getCompleteString().equals(((Package) obj).getCompleteString());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return getCompleteString().hashCode();
	}
}
