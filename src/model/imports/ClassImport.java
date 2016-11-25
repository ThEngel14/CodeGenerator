package model.imports;

import model.ItemDescriptor;
import model.Package;

public class ClassImport implements ImportItem {
	private ItemDescriptor _class;

	public ClassImport(ItemDescriptor _class) {
		this._class = _class;
	}

	public ItemDescriptor getImportClass() {
		return _class;
	}

	public void setImportClass(ItemDescriptor _class) {
		this._class = _class;
	}

	@Override
	public String getCompleteImport() {
		return _class.getFullName();
	}

	@Override
	public Package getPackage() {
		return _class.getPackage();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ClassImport) {
			return _class.equals(((ClassImport) obj).getImportClass());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return _class.hashCode();
	}
}
