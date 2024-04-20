void SlucajniNizVelikihIMalihSlova(int n, char *polje) {
    int i;
    for (i = 0; i < n; i++) {
        *polje = 'A' + rand() % ('Z' - 'A' + 1);
        polje++;
    }
    for (i = n; i <= 2 * n; i++) {
        if (i != 2 * n) {
            *polje = 'a' + rand() % ('z' - 'a' + 1);
            polje++;
        } else
            *polje = '\0';
    }
    *(polje + 2 * n) = '\0';
    return;
}