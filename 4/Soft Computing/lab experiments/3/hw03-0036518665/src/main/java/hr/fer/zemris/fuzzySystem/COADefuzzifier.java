package hr.fer.zemris.fuzzySystem;

import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IDomain;
import hr.fer.zemris.fuzzy.IFuzzySet;

public class COADefuzzifier implements Defuzzifier {

	@Override
	public int vrijednost(IFuzzySet set) {
		IDomain domain = set.getDomain();
		
		double brojnik = 0., nazivnik = 0.;
		for(DomainElement de : domain) {
			brojnik += (double) de.getComponentValue(0) * set.getValueAt(de);
			nazivnik += set.getValueAt(de);
		}
		
		double coa = brojnik / nazivnik;
		if(coa - Math.floor(coa) < 0.5)
			return (int) Math.floor(coa);
		else
			return (int) Math.ceil(coa);
	}
}
