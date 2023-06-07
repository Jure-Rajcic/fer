#include <dlfcn.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "stdio.h"
#include "myfactory.h"


static void* stack_alloc(size_t size) {
    char buf[size];
    return buf;
}

static void* heap_alloc(size_t size) {
    return malloc(size);
}


void* myfactory(char const* libname, char const* ctorarg, AllocationType alloc) {
    char libpath[256];
    snprintf(libpath, sizeof(libpath), "./%s.so", libname);

    void* handle = dlopen(libpath, RTLD_NOW);
    if (!handle) {
        fprintf(stderr, "Error loading library: %s\n", dlerror());
        exit(1);
    }

    ObjectInfo (*info)() = dlsym(handle, "object_info");
    if (!info) {
        fprintf(stderr, "Error loading 'object_info' function: %s\n", dlerror());
        exit(1);
    }

    void* instance;
    switch (alloc) {
    case ALLOC_STACK:
        instance = stack_alloc(info().size);
        break;
    case ALLOC_HEAP:
        instance = heap_alloc(info().size);
        break;
    default:
        fprintf(stderr, "Error: Unknown allocation type\n");
        exit(1);
        break;
    }
    info().constructor(instance, ctorarg);
    return instance;
}


