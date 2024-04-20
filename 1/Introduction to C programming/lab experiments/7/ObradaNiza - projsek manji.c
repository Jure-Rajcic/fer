struct spremnik_s {

    float prosjek;
    int *niz;
    int N;

} rjesenje;

void ObradaNiza(int *niz, int N) {

    float suma = 0;
    float prosj;

    for (int i = 0; i < N; i++) {
        suma += *(niz + i);
    }

    prosj = suma / N;

    if (prosj < rjesenje.prosjek) {
        rjesenje.prosjek = prosj;
        rjesenje.niz = &niz[0];
        rjesenje.N = N;
    }

    return;
}