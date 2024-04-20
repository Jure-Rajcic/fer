#include <stdio.h>
int main(void) {
    int lipe;
    printf("Upisite stanje racuna u lipama > ");
    scanf("%d", &lipe);
    printf("Kuna: %d Lipa: %d", lipe / 100, lipe % 100);
    return 0;
}
