#include "animal.h"

char const* animal_name(Animal* this) {
    return this->vtable->name(this);
}

char const* animal_greet(Animal* this) {
    return this->vtable->greet(this);
}

char const* animal_menu(Animal* this) {
    return this->vtable->menu(this);
}

void animal_constructor(Animal* this) {
    this->vtable = &ANIMAL_VT;
}
