#include <stdio.h>

int main(void) {

    unsigned int num;

    printf("Upisite nenegativni cijeli broj > ");
    scanf("%u", &num);

    printf("%u(10) = ", num);

    for (int posmak = 31; posmak >= 0; posmak--) {
        printf("%d", num >> posmak & 0x1);
    }

    printf("(2)");

    return 0;
}