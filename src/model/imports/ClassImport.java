package model.imports;

import model.ItemDescriptor;

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
}
