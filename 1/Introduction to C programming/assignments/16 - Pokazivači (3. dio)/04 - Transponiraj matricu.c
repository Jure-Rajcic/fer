#include <stdio.h>

void transpKvad(int *num, int redMatrice) {

    int temp;

    for (int i = 0; i < redMatrice - 1; ++i) {
        for (int j = i + 1; j < redMatrice; ++j) {
            temp = *(num + redMatrice * i + j);
            *(num + redMatrice * i + j) = *(num + redMatrice * j + i);
            *(num + redMatrice * j + i) = temp;
        }
    }
    return;
}

int main(void) {

    int redMatrice;

    printf("Upisite red matrice > ");
    scanf("%d", &redMatrice);

    int matrica[redMatrice][redMatrice];

    printf("Upisite clanove > \n");
    for (int i = 0; i < redMatrice; i++) {
        for (int j = 0; j < redMatrice; j++) {
            scanf("%d", &matrica[i][j]);
        }
    }

    transpKvad(&matrica[0][1], redMatrice);

    for (int i = 0; i < redMatrice; i++) {
        for (int j = 0; j < redMatrice; j++) {
            printf("%5d", matrica[i][j]);
        }
        printf("\n");
    }

    return 0;
}