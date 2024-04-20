int odrediIndNajMClana(int *frekvencije, int n) {

    int minIndex = 0;

    int min = *frekvencije;

    for (int i = 1; i < n; i++) {
        if (*(frekvencije + i) < min) {
            minIndex = i;
            min = *(frekvencije + i);
        }
    }
    return minIndex;
}

int odrediFrek(int *matrica, int m, int n, int *frekvencije) {

    int frekv = 0;

    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < m * n; j++) {
            if (*(matrica + j) == i) {
                frekv++;
            }
        }

        *(frekvencije + i) = frekv;
        frekv = 0;
    }

    int rarest = odrediIndNajMClana(&frekvencije[0], 3);
    return frekvencije[rarest];
}