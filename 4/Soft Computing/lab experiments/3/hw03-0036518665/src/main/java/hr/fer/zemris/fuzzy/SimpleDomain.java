package hr.fer.zemris.fuzzy;

import java.util.Iterator;

public class SimpleDomain extends Domain {

	private int first;
	private int last;
	
	public SimpleDomain(int first, int last) {
		super();
		this.first = first;
		this.last = last;
		for(int i = first; i < last; i++) {
			super.elements.add(DomainElement.of(new int[] {i}));
		}
	}
	
	@Override
	public int getCardinality() {
		return super.elements.size();
	}

	@Override
	public IDomain getComponent(int index) {
		return this;
	}

	@Override
	public int getNumberOfComponents() {
		return 1;
	}

	@Override
	public Iterator<DomainElement> iterator() {
		//return new DomainElementIterator(super.elements);
		return new Iterator<DomainElement>(){
					
			int i = 0;

			@Override
			public boolean hasNext() {
				return i < SimpleDomain.this.getCardinality();
			}

			@Override
			public DomainElement next() {
				return SimpleDomain.this.elementForIndex(i++);
			}
					
		};
	}

	public int getFirst() {
		return this.first;
	}

	public int getLast() {
		return this.last;
	}

}
