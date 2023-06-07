#ifndef PARROT_H
#define PARROT_H

#include "animal.h"
#include "myfactory.h"

typedef struct Parrot Parrot;

struct Parrot {
    Animal base;
    const char* name;
};

char const* parrot_name(Parrot* this);
char const* parrot_greet(Animal* this);
char const* parrot_menu(Animal* this);
void* parrot_constructor(Parrot* this, char const* name);


static AnimalVTable PARROT_VT = {
    .name = (char const* (*)(Animal* this)) parrot_name,
    .greet = (char const* (*)(Animal* this)) parrot_greet,
    .menu = (char const* (*)(Animal* this)) parrot_menu,
};

static ObjectInfo ParrotOBjectInfo = {
    .constructor = (void* (*)(void*, char const*)) parrot_constructor,
    .size = sizeof(Parrot),
};

#endif
