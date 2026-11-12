import numpy as np
import matplotlib.pyplot as plt
import scipy.misc as misc

def recover_affine_diamond(Hs, Ws, Hd, Wd):
    A = .25*np.eye(2) + np.random.normal(size=(2, 2))
    center_source = np.array([Hs/2., Ws/2.])
    center_dest = np.array([Hd/2., Wd/2.])
    b = center_dest - A @ center_source
    return A, b

def affine_nn(Is, A, b, Hd, Wd):
    Id = np.zeros((Hd, Wd, 3))
    invA = np.linalg.inv(A)
    for i in range(Hd):
        for j in range(Wd):
            orig_coords = invA @ (np.array([i, j]) - b)
            orig_coords = np.round(orig_coords).astype(int)
            
            if 0 <= orig_coords[0] < Is.shape[0] and 0 <= orig_coords[1] < Is.shape[1]:
                Id[i, j] = Is[orig_coords[0], orig_coords[1]]
            else:
                Id[i, j] = 0
    return Id

def affine_bilin(Is, A, b, Hd, Wd):
    Id = np.zeros((Hd, Wd, 3))
    invA = np.linalg.inv(A)
    for i in range(Hd):
        for j in range(Wd):
            orig_coords = invA @ (np.array([i, j]) - b)
            
            x, y = int(orig_coords[0]), int(orig_coords[1])
            dx, dy = orig_coords[0] - x, orig_coords[1] - y
            
            if 0 <= x < Is.shape[0]-1 and 0 <= y < Is.shape[1]-1:
                Id[i, j] = (1-dx) * (1-dy) * Is[x, y] + dx * (1-dy) * Is[x+1, y] + (1-dx) * dy * Is[x, y+1] + dx * dy * Is[x+1, y+1]
            else:
                Id[i, j] = 0
                
    return Id

if __name__ == "__main__":
    Is = misc.face()
    Is = np.asarray(Is)

    Hd,Wd = 200,200
    A,b = recover_affine_diamond(Is.shape[0],Is.shape[1], Hd,Wd)

    Id1 = affine_nn(Is, A,b, Hd,Wd)
    Id2 = affine_bilin(Is, A,b, Hd, Wd)
    # dodati ispis standardne devijacije
    print("Standardna devijacija:", np.sqrt(np.mean((Id1 - Id2)**2)))

    fig = plt.figure()
    if len(Is.shape)==2: 
        plt.gray()

    for i,im in enumerate([Is, Id1, Id2]):
        fig.add_subplot(1,3, i+1)
        plt.imshow(im.astype(int))
    plt.show()