package hr.fer.zemris.fuzzy;

public class MutableFuzzySet implements IFuzzySet {

	private double[] membership;
	private IDomain domain;
	
	public MutableFuzzySet(IDomain domain) {
		this.domain = domain;
		this.membership = new double[domain.getCardinality()];
	}
	
	@Override
	public IDomain getDomain() {
		return this.domain;
	}

	@Override
	public double getValueAt(DomainElement element) {
		return this.membership[domain.indexOfElement(element)];
	}

	public MutableFuzzySet set(DomainElement element, double value) {
		this.membership[domain.indexOfElement(element)] = value;
		return this;
	}
}
