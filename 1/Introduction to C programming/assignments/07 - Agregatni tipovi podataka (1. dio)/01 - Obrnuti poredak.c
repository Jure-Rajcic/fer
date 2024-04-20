#include <stdio.h>

int main(void) {
    int arr[10];

    printf("Upisite 10 cijelih brojeva > ");

    for (int i = 0; i < 10; i++) {
        scanf("%d", &arr[i]);
    }

    for (int i = 9; i >= 0; i--) {
        printf("%d ", arr[i]);
    }

    return 0;
}