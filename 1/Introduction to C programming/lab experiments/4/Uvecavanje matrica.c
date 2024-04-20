#include <stdio.h>

int main(void) {
    int n, counter;
    int i, j;

    printf("Upisite dimenziju matrice > ");
    scanf("%d", &n);

    int arr[n][n], bigArr[n][n + 1];

    printf("Upisite elemente matrice > ");

    for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++) {
            scanf("%d", &arr[i][j]);
        }
    }

    for (i = 0; i < n; i++) {
        counter = 0;
        for (j = 0; j < n; j++) {

            if (arr[i][j] == 1) {
                counter++;
            }

            bigArr[i][j] = arr[i][j];
        }

        if (counter % 2 == 0) {
            bigArr[i][j] = 1;
        } else {
            bigArr[i][j] = 0;
        }
    }

    printf("Nova matrica: ");

    for (i = 0; i < n; i++) {

        printf("\n");

        for (j = 0; j < n + 1; j++) {
            printf("%d ", bigArr[i][j]);
        }
    }

    return 0;
}