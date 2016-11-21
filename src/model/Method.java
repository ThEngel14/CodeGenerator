package model;

import model.util.EqualsUtil;

public class Method {
	private Modifier modifier;
	private Type returnType;
	private boolean _abstract;
	private boolean _static;
	private boolean _final;
	private String name;
	private Parameter[] parameter;
	private String[] body;

	public Method(Modifier modifier, Type returnType, String name, Parameter... parameters) {
		this.modifier = modifier;
		this.returnType = returnType;
		this.name = name;
		this.parameter = parameters;
		body = null;
		_abstract = false;
		_static = false;
		_final = false;
	}

	public Modifier getModifier() {
		return modifier;
	}

	public void setModifier(Modifier modifier) {
		this.modifier = modifier;
	}

	public Type getReturnType() {
		return returnType;
	}

	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}

	public boolean isAbstract() {
		return _abstract;
	}

	public void setAbstract(boolean _abstract) {
		this._abstract = _abstract;
	}

	public boolean isStatic() {
		return _static;
	}

	public void setStatic(boolean _static) {
		this._static = _static;
	}

	public boolean isFinal() {
		return _final;
	}

	public void setFinal(boolean _final) {
		this._final = _final;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Parameter[] getParameter() {
		return parameter;
	}

	public void setParameter(Parameter... parameter) {
		this.parameter = parameter;
	}

	public String[] getBody() {
		return body;
	}

	public void setBody(String... body) {
		this.body = body;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Method) {
			Method method = (Method) obj;
			return modifier.equals(method.getModifier()) && EqualsUtil.equals(returnType, method.getReturnType())
					&& _abstract == method.isAbstract() && _static == method.isStatic() && _final == method.isFinal()
					&& name.equals(method.getName()) && EqualsUtil.equals(parameter, method.getParameter())
					&& EqualsUtil.equals(body, method.getBody());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return modifier.hashCode() * (returnType != null ? returnType.hashCode() : 1) * name.hashCode();
	}
}
