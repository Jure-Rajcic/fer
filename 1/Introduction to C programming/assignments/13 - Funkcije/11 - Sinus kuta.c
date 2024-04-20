#include <math.h>
#include <stdio.h>

double sinus(double radian, int numOfElements) {

    double sin = 0;
    unsigned long long fact;

    for (int i = 1; i <= numOfElements; i++) {

        fact = 1;
        for (int j = 2; j <= (2 * i - 1); j++) {
            fact = fact * j;
        }

        sin = sin + pow(-1, i + 1) * pow(radian, 2 * i - 1) / fact;
    }

    return sin;
}

int main(void) {

    double rad, mySin;
    int preciznost;

    printf("Upisite x i n > ");
    scanf("%lf %d", &rad, &preciznost);

    mySin = sinus(rad, preciznost);

    printf("sinus(%.15lf) = %.15lf\n", rad, mySin);
    printf("  sin(%.15lf) = %.15lf\n", rad, sin(rad));
    printf("                 razlika = %.15lf\n", mySin - sin(rad));

    return 0;
}