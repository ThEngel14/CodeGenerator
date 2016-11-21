package manual;

import org.junit.Test;

import generator.CodeGenerator;
import generator.JavaGenerator;
import model.Class;
import model.Field;
import model.Interface;
import model.Method;
import model.Modifier;
import model.Package;
import model.Parameter;
import model.Type;
import model.Variable;
import model.imports.ClassImport;
import model.imports.ImportItem;
import model.imports.PackageImport;

public class ClassGeneratorTest {

	@Test
	public void testClassGenerator() {
		Package _package = new Package("model", "test");
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
		System.out.println("Generated java code:\n");
		System.out.println(generator.generateClassCode(0, c));
	}
}