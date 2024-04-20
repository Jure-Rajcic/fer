#include <stdio.h>

int main(void) {
    int n;
    int prvi, drugi, treci;
    int oldPrvi, oldDrugi, oldTreci;
    int oldMaxNum, maxNum;
    int redak;

    printf("Upisite broj redaka/stupaca matrice > ");
    scanf("%d", &n);

    printf("Upisite %dx%d clanova >\n", n,
           n); // radi samo za 3x3 matrice
    for (int i = 0; i < 3; i++) {

        scanf("%d %d %d\n", &prvi, &drugi, &treci);

        if (prvi > drugi && prvi > treci) {
            maxNum = prvi;
        } else if (drugi > prvi && drugi > treci) {
            maxNum = drugi;
        } else if (treci > drugi && treci > prvi) {
            maxNum = treci;
        }

        if (i == 0) {
            redak = 0;
            oldMaxNum = maxNum;
            oldPrvi = prvi;
            oldDrugi = drugi;
            oldTreci = treci;
        } else if (maxNum > oldMaxNum) {
            oldPrvi = prvi;
            oldDrugi = drugi;
            oldTreci = treci;
            redak = i;
            oldMaxNum = maxNum;
        }
    }

    printf("Najveci clan: %d\n", oldMaxNum);
    printf("Redak (%d): %d %d %d", redak, oldPrvi, oldDrugi, oldTreci);

    return 0;
}