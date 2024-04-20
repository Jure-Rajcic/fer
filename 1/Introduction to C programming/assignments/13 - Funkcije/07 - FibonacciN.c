#include <stdio.h>

void fibonacciN(int numOfElements) {

    int fibSecondLast = 1;
    int fibLast = 1;
    int temp;

    if (numOfElements >= 1) {
        printf("%d", fibSecondLast);
    }
    if (numOfElements >= 2) {
        printf(", %d", fibLast);
    }

    for (int i = 3; i < numOfElements + 1; i++) {

        temp = fibLast;

        fibLast = fibSecondLast + fibLast;
        fibSecondLast = temp;

        printf(", %d", fibLast);
    }

    return;
}

int main(void) {

    int brojClanova;

    printf("Upisite broj clanova > ");
    scanf("%d", &brojClanova);

    fibonacciN(brojClanova);

    return 0;
}