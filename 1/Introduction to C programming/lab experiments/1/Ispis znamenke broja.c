#include <stdio.h>

int main(void) {
    int broj, drugaZnam, zadnjaZnam;

    printf("Upisite cetveroznamenkasti cijeli broj > ");
    scanf("%d", &broj);

    if ((broj >= 1000 && broj <= 9999) || (broj <= -1000 && broj >= -9999)) {
        if (broj < 0) {
            broj = -broj;
        }

        drugaZnam = broj / 100 % 10;
        zadnjaZnam = broj % 10;

        printf("Druga i zadnja znamenka > %d %d", drugaZnam, zadnjaZnam);
    } else {
        printf("Nije unesen cetveroznamenkasti cijeli broj.");
    }

    return 0;
}