package hr.fer.zemris.fuzzySystem;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.fuzzy.CalculatedFuzzySet;
import hr.fer.zemris.fuzzy.Domain;
import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzy.MutableFuzzySet;
import hr.fer.zemris.fuzzy.Operations;
import hr.fer.zemris.fuzzy.StandardFuzzySets;

public class AkcelFuzzySystemMin implements FuzzySystem {

	private Defuzzifier def;
	private int valueL;
	private int valueLK;
	private int valueD;
	private int valueDK;
	private int valueV;
	private List<FuzzyRule> rules = new ArrayList<>();
	
	public AkcelFuzzySystemMin(Defuzzifier def) {
		this.def = def;
		rules.add(rule1);
		rules.add(rule2);
		rules.add(rule3);
	}
	
	private IFuzzySet udaljenostNeVrloBlizu = new CalculatedFuzzySet(Domain.intRange(0, 1301), StandardFuzzySets.gammaFunction(30, 50));
	private IFuzzySet udaljenostVrloBlizu = Operations.unaryOperation(udaljenostNeVrloBlizu, Operations.zadehNot());
	private IFuzzySet pozitivnaAkceleracija = new MutableFuzzySet(Domain.intRange(0, 2)).set(DomainElement.of(1), 1.);
	private IFuzzySet negativnaAkceleracija = Operations.unaryOperation(pozitivnaAkceleracija, Operations.zadehNot());
	private IFuzzySet velikaBrzina = new CalculatedFuzzySet(Domain.intRange(0, 75), StandardFuzzySets.gammaFunction(45, 60));
	
	// AKO DK je blizu ILI LK je blizu ONDA A je negativna
	private FuzzyRule rule1 = new FuzzyRule() {

		@Override
		public IFuzzySet zaključak() {
			MutableFuzzySet result = new MutableFuzzySet(negativnaAkceleracija.getDomain()).set(DomainElement.of(0),
					Math.max(udaljenostVrloBlizu.getValueAt(DomainElement.of(AkcelFuzzySystemMin.this.valueLK)),
					Math.max(udaljenostVrloBlizu.getValueAt(DomainElement.of(AkcelFuzzySystemMin.this.valueDK)),
					Math.max(udaljenostVrloBlizu.getValueAt(DomainElement.of(AkcelFuzzySystemMin.this.valueD)),
					udaljenostVrloBlizu.getValueAt(DomainElement.of(AkcelFuzzySystemMin.this.valueL))))));
			return result;
		}
		
	};
	
	// AKO DK nije blizu ONDA A je pozitvna
	private FuzzyRule rule2 = new FuzzyRule() {

		@Override
		public IFuzzySet zaključak() {
			MutableFuzzySet result = new MutableFuzzySet(pozitivnaAkceleracija.getDomain()).set(DomainElement.of(1),
					Math.max(udaljenostNeVrloBlizu.getValueAt(DomainElement.of(AkcelFuzzySystemMin.this.valueL)),
							udaljenostNeVrloBlizu.getValueAt(DomainElement.of(AkcelFuzzySystemMin.this.valueD))));;
			return result;
		}
			
	};
	
	// AKO Vm je velika ONDA A je negativna
	private FuzzyRule rule3 = new FuzzyRule() {

		@Override
		public IFuzzySet zaključak() {
			MutableFuzzySet result = new MutableFuzzySet(negativnaAkceleracija.getDomain()).set(DomainElement.of(0),
					velikaBrzina.getValueAt(DomainElement.of(AkcelFuzzySystemMin.this.valueV)));
			return result;
		}
		
	};
	
	@Override
	public int zaključi(int L, int D, int LK, int DK, int V, int S, boolean print) {
		this.valueLK = LK;
		this.valueDK = DK;
		this.valueL = L;
		this.valueD = D;
		this.valueV = V;
		
		IFuzzySet zaključak1 = rule1.zaključak();
		IFuzzySet zaključak2 = rule2.zaključak();
		IFuzzySet zaključak3 = rule3.zaključak();
		
		IFuzzySet zaključak =
				Operations.binaryOperation(
				Operations.binaryOperation(zaključak1, zaključak2, Operations.zadehOr()),
				zaključak3, Operations.zadehOr());
		
		if(print)
			for(DomainElement e : zaključak.getDomain()) {
				System.out.println("mu("+e+")="+zaključak.getValueAt(e));
				}
		
		return def.vrijednost(zaključak) == 0 ? -1 : 2;
	}

	@Override
	public IFuzzySet getZakljucakFoRule(int number) {
		return rules.get(number).zaključak();
	}

}
