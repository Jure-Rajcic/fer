import numpy as np
import matplotlib.pyplot as plt
from scipy.ndimage import gaussian_filter, convolve
from PIL import Image

img = np.array(Image.open("1\FER.jpeg"))
img = np.mean(img, axis=2).astype(float)

grad_x = convolve(img, np.array([[-1, 0, 1], [-2, 0, 2], [-1, 0, 1]]))
grad_y = convolve(img, np.array([[1, 2, 1], [0, 0, 0], [-1, -2, -1]]))

grad_xx = grad_x**2
grad_xy = grad_x * grad_y
grad_yy = grad_y**2

kernel_size = 3  
weights = np.ones((kernel_size, kernel_size))  
sum_grad_xx, sum_grad_xy, sum_grad_yy  = convolve(grad_xx, weights) ,convolve(grad_xy, weights) ,convolve(grad_yy, weights)

det = sum_grad_xx * sum_grad_yy - sum_grad_xy ** 2
trace = sum_grad_xx + sum_grad_yy

k = 0.04
harris_response = det - k * trace ** 2

plt.figure(figsize=(8, 8))
plt.imshow(harris_response, cmap='gray')
plt.title('Harris Corner Response')
plt.axis('off')
plt.show()