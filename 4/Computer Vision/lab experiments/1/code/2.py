import numpy as np
import matplotlib.pyplot as plt
import scipy.misc as misc

def recover_affine_diamond(Hs, Ws, Hd, Wd):
    # Točke izvorišne slike koje želite mapirati
    source_points = np.array([
        [Hs/2, 0],
        [Hs, Ws/2],
        [Hs/2, Ws],
        [0, Ws/2]
    ])
    
    # Kutevi odredišne slike
    dest_points = np.array([
        [0, 0],
        [0, Wd],
        [Hd, Wd],
        [Hd, 0]
    ])
    
    A = []
    b = []
    for i in range(4):
        A.append([source_points[i][0], source_points[i][1], 1, 0, 0, 0])
        A.append([0, 0, 0, source_points[i][0], source_points[i][1], 1])
        b.append(dest_points[i][0])
        b.append(dest_points[i][1])
    
    A = np.asarray(A)
    b = np.asarray(b)
    
    params, _, _, _ = np.linalg.lstsq(A, b, rcond=None)
    
    transform_matrix = params.reshape(2, 3)
    return transform_matrix[:, :2], transform_matrix[:, 2]

Is = misc.face()
Hs, Ws, _ = Is.shape
Hd, Wd = 200, 200
A, b = recover_affine_diamond(Hs, Ws, Hd, Wd)

def affine_transform(Is, A, b, Hd, Wd):
    invA = np.linalg.inv(A)
    Id = np.zeros((Hd, Wd, 3))
    for i in range(Hd):
        for j in range(Wd):
            orig_coords = invA @ (np.array([i, j]) - b)
            orig_coords = np.round(orig_coords).astype(int)
            if 0 <= orig_coords[0] < Hs and 0 <= orig_coords[1] < Ws:
                Id[i, j] = Is[orig_coords[0], orig_coords[1]]
    return Id

transformed_image = affine_transform(Is, A, b, Hd, Wd)
fig, axes = plt.subplots(1, 2, figsize=(10, 5))
axes[0].imshow(Is)
axes[0].set_title("Originalna slika")
axes[1].imshow(transformed_image.astype(np.uint8))
axes[1].set_title("Transformirana slika")
plt.show()
