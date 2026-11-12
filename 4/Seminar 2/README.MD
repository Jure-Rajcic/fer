# Seminar 2 📂

### 🎓 Application of Machine Learning in SVG Illustration Generation
*(Primjena strojnog učenja u generiranju ilustracija u SVG formatu)*

**Author:** Jure Rajčić  
**Mentor:** Prof. dr. sc. Goran Delač  

---

### 📖 Overview
This seminar explores the automatic generation of scalable vector graphic (SVG) illustrations using modern generative machine learning techniques. While diffusion models typically operate on raster image representations, this work examines end-to-end workflows to generate stylized, semantically coherent illustrations suitable for vector conversion:

- **Generative Architecture**: Utilized **Stable Diffusion XL (SDXL)** within PyTorch and Hugging Face Diffusers.
- **Dataset Pipeline**: Curated open-source stylized SVG illustrations (e.g., *unDraw*), coupled with rasterization preprocessing (SVG to PNG) and prompt engineering.
- **Model Fine-Tuning**: Applied parameter-efficient fine-tuning via **LoRA** and **DreamBooth** to adapt the generative diffusion model to flat vector illustration aesthetics.
- **Evaluation**: Assessed semantic text-to-image alignment using **CLIP** similarity metrics alongside visual quality benchmarks.

---

### 📂 Seminar Materials
- **📄 Seminar Paper**: `Seminar2-doc.pdf`
- **📊 Presentation**: `Presentation.pptx`

