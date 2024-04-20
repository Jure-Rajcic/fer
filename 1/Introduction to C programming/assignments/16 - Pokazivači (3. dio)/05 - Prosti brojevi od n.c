#include <stdio.h>

void getPrim(int testNum, int primesNeeded, int *primeArray) {

    _Bool dividerFound;

    for (int numsFound = 0; numsFound < primesNeeded; numsFound++) {

        dividerFound = 1;

        while (dividerFound) {

            dividerFound = 0;
            testNum++;

            if (testNum % 2 == 0) {
                dividerFound = 1;
            } else {
                for (int i = 3; i < (testNum / 2); i += 2) {
                    if (testNum % i == 0) {
                        dividerFound = 1;
                        break;
                    }
                }
            }
        }

        *(primeArray + numsFound) = testNum;
    }

    return;
}

int main(void) {

    int startNum, amount;

    printf("Upisite donju granicu i broj prim brojeva > ");
    scanf("%d %d", &startNum, &amount);

    int primeNums[amount];

    getPrim(startNum - 1, amount, &primeNums[0]);

    for (int i = 0; i < amount; i++) {
        printf("%7d\n", primeNums[i]);
    }

    return 0;
}