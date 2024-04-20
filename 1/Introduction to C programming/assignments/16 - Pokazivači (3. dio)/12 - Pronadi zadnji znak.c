#include <stdio.h>
#define DULJINA 20

char *traziZadnjiZnak(char *array, char neededChar) {

    char *lastRecurence = NULL;

    while (*array != '\0') {
        if (*array == neededChar) {
            lastRecurence = array;
        }

        array++;
    }

    return lastRecurence;
}

int main(void) {

    char niz[DULJINA + 1], trazeniZnak;

    printf("Upisite niz > ");
    fgets(niz, DULJINA + 1, stdin);

    printf("Upisite znak > ");
    scanf("%c", &trazeniZnak);

    char *pozicijaZnaka = traziZadnjiZnak(&niz[0], trazeniZnak);

    if (pozicijaZnaka != NULL)
        printf("%d", pozicijaZnaka - niz);
    else
        printf("U nizu nema zadanog znaka");

    return 0;
}