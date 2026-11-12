"""
PPKS Lab 2 - Primjena konvolucije i korelacije na slikama
=========================================================
Implementacija:
  1. Gaussov filtar (konvolucija) za zamućivanje slike
  2. Traženje uzorka korelacijom (template matching)

Filteri preuzeti iz: PPKS_03_konvolucija.pdf
"""

import numpy as np
import scipy.ndimage
import scipy.stats
from PIL import Image, ImageDraw, ImageFont
import os

# ── Paths ─────────────────────────────────────────────────────────────────────
OUTPUT_DIR = os.path.dirname(os.path.abspath(__file__))
INPUT_PATH  = os.path.join(OUTPUT_DIR, "original.png")
CONV_PATH   = os.path.join(OUTPUT_DIR, "gaussian_blur.png")
MEAN_PATH   = os.path.join(OUTPUT_DIR, "mean_blur.png")
CORR_PATH   = os.path.join(OUTPUT_DIR, "correlation_heatmap.png")
TEMPL_PATH  = os.path.join(OUTPUT_DIR, "template.png")


# ── 1. Create a synthetic test image ─────────────────────────────────────────
def create_test_image(path: str, size: tuple[int, int] = (512, 512)):
    """Generate a grayscale image with geometric shapes and repeating circles."""
    img = Image.new("L", size, color=30)           # dark background
    draw = ImageDraw.Draw(img)

    # Background grid of dots (repeating pattern we will look for later)
    dot_r = 10
    spacing = 80
    for row in range(1, size[1] // spacing + 1):
        for col in range(1, size[0] // spacing + 1):
            cx = col * spacing
            cy = row * spacing
            draw.ellipse([cx - dot_r, cy - dot_r, cx + dot_r, cy + dot_r], fill=220)

    # Large filled rectangle
    draw.rectangle([50, 50, 200, 150], fill=160)

    # Triangle (polygon)
    draw.polygon([(300, 50), (450, 50), (375, 180)], fill=200)

    # Random noise overlay (simulate real-world noise)
    arr = np.array(img, dtype=np.float32)
    np.random.seed(42)
    noise = np.random.normal(0, 15, arr.shape)
    arr = np.clip(arr + noise, 0, 255).astype(np.uint8)

    result = Image.fromarray(arr)
    result.save(path)
    print(f"[OK] Originalna slika pohranjena u: {path}")
    return result


# ── 2. Gaussian kernel (from PPKS_03_konvolucija.pdf) ────────────────────────
def gaussian_kernel(size: int, sigma: float = 1.0) -> np.ndarray:
    """
    Build a 2-D Gaussian kernel using the CDF-difference method shown in the
    course materials (PPKS_03_konvolucija.pdf).
    """
    x = np.linspace(-sigma, sigma, size + 1)
    kernel1d = np.diff(scipy.stats.norm.cdf(x))
    kernel2d = np.outer(kernel1d, kernel1d)
    kernel2d /= kernel2d.sum()          # normalise → sum = 1
    return kernel2d


# ── 3. Mean (Box) kernel ──────────────────────────────────────────────────────
def mean_kernel(size: int) -> np.ndarray:
    """Uniform averaging kernel (all weights = 1/n²)."""
    return np.ones((size, size), dtype=np.float64) / (size * size)


# ── 4. Apply convolution filters ─────────────────────────────────────────────
def apply_filters(image_path: str):
    img = Image.open(image_path).convert("L")
    arr = np.array(img, dtype=np.float32)

    # --- Gaussian blur (5×5, σ=1.0) -----------------------------------------
    g_kernel = gaussian_kernel(size=5, sigma=1.0)
    print(f"\nGausov kernel (5×5, σ=1.0):\n{np.round(g_kernel, 4)}")

    blurred_gauss = scipy.ndimage.convolve(arr, g_kernel, mode="reflect")
    blurred_gauss = np.clip(blurred_gauss, 0, 255).astype(np.uint8)
    Image.fromarray(blurred_gauss).save(CONV_PATH)
    print(f"[OK] Gaussovo zamućivanje pohraneno: {CONV_PATH}")

    # --- Mean blur (5×5) -----------------------------------------------------
    m_kernel = mean_kernel(size=5)
    print(f"\nMean kernel (5×5):\n{np.round(m_kernel, 4)}")

    blurred_mean = scipy.ndimage.convolve(arr, m_kernel, mode="reflect")
    blurred_mean = np.clip(blurred_mean, 0, 255).astype(np.uint8)
    Image.fromarray(blurred_mean).save(MEAN_PATH)
    print(f"[OK] Mean zamućivanje pohraneno: {MEAN_PATH}")

    return arr


# ── 5. Correlation – template matching ───────────────────────────────────────
def create_template(path: str, radius: int = 10):
    """Save the dot template (same shape as the repeating circles in the image)."""
    size = radius * 2 + 6
    tmpl = Image.new("L", (size, size), color=0)
    draw = ImageDraw.Draw(tmpl)
    draw.ellipse([3, 3, size - 4, size - 4], fill=220)
    tmpl.save(path)
    print(f"[OK] Template pohranjen: {path}")
    return np.array(tmpl, dtype=np.float32)


def normalized_cross_correlation(image: np.ndarray, template: np.ndarray) -> np.ndarray:
    """
    Normalized cross-correlation (NCC) using scipy.signal.correlate2d equivalent
    built on scipy.ndimage.correlate.

    NCC formula:  NCC(x,y) = Σ (I_patch - μ_I)(T - μ_T) / (σ_I · σ_T · n)

    This makes the result invariant to brightness differences.
    """
    h, w = template.shape
    pad_h, pad_w = h // 2, w // 2

    # Normalise template
    t_mean = template.mean()
    t_std  = template.std()
    t_norm = (template - t_mean) / (t_std + 1e-8)

    # Pad image so output has same shape
    padded = np.pad(image, ((pad_h, pad_h), (pad_w, pad_w)), mode="reflect")

    ncc = np.zeros_like(image, dtype=np.float32)
    rows, cols = image.shape

    # Sliding window correlation
    for r in range(rows):
        for c in range(cols):
            patch = padded[r: r + h, c: c + w]
            p_mean = patch.mean()
            p_std  = patch.std()
            if p_std < 1e-8:
                ncc[r, c] = 0.0
            else:
                p_norm = (patch - p_mean) / (p_std + 1e-8)
                ncc[r, c] = (p_norm * t_norm).mean()

    return ncc


def apply_correlation(image_arr: np.ndarray, template_arr: np.ndarray, out_path: str):
    """Compute NCC and save a heat-map showing where the template was found."""
    print("\nIzračunam korelaciju (NCC)... (može potrajati ~30 s)")
    ncc = normalized_cross_correlation(image_arr, template_arr)

    # Normalise to [0, 255] for visualisation
    ncc_vis = (ncc - ncc.min()) / (ncc.max() - ncc.min() + 1e-8) * 255
    ncc_vis = ncc_vis.astype(np.uint8)

    # Threshold and mark detected positions
    threshold = 0.75 * 255
    detected  = np.argwhere(ncc_vis > threshold)

    # Convert heat-map to colour for display (pseudo-colour)
    heat_rgb = np.stack([ncc_vis, np.zeros_like(ncc_vis), (255 - ncc_vis)], axis=2)
    heat_img = Image.fromarray(heat_rgb.astype(np.uint8), mode="RGB")

    # Draw green circles at detected locations
    draw = ImageDraw.Draw(heat_img)
    r = 6
    for (row, col) in detected:
        draw.ellipse([col - r, row - r, col + r, row + r], outline=(0, 255, 0), width=2)

    heat_img.save(out_path)
    print(f"[OK] Korelacijska mapa pohranjena: {out_path}")
    print(f"     Pronađeno {len(detected)} podudaranja (prag NCC > 0.75)")
    return ncc


# ── Main ──────────────────────────────────────────────────────────────────────
if __name__ == "__main__":
    print("=" * 60)
    print("PPKS Lab 2 – Konvolucija i korelacija na slikama")
    print("=" * 60)

    # 1. Create test image
    create_test_image(INPUT_PATH, size=(512, 512))

    # 2. Apply filters
    img_arr = apply_filters(INPUT_PATH)

    # 3. Create template and run correlation
    tmpl_arr = create_template(TEMPL_PATH, radius=10)
    apply_correlation(img_arr, tmpl_arr, CORR_PATH)

    print("\n[DONE] Sve slike su generirane. Pokrenite lab2_report.md za izvještaj.")
