#include <stdio.h>

int main(void) {

    unsigned int num = 0u;
    int bit, position;

    while (1) {

        printf("Upisite bit i poziciju > ");
        scanf("%d %d", &bit, &position);

        if (bit > 1 || bit < 0 || position > 31 || position < 0) {
            printf("Pogresna vrijednost ili pozicija bita");
            break;
        } else {

            if (bit == 1) {
                num = num | (0x1 << position);
            } else if (bit == 0) {
                num = num & ~(0x1 << position);
            }

            for (int posmak = 31; posmak >= 0; posmak--) {
                printf("%d", num >> posmak & 0x1);
            }

            printf("(2) = %u(10)\n", num);
        }
    }

    return 0;
}