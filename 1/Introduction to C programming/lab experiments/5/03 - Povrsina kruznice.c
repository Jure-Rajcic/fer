#include <math.h>
#include <stdio.h>

float PiSuma(int clanova) {

    float piCetvrt = 1;

    for (int i = 1; i < clanova; i++) {
        piCetvrt += (pow(-1, i) * (1. / (i * 2 + 1)));
    }

    return piCetvrt * 4;
}

float Povrsina(float promjer, int clanova) {

    float povrsina = (promjer / 2) * (promjer / 2) * PiSuma(clanova);

    return povrsina;
}

int main(void) {

    int brojClanovaReda;
    float promjerKruznice;

    float povrsinaRed, povrsinaAsin;

    printf("Upisite broj clanova reda > ");
    scanf("%d", &brojClanovaReda);

    printf("Upisite promjer kruznice > ");
    scanf("%f", &promjerKruznice);

    if ((brojClanovaReda >= 1 && brojClanovaReda <= 100) &&
        (promjerKruznice >= 1 && promjerKruznice <= 100)) {
        povrsinaRed = Povrsina(promjerKruznice, brojClanovaReda);
        printf("Povrsina kruznice (PI pomocu reda) > %.3f\n", povrsinaRed);

        povrsinaAsin =
            (promjerKruznice / 2) * (promjerKruznice / 2) * (asin(1.0) * 2);
        printf("Povrsina kruznice (PI pomocu asin) > %.3f\n", povrsinaAsin);

        if (povrsinaRed > povrsinaAsin) {

            printf("Apsolutna razlika > %.3f\n", povrsinaRed - povrsinaAsin);
        } else if (povrsinaAsin > povrsinaRed) {
            printf("Apsolutna razlika > %.3f\n", povrsinaAsin - povrsinaRed);
        }

    } else {
        printf("Ucitani parametri nisu dobri!\n");
    }

    return 0;
}