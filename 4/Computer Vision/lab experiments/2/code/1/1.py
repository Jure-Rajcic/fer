from PIL import Image
import numpy as np


img = np.array(Image.open("1\FER.jpeg"))
print(f"Image dimensions: {img.ndim}, Image shape: {img.shape}")

min_intensity, max_intensity = np.min(img), np.max(img)
print(f"Minimum intensity: {min_intensity}, Maximum intensity: {max_intensity}")

print(img[:10, :10])
img = img.astype(float)
print(img.dtype)