#include "tiger.h"


char const* tiger_name(Tiger* this) {
    return this->name;
}

char const* tiger_greet(Animal* this) {
    return "Tiger greet!";
}

char const* tiger_menu(Animal* this) {
    return "A lot of meat";
}


void* tiger_constructor(Tiger* this, char const* name) {
    animal_constructor((Animal*) this);
    this->base.vtable = &TIGER_VT;
    this->name = name;
    return this;
}


ObjectInfo object_info() {
    return TigerOBjectInfo;
}