package model.imports;

import model.Package;

public class PackageImport implements ImportItem {
	private Package _package;

	public PackageImport(Package _package) {
		this._package = _package;
	}

	public Package getPackage() {
		return _package;
	}

	public void setPackage(Package _package) {
		this._package = _package;
	}

	@Override
	public String getCompleteImport() {
		return String.format("%s.*", _package.getCompleteString());
	}
}
