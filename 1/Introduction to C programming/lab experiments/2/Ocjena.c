#include <stdio.h>

int main(void) {
    int brojBodova;

    printf("Unesite broj bodova > ");
    scanf("%d", &brojBodova);

    if (brojBodova >= 0 && brojBodova <= 49) {
        printf("OCJENA: 1");
    } else if (brojBodova >= 50 && brojBodova <= 65) {
        printf("OCJENA: 2");
    } else if (brojBodova >= 66 && brojBodova <= 75) {
        printf("OCJENA: 3");
    } else if (brojBodova >= 76 && brojBodova <= 89) {
        printf("OCJENA: 4");
    } else if (brojBodova >= 90 && brojBodova <= 100) {
        printf("OCJENA: 5");
    } else {
        printf("KRIVI UNOS");
    }

    return 0;
}