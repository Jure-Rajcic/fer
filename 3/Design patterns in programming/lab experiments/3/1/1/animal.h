#ifndef ANIMAL_H
#define ANIMAL_H

typedef struct Animal Animal;

typedef struct {
    char const* (*name)(Animal* this);
    char const* (*greet)(Animal* this);
    char const* (*menu)(Animal* this);
} AnimalVTable;

struct Animal {
    AnimalVTable *vtable;
};

char const* animal_name(Animal* this);
char const* animal_greet(Animal* this);
char const* animal_menu(Animal* this);

static AnimalVTable ANIMAL_VT = {
    .name = (char const* (*)(Animal* this)) animal_name,
    .greet = (char const* (*)(Animal* this)) animal_greet,
    .menu = (char const* (*)(Animal* this)) animal_menu,
};

void animal_constructor(Animal* this);


#endif
