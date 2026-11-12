package hr.fer.zemris.fuzzySystem;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.fuzzy.CalculatedFuzzySet;
import hr.fer.zemris.fuzzy.Domain;
import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IDomain;
import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.MutableFuzzySet;
import hr.fer.zemris.fuzzy.Operations;
import hr.fer.zemris.fuzzy.StandardFuzzySets;

public class KormiloFuzzySystemMin implements FuzzySystem {

	private Defuzzifier def;
	private int valueL;
	private int valueD;
	private int valueLK;
	private int valueDK;
	private int valueS;
	private List<FuzzyRule> rules = new ArrayList<>();
	
	public KormiloFuzzySystemMin(Defuzzifier def) {
		this.def = def;
		rules.add(rule1);
		rules.add(rule2);
		rules.add(rule3);
		rules.add(rule4);
		rules.add(rule5);
		rules.add(rule6);
	}
	
	private IFuzzySet udaljenostNeBlizu = new CalculatedFuzzySet(Domain.intRange(0, 1301), StandardFuzzySets.gammaFunction(50, 60));
	private IFuzzySet udaljenostBlizu = Operations.unaryOperation(udaljenostNeBlizu, Operations.zadehNot());
	private IFuzzySet kutKormilaMalen = new CalculatedFuzzySet(Domain.intRange(-90, 91), StandardFuzzySets.lambdaFunction(85, 90, 95));
	private IFuzzySet kutKormilaUdesno = new CalculatedFuzzySet(Domain.intRange(-90, 91), StandardFuzzySets.lFunction(10, 15));
	private IFuzzySet kutKormilaUlijevo = new CalculatedFuzzySet(Domain.intRange(-90, 91), StandardFuzzySets.gammaFunction(165, 170));
	
	// AKO L je ne blizu I D je ne blizu ONDA K je malen
	private FuzzyRule rule1 = new FuzzyRule() {
		
		@Override
		public IFuzzySet zaključak() {
			IDomain domain = kutKormilaMalen.getDomain();
			MutableFuzzySet result = new MutableFuzzySet(domain);
			for(DomainElement de : result.getDomain())
				result.set(de, Math.max(
						Math.min(udaljenostNeBlizu.getValueAt(DomainElement.of(KormiloFuzzySystemMin.this.valueL)), kutKormilaMalen.getValueAt(DomainElement.of(de.getComponentValue(0)))),
						Math.min(udaljenostNeBlizu.getValueAt(DomainElement.of(KormiloFuzzySystemMin.this.valueD)), kutKormilaMalen.getValueAt(DomainElement.of(de.getComponentValue(0))))));
			return result;
		}
	};
	
	// AKO L je blizu ILI LK je blizu ONDA K je udesno
	private FuzzyRule rule2 = new FuzzyRule() {
		
		@Override
		public IFuzzySet zaključak() {
			MutableFuzzySet result = new MutableFuzzySet(kutKormilaUdesno.getDomain());
			for(DomainElement de : result.getDomain())
				result.set(de, Math.max(
						Math.min(udaljenostBlizu.getValueAt(DomainElement.of(KormiloFuzzySystemMin.this.valueL)),
								kutKormilaUdesno.getValueAt(DomainElement.of(de.getComponentValue(0)))),
						Math.min(udaljenostBlizu.getValueAt(DomainElement.of(KormiloFuzzySystemMin.this.valueLK)),
								kutKormilaUdesno.getValueAt(DomainElement.of(de.getComponentValue(0))))));
			return result;
		}
	};
	
	// AKO D je blizu ILI DK je blizu ONDA K je ulijevo
	private FuzzyRule rule3 = new FuzzyRule() {

		@Override
		public IFuzzySet zaključak() {
			MutableFuzzySet result = new MutableFuzzySet(kutKormilaUlijevo.getDomain());
			for(DomainElement de : result.getDomain())
				result.set(de, Math.max(
						Math.min(udaljenostBlizu.getValueAt(DomainElement.of(KormiloFuzzySystemMin.this.valueD)),
								kutKormilaUlijevo.getValueAt(DomainElement.of(de.getComponentValue(0)))),
						Math.min(udaljenostBlizu.getValueAt(DomainElement.of(KormiloFuzzySystemMin.this.valueDK)),
								kutKormilaUlijevo.getValueAt(DomainElement.of(de.getComponentValue(0))))));
			return result;
		}
	};
	
	// AKO DK nije blizu ONDA K je udesno
	private FuzzyRule rule4 = new FuzzyRule() {

		@Override
		public IFuzzySet zaključak() {
			MutableFuzzySet result = new MutableFuzzySet(kutKormilaUdesno.getDomain());
			for(DomainElement de : result.getDomain())
				result.set(de, 
						Math.min(udaljenostNeBlizu.getValueAt(DomainElement.of(KormiloFuzzySystemMin.this.valueDK)),
								kutKormilaUdesno.getValueAt(DomainElement.of(de.getComponentValue(0)))));
			return result;
		}
		
	};
	
	// AKO LK nije blizu ONDA K je ulijevo
	private FuzzyRule rule5 = new FuzzyRule() {

		@Override
		public IFuzzySet zaključak() {
			MutableFuzzySet result = new MutableFuzzySet(kutKormilaUdesno.getDomain());
			for(DomainElement de : result.getDomain())
				result.set(de, 
						Math.min(udaljenostNeBlizu.getValueAt(DomainElement.of(KormiloFuzzySystemMin.this.valueLK)),
								kutKormilaUlijevo.getValueAt(DomainElement.of(de.getComponentValue(0)))));
			return result;
		}
		
	};
	
	// Ako S je 0 ONDA K je u suprotnom smjeru
	private FuzzyRule rule6 = new FuzzyRule() {

		@Override
		public IFuzzySet zaključak() {
			MutableFuzzySet result = new MutableFuzzySet(kutKormilaUdesno.getDomain());
			if(KormiloFuzzySystemMin.this.valueS == 0)
				result.set(DomainElement.of(90), 1.);
			return result;
		}
		
	};
	
	@Override
	public int zaključi(int L, int D, int LK, int DK, int V, int S, boolean print) {
		this.valueL = L;
		this.valueD = D;
		this.valueLK = LK;
		this.valueDK = DK;
		this.valueS = S;
		
		IFuzzySet zaključak1 = rule1.zaključak();
		IFuzzySet zaključak2 = rule2.zaključak();
		IFuzzySet zaključak3 = rule3.zaključak();
		IFuzzySet zaključak4 = rule4.zaključak();
		IFuzzySet zaključak5 = rule5.zaključak();
		IFuzzySet zaključak6 = rule6.zaključak();
		
		IFuzzySet zaključak = 
				Operations.binaryOperation(
				Operations.binaryOperation(
				Operations.binaryOperation(
						Operations.binaryOperation(
				Operations.binaryOperation(zaključak1, zaključak2, Operations.zadehOr()),
				zaključak3, Operations.zadehOr()),
						zaključak4, Operations.zadehOr()),
				zaključak5, Operations.zadehOr()),
				zaključak6, Operations.zadehOr());
		
		if(print)
			for(DomainElement e : zaključak.getDomain()) {
				System.out.println("mu("+e+")="+zaključak.getValueAt(e));
				}
		int value = def.vrijednost(zaključak);
		return value;
	}

	@Override
	public IFuzzySet getZakljucakFoRule(int number) {
		return rules.get(number).zaključak();
	}

}
