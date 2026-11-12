package hr.fer.zemris.fuzzy;

import java.util.Iterator;

public class CompositeDomain extends Domain {

	private SimpleDomain[] components;
	
	public CompositeDomain(SimpleDomain[] components) {
		super();
		this.components = components;
		this.cartesianProduct();
	}
	
	@Override
	public int getCardinality() {
		int res = 1;
		for(int i = 0; i < this.components.length; i++) {
			res *= this.components[i].getCardinality();
		}
		return res;
	}

	@Override
	public IDomain getComponent(int index) {
		return this.components[index];
	}

	@Override
	public int getNumberOfComponents() {
		return this.components.length;
	}

	@Override
	public Iterator<DomainElement> iterator() {
		//return new DomainElementIterator(super.elements);
		return new Iterator<DomainElement>(){
			
			int i = 0;

			@Override
			public boolean hasNext() {
				return i < CompositeDomain.this.getCardinality();
			}

			@Override
			public DomainElement next() {
				return CompositeDomain.this.elementForIndex(i++);
			}
					
		};
	}
	
	private void cartesianProduct() {
		for(int i = 0; i < this.getCardinality(); i++) {
			int[] array = new int[this.getNumberOfComponents()];
			int j = 1, k = this.getNumberOfComponents() - 1;
			for(int l = this.getNumberOfComponents() - 1; l >= 0; l--) {
				SimpleDomain d = this.components[l];
				array[k--] = d.elementForIndex((i/j) % d.getCardinality()).getComponentValue(0);
				j *= d.getCardinality();
			}
			super.elements.add(DomainElement.of(array));
		}
	}
}
