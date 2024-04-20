#include <stdio.h>

int main(void) {
    struct pretinac_s {
        int sifra;

        int visina;
        int sirina;
        int dubina;

        float volumen;
    };

    int brojPretinaca;

    printf("Upisite broj pretinaca > ");
    scanf("%d", &brojPretinaca);
    struct pretinac_s pretinci[brojPretinaca];

    printf("Upisite podatke za pretince (%d)\n", brojPretinaca);

    for (int i = 0; i < brojPretinaca; i++) {

        printf("%2d. pretinac > ", i + 1);
        scanf("%d %d %d %d", &pretinci[i].sifra, &pretinci[i].sirina,
              &pretinci[i].visina, &pretinci[i].dubina);

        pretinci[i].volumen = (float)pretinci[i].sirina * pretinci[i].visina *
                              pretinci[i].dubina / 1000;
    }

    printf("Sortirani pretinci:\n");

    struct pretinac_s temp;

    for (int i = 0; i < brojPretinaca; i++) {
        for (int j = 0; j < brojPretinaca - 1; j++) {
            if (pretinci[j].volumen < pretinci[j + 1].volumen) {
                temp = pretinci[j + 1];
                pretinci[j + 1] = pretinci[j];
                pretinci[j] = temp;
            }
        }
    }

    for (int i = 0; i < brojPretinaca; i++) {
        printf("%d = %6.2f litara\n", pretinci[i].sifra, pretinci[i].volumen);
    }

    return 0;
}