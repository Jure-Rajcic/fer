#include <stdio.h>

int main(void) {

    unsigned int num;

    printf("Upisite nenegativni cijeli broj > ");
    scanf("%u", &num);

    printf("%u(10) = ", num);

    for (int posmak = 28; posmak >= 0; posmak -= 4) {
        if ((num >> posmak & 0xF) <= 9) {
            printf("%d", num >> posmak & 0xF);
        } else {
            printf("%c", (num >> posmak & 0xF) - 10 + 'A');
        }
    }

    printf("(16)");

    return 0;
}