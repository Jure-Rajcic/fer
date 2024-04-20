#include "08 - modulA.h"
#include <stdio.h>
int x = 20;
void fun2(void) {
    x += 4;
    printf("%d\n", x); // 27
}