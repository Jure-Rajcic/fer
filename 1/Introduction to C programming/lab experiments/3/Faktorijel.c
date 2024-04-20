#include <stdio.h>

int main(void) {
    int n, faktorijel;

    printf("Upisite broj clanova polja > ");
    scanf("%d", &n);

    int clanovi[n];

    for (int i = 0; i < n; i++) {
        printf("Upisite %d. broj > ", i + 1);
        scanf("%d", &clanovi[i]);
    }

    printf("ulaz:");

    for (int i = 0; i < n; i++) {
        printf(" %d", clanovi[i]);
    }

    printf("\nizlaz:\n");

    for (int i = 0; i < n; i++) {

        faktorijel = 1;

        for (int j = 1; j < clanovi[i] + 1; j++) {
            faktorijel = faktorijel * j;
        }

        printf("%d! = %d\n", clanovi[i], faktorijel);
    }

    return 0;
}