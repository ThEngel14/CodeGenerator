package model.imports;

import model.Class;

public class ClassImport implements ImportItem {
	private Class _class;

	public ClassImport(Class _class) {
		this._class = _class;
	}

	public Class getImportClass() {
		return _class;
	}

	public void setImportClass(Class _class) {
		this._class = _class;
	}

	@Override
	public String getCompleteImport() {
		return _class.getFullName();
	}
}
