#include <stdio.h>

int main(void) {
    char arr1[50 + 1], arr2[5 + 1];

    printf("Upisite 1. niz > ");
    fgets(arr1, 50 + 1, stdin);

    printf("Upisite 2. niz > ");
    fgets(arr2, 50 + 1, stdin);

    int i = 0;
    while (arr1[i] != '\0') {
        if (arr1[i] == '\n')
            arr1[i] = '\0';
        i = i + 1;
    }
    i = 0;
    while (arr2[i] != '\0') {
        if (arr2[i] == '\n')
            arr2[i] = '\0';
        i = i + 1;
    }

    for (i = 0; i < sizeof(arr2) - 1; i++) {

        for (int j = 0; j < sizeof(arr1); j++) {
            if (arr2[i] == arr1[j] && arr2[i] != '\0') {
                arr1[j] = arr1[j] + 32;
            }
        }
    }

    printf("Novi 1. niz = %s", arr1);

    return 0;
}