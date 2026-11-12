import numpy as np
import matplotlib.pyplot as plt
from scipy.ndimage import gaussian_filter
from PIL import Image

img = np.array(Image.open("1\FER.jpeg"))
sigma = 5 
smoothed_img = gaussian_filter(img, sigma=sigma)

plt.imshow(smoothed_img, cmap='gray')
plt.title(f'Gaussovo zaglađivanje\nSigma = {sigma}')
plt.show()