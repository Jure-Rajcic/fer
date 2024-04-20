#include <stdio.h>

unsigned long long fibonacci(int elementNum) {

    if (elementNum == 1 || elementNum == 2) {
        return 1;
    } else {
        return fibonacci(elementNum - 2) + fibonacci(elementNum - 1);
    }
}

int main(void) {

    int num;

    printf("Upisite redni broj clana niza > ");
    scanf("%d", &num);

    printf("fibonacci(%d) = %llu", num, fibonacci(num));

    return 0;
}