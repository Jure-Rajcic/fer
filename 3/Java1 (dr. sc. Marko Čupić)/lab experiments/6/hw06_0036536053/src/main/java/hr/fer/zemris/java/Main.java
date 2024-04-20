package hr.fer.zemris.java;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class Main {
    public static void main(String[] args) {

        Complex[] arr = new Complex[] { Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG};

        ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(2, 0), arr);
        ComplexPolynomial cp = crp.toComplexPolynom();
        System.out.println(crp);
        System.out.println(cp);
        System.out.println(cp.derive());
        // System.out.println(Complex.ZERO);
        // System.out.println(Complex.ONE);
        // System.out.println(Complex.ONE_NEG);
        // System.out.println(Complex.IM);
        // System.out.println(Complex.IM_NEG);

    }
}
