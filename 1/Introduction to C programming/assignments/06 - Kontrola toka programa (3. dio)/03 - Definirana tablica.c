#include <stdio.h>

int main(void) {
    int m, n;

    printf("Upisite m, n > ");
    scanf("%d %d", &m, &n);

    for (int i = 1; i < m + 1; i++) {

        for (int j = 1; j < n + 1; j++) {

            printf("%4d", n + i - j);
        }

        printf("\n");
    }

    return 0;
}