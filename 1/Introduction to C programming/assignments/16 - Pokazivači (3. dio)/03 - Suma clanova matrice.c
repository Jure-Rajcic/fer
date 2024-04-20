#include <stdio.h>

void zbroj2D(double *sum, double *num) {
    *sum = *sum + *num;
    return;
}

int main(void) {

    int numOfRows, numOfColumns;
    double suma = 0;

    printf("Upisite dimenzije > ");
    scanf("%d %d", &numOfRows, &numOfColumns);

    double matrica[numOfRows][numOfColumns];

    printf("Upisite clanove > \n");
    for (int i = 0; i < numOfRows; i++) {
        for (int j = 0; j < numOfColumns; j++) {
            scanf("%lf", &matrica[i][j]);

            zbroj2D(&suma, &matrica[i][j]);
        }
    }

    printf("Suma je: %.6lf\n", suma);

    return 0;
}