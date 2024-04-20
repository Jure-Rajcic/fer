#include "07 - fibonacci.h"
#include <stdio.h>

int main(void) {

    int n;
    while (1) {
        printf("Upisite broj Fibonaccijevih brojeva > ");
        scanf("%d", &n);

        if (n > 0) {
            resetFibonacci();

            for (int i = 0; i < n; i++) {
                printf("%d\n", getNextFibonacci());
            }
        } else {
            break;
        }
    }

    return 0;
}