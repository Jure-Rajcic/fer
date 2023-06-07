
#include "animal.h"
#include <stdlib.h>
#include <stdio.h>

void testAnimals(void){
  struct Animal* p1=createDog("Hamlet");
  struct Animal* p2=createCat("Ofelija");
  struct Animal* p3=createDog("Polonije");

  animalPrintGreeting(p1);
  animalPrintGreeting(p2);
  animalPrintGreeting(p3);

  animalPrintMenu(p1);
  animalPrintMenu(p2);
  animalPrintMenu(p3);

  free(p1); free(p2); free(p3);
}


char const *dummyPrintFun(void){ return "pod zadatak 2";}

int main(void) {
    printf("1.\n");

/// testAnimals
    testAnimals();
    //   Hamlet pozdravlja: vau!
    //   Ofelija pozdravlja: mijau!
    //   Polonije pozdravlja: vau!
    //   Hamlet voli kuhanu govedinu
    //   Ofelija voli konzerviranu tunjevinu
    //   Polonije voli kuhanu govedinu

/// stack
    printf("2.\n");

    PTRFUN funcTable[] = { dummyPrintFun };
    struct Animal a1 = { "zivotinja koja zivi na stacku", funcTable};
    // ILI 
    // a1.name = "...";
    // a1.table = funcTable;
    animalPrintGreeting(&a1);

/// heap
    printf("3.\n");
    struct Animal* a2 = (struct Animal*)malloc(sizeof(struct Animal));
    a2->name = "zivotinja koja zivi na heepu";
    a2->table[0] = dummyPrintFun;
    animalPrintGreeting(a2);
    free(a2);

/// number of dogs
    printf("4.\n");
    int n;
    printf("Enter number of dogs: ");
    scanf("%d", &n);


    for (int i = 0; i < n; i++) {
        char buffer[20];
        snprintf(buffer, sizeof(buffer), "pas %d", i+1);
        animalPrintGreeting(createDog(buffer));
    }
    return 0;
}
// ---------------------- nema veze sto se crveni VS nije sav svoj ---------------------- //
// gcc -o main main.c animal.c
// ./main