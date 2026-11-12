package hr.fer.zemris.fuzzy;

import java.util.ArrayList;
import java.util.List;

public abstract class Domain implements IDomain {

	protected List<DomainElement> elements;
	
	public Domain() {
		this.elements = new ArrayList<>();
	}
	
	@Override
	public int indexOfElement(DomainElement element) {
		return this.elements.indexOf(element);
	}

	@Override
	public DomainElement elementForIndex(int index) {
		return this.elements.get(index);
	}
	
	public static IDomain intRange(int first, int last) {
		return new SimpleDomain(first, last);
	}
	
	public static Domain combine(IDomain first, IDomain second) {
		int size = first.getNumberOfComponents() + second.getNumberOfComponents();
		SimpleDomain[] components = new SimpleDomain[size];
		
		int j = 0;
		for(int i = 0; i < first.getNumberOfComponents(); i++)
			components[j++] = (SimpleDomain) first.getComponent(i);
		for(int i = 0; i < second.getNumberOfComponents(); i++)
			components[j++] = (SimpleDomain) second.getComponent(i);
		
		return new CompositeDomain(components);
	}
}
