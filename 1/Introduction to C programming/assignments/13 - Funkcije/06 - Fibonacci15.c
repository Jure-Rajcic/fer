#include <stdio.h>

void fibonacci15(void) {

    int fibSecondLast = 1;
    printf("%d, ", fibSecondLast);
    int fibLast = 1;
    printf("%d, ", fibLast);

    int temp;

    for (int i = 0; i < 13; i++) {
        temp = fibLast;

        fibLast = fibSecondLast + fibLast;
        fibSecondLast = temp;

        printf("%d", fibLast);

        if (i != 12) {
            printf(", ");
        }
    }

    return;
}

int main(void) {

    fibonacci15();

    return 0;
}