#include <math.h>
#include <stdio.h>

void korijeni(int number, double *seRoot, double *thRoot, double *foRoot) {

    *seRoot = pow(number, 1. / 2);
    *thRoot = pow(number, 1. / 3);
    *foRoot = pow(number, 1. / 5);

    return;
}

int main(void) {

    int num;
    double secondRoot, thirdRoot, fourthRoot;

    printf("Upisite nenegativni cijeli broj > ");
    scanf("%d", &num);

    korijeni(num, &secondRoot, &thirdRoot, &fourthRoot);

    printf("Rezultati su:\n%.8lf\n%.8lf\n%.8lf", secondRoot, thirdRoot,
           fourthRoot);

    return 0;
}