package hr.fer.zemris.fuzzy.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import hr.fer.zemris.fuzzySystem.AkcelFuzzySystemMin;
import hr.fer.zemris.fuzzySystem.COADefuzzifier;
import hr.fer.zemris.fuzzySystem.Defuzzifier;
import hr.fer.zemris.fuzzySystem.FuzzySystem;
import hr.fer.zemris.fuzzySystem.KormiloFuzzySystemMin;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		Defuzzifier def = new COADefuzzifier();
		
		int L=0,D=0,LK=0,DK=0,V=0,S=0,akcel,kormilo;
		String line = null;
		while(true){
			if((line = input.readLine())!=null){
				if(line.charAt(0)=='K') break;
				Scanner s = new Scanner(line);
				L = s.nextInt();
				D = s.nextInt();
				LK = s.nextInt();
				DK = s.nextInt();
				V = s.nextInt();
				S = s.nextInt();
	        }

	        // fuzzy magic ...
			
			FuzzySystem fsAkcel = new AkcelFuzzySystemMin(def);
			FuzzySystem fsKormilo = new KormiloFuzzySystemMin(def);

	        akcel = fsAkcel.zaključi(L, D, LK, DK, V, S, false);
	        kormilo = fsKormilo.zaključi(L, D, LK, DK, V, S, false);
	        
	        System.out.println(akcel + " " + kormilo);
	        System.out.flush();
		}
	}
}
