#ifndef DOG_H
#define DOG_H

#include "animal.h"
#include "myfactory.h"

typedef struct Dog Dog;

struct Dog {
    Animal base;
    const char* name;
};

char const* dog_name(Dog* this);
char const* dog_greet(Animal* this);
char const* dog_menu(Animal* this);
void* dog_constructor(Dog* this, char const* name);


static AnimalVTable DOG_VT = {
    .name = (char const* (*)(Animal* this)) dog_name,
    .greet = (char const* (*)(Animal* this)) dog_greet,
    .menu = (char const* (*)(Animal* this)) dog_menu,
};

static ObjectInfo DogOBjectInfo = {
    .constructor = (void* (*)(void*, char const*)) dog_constructor,
    .size = sizeof(Dog),
};

#endif
