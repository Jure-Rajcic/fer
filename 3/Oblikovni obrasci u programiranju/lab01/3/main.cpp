#include <stdio.h>
#include <stdlib.h>


class CoolClass{
public:
  virtual void set(int x){x_=x;};
//   virtual int get() = 0; this would signal that class is abstract and cant be instantiated
  virtual int get(){return x_;};

private:
  int x_;
//   int int1;
//   int int2;
};

class PlainOldClass{
public:
  void set(int x){x_=x;};
  int get(){return x_;};
private:
  int x_;
};

int main(){
    printf("sizeof(PlainOldClass) = %ld\n", sizeof(PlainOldClass));
    printf("sizeof(CoolClass) = %ld\n", sizeof(CoolClass));

    return 0;
}

/*
Odg:
    Ugl PlainOldClass zauzima 4 bajta, gdje se pohranjuje smao int tip podatka,
    ona nema nikakv pokazivac na funkcije iz razloga sto kod poziva
    ne-virtualnih funkcija korisit skriveni this parametar preko kojeg funkcija
    moze odrediti adresu s kojeg cita podatke objekta.

    CoolClass ima je primjer klase koja ima 1 ili vise virtualnoh metoda sto znac
    da se u njoj nalazi pokazivac na vtablicu => vpointer na 64 bitnoj arhitekturi 
    zauzima 8 bytova. 
    Vtablica je tablica funkcija i zbog same cinjenice da virtualne funkcije 
    mozemo nadjacati potrebno je znati koja se funkcija poziva u odredenom trenutku
    (sto nam daje vtablica).
    Ovo sve nam govori da CoolClass zauzima 12 bytova. No ispi je 16 bytova.
    Razlog je sto compiler dodaje 4 byta zbog paddinga, tj. da bi se objekt CoolClass
    mogao pohraniti u memoriji na 8 bytovnoj adresi. (Npr dodavanjem jos 1 podatha tipa
    int size ostaje isit dok se dodavanjem još jednog int podatka (ukupno dodana 2 inta) 
    size povecava za 4 byta => sve uk 16 + 8)

*/