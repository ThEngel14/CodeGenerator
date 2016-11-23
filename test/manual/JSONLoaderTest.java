package manual;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.junit.Test;

import generator.CodeGenerator;
import generator.JavaGenerator;
import input.JSONLoader;
import io.writer.Writer;
import model.writable.Class;

public class JSONLoaderTest {
	@Test
	public void testJSONLoader() throws IOException {
		Collection<Class> classes = JSONLoader
				.loadClassesFromJSON(new File(System.getProperty("user.dir") + File.separator + "data"));
		CodeGenerator generator = new JavaGenerator();
		File srcDirectory = new File(System.getProperty("user.dir") + File.separator + "test");
		for (Class c : classes) {
			Writer.writeFile(srcDirectory, c, generator);
		}
	}
}
