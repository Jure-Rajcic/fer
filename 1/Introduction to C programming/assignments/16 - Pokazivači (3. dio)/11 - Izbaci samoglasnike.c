#include <stdio.h>
#define DULJINA 20

// ne radi

void izbaciNR(char *array) {

    while (*array != '\0') {

        if (*array == '\n' && *(array + 1) == '\0')
            *array = '\0';

        array++;
    }
    return;
}

void izbaciSamoglase(char *array) {

    int i;

    while (*array != '\0') {

        if (*array == 'a' || *array == 'A' || *array == 'e' || *array == 'E' ||
            *array == 'i' || *array == 'I' || *array == 'o' || *array == 'O' ||
            *array == 'u' || *array == 'U') {

            i = 0;

            while (*(array + i) != '\0') {
                *(array + i) = *(array + i + 1);

                i++;
            }
        }

        array++;
    }

    return;
}

int main(void) {

    char niz[DULJINA + 1];

    printf("Upisite niz > ");
    fgets(niz, DULJINA + 1, stdin);

    izbaciNR(&niz[0]);
    izbaciSamoglase(&niz[0]);

    printf("%s", niz);

    return 0;
}