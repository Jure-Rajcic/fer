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
    int numOfRows, numOfColumns;

    char silazno;
    _Bool doSilazno;

    printf("Upisite smjer poretka (S-silazno) > ");
    scanf("%c", &silazno);

    printf("Upisite dimenzije > ");
    scanf("%d %d", &numOfRows, &numOfColumns);

    printf("Upisite clanove >\n");
    int array[numOfRows][numOfColumns];
    for (int i = 0; i < numOfRows; i = i + 1) {
        for (int j = 0; j < numOfColumns; j++) {
            scanf("%d", &array[i][j]);
        }
    }

    if (silazno == 'S') {
        doSilazno = 1;
    } else {
        doSilazno = 0;
    }

    for (int rowNum = 0; rowNum < numOfRows; rowNum++) {
        sort1D(doSilazno, numOfColumns, &array[rowNum][0]);
    }

    for (int i = 0; i < numOfRows; i++) {
        for (int j = 0; j < numOfColumns; j++)
            printf("%5d ", array[i][j]);

        printf("\n");
    }
    return 0;
}