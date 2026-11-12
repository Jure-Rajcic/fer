import numpy as np
from PIL import Image


img = np.array(Image.open("2\HOUSE.jpg"))
print(f"Image dimensions: {img.ndim}, Image shape: {img.shape}")

min_intensity, max_intensity = np.min(img), np.max(img)
print(f"Minimum intensity: {min_intensity}, Maximum intensity: {max_intensity}")

print(img[:10, :10])
img = img.astype(float)
print(img.dtype)