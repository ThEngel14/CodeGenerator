package model.imports;

import model.Package;

public interface ImportItem {
	String getCompleteImport();

	Package getPackage();
}
