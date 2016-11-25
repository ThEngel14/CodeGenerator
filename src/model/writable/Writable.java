package model.writable;

import java.util.Set;

import model.Package;
import model.imports.ImportItem;

public interface Writable {
	Package getPackage();

	Set<ImportItem> getImportItems();

	String getName();

	default String getFullName() {
		if (getPackage() == null) {
			return getName();
		}
		return String.format("%s.%s", getPackage().getCompleteString(), getName());
	}
}
