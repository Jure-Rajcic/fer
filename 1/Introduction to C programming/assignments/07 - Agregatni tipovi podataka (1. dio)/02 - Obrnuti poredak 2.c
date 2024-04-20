#include <stdio.h>

int main(void) {
    int arr[10];
    int temp;

    printf("Upisite 10 cijelih brojeva > ");

    for (int i = 0; i < 10; i++) {
        scanf("%d", &arr[i]);
    }

    for (int i = 0; i < 5; i++) {
        temp = arr[i];
        arr[i] = arr[9 - i];
        arr[9 - i] = temp;
    }

    for (int i = 0; i < 10; i++) {
        printf("%d", arr[i]);

        if (i != 9) {
            printf(", ");
        }
    }

    return 0;
}