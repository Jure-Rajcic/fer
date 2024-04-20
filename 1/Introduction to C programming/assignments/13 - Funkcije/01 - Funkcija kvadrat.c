#include <stdio.h>

float kvadrat(float sqrNum) { return (sqrNum * sqrNum); }

int main(void) {
    float num;

    printf("Upisite realni broj > ");
    scanf("%f", &num);

    printf("%.6f na kvadrat jest %.6f", num, kvadrat(num));

    return 0;
}