#include "dog.h"
#include <stdlib.h>
#include "stdio.h"


char const* dog_name(Dog* this) {
    return this->name;
}

char const* dog_greet(Animal* this) {
    return "Woof!";
}

char const* dog_menu(Animal* this) {
    return "Meat";
}


void* dog_constructor(Dog* this, char const* name) {
    animal_constructor((Animal*) this);
    this->base.vtable = &DOG_VT;
    this->name = name;
    return this;
}


ObjectInfo object_info() {
    return DogOBjectInfo;
}