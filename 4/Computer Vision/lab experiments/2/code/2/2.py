import numpy as np
from PIL import Image
from scipy.ndimage.filters import gaussian_filter
import matplotlib.pyplot as plt


img = np.array(Image.open("2\HOUSE.jpg"))
sigma = 1.5 
smoothed_img = gaussian_filter(img, sigma=sigma)

plt.imshow(smoothed_img, cmap='gray')
plt.title(f'Gaussovo zaglađivanje\nSigma = {sigma}')
plt.show()