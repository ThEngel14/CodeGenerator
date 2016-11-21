package generator;

import model.Class;
import model.Field;
import model.Method;
import model.Package;
import model.Parameter;
import model.imports.ImportItem;

public interface CodeGenerator {
	String generatePackageCode(int level, Package _package);

	String generateImportCode(int level, ImportItem importItem);

	String generateParameterCode(int level, Parameter param);

	String generateFieldCode(int level, Field field);

	String generateMethodCode(int level, Method method);

	String generateClassCode(int level, Class _class);
}
