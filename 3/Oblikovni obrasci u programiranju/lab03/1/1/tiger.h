#ifndef TIGER_H
#define TIGER_H

#include "animal.h"
#include "myfactory.h"

typedef struct Tiger Tiger;

struct Tiger {
    Animal base;
    const char* name;
};

char const* tiger_name(Tiger* this);
char const* tiger_greet(Animal* this);
char const* tiger_menu(Animal* this);
void* tiger_constructor(Tiger* this, char const* name);


static AnimalVTable TIGER_VT = {
    .name = (char const* (*)(Animal* this)) tiger_name,
    .greet = (char const* (*)(Animal* this)) tiger_greet,
    .menu = (char const* (*)(Animal* this)) tiger_menu,
};

static ObjectInfo TigerOBjectInfo = {
    .constructor = (void* (*)(void*, char const*)) tiger_constructor,
    .size = sizeof(Tiger),
};

#endif
