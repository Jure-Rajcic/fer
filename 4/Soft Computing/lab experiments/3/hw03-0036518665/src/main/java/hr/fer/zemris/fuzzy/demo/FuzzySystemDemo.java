package hr.fer.zemris.fuzzy.demo;

import java.util.Scanner;

import hr.fer.zemris.fuzzySystem.AkcelFuzzySystemMin;
import hr.fer.zemris.fuzzySystem.COADefuzzifier;
import hr.fer.zemris.fuzzySystem.Defuzzifier;
import hr.fer.zemris.fuzzySystem.FuzzySystem;
import hr.fer.zemris.fuzzySystem.KormiloFuzzySystemMin;

public class FuzzySystemDemo {

	public static void main(String[] args) {
		System.out.println("Unesite vrijednosti L (0-1300), D (0-1300), LK (0-1300), DK (0-1300), V (>= 0) i S (0 ili 1)");
		
		Defuzzifier def = new COADefuzzifier();
		
		Scanner sc = new Scanner(System.in);
		int L = sc.nextInt(), D = sc.nextInt(), LK = sc.nextInt(), DK = sc.nextInt(), V = sc.nextInt(), S = sc.nextInt();
		
		FuzzySystem fsAkcel = new AkcelFuzzySystemMin(def);
		FuzzySystem fsKormilo = new KormiloFuzzySystemMin(def);

        int akcel = fsAkcel.zaključi(L, D, LK, DK, V, S, true);
        int kormilo = fsKormilo.zaključi(L, D, LK, DK, V, S, true);
        
        System.out.println(akcel + " " + kormilo);
	}
}
