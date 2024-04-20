Verzija Python interpretera: 3.8.5 (u kodu nisu korištene nikakve kompliciranije funkcije pa bi kod trebao raditi na većini ili vjerojatno svim verzijama Python 3.x interpretera)

Program je namijenjen da se pokreće preko terminala.

Potrebno je unijeti poruku čija je duljina veća od 0, te 2 broja čija veličina mora biti veća od 0.

Ukoliko se bilo koji od prethodno navedenih uvjeta ne ispuni, program prekida izvođenje i ispisuje prikladan tekst pogreške.

Pri unošenju poruke, možete, ali i ne morate staviti zvjezdicu "*" kao zadnji znak poruke.

Ukoliko ne zaključite poruku sa znakom zvjezdice, program će to prepoznati i sam će dodati zvjezdicu na kraj poruke.

Zvjezdicom će ujedno u ispisu kodirane poruke biti naznačen njezin kraj.

Format u kojem se ispisuje kodirana poruka je niz uređenih trojki koje su međusobno odvojene razmakom.

Testni primjeri:

1. primjer:

	unosi:	cabracadabrarrarrad*, 7, 6
	ispis: (0, 0, c) (0, 0, a) (0, 0, b) (0, 0, r) (3, 1, c) (2, 1, d) (7, 4, r) (3, 5, d) (0, 0, *)

2. primjer:

	unosi: aaaabbbbbbccccccd*, 6, 5
	ispis: (0, 0, a) (1, 3, b) (1, 4, b) (0, 0, c) (1, 4, c) (0, 0, d) (0, 0, *)

3. primjer:

	unosi: abaabbabaacaacabb*, 6, 5
	ispis: (0, 0, a) (0, 0, b) (2, 1, a) (3, 1, b) (6, 4, c) (3, 4, b) (1, 1, *)

4. primjer:
	
	unosi: aaaaaaaaaaa*, 1, 10
	ispis: (0, 0, a) (1, 9, a) (0, 0, *)

5. primjer:

	unosi: aaaabbbccd*, 6, 5
	ispis: (0, 0, a) (1, 3, b) (1, 2, c) (1, 1, d) (0, 0, *)

6. primjer: 

	unosi: aaaaabaaaaa*, 1, 5
	ispis: (0, 0, a) (1, 4, b) (0, 0, a) (1, 4, *)