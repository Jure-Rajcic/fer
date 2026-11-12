package hr.fer.zemris.fuzzy;

public class Operations {

	public Operations() {
	}
	
	public static IFuzzySet unaryOperation(IFuzzySet set, IUnaryFunction function) {
		IDomain domain = set.getDomain();
		MutableFuzzySet resultSet = new MutableFuzzySet(domain);
		
		for(DomainElement d : domain)
			resultSet.set(d, function.valueAt(set.getValueAt(d)));
		
		return resultSet;
	}
	
	public static IFuzzySet binaryOperation(IFuzzySet first, IFuzzySet second, IBinaryFunction function) {
		MutableFuzzySet resultSet = new MutableFuzzySet(first.getDomain());
		
		for(DomainElement d: first.getDomain())
			resultSet.set(d, function.valueAt(first.getValueAt(d), second.getValueAt(d)));
		
		return resultSet;
	}
	
	public static IUnaryFunction zadehNot() {
		return x -> 1. - x;
	}
	
	public static IBinaryFunction zadehAnd() {
		return (x, y) -> Math.min(x, y);
	}
	
	public static IBinaryFunction zadehOr() {
		return (x, y) -> Math.max(x, y);
	}
	
	public static IBinaryFunction hamacherTNorm(double v) {
		if(v < 0)
			throw new IllegalArgumentException("Value for Hamacher t-norm must be >= 0");
		return (x, y) -> (x*y) / (v + (1 - v)*(x + y - x*y));
	}
	
	public static IBinaryFunction hamacherSNorm(double v) {
		if(v < 0)
			throw new IllegalArgumentException("Value for Hamacher s-norm must be >= 0");
		return (x, y) -> (x + y - (2 - v)*x*y) / (1 - (1 - v)*x*y);
	}
}
