#include <stdio.h>

int srednji(int broj1, int broj2, int broj3) {

    int srednji;

    if (((broj1 >= broj2) && (broj1 <= broj3)) ||
        ((broj1 >= broj3) && (broj1 <= broj2))) {
        srednji = broj1;
    } else if (((broj2 >= broj1) && (broj2 <= broj3)) ||
               ((broj2 >= broj3) && (broj2 <= broj1))) {
        srednji = broj2;
    } else if (((broj3 >= broj2) && (broj3 <= broj1)) ||
               ((broj3 >= broj1) && (broj3 <= broj2))) {
        srednji = broj3;
    }

    return srednji;
}

int main(void) {

    int num1, num2, num3;

    while (1) {

        printf("Upisite tri prirodna broja > ");
        scanf("%d %d %d", &num1, &num2, &num3);

        if ((num1 > 0 && num1 < 100) && (num2 > 0 && num2 < 100) &&
            (num3 > 0 && num3 < 100)) {

            printf("Srednji broj od %d %d %d je %d.\n", num1, num2, num3,
                   srednji(num1, num2, num3));
        } else
            break;
    }

    return 0;
}