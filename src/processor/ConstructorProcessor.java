package processor;

import java.util.ArrayList;
import java.util.List;

import generator.GeneratorUtil;
import model.Field;
import model.Method;
import model.Parameter;
import model.writable.Class;

public class ConstructorProcessor implements Processor {

	@Override
	public void processClass(Class _class) {
		if (_class.getMethods() != null) {
			for (Method m : _class.getMethods()) {
				String returnType = m.getReturnType() != null ? m.getReturnType().getName() : null;
				if (returnType == null && m.getName().equals(_class.getName())) {
					processConstructorMethod(_class, m);
				}
			}
		}
	}

	private void processConstructorMethod(Class _class, Method constructor) {
		if (_class.getFields() == null || _class.getFields().isEmpty() || constructor.getParameter() == null
				|| constructor.getParameter().length == 0) {
			return;
		}
		
		List<Field> fields = new ArrayList<>(_class.getFields());
		for(Parameter p : constructor.getParameter()) {
			for(int i = 0; i < fields.size(); i++) {
				Field f = fields.get(i);
				if(f.getType().equals(p.getType())) {
					constructor.getBody().add(String.format("this.%s = %s%s", f.getVariable().getName(),
							p.getVariable().getName(), GeneratorUtil.SEMICOLON));
					fields.remove(i);
					break;
				}
			}
		}
	}
}
