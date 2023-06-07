#include "parrot.h"


char const* parrot_name(Parrot* this) {
    return this->name;
}

char const* parrot_greet(Animal* this) {
    return "Parrot greet!";
}

char const* parrot_menu(Animal* this) {
    return "Rassberry";
}


void* parrot_constructor(Parrot* this, char const* name) {
    animal_constructor((Animal*) this);
    this->base.vtable = &PARROT_VT;
    this->name = name;
    return this;
}


ObjectInfo object_info() {
    return ParrotOBjectInfo;
}