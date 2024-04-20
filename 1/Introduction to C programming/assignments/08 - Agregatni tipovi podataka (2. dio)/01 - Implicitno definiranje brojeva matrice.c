#include <stdio.h>

#define BROJ_REDAKA 5
#define BROJ_STUPACA 7

int main(void) {

    float array[BROJ_REDAKA][BROJ_STUPACA] = {{[5] = 0.9f},
                                              {0.f},
                                              {31.1f, 32.2f, 33.3f, 34.2f},
                                              {1.f, 4.f, [6] = 7.7}};

    for (int i = 0; i < BROJ_REDAKA; i++) {
        for (int j = 0; j < BROJ_STUPACA; j++) {
            printf("%5.1f", array[i][j]);
        }
        printf("\n");
    }

    return 0;
}