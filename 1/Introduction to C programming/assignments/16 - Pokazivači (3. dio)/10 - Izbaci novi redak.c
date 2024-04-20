#include <stdio.h>
#define DULJINA 20

void izbaciNR(char *array) {

    while (*array != '\0') {

        if (*array == '\n' && *(array + 1) == '\0')
            *array = '\0';

        array++;
    }
    return;
}

int main(void) {

    char niz[DULJINA];

    printf("Upisite niz > ");
    fgets(niz, DULJINA + 1, stdin);

    izbaciNR(&niz[0]);

    printf("%s!", niz);

    return 0;
}