package manual;

import java.io.File;

import org.junit.Test;

import generator.CodeGenerator;
import generator.JavaGenerator;
import model.Field;
import model.Method;
import model.Modifier;
import model.Package;
import model.Parameter;
import model.Type;
import model.Variable;
import model.imports.ClassImport;
import model.imports.ImportItem;
import model.imports.PackageImport;
import model.writable.Class;
import model.writable.Interface;
import writer.Writer;

public class GeneratorTest {

	@Test
	public void testClassGenerator() {
		Package _package = new Package("data", "generated");
		Class c = new Class(_package, Modifier.PUBLIC, "TestClass");

		Class superClass = new Class(new Package("model"), Modifier.PUBLIC, "Variable");

		ImportItem[] importItems = new ImportItem[3];
		importItems[0] = new PackageImport(new Package("java", "util"));
		importItems[1] = new ClassImport(superClass);
		importItems[2] = new ClassImport(new Class(new Package("model", "imports"), Modifier.PUBLIC, "ImportItem"));
		c.setImportItems(importItems);

		c.setSuperClass(superClass);
		c.setInterfaces(new Interface(new Package("model", "imports"), "ImportItem"));

		Field[] fields = new Field[2];
		fields[0] = new Field(Modifier.PRIVATE, new Type("int"), new Variable("counter"));
		fields[1] = new Field(Modifier.DEFAULT, new Type("Collection<String>"), new Variable("list"));
		c.setFields(fields);
		
		Method[] methods = new Method[2];
		methods[0] = new Method(Modifier.PUBLIC, null, c.getName(),
				new Parameter(new Type("int"), new Variable("counter")));
		methods[0].setBody("this.counter = counter;");
		methods[1] = new Method(Modifier.PUBLIC, new Type("String"), "getCompleteImport");
		c.setMethods(methods);

		CodeGenerator generator = new JavaGenerator();
		Writer.writeFile(new File(System.getProperty("user.dir") + File.separator + "test"), c, generator);
	}

	@Test
	public void testInterfaceGenerator() {
		Package _package = new Package("data", "generated");
		Interface _interface = new Interface(_package, "TestInterface");

		ImportItem[] importItems = new ImportItem[2];
		importItems[0] = new PackageImport(new Package("java", "util"));
		importItems[1] = new ClassImport(new Class(new Package("model", "imports"), Modifier.PUBLIC, "ImportItem"));
		_interface.setImportItems(importItems);

		_interface.setInterfaces(new Interface(new Package("model", "imports"), "ImportItem"));

		Method[] methods = new Method[1];
		methods[0] = new Method(null, new Type("String"), "someMethod");
		methods[0].setHasBody(false);
		_interface.setMethods(methods);

		CodeGenerator generator = new JavaGenerator();
		Writer.writeFile(new File(System.getProperty("user.dir") + File.separator + "test"), _interface, generator);
	}
}
