package model;

public class Field {
	private Modifier modifier;
	private boolean _final;
	private boolean _static;
	private Type type;
	private Variable variable;

	public Field(Modifier modifier, Type type, Variable variable) {
		this.modifier = modifier;
		this.type = type;
		this.variable = variable;
		_final = false;
		_static = false;
	}

	public Modifier getModifier() {
		return modifier;
	}

	public void setModifier(Modifier modifier) {
		this.modifier = modifier;
	}

	public boolean isFinal() {
		return _final;
	}

	public void setFinal(boolean _final) {
		this._final = _final;
	}

	public boolean isStatic() {
		return _static;
	}

	public void setStatic(boolean _static) {
		this._static = _static;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Variable getVariable() {
		return variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Field) {
			Field field = (Field) obj;
			return modifier.equals(field.getModifier()) && _final == field.isFinal()
					&& _static == field.isStatic() && type.equals(field.getType())
					&& variable.equals(field.getVariable());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return type.hashCode() * variable.hashCode() * modifier.hashCode();
	}
}
