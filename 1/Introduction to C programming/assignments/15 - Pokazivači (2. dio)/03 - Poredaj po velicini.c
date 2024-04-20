#include <stdio.h>

void maks(double *numeroUno, double *numeroDuo, double *numeroTress) {

    double temp;

    if (*numeroTress > *numeroUno) {
        temp = *numeroUno;
        *numeroUno = *numeroTress;
        *numeroTress = temp;
    }

    if (*numeroDuo > *numeroUno) {
        temp = *numeroUno;
        *numeroUno = *numeroDuo;
        *numeroDuo = temp;
    }

    if (*numeroTress > *numeroDuo) {
        temp = *numeroDuo;
        *numeroDuo = *numeroTress;
        *numeroTress = temp;
    }

    return;
}

int main(void) {

    double num1, num2, num3;

    printf("Upisite tri realna broja > ");
    scanf("%lf %lf %lf", &num1, &num2, &num3);

    maks(&num1, &num2, &num3);

    printf("%lf %lf %lf", num3, num2, num1);

    return 0;
}