#include <stdio.h>

void tablicaMnozenja(int brojRed, int brojStup) {

    printf("%11d", 1);
    for (int j = 2; j < brojStup + 1; j++) {
        printf("%5d", j);
    }

    for (int i = 1; i < brojRed + 1; i++) {

        printf("\n %5d", i);

        for (int j = 1; j < brojStup + 1; j++) {
            printf("%5d", i * j);
        }
    }

    return;
}

int main(void) {

    int numRow, numCol;

    printf("Upisite broj redaka i stupaca > ");
    scanf("%d %d", &numRow, &numCol);

    tablicaMnozenja(numRow, numCol);

    return 0;
}