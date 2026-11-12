package hr.fer.zemris.neinr.hw06.neurofuzzy.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Matrica {

	private double[][] elements;
	private int rows;
	private int columns;
	private int noOfChanges = 0;
	private double epsilon = 1E-9;
	
	/**
	 * Konstruktor koji stvara matricu dimenzija n x m, te na sve elemente stavlja 0
	 * 
	 * @param n broj redaka
	 * @param m broj stupaca
	 */
	public Matrica(int n, int m) {
		this.rows = n;
		this.columns = m;
		this.elements = new double[n][m];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				this.elements[i][j] = 0.;
			}
		}
	}
	
	/**
	 * Konstruktor kojem se preda 2-D polje s kojim se puni matrica, te njezine dimenzije
	 * 
	 * @param elements 2-D polje s kojim se puni matrica
	 * @param n broj redaka
	 * @param m broj stupaca
	 */
	public Matrica(double[][] elements, int n, int m) {
		this.elements = elements;
		this.rows = n;
		this.columns = m;
	}
	
	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	/**
	 * Getter, vra�a broj redaka matrice
	 * 
	 * @return broj redaka matrice
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Getter, vra�a broj stupaca matrice
	 * 
	 * @return broj stupaca matrice
	 */
	public int getColumns() {
		return columns;
	}
	
	/**
	 * dohva�a broj promjena pi matrice
	 * 
	 * @return broj promjena
	 */
	private int getNoOfChanges() {
		return noOfChanges;
	}

	/**
	 * mijenja broj promjena pi-matrice
	 * 
	 * @param noOfChanges novi broj promjena
	 */
	private void setNoOfChanges(int noOfChanges) {
		this.noOfChanges = noOfChanges;
	}
	
	/**
	 * Metoda koja mijenja dimenzije matrice
	 * 
	 * @param x novi broj redaka
	 * @param y novi broj stupaca
	 */
	public void changeDimension(int x, int y) {
		double[][] array = new double[x][y];
		for(int i = 0; i < x; i++)
			for(int j = 0; j < y; j++)
				if(i < this.rows &&  i < this.columns)
					array[i][j] = this.elements[i][j];
				else
					array[i][j] = 0.;
		
		this.elements = array;
		this.rows = x;
		this.columns = y;
	}
	
	/**
	 * Metoda koja vra�a kopiju trenutne matrice
	 */
	public Matrica clone() {
		return new Matrica(this.elements.clone(), this.rows, this.columns);
	}
	
	public static Matrica fromFile(String filename) {
		if(!Files.exists(Paths.get(filename)))
			throw new IllegalArgumentException("File " + filename + " does not exist.");
		
		Matrica result = null;
		try {
			List<String> lines = Files.readAllLines(Paths.get(filename));
			
			int n = lines.size(), m = lines.get(0).trim().split("[ \t]").length;
			result = new Matrica(n, m);
			
			for(int i = 0; i < n; i++) {
				String[] array = lines.get(i).trim().split("[ \t]");
				for(int j = 0; j < m; j++) {
					result.elements[i][j] = Double.valueOf(array[j]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void toFile(String filename) {
		if(Files.exists(Paths.get(filename)))
			throw new IllegalArgumentException("File " + filename + " already exists.");
		
		try {
			Files.writeString(Paths.get(filename), this.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metoda koja vra�a element na zadanoj poziciji
	 * 
	 * @param i redak elementa
	 * @param j stupac elementa
	 * @return element, ako postoji
	 */
	public double getElement(int i, int j) {
		if(i >= this.rows || j >= this.columns)
			throw new IndexOutOfBoundsException("Index out of bounds, " + i + "," + j + " dimensions: " + this.rows + "," + this.columns);
		return this.elements[i][j];
	}
	
	/**
	 * Metoda koja postavlja �eljenu vrijednost na zadanu poziciju matrice
	 * 
	 * @param value nova vrijednost
	 * @param i redak elementa
	 * @param j stupac elementa
	 */
	public void setElement(double value, int i, int j) {
		if(i >= this.rows || j >= this.columns)
			throw new IndexOutOfBoundsException("Index out of bounds");
		this.elements[i][j] = value;
	}
	
	/**
	 * Metoda koja matricu mno�i sa zadanim skalarom
	 * 
	 * @param value skalar s kojim se treba pomno�iti
	 */
	public Matrica multiply(double value) {
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.columns; j++) {
				this.elements[i][j] *= value;
			}
		}
		return this;
	}
	
	/**
	 * Metoda koja zbraja zadane matrice
	 * 
	 * @param a prva matrica
	 * @param b druga matrica
	 * @return rezultat
	 */
	public static Matrica add(Matrica a, Matrica b) {
		if(a.rows != b.rows || a.columns != b.columns)
			throw new IllegalArgumentException("Matrices are not of the same size.");
		
		Matrica result = new Matrica(a.rows, a.columns);
		for(int i = 0; i < a.rows; i++) {
			for(int j = 0; j < a.columns; j++) {
				result.setElement(a.elements[i][j] + b.elements[i][j], i, j);
			}
		}
		
		return result;
	}
	
	/**
	 * Metoda koja oduzima zadane matrice
	 * 
	 * @param a prva matrica
	 * @param b druga matrica
	 * @return rezultat
	 */
	public static Matrica sub(Matrica a, Matrica b) {
		if(a.rows != b.rows || a.columns != b.columns)
			throw new IllegalArgumentException("Matrices are not of the same size.");
		
		Matrica result = new Matrica(a.rows, a.columns);
		for(int i = 0; i < a.rows; i++) {
			for(int j = 0; j < a.columns; j++) {
				result.setElement(a.elements[i][j] - b.elements[i][j], i, j);
			}
		}
		
		return result;
	}
	
	/**
	 * Metoda koja mno�i zadane matrice
	 * 
	 * @param a prva matrica
	 * @param b druga matrica
	 * @return rezultat
	 */
	public static Matrica multiply(Matrica a, Matrica b) {
		if(a.columns != b.rows)
			throw new IllegalArgumentException("Column dimension of the first matrix must be equal to row dimension of the second.");
		
		Matrica result = new Matrica(a.rows, b.columns);
		for(int i = 0; i < result.rows; i++) {
			for(int j = 0; j < result.columns; j++) {
				double sum = 0.;
				for(int k = 0; k < a.columns; k++)
					sum += a.elements[i][k] * b.elements[k][j];
				result.setElement(sum, i, j);
			}
		}
		
		return result;
	}
	
	/**
	 * Metoda koja transponira zadanu matricu
	 * 
	 * @param a matrica koju treba transponirati
	 * @return rezultat
	 */
	public static Matrica transpose(Matrica a) {
		Matrica result = new Matrica(a.columns, a.rows);
		
		for(int i = 0; i < result.rows; i++) {
			for(int j = 0; j < result.columns; j++) {
				result.elements[i][j] = a.elements[j][i];
			}
		}
		
		return result;
	}
	
	/**
	 * Metoda koja zbraja trenutnu matricu s zadanom, ekvivalent od "+="
	 * 
	 * @param b matrica s kojom treba zbrojiti trenutnu
	 * @return rezultat
	 */
	public Matrica add(Matrica b) {
		if(this.rows != b.rows || this.columns != b.columns)
			throw new IllegalArgumentException("Matrices are not of the same size.");
		
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.columns; j++) {
				this.elements[i][j] += b.elements[i][j];
			}
		}
		return this;
	}
	
	/**
	 * Metoda koja od trenutne matrice oduzima zadanu, ekvivalent od "-="
	 * 
	 * @param b matrica koju treba oduzeti
	 * @return rezultat
	 */
	public Matrica sub(Matrica b) {
		if(this.rows != b.rows || this.columns != b.columns)
			throw new IllegalArgumentException("Matrices are not of the same size.");
		
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.columns; j++) {
				this.elements[i][j] -= b.elements[i][j];
			}
		}
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.columns; j++) {
				sb.append(this.elements[i][j] + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Matrica other = (Matrica) obj;
		if (columns != other.columns)
			return false;
		if (rows != other.rows)
			return false;
		for(int i = 0; i < this.rows; i++)
			for(int j = 0; j < this.columns; j++)
				if(this.elements[i][j] - other.elements[i][j] > this.epsilon)
					return false;
		return true;
	}
	
	/**
	 * Metoda koja radi unaprednu supstituciju
	 * 
	 * @param b vektor koji predstavlja desnu stranu jednad�be
	 * @return vektor rje�enja
	 */
	public Matrica forwardSubstitution(Matrica b) {
		if(b.rows != this.rows || b.columns != 1)
			throw new IllegalArgumentException("The matrix must have n x 1 dimension.");
		
		Matrica result = new Matrica(b.rows, b.columns);
		for(int i = 0; i < result.rows; i++) {
			result.elements[i][0] = b.elements[i][0];
			for(int j = 0; j < i; j++) {
				result.elements[i][0] -= this.elements[i][j] * result.elements[j][0];
			}
		}
		
		return result;
	}
	
	/**
	 * Metoda koja radi unazadnu supstituciju
	 * 
	 * @param b vektor koji predstavlja desnu stranu jednad�be
	 * @return vektor rje�enja
	 */
	public Matrica backwardSubstitution(Matrica b) {
		if(b.rows != this.rows || b.columns != 1)
			throw new IllegalArgumentException("The matrix must have n x 1 dimension.");
		
		Matrica result = new Matrica(b.rows, b.columns);
		for(int i = result.rows - 1; i >= 0; i--) {
			result.elements[i][0] = b.elements[i][0];
			for(int j = i + 1; j < result.rows; j++) {
				result.elements[i][0] -= this.elements[i][j] * result.elements[j][0];
			}
			result.elements[i][0] /= this.elements[i][i];
		}
		
		return result;
	}
	
	/**
	 * Metoda koja radi LU dekompoziciju zadane matrice
	 * 
	 * @param A matrica za koju treba napraviti LU dekompoziciju
	 */
	public static Matrica LUDecomposition(Matrica A) {
		if(A.getRows() != A.getColumns())
			throw new IllegalArgumentException("Matrix must have square dimensions.");
		
		for(int i = 0; i < A.getRows(); i++)
			if(A.getElement(i, i) < A.epsilon)
				throw new IllegalArgumentException("Matrix cannot have 0 on diagonal elements.");
		
		for(int i = 0; i < A.getRows() - 1; i++) {
			if(Math.abs(A.getElement(i, i)) < A.epsilon) {
				System.out.println("LU decomposition is not possible. Matrix is singular.");
				return null;
			}
			for(int j = i + 1; j < A.getRows(); j++) {
				A.setElement(A.getElement(j, i) / A.getElement(i, i), j, i);
				for(int k = i + 1; k < A.rows; k++) {
					A.setElement(A.getElement(j, k) - A.getElement(j, i) * A.getElement(i, k), j, k);
				}
			}
		}
		
		if(Math.abs(A.getElement(A.getRows() - 1, A.getRows() - 1)) < A.epsilon) {
			System.out.println("LU decomposition is not possible. Matrix is singular.");
			return null;
		}
		
		return A;
	}
	
	/**
	 * Metoda koja radi LUP dekompoziciju zadane matrice
	 * 
	 * @param A matrica za koju treba napraviti LUP dekompoziciju
	 */
	public static Matrica LUPDecomposition(Matrica A) {
		if(A.getRows() != A.getColumns())
			throw new IllegalArgumentException("Matrix must have square dimensions.");
		
		Matrica pi = new Matrica(A.getRows(), 1);
		A.setNoOfChanges(0);
		for(int i = 0; i < pi.getRows(); i++) {
			pi.setElement(i, i, 0);
		}
		
		for(int i = 0; i < A.getRows() - 1; i++) {
			double pivot = 0.;
			int l = i;
			for(int j = i; j < A.getRows(); j++) {
				if(Math.abs(A.getElement(j, i)) > pivot) {
					pivot = Math.abs(A.getElement(j, i));
					l = j;
				}
			}
			if(pivot < 1E-9)
				throw new IllegalStateException("Matrix is singular.");
			if(l != i)
				A.setNoOfChanges(A.getNoOfChanges() + 1);
			
			changeMatrixElements(pi, i, 0, l, 0);
			for(int j = 0; j < A.getRows(); j++) {
				changeMatrixElements(A, i, j, l, j);
			}
			
			for(int j = i + 1; j < A.getRows(); j++) {
				A.setElement(A.getElement(j, i) / A.getElement(i, i), j, i);
				for(int k = i + 1; k < A.getRows(); k++) {
					A.setElement(A.getElement(j, k) - A.getElement(j, i) * A.getElement(i, k), j, k);
				}
			}
		}
		
		for(int i = 0; i < A.getRows(); i++) {
			for(int j = 0; j < A.getColumns(); j++) {
				if(Math.abs(A.getElement(i, j)) < A.epsilon)
					A.setElement(A.getElement(i, j) * -1, i, j);
			}
		}
		
		return pi;
	}
	
	/**
	 * Pomo�na metoda koja mijenja elemente na zadanim pozicijama u matrici
	 * 
	 * @param A matrica u kojoj treba odraditi zamjenu
	 * @param x1 index retka prvog elementa
	 * @param y1 index stupca prvog elementa
	 * @param x2 index retka drugog elementa
	 * @param y2 index stupca drugog elementa
	 */
	private static void changeMatrixElements(Matrica A, int x1, int y1, int x2, int y2) {
		double tmp = A.getElement(x1, y1);
		A.setElement(A.getElement(x2, y2), x1, y1);
		A.setElement(tmp, x2, y2);
	}
	
	/**
	 * Metoda koja ra�una inverz zadane matrice
	 * 
	 * @param A matrica za koju treba izra�unati inverz
	 * @return rezultat
	 */
	public static Matrica inverse(Matrica A) {
		Matrica pi = Matrica.LUPDecomposition(A);
		
		Matrica P = new Matrica(pi.getRows(), pi.getRows());
		for(int i = 0; i < P.getRows(); i++) {
			P.setElement(1., i, (int) pi.getElement(i, 0));
		}
		
		Matrica inverse = new Matrica(A.getRows(), A.getColumns());
		for(int i = 0; i < P.getRows(); i++) {
			Matrica b = new Matrica(P.getColumns(), 1);
			for(int j = 0; j < P.getColumns(); j++) {
				b.setElement(P.getElement(j, i), j, 0);
			}
			Matrica y = A.forwardSubstitution(b);
			Matrica x = A.backwardSubstitution(y);
			for(int j = 0; j < P.getColumns(); j++) {
				inverse.setElement(x.getElement(j, 0), j, i);
			}
		}
		
		for(int i = 0; i < inverse.getRows(); i++) {
			for(int j = 0; j < inverse.getColumns(); j++) {
				if(Math.abs(inverse.getElement(i, j)) < 1E-9)
					inverse.setElement(inverse.getElement(i, j) * -1, i, j);
			}
		}
		
		return inverse;
	}
	
	/**
	 * Metoda koja ra�una determinantu matrice pomo�u LUP dekompozicije
	 * 
	 * @param A matrica �ija se determinanta tra�i
	 * @return rezultat
	 */
	public static double determinant(Matrica A) {
		Matrica.LUPDecomposition(A);
		
		double result = 1.;
		for(int i = 0; i < A.getRows(); i++) {
			result *= A.getElement(i, i);
		}
		
		return result * Math.pow(-1., A.getNoOfChanges());
	}
}
