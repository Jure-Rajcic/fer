#include <stdio.h>

_Bool jestVelikoSlovo(char cPar) {

    if (cPar < 'a')
        return 1;
    else
        return 0;
}

int main(void) {

    char c;

    printf("Upisite znak > ");
    scanf("%c", &c);

    if (jestVelikoSlovo(c))
        printf("Jest veliko slovo");
    else
        printf("Nije veliko slovo");

    return 0;
}