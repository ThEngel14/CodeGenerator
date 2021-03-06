package model.imports;

import model.Package;

public class PackageImport implements ImportItem {
	private Package _package;

	public PackageImport(Package _package) {
		this._package = _package;
	}

	@Override
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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PackageImport) {
			return _package.equals(((PackageImport) obj).getPackage());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return _package.hashCode();
	}
}
