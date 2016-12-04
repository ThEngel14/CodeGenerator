package processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.sun.xml.internal.ws.util.StringUtils;

import generator.GeneratorUtil;
import model.Field;
import model.Method;
import model.Modifier;
import model.Parameter;
import model.Type;
import model.writable.Class;

public class GetterSetterProcessor implements Processor {
	private Collection<String> isPrefix = new ArrayList<String>(Arrays.asList(new String[] { "boolean", "Boolean" }));

	@Override
	public void processClass(Class _class) {
		if (_class.getFields() != null && !_class.getFields().isEmpty()) {
			for (Field field : _class.getFields()) {
				addMethod(_class, createGetter(field));
				if (!field.isFinal()) {
					addMethod(_class, createSetter(field));
				}
			}
		}
	}

	private void addMethod(Class _class, Method method) {
		_class.getMethods().remove(method);
		_class.getMethods().add(method);
	}

	private Method createGetter(Field field) {
		String methodName = "get";
		if (isPrefix.contains(field.getType().getName())) {
			methodName = "is";
		}
		methodName += StringUtils.capitalize(field.getVariable().getName());
		Method getter = new Method(Modifier.PUBLIC, field.getType(), methodName);
		getter.setBody(
				String.format("%s %s%s", GeneratorUtil.RETURN, field.getVariable().getName(), GeneratorUtil.SEMICOLON));

		return getter;
	}

	private Method createSetter(Field field) {
		String methodName = "set";
		methodName += StringUtils.capitalize(field.getVariable().getName());
		Method setter = new Method(Modifier.PUBLIC, new Type("void"), methodName,
				new Parameter(field.getType(), field.getVariable()));
		setter.setBody(String.format("this.%s = %s%s", field.getVariable().getName(),
				setter.getParameter().get(0).getVariable().getName(), GeneratorUtil.SEMICOLON));

		return setter;
	}
}
