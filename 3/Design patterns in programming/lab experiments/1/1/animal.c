#include "animal.h"
#include <stdio.h>
#include <stdlib.h>

// virtualne metode -> metode koje omoguuju polimorfizam
char const *dogGreet(void){ return "vau!";}
char const *dogMenu(void){ return "kuhanu govedinu";}
// konstruktor
void constructDog(struct Animal *dog, char const *name){
    dog->name = name;
    dog->table = malloc(sizeof(PTRFUN) * 2);
    dog->table[0] = dogGreet;
    dog->table[1] = dogMenu;
}

struct Animal *createDog(char const *name) {
    struct Animal *dog = malloc(sizeof(struct Animal));
    constructDog(dog, name);
    return dog;
}


char const *catGreet(void){ return "mijau!";}
char const *catMenu(void){ return "konzerviranu tunjevinu";}
void constructCat(struct Animal *cat, char const *name) {
    cat->name = name;
    cat->table = malloc(sizeof(PTRFUN) * 2);
    cat->table[0] = catGreet;
    cat->table[1] = catMenu;
}
struct Animal *createCat(char const *name) {
    struct Animal *cat = malloc(sizeof(struct Animal));
    constructCat(cat, name);
    return cat;
}

void animalPrintGreeting(struct Animal *animal){
   printf("%s pozdravlja: %s\n", animal->name, animal->table[0]());
}

void animalPrintMenu(struct Animal *animal){
   printf("%s voli %s\n", animal->name, animal->table[1]());
}