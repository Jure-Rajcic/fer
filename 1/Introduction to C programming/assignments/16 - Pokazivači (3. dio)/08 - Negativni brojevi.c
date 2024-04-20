#include <stdio.h>

void negativci(int numOfElements, int *array) {

    for (int elementNum = 0; elementNum < numOfElements; elementNum++) {
        if (array[elementNum] < 0)
            printf("%d ", array[elementNum]);
    }

    return;
}

int main(void) {

    int numOfElements;

    printf("Upisite broj clanova > ");
    scanf("%d", &numOfElements);

    int array[numOfElements];

    printf("Upisite clanove > ");
    for (int i = 0; i < numOfElements; i++)
        scanf("%d", &array[i]);

    negativci(numOfElements, &array[0]);

    return 0;
}