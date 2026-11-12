package hr.fer.zemris.fuzzy.demo;

import java.util.Scanner;

import hr.fer.zemris.fuzzy.DomainElement;
import hr.fer.zemris.fuzzy.IFuzzySet;
import hr.fer.zemris.fuzzySystem.AkcelFuzzySystemMin;
import hr.fer.zemris.fuzzySystem.COADefuzzifier;
import hr.fer.zemris.fuzzySystem.Defuzzifier;
import hr.fer.zemris.fuzzySystem.FuzzySystem;
import hr.fer.zemris.fuzzySystem.KormiloFuzzySystemMin;

public class FuzzyRuleDemo {

	public static void main(String[] args) {
		System.out.println("Unesite vrijednosti L (0-1300), D (0-1300), LK (0-1300), DK (0-1300), V (>= 0) i S (0 ili 1)");
		
		Defuzzifier def = new COADefuzzifier();
		
		Scanner sc = new Scanner(System.in);
		int L = sc.nextInt(), D = sc.nextInt(), LK = sc.nextInt(), DK = sc.nextInt(), V = sc.nextInt(), S = sc.nextInt();
		
		FuzzySystem fsAkcel = new AkcelFuzzySystemMin(def);
		FuzzySystem fsKormilo = new KormiloFuzzySystemMin(def);
		
		int akcel = fsAkcel.zaključi(L, D, LK, DK, V, S, false);
        int kormilo = fsKormilo.zaključi(L, D, LK, DK, V, S, false);
        
        System.out.println("Jedan od zaključaka za akceleraciju:");
        IFuzzySet akcelZakljucak = fsAkcel.getZakljucakFoRule((int) (Math.random() * 2.));
        for(DomainElement e : akcelZakljucak.getDomain()) {
			System.out.println("mu("+e+")="+akcelZakljucak.getValueAt(e));
		}
        System.out.println(def.vrijednost(akcelZakljucak) == 0 ? -1 : 2);
        
        System.out.println("Jedan od zaključaka za kormilo:");
        IFuzzySet kormiloZakljucak = fsKormilo.getZakljucakFoRule((int) (Math.random() * 6.));
        for(DomainElement e : kormiloZakljucak.getDomain()) {
			System.out.println("mu("+e+")="+kormiloZakljucak.getValueAt(e));
		}
        System.out.println(def.vrijednost(kormiloZakljucak));
	}
}
