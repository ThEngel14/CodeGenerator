package model.writable;

import model.Package;
import model.imports.ImportItem;

public interface Writable {
	Package getPackage();

	ImportItem[] getImportItems();

	String getName();

	default String getFullName() {
		if (getPackage() == null) {
			return getName();
		}
		return String.format("%s.%s", getPackage().getCompleteString(), getName());
	}
}
