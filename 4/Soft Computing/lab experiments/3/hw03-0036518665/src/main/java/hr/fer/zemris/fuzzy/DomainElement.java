package hr.fer.zemris.fuzzy;

import java.util.Arrays;

public class DomainElement {

	private int[] values;
	
	public DomainElement(int[] values) {
		this.values = values;
	}
	
	public int getNumberOfComponents() {
		return this.values.length;
	}
	
	public int getComponentValue(int index) {
		return this.values[index];
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(values);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DomainElement other = (DomainElement) obj;
		if (!Arrays.equals(values, other.values))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if(this.getNumberOfComponents() > 1)
			sb.append("(");
		for(int i = 0; i < this.getNumberOfComponents(); i++) {
			if(i < this.getNumberOfComponents() - 1)
				sb.append(this.getComponentValue(i) + ", ");
			else
				sb.append(this.getComponentValue(i));
		}
		if(this.getNumberOfComponents() > 1)
			sb.append(")");
		
		return sb.toString();
	}
	
	public static DomainElement of(int... values) {
		return new DomainElement(values);
	}
}
