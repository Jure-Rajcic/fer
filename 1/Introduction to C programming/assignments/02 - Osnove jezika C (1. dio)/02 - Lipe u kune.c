#include <stdio.h>

int main(void) {
    int lipe;
    int kune;

    printf("Upisite stanje racuna u lipama > ");
    scanf("%d", &lipe);

    kune = lipe / 100;
    lipe = lipe % 100;

    printf("Kuna: %d Lipa: %d", kune, lipe);

    return 0;
}
