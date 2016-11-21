package model;

public class Parameter {
	private Type type;
	private Variable variable;
	
	public Parameter(Type type, Variable variable) {
		this.type = type;
		this.variable = variable;
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
		if(obj instanceof Parameter) {
			Parameter param = (Parameter) obj;
			return type.equals(param.getType()) && variable.equals(param.getVariable());
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return type.hashCode() * variable.hashCode();
	}
}
