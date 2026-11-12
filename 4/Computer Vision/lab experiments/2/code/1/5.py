import numpy as np
from scipy.ndimage import maximum_filter
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

threshold_value = 1e10  
suppressed_response = np.where(harris_response > threshold_value, harris_response, 0)

local_neighborhood_size = 5 
half_size = local_neighborhood_size // 2
nms_response = suppressed_response.copy() 

for y in range(half_size, suppressed_response.shape[0] - half_size):
    for x in range(half_size, suppressed_response.shape[1] - half_size):
        local_max = suppressed_response[y - half_size:y + half_size + 1, x - half_size:x + half_size + 1].max()
        if suppressed_response[y, x] != local_max:
            nms_response[y, x] = 0

plt.figure(figsize=(12, 6))

plt.subplot(1, 3, 1)
plt.imshow(harris_response, cmap='gray')
plt.title('Original Harris Response')
plt.axis('off')

plt.subplot(1, 3, 2)
plt.imshow(suppressed_response, cmap='gray')
plt.title('Thresholded Response')
plt.axis('off')

plt.subplot(1, 3, 3)
plt.imshow(nms_response, cmap='gray')
plt.title('Non-Maximum Suppressed Response')
plt.axis('off')

plt.tight_layout()
plt.show()
