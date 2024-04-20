#include <stdio.h>

int *prviNegativac(int numOfElements, int *array) {

    for (int elementNum = 0; elementNum < numOfElements; elementNum++) {
        if (*(array + elementNum) < 0) {
            return array + elementNum;
        }
    }

    return NULL;
}

int main(void) {

    int numOfElements, *nonPositiveNum;

    printf("Upisite broj clanova > ");
    scanf("%d", &numOfElements);

    int array[numOfElements];

    printf("Upisite clanove > ");
    for (int i = 0; i < numOfElements; i++)
        scanf("%d", &array[i]);

    nonPositiveNum = prviNegativac(numOfElements, array);

    if (nonPositiveNum == NULL)
        printf("Nema negativnih");
    else
        printf("Prvi negativni je %d", *nonPositiveNum);

    return 0;
}