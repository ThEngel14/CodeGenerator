package processor;

import java.util.Arrays;
import java.util.HashSet;

import generator.GeneratorUtil;
import model.Method;
import model.writable.Class;

public class MethodReturnProcessor implements Processor {
	private HashSet<String> noReturn = new HashSet<String>(Arrays.asList(new String[] { "void" }));
	private HashSet<String> valueReturn = new HashSet<String>(
			Arrays.asList(new String[] { "byte", "short", "int", "long", "float", "double", "char" }));
	private HashSet<String> booleanReturn = new HashSet<String>(Arrays.asList(new String[] { "boolean" }));

	@Override
	public void processClass(Class _class) {
		if (_class.getMethods() != null) {
			for(Method m : _class.getMethods()) {
				String returnType = m.getReturnType() != null ? m.getReturnType().getName() : null;
				if (returnType == null || noReturn.contains(returnType)) {
					continue;
				}

				String returnFormat;
				if (valueReturn.contains(returnType)) {
					returnFormat = "%s 0%s";
				} else if (booleanReturn.contains(returnType)) {
					returnFormat = "%s false%s";
				} else {
					returnFormat = "%s null%s";
				}

				if (returnFormat != null) {
					m.getBody().add(String.format(returnFormat, GeneratorUtil.RETURN, GeneratorUtil.SEMICOLON));
				}
			}
		}
	}

}
