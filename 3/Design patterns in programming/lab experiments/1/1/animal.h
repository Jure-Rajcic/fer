#ifndef ANIMAL_H
#define ANIMAL_H

typedef char const *(*PTRFUN)(void);

struct Animal{
    char const *name; // data
    PTRFUN *table; // virtual table
};

struct Animal *createDog(char const *name);
struct Animal *createCat(char const *name);

// methods
void animalPrintGreeting(struct Animal *animal);
void animalPrintMenu(struct Animal *animal);


#endif /* ANIMAL_H */
