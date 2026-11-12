package hr.fer.zemris.fuzzy;

import java.util.ArrayList;
import java.util.List;

public class Relations {
	
	public static boolean isSymmetric(IFuzzySet set) {
		if(!isUtimesURelation(set))
			return false;
		
		IDomain domain = set.getDomain();
		
		for(DomainElement e1 : domain) {
			for(DomainElement e2 : domain) {
				if(e1.getComponentValue(0) == e2.getComponentValue(1) 
						&& e1.getComponentValue(1) == e2.getComponentValue(0)
						&& !(set.getValueAt(e1) == set.getValueAt(e2)))
					return false;
			}
		}
		
		return true;
	}
	
	public static boolean isReflexive(IFuzzySet set) {
		if(!isUtimesURelation(set))
			return false;
		
		IDomain domain = set.getDomain();
		
		for(DomainElement e : domain)
			if(e.getComponentValue(0) == e.getComponentValue(1) && set.getValueAt(e) != 1)
				return false;
		
		return true;
	}
	
	public static boolean isMaxMinTransitive(IFuzzySet set) {
		if(!isUtimesURelation(set))
			return false;
		
		IDomain domain = set.getDomain();
		
		for(DomainElement e1 : domain) {
			for(DomainElement e2: domain) {
				List<DomainElement> transitive = new ArrayList<>();
				if(e1.getComponentValue(1) == e2.getComponentValue(0)) {
					transitive.add(Math.min(set.getValueAt(e1), set.getValueAt(e2)) == set.getValueAt(e1) ? e1 : e2);
				}
				if(!transitive.isEmpty()) {
					DomainElement maxElement = maxElement(transitive, set);
					for(DomainElement e3 : domain) {
						if(e3.getComponentValue(0) == e1.getComponentValue(0) 
								&& e3.getComponentValue(1) == e2.getComponentValue(1)
								&& set.getValueAt(e3) < set.getValueAt(maxElement))
							return false;
					}
				}
			}
		}
		return true;
	}
	
	public static IFuzzySet compositionOfBinaryRelations(IFuzzySet a, IFuzzySet b) {
		MutableFuzzySet result = new MutableFuzzySet(Domain.combine(a.getDomain().getComponent(0), b.getDomain().getComponent(1)));
		IDomain left = a.getDomain().getComponent(0), middle = a.getDomain().getComponent(1);
		IDomain right = b.getDomain().getComponent(1);
		
		int j = 0, k = 0, l = 0, m = 0, n = 0;
		for(DomainElement e : result.getDomain()) {
			List<Double> elems = new ArrayList<>();
			for(int i = 0; i < middle.getCardinality(); i++) {
				elems.add(Math.min(a.getValueAt(a.getDomain().elementForIndex(m + j)),
						b.getValueAt(b.getDomain().elementForIndex(k))));
				m++;
				if (m % a.getDomain().getComponent(1).getCardinality() == 0) {
					m = 0;
				}
				k += right.getCardinality();
			}
			result.set(e, maxInList(elems));
			k = (++l);
			if(l == right.getCardinality()) {
				l = 0;
				k = l;
			}
			if(++n == left.getComponent(1).getCardinality()) {
				n = 0;
				j += a.getDomain().getComponent(1).getCardinality();
			}
		}
		
		return result;
	}
	
	public static boolean isFuzzyEquivalence(IFuzzySet set) {
		if(!isUtimesURelation(set))
			return false;
		if(!isSymmetric(set))
			return false;
		if(!isReflexive(set))
			return false;
		if(!isMaxMinTransitive(set))
			return false;
		
		return true;
	}

	public static boolean isUtimesURelation(IFuzzySet set) {
		IDomain domain = set.getDomain();
		
		if(domain.getNumberOfComponents() != 2)
			return false;
		
		IDomain domain1 = domain.getComponent(0), domain2 = domain.getComponent(1);
		if(domain1.getCardinality() != domain2.getCardinality())
			return false;
		
		for(int i = 0; i < domain1.getCardinality(); i++) {
			if(!domain1.elementForIndex(i).equals(domain2.elementForIndex(i)))
				return false;
		}
		
		return true;
	}
	
	private static DomainElement maxElement(List<DomainElement> max, IFuzzySet set) {
		double maxValue = 0.;
		DomainElement maxElement = null;
		
		for(int i = 0; i < max.size(); i++) {
			if(i == 0) {
				maxElement = max.get(i);
				maxValue = set.getValueAt(maxElement);
				continue;
			}
			if(set.getValueAt(max.get(i)) > maxValue) {
				maxElement = max.get(i);
				maxValue = set.getValueAt(maxElement);
			}
		}
		
		return maxElement;
	}
	
	private static double maxInList(List<Double> list) {
		double max = 0.;
		for(double d : list)
			if(d > max)
				max = d;
		return max;
	}
}
