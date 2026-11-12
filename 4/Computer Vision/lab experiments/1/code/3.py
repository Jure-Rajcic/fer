import numpy as np

def recover_projective(Qs, Qd):
    if Qs.shape[0] < 4 or Qd.shape[0] < 4:
        raise ValueError("Potrebno je najmanje 4 točke za procjenu projekcijske transformacije.")

    # Broj točaka
    n = Qs.shape[0]

    # Izgradnja matrice A
    A = np.zeros((2*n, 9))
    for i in range(n):
        Xs, Ys = Qs[i]
        Xd, Yd = Qd[i]
        A[2*i] = [-Xs, -Ys, -1, 0, 0, 0, Xd*Xs, Xd*Ys, Xd]
        A[2*i + 1] = [0, 0, 0, -Xs, -Ys, -1, Yd*Xs, Yd*Ys, Yd]

    # Rješavanje homogenog sustava Ax = 0
    _, _, Vt = np.linalg.svd(A)
    h = Vt[-1]  # Vektor h koji odgovara najmanjoj singularnoj vrijednosti

    # Rekonstrukcija matrice projekcijske transformacije
    H = h.reshape(3, 3)
    return H

# Primjer korištenja
Qs = np.array([
    [0, 0],
    [1, 0],
    [1, 1],
    [0, 1]
])

Qd = np.array([
    [0, 0],
    [200, 0],
    [200, 200],
    [0, 200]
])

H = recover_projective(Qs, Qd)
print("Matrica projekcijske transformacije:")
print(H)
