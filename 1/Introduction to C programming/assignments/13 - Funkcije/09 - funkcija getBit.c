#include <stdio.h>

_Bool getBit(int num, int ordinalNum) {
    // printf("\n%d\n", (num >> (ordinalNum - 1)) & 0x1);

    if ((num >> ordinalNum) & 0x1)
        return 1;
    else
        return 0;
}

int main(void) {

    int broj, redniBroj;

    printf("Upisite nenegativni cijeli broj > ");
    scanf("%d", &broj);

    printf("Upisite redni broj bita > ");
    scanf("%d", &redniBroj);

    printf("Vrijednost bita je %d", getBit(broj, redniBroj));

    return 0;
}