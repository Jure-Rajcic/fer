package hr.fer.zemris.fuzzy.utils;

import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IDomain;
import hr.fer.zemris.fuzzy.IFuzzySet;

public class Debug {

	public static void print(IDomain domain, String headingText) {
		
		if(headingText!=null) {
			System.out.println(headingText);
		}
		for(DomainElement e : domain) {
			System.out.println("Element domene: " + e);
		}
		
		System.out.println("Kardinalitet domene je: " + domain.getCardinality());
		System.out.println();
	}
	
	public static void print(IFuzzySet set, String headingText) {
		IDomain domain = set.getDomain();
		
		if(headingText!=null) {
			System.out.println(headingText);
		}
		for(DomainElement e : domain) {
			System.out.printf("d(" + e + ")=%.6f\n", set.getValueAt(e));
		}
	}
}
