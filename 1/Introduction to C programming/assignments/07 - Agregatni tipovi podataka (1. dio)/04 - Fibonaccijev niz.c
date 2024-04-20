#include <stdio.h>

int main(void) {

    int fibArr[40];

    fibArr[0] = fibArr[1] = 1;

    printf("%d\n%d\n", fibArr[0], fibArr[1]);

    for (int i = 2; i < 40; i++) {
        fibArr[i] = fibArr[i - 1] + fibArr[i - 2];

        printf("%d\n", fibArr[i]);
    }

    return 0;
}