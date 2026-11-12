from scipy.ndimage import convolve
import numpy as np
from PIL import Image
import matplotlib.pyplot as plt


img = np.array(Image.open("2\HOUSE.jpg"))
img = np.mean(img, axis=2).astype(float)

sobel_x = np.array([[-1, 0, 1], [-2, 0, 2], [-1, 0, 1]])
sobel_y = np.array([[1, 2, 1], [0, 0, 0], [-1, -2, -1]])

grad_x, grad_y = convolve(img, sobel_x), convolve(img, sobel_y)

grad_xx = grad_x**2
grad_xy = grad_x * grad_y
grad_yy = grad_y**2

gradient_magnitude = np.sqrt(grad_x**2 + grad_y**2)

normalized_gradient_magnitude = (gradient_magnitude / gradient_magnitude.max()) * 255
normalized_gradient_magnitude = normalized_gradient_magnitude.astype(np.uint8)

gradient_angle = np.arctan2(grad_y, grad_x) * (180 / np.pi)

plt.figure(figsize=(10, 8))
plt.subplot(121)
plt.imshow(img, cmap='gray')

plt.subplot(122)
plt.imshow(normalized_gradient_magnitude, cmap='gray')
plt.show()