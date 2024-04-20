#include <stdio.h>

int main(void) {
    int n;

    printf("Upisite red matrice > ");
    scanf("%d", &n);

    if (n >= 1 && n <= 10) {
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (i == j) {
                    printf("1");
                } else if (i != j) {
                    printf("0");
                }

                if (j != n) {
                    printf(" ");
                } // tako da ispis bude pravilan, tj. da nema razmaka na kraju
                  // svakog retka
            }

            printf("\n");
        }

    } else {
        printf("Red matrice je neispravan");
    }

    return 0;
}