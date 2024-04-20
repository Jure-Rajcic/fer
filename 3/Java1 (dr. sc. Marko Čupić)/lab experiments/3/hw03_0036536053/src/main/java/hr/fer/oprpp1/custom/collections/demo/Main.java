package hr.fer.oprpp1.custom.collections.demo;

import java.lang.reflect.Array;
import java.lang.reflect.Executable;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import hr.fer.oprpp1.custom.collections.SimpleHashtable;

public class Main {
    // java -cp target/classes hr.fer.oprpp1.custom.collections.demo.Main
    public static void main(String[] args) {
        // create collection:
        SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
        // fill data:
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5); // overwrites old grade for Ivana
        System.out.println(Arrays.toString(examMarks.toArray()));
        // for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
        // System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
        // }
        // // Ante => 2
        // // Ivana => 5
        // // Jasna => 2
        // // Kristina => 5
        // System.out.println();

        // // Kod prikazan u nastavku također bi morao raditi i ispisati kartezijev
        // produkt
        // // uređenih parova
        // for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
        // for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
        // System.out.printf(
        // "(%s => %d) - (%s => %d)%n",
        // pair1.getKey(), pair1.getValue(),
        // pair2.getKey(), pair2.getValue());
        // }
        // }
        // System.out.println();

        // // U sljedećem primjeru, iz kolekcije se uklanja ocjena za Ivanu na korektan
        // // način (nema iznimke).
        // System.out.println(Arrays.toString(examMarks.toArray()));
        // Iterator<SimpleHashtable.TableEntry<String, Integer>> iter =
        // examMarks.iterator();
        // while (iter.hasNext()) {
        // SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
        // if (pair.getKey().equals("Ivana")) {
        // iter.remove(); // sam iterator kontrolirano uklanja trenutni element
        // }
        // }
        // System.out.println(Arrays.toString(examMarks.toArray()));
        // System.out.println();

        // // Sljedeći kod bacio bi IllegalStateException jer se uklanjanje poziva više
        // od jednom za trenutni par nad
        // // kojim je iterator (to bi bacio drugi poziv metode remove):
        // try {
        // Iterator<SimpleHashtable.TableEntry<String, Integer>> iter =
        // examMarks.iterator();
        // while (iter.hasNext()) {
        // SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
        // if (pair.getKey().equals("Ivana")) {
        // iter.remove();
        // iter.remove();
        // }
        // }
        // } catch (IllegalStateException e) {
        // System.out.println("Kod je bacio IllegalStateException, to smo i htjeli");
        // }
        // System.out.println();

        // // Sljedeći kod bacio bi ConcurrentModificationException jer se uklanjanje
        // poziva “izvana” (direktno nad
        // // kolekcijom a ne kroz iterator koji to može obaviti kontrolirano i
        // ažurirati svoje interne podatke). Iznimku bi
        // // bacila metoda hasNext jer je ona prva koja se u prikazanom primjeru poziva
        // nakon brisanja.
        // try {
        // Iterator<SimpleHashtable.TableEntry<String, Integer>> iter =
        // examMarks.iterator();
        // while (iter.hasNext()) {
        // SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
        // if (pair.getKey().equals("Ivana")) {
        // examMarks.remove("Ivana");
        // }
        // }
        // } catch (ConcurrentModificationException e) {
        // System.out.println("Kod je bacio ConcurrentModificationException, to smo i
        // htjeli");
        // }
        // System.out.println();

        // // Sljedeći kod trebao bi ispisati sve parove i po završetku ostaviti
        // // kolekciju praznom.
        // Iterator<SimpleHashtable.TableEntry<String, Integer>> iter =
        // examMarks.iterator();

        // while (iter.hasNext()) {
        // SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
        // System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
        // iter.remove();
        // }
        // System.out.printf("Veličina: %d%n", examMarks.size());
        // System.out.println();
    }

}
