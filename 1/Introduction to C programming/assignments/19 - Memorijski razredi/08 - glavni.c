#include "08 - modulA.h"
#include "08 - modulB.h"
#include <stdio.h>
void fun1(void);

int main(void) {
    int x = 30;
    x += 2;
    printf("%d\n", x); // 32
    fun1();            // 23
    fun2();            // 27
    fun3();            // 10
    fun4();            // 33
    fun3();            // 15
    return 0;
}

void fun1(void) {
    x += 3;
    printf("%d\n", x); // 23
}