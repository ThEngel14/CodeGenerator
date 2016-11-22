package generator;

import model.Field;
import model.Method;
import model.Package;
import model.Parameter;
import model.imports.ImportItem;
import model.writable.Class;
import model.writable.Interface;

public interface CodeGenerator {
	String getFileExtension();

	String generatePackageCode(int level, Package _package);

	String generateImportCode(int level, ImportItem importItem);

	String generateParameterCode(int level, Parameter param);

	String generateFieldCode(int level, Field field);

	String generateMethodCode(int level, Method method);

	String generateClassCode(int level, Class _class);

	String generateInterfaceCode(int level, Interface _interface);
}
