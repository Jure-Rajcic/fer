#include <stdio.h>

#define BROJ_REDAKA 3
#define BROJ_STUPACA 6
#define BROJ_SLOJEVA 4

int main(void) {

    int array[BROJ_SLOJEVA][BROJ_REDAKA][BROJ_STUPACA];

    for (int i = 0; i < BROJ_SLOJEVA; i++) {
        for (int j = 0; j < BROJ_REDAKA; j++) {
            for (int k = 0; k < BROJ_STUPACA; k++) {
                array[i][j][k] = (i + 1) * 100 + (j + 1) * 10 + (k + 1);
            }
        }
    }

    for (int i = 0; i < BROJ_SLOJEVA; i++) {

        if (i != 3) {
            printf("\n");
        }

        printf("%d. sloj\n", i + 1);

        for (int j = 0; j < BROJ_REDAKA; j++) {

            for (int k = 0; k < BROJ_STUPACA; k++) {
                printf("%5d", array[i][j][k]);
            }

            printf("\n");
        }
    }

    return 0;
}