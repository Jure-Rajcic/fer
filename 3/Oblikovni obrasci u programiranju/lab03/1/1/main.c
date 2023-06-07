#include <stdio.h>
#include <stdlib.h>


#include "animal.h"
#include "myfactory.h"


void animalPrintGreeting(Animal* ptr) {
    printf("%s pozdravlja: %s\n", animal_name(ptr), animal_greet(ptr));
}

void animalPrintMenu(Animal* a) {
    printf("%s voli %s\n", animal_name(a), animal_menu(a));
}


// ./main libdog jure libtiger marko libparrot bozo libdog ivan
int main(int argc, char *argv[]){
    // AllocationType allocs[2] = {ALLOC_STACK, ALLOC_HEAP};
    for (int i=0; i<argc/2; ++i){
        struct Animal* p=(struct Animal*)myfactory(argv[1+2*i], argv[1+2*i+1], ALLOC_HEAP);
        if (!p){
        printf("Creation of plug-in object %s failed.\n", argv[1+2*i]);
        continue;
        }
        animalPrintGreeting(p);
        animalPrintMenu(p);
        free(p); //  ----- PITAT PROFESORA -----
        /*
        Ako se koristi stack za pohranu varijabli onda ce prilikom izlaska iz funkcije,
        polje koje sluzi za pohranu varijabli biti oslobodjeno ??.
        */
        printf("\n");
  }
}


/*

gcc -fPIC -c animal.c
gcc -fPIC -c dog.c
gcc -fPIC -c myfactory.c

gcc -shared -o libdog.so animal.o dog.o

gcc main.c animal.o myfactory.o -o main -ldl && ./main

int main() {
    Animal* animal_ptr = (Animal*) myfactory("libdog", "Mirko", ALLOC_STACK);
    animalPrintGreeting(animal_ptr);
    animalPrintMenu(animal_ptr);
    return 0;
}

*/

