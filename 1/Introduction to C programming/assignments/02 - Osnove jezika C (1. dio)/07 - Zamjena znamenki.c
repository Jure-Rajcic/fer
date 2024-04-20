#include <stdio.h>

int main(void) {
    int a, b, temp;

    printf("Upisite 2 cijela broja > ");
    scanf("%d %d", &a, &b);

    temp = b;
    b = a % 10;
    a = temp % 10;

    printf("a = %d \nb = %d", a, b);

    return 0;
}