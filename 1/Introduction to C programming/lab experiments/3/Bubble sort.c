#include <stdio.h>

int main(void) {
    int n, pomocna;

    printf("Unesite broj clanova polja N > ");
    scanf("%d", &n);

    printf("Unesite clanove polja velicine %d:\n", n);

    int polje[n];

    for (int i = 0; i < n; i = i + 1)
        scanf("%d", &polje[i]);

    for (int i = 0; i < n - 1; i = i + 1)

        for (int j = 0; j < n - i - 1; j++)

            if (polje[j] > polje[j + 1]) {
                pomocna = polje[j];
                polje[j] = polje[j + 1];
                polje[j + 1] = pomocna;
            }

    printf("Sortirano polje:\n");
    for (int i = 0; i < n; i++)
        printf("%d ", polje[i]);

    return 0;
}