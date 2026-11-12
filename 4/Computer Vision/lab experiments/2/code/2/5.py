from scipy.ndimage import convolve
import numpy as np
from PIL import Image
import matplotlib.pyplot as plt


def angle_mapping(angle):
    angle = np.rad2deg(angle) % 180  
    if (0 <= angle < 22.5) or (157.5 <= angle < 180):
        return 0
    elif 22.5 <= angle < 67.5:
        return 45
    elif 67.5 <= angle < 112.5:
        return 90
    elif 112.5 <= angle < 157.5:
        return 135
    
    
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
vectorized_angle_mapping = np.vectorize(angle_mapping)
mapped_angles = vectorized_angle_mapping(np.arctan2(grad_y, grad_x) * (180 / np.pi))

M, N = normalized_gradient_magnitude.shape
suppressed = np.zeros((M, N), dtype=np.int32)

for i in range(1, M-1):
    for j in range(1, N-1):
        if mapped_angles[i, j] == 0:
            neighbors = [normalized_gradient_magnitude[i, j-1], normalized_gradient_magnitude[i, j+1]]
        if mapped_angles[i, j] == 45:
            neighbors = [normalized_gradient_magnitude[i-1, j+1], normalized_gradient_magnitude[i+1, j-1]]
        if mapped_angles[i, j] == 90:
            neighbors = [normalized_gradient_magnitude[i-1, j], normalized_gradient_magnitude[i+1, j]]
        if mapped_angles[i, j] == 135:
            neighbors = [normalized_gradient_magnitude[i-1, j-1], normalized_gradient_magnitude[i+1, j+1]]
        
        if normalized_gradient_magnitude[i, j] >= max(neighbors):
            suppressed[i, j] = normalized_gradient_magnitude[i, j]

def hysteresis_thresholding(gradient_magnitude, low_threshold, high_threshold):
    M, N = gradient_magnitude.shape
    edges = np.zeros((M, N), dtype=np.int32)
    
    strong_i, strong_j = np.where(gradient_magnitude > high_threshold)
    weak_i, weak_j = np.where((gradient_magnitude >= low_threshold) & (gradient_magnitude <= high_threshold))
    
    edges[strong_i, strong_j] = 1
    
    for i, j in zip(weak_i, weak_j):
        if np.any(edges[i-1:i+2, j-1:j+2]):
            edges[i, j] = 1
    
    return edges

min_value, max_value = 10, 90

final_edges = hysteresis_thresholding(suppressed, min_value, max_value)

plt.imshow(final_edges, cmap='gray')
plt.show()