int BrojDjelitelja(int testNum) {

    int brojDjelitelja = 0;

    for (int i = 2; i < testNum; i++) {
        if (testNum % i == 0) {
            brojDjelitelja++;
        }
    }

    return brojDjelitelja;
}

void NajmanjiBrojDjelitelja(int m, int n, int *p1, int *p2) {

    int minBroj = m;

    for (int i = m; i <= n; i++) {
        if (BrojDjelitelja(i) < BrojDjelitelja(minBroj)) {
            minBroj = i;
        }
    }

    *p1 = minBroj;

    *p2 = BrojDjelitelja(minBroj);

    return;
}