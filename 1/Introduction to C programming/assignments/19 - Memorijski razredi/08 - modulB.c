#include "08 - modulB.h"
#include <stdio.h>
void fun3(void) {
    static int x = 5;
    x += 5;
    printf("%d\n", x); // 10 // 15
}
void fun4(void) {
    extern int x;
    x += 6;
    printf("%d\n", x); // 33
}
