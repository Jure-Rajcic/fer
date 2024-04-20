#include <stdio.h>
// ne radi
int main(void) {
    int prvi, drugi, djelitelj;

    printf("Unesite dva broja > ");
    scanf("%d %d", &prvi, &drugi);

    if (prvi >= drugi) {
        djelitelj = drugi;
    } else {
        djelitelj = prvi;
    }

    for (int i = 1; i <= djelitelj; i++) {
        if (prvi % i == 0 && drugi % i == 0) {
            djelitelj = i;
        }
    }
    printf("%d", djelitelj);

    return 0;
}