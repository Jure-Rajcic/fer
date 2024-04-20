#include <stdio.h>

int main(void) {

    int num, arr[31];
    int i = -1;

    printf("Upisite cijeli broj > ");
    scanf("%d", &num);

    while (num != 0) {
        i++;
        arr[i] = num % 2;
        num /= 2;
    }

    for (int j = i; j >= 0; j--) {
        printf("%d", arr[j]);
    }

    return 0;
}