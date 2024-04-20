#include <stdio.h>

void switchNums(int *num1, int *num2) {
    int temp;

    temp = *num1;
    *num1 = *num2;
    *num2 = temp;

    return;
}

void sort1D(_Bool silazno, int elements, int *numArray) {

    for (int i = 0; i < elements - 1; i++)

        for (int j = 0; j < elements - i - 1; j++)

            if (silazno) {
                if (*(numArray + j) < *(numArray + j + 1)) {
                    switchNums(numArray + j, numArray + j + 1);
                }
            } else {
                if (*(numArray + j) > *(numArray + j + 1)) {
                    switchNums(numArray + j, numArray + j + 1);
                }
            }

    return;
}

int main(void) {
    int numOfElements;

    char silazno;
    _Bool doSilazno;

    printf("Upisite smjer poretka (S-silazno) > ");
    scanf("%c", &silazno);

    printf("Upisite dimenziju > ");
    scanf("%d", &numOfElements);

    printf("Upisite clanove > ");
    int array[numOfElements];
    for (int i = 0; i < numOfElements; i = i + 1) {
        scanf("%d", &array[i]);
    }

    if (silazno == 'S') {
        doSilazno = 1;
    } else {
        doSilazno = 0;
    }

    sort1D(doSilazno, numOfElements, &array[0]);

    for (int i = 0; i < numOfElements; i++)
        printf("%d ", array[i]);

    return 0;
}