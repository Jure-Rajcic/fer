#include <stdio.h>

int main(void) {
    int primeArr[500];

    primeArr[0] = 2;

    int k = 1;
    _Bool prime;

    for (int i = 1; i < 500; i++) {

        prime = 0;

        while (prime == 0) {
            k = k + 2;
            prime = 1;

            for (int j = 0; j < i; j++) {

                if (k % primeArr[j] == 0) {
                    prime = 0;
                    break;
                }
            }
        }

        primeArr[i] = k;
    }

    for (int i = 0; i < 500; i++) {
        printf("%d.%6d\n", i + 1, primeArr[i]);
    }

    return 0;
}