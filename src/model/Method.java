package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.util.EqualsUtil;

public class Method {
	private Modifier modifier;
	private Type returnType;
	private boolean _abstract;
	private boolean _static;
	private boolean _final;
	private String name;
	private List<Parameter> parameter;
	private boolean hasBody;
	private List<String> body;

	public Method(Modifier modifier, Type returnType, String name, Parameter... parameters) {
		this.modifier = modifier;
		this.returnType = returnType;
		this.name = name;
		this.parameter = null;
		if (parameters != null) {
			parameter = new ArrayList<>();
			for (Parameter p : parameters) {
				parameter.add(p);
			}
		}
		hasBody = true;
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

	public List<Parameter> getParameter() {
		return parameter;
	}

	public void setParameter(List<Parameter> parameter) {
		this.parameter = parameter;
	}

	public boolean hasBody() {
		return hasBody;
	}

	public void setHasBody(boolean hasBody) {
		this.hasBody = hasBody;
	}

	public List<String> getBody() {
		return body;
	}

	public void setBody(String... body) {
		this.body = new ArrayList<String>(Arrays.asList(body));
	}

	public boolean equalContent(Method method) {
			return EqualsUtil.equals(modifier, method.getModifier())
					&& EqualsUtil.equals(returnType, method.getReturnType())
					&& _abstract == method.isAbstract() && _static == method.isStatic() && _final == method.isFinal()
					&& name.equals(method.getName()) && EqualsUtil.equals(parameter, method.getParameter())
					&& hasBody == method.hasBody() && EqualsUtil.equals(body, method.getBody());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Method) {
			Method method = (Method) obj;
			return name.equals(method.getName()) && EqualsUtil.equalsCollection(parameter, method.getParameter());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return (returnType != null ? returnType.hashCode() : 1) * name.hashCode();
	}
}
