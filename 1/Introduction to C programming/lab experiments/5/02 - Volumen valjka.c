#include <math.h>
#include <stdio.h>

float PiSuma(int clanova) {

    float piCetvrt = 1;

    for (int i = 1; i < clanova; i++) {
        piCetvrt += (pow(-1, i) * (1. / (i * 2 + 1)));
    }

    return piCetvrt * 4;
}

float Volumen(float promjer, float visina, int clanova) {

    float volumen = (promjer / 2) * (promjer / 2) * PiSuma(clanova) * visina;

    return volumen;
}

int main(void) {

    int brojClanovaReda;
    float promjerBaze, visina;

    float volumenRed, volumenAsin;

    printf("Upisite broj clanova reda > ");
    scanf("%d", &brojClanovaReda);

    printf("Upisite promjer baze cilindra > ");
    scanf("%f", &promjerBaze);

    printf("Upisite visinu cilindra > ");
    scanf("%f", &visina);

    if ((brojClanovaReda >= 1 && brojClanovaReda <= 100) &&
        (promjerBaze >= 1 && promjerBaze <= 100) &&
        (visina >= 1 && visina <= 100)) {

        volumenRed = Volumen(promjerBaze, visina, brojClanovaReda);
        printf("Volumen cilindra (PI pomocu reda) > %.4f\n", volumenRed);

        volumenAsin =
            (promjerBaze / 2) * (promjerBaze / 2) * (asin(1.0) * 2) * visina;
        printf("Volumen cilindra (PI pomocu asin) > %.4f\n", volumenAsin);

        if (volumenRed > volumenAsin) {

            printf("Apsolutna razlika > %.4f\n", volumenRed - volumenAsin);
        } else if (volumenAsin > volumenRed) {
            printf("Apsolutna razlika > %.4f\n", volumenAsin - volumenRed);
        }

    } else {
        printf("Ucitani parametri nisu dobri!\n");
    }

    return 0;
}