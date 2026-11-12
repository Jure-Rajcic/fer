import numpy as np
import matplotlib.pyplot as plt
from scipy.ndimage import maximum_filter
from PIL import Image
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
nms_response = np.where(harris_response > threshold_value, harris_response, 0)

coords_of_responses = np.nonzero(nms_response)

k = 100  
sorted_indices = np.argsort(nms_response[coords_of_responses])[::-1][:k]
k_largest_coords = [(coords_of_responses[0][idx], coords_of_responses[1][idx]) for idx in sorted_indices]

original_image_array = np.array(Image.open("1\FER.jpeg"))

plt.figure(figsize=(8, 8))
plt.imshow(original_image_array, cmap='gray')  

for y, x in k_largest_coords:
    plt.scatter(x, y, s=100, facecolors='none', edgecolors='r')

plt.axis('off')
plt.title('Detected Corners')
plt.show()