package context;

import java.util.TreeSet;

import model.writable.Class;

public class Context {
	// TODO implement correctly
	private TreeSet<Class> classes;

	public Context() {
		classes = new TreeSet<>();
	}

	public void addClass(Class _class) {
		classes.add(_class);
	}

	public void removeClass(Class _class) {
		classes.remove(_class);
	}

	public Class getClassInstance(String name) {
		return null;
	}
}
