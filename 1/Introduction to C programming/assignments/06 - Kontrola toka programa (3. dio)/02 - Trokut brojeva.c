#include <stdio.h>

int main(void) {
    int n;
    int broj = 0;

    printf("Upisite broj > ");
    scanf("%d", &n);

    if (n >= 1 && n <= 10) {

        if (n == 1) {
            printf("1");
        } else {
            for (int i = 1; i < n + 1; i++) {

                for (int j = 1; j < n + 1; j++) {

                    printf(" ");

                    if (i <= j) {

                        broj++;

                        printf("%4d", broj);

                    } else if (i != j) {
                        printf("    ");
                    }
                }
                printf("\n");
            }
        }
    } else {
        printf("Broj je neispravan");
    }

    return 0;
}