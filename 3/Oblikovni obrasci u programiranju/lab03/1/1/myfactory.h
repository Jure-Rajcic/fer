#ifndef MYFACTORY_H
#define MYFACTORY_H

# include <stddef.h>

typedef struct {
    void* (*constructor)(void* this, char const* ctorarg);
    size_t size;
} ObjectInfo;

typedef enum {
    ALLOC_STACK,
    ALLOC_HEAP,
} AllocationType;

void* myfactory(char const* libname, char const* ctorarg, AllocationType alloc);

#endif 
