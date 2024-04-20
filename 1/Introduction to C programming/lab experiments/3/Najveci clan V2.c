#include <stdio.h>

int main(void) {
    int n;
    int maxNum, redak = 0;

    printf("Upisite broj redaka/stupaca matrice > ");
    scanf("%d", &n);

    int matrica[n][n];

    printf("Upisite %dx%d clanova >\n", n, n);

    for (int i = 0; i < n; i++) {

        for (int j = 0; j < n; j++) {

            scanf("%d", &matrica[i][j]);

            if (i == 0 && j == 0) {
                maxNum = matrica[i][j];
            } else if (matrica[i][j] > maxNum) {
                maxNum = matrica[i][j];
                redak = i;
            }
        }
    }

    printf("Najveci clan: %d\n", maxNum);
    printf("Redak (%d):", redak);

    for (int j = 0; j < n; j++) {
        printf(" %d", matrica[redak][j]);
    }

    return 0;
}