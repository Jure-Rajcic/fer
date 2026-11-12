package hr.fer.zemris.fuzzy;

import java.util.Iterator;
import java.util.List;

public class DomainElementIterator implements Iterator<DomainElement> {

	private int counter = 0;
	private List<DomainElement> elements;
	
	public DomainElementIterator(List<DomainElement> elements) {
		this.elements = elements;
	}
	
	@Override
	public boolean hasNext() {
		return this.counter < this.elements.size();
	}
	
	@Override
	public DomainElement next() {
		return this.elements.get(this.counter++);
	}

}
