void SlucajniNizMalihSlovaIBrojeva(int n, char *polje) {

    for (int i = 0; i < n; i++) {
        *(polje + i) = rand() % ('z' - 'a' + 1) + 'a';
    }

    for (int i = 0; i < n; i++) {
        *(polje + i + n) = rand() % 10 + '0';
    }

    *(polje + 2 * n) = '\0';

    return;
}