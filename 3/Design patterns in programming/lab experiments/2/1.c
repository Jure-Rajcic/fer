#include <stdio.h>
#include <string.h>
#include <stdlib.h>

// Generic Programming (C)
const void* mymax(const void *base, size_t n, size_t size, int (*compar)(const void *, const void *)) {
    const void *max_element = base;
    const void *current_element = (const char *)base + size;

    for (size_t i = 1; i < n; ++i) {
        if (compar(current_element, max_element) > 0) {
            max_element = current_element;
        }
        current_element = (const char *)current_element + size;
    }
    return max_element;
}


int gt_int(const void *a, const void *b)
{
    int x = *(const int *)a;
    int y = *(const int *)b;
    return (x > y) ? 1 : 0;
}

int gt_char(const void *a, const void *b)
{
    char x = *(const char *)a;
    char y = *(const char *)b;
    return (x > y) ? 1 : 0;
}

int gt_str(const void *a, const void *b)
{
    const char *x = *(const char *const *)a;
    const char *y = *(const char *const *)b;
    return strcmp(x, y) > 0 ? 1 : 0;
}

int main() {
    int arr_int[] = { 1, 3, 5, 7, 4, 6, 9, 2, 0 };
    char arr_char[] = "Suncana strana ulice";
    const char* arr_str[] = { "Gle", "malu", "vocku", "poslije", "kise", "Puna", "je", "kapi", "pa", "ih", "njise" };

    const int *max_int = (const int *)mymax(arr_int, sizeof(arr_int) / sizeof(arr_int[0]), sizeof(arr_int[0]), gt_int);
    const char *max_char = (const char *)mymax(arr_char, sizeof(arr_char) / sizeof(arr_char[0]), sizeof(arr_char[0]), gt_char);
    const char **max_str = (const char **)mymax(arr_str, sizeof(arr_str) / sizeof(arr_str[0]), sizeof(arr_str[0]), gt_str);

    printf("Max int: %d\n", *max_int);
    printf("Max char: %c\n", *max_char);
    printf("Max str: %s\n", *max_str);

    return 0;
}

