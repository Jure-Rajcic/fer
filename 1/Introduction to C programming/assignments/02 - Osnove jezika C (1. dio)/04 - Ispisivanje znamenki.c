#include <stdio.h>

int main(void) {
    int broj;

    int prvaZnam;
    int trecaZnam;
    int petaZnam;

    printf("Upisite 5-znamenkasti cijeli broj > ");
    scanf("%d", &broj);

    prvaZnam = broj / 10000;
    trecaZnam = broj / 100 % 10;
    petaZnam = broj % 10;

    printf("%d %d %d", prvaZnam, trecaZnam, petaZnam);

    return 0;
}