#include <stdio.h>

int maks(int *numeroUno, int *numeroDuo) {

    int biggeroNumero;

    if (*numeroUno > *numeroDuo) {
        biggeroNumero = *numeroUno;
    } else {
        biggeroNumero = *numeroDuo;
    }

    return biggeroNumero;
}

int main(void) {

    int num1, num2, biggerNum;

    printf("Upisite dva cijela broja > ");
    scanf("%d %d", &num1, &num2);

    biggerNum = maks(&num1, &num2);

    printf("Rezultat je %d", biggerNum);

    return 0;
}