#include <stdio.h>

int main(void) {
    int n, counter;
    int i, j;

    printf("Upisite dimenziju matrice > ");
    scanf("%d", &n);

    int arr[n][n], bigArr[n + 1][n];

    printf("Upisite elemente matrice > ");

    for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++) {
            scanf("%d", &arr[i][j]);
        }
    }

    for (j = 0; j < n; j++) {
        counter = 0;
        for (i = 0; i < n; i++) {
            if (arr[i][j] == 1) {
                counter++;
            }

            bigArr[i][j] = arr[i][j];
        }

        if (counter % 2 == 0) {
            bigArr[i][j] = 0;
        } else {
            bigArr[i][j] = 1;
        }
    }

    printf("Nova matrica: ");

    for (i = 0; i < n + 1; i++) {

        printf("\n");

        for (j = 0; j < n; j++) {
            printf("%d ", bigArr[i][j]);
        }
    }

    return 0;
}