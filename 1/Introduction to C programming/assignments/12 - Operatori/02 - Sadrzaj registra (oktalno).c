#include <stdio.h>

int main(void) {

    unsigned int num;

    printf("Upisite nenegativni cijeli broj > ");
    scanf("%u", &num);

    printf("%u(10) = ", num);

    for (int posmak = 30; posmak >= 0; posmak -= 3) {
        printf("%d", num >> posmak & 0x7);
    }

    printf("(8)");

    return 0;
}