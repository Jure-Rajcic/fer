from scipy.ndimage import convolve
import numpy as np
import matplotlib.pyplot as plt
from PIL import Image

img = np.array(Image.open("1\FER.jpeg"))
img = np.mean(img, axis=2).astype(float)

grad_x = convolve(img, np.array([[-1, 0, 1], [-2, 0, 2], [-1, 0, 1]]))
grad_y = convolve(img, np.array([[1, 2, 1], [0, 0, 0], [-1, -2, -1]]))

grad_xx = grad_x**2
grad_xy = grad_x * grad_y
grad_yy = grad_y**2

plt.figure(figsize=(15, 5))

plt.subplot(2, 3, 1)
plt.imshow(grad_x, cmap='gray')
plt.title('I_x')

plt.subplot(2, 3, 2)
plt.imshow(grad_y, cmap='gray')
plt.title('I_y')

plt.subplot(2, 3, 3)
plt.imshow(np.sqrt(grad_xx + grad_yy), cmap='gray')
plt.title('sqrt(I_xx + I_yy)')

plt.subplot(2, 3, 4)
plt.imshow(grad_xx, cmap='gray')
plt.title('I_xx')

plt.subplot(2, 3, 5)
plt.imshow(grad_xy, cmap='gray')
plt.title('I_xy')

plt.subplot(2, 3, 6)
plt.imshow(grad_yy, cmap='gray')
plt.title('I_yy')

plt.tight_layout()
plt.show()