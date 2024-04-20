#include "07 - fibonacci.h"
#include <stdio.h>

static unsigned long long leadingNum, followingNum;
static int num;

void resetFibonacci(void) {
    leadingNum = 1;
    followingNum = 1;
    num = 0;
    return;
}

unsigned long long getNextFibonacci(void) {
    num++;

    if (num <= 2) {
        return 1;
    } else {
        unsigned long long temp = leadingNum;
        leadingNum += followingNum;
        followingNum = temp;

        return leadingNum;
    }
}