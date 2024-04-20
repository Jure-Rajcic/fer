#include <stdio.h>

int main(void) {
    int trojkaNum = 1;

    for (int i = 1; i <= 100; i++) {

        for (int j = 1; j <= 100; j++) {

            for (int k = 1; k <= 100; k++) {
                if (i * i + j * j == k * k) {
                    printf("%d. trojka: %2d^2 + %2d^2 = %2d^2\n", trojkaNum, i,
                           j, k);
                    trojkaNum++;
                }
            }
        }
    }

    return 0;
}