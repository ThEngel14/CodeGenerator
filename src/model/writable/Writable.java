package model.writable;

import model.Package;
import model.imports.ImportItem;

public interface Writable {
	Package getPackage();

	ImportItem[] getImportItems();

	String getName();
}
