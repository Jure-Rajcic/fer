#include <stdio.h>

_Bool getBit(int num, int ordinalNum) {
    // printf("\n%d\n", (num >> (ordinalNum - 1)) & 0x1);

    if ((num >> ordinalNum) & 0x1)
        return 1;
    else
        return 0;
}

int main(void) {

    int broj;

    printf("Upisite nenegativni cijeli broj > ");
    scanf("%d", &broj);

    for (int i = 31; i >= 0; i--) {
        printf("%d", getBit(broj, i));
    }

    return 0;
}