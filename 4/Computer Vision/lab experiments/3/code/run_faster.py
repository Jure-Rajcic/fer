import matplotlib
import torch
from PIL import Image
import torchvision
import matplotlib.pyplot as plt
import matplotlib.patches as patches

from faster import FasterRCNNwithFPN
from fpn import FeaturePyramidNetwork
from resnet import resnet50
from utils import normalize_img
from torchvision.models.detection._utils import overwrite_eps
from torchvision.models._meta import _COCO_CATEGORIES
import numpy as np


def generate_colormap(n):
    colormap = matplotlib.cm.get_cmap("jet", n)
    st0 = np.random.get_state()
    np.random.seed(44)
    perm = np.random.permutation(n)
    np.random.set_state(st0)
    cmap = np.round(colormap(np.arange(n)) * 255).astype(np.uint8)[:,:3]
    cmap = cmap[perm, :]
    return cmap


if __name__ == '__main__':
    backbone = resnet50(
        norm_layer=torchvision.ops.misc.FrozenBatchNorm2d
    )
    overwrite_eps(backbone, 0.0)

    fpn = FeaturePyramidNetwork(
        input_keys_list=['res2', 'res3', 'res4', 'res5'],
        input_channels_list=[256, 512, 1024, 2048],
        output_channels=256,
        norm_layer=None,
        pool=False
    )

    model = FasterRCNNwithFPN(
        backbone,
        fpn,
        num_classes=91
    )
    model.load_state_dict(torch.load('data/faster_params.pth'))
    model.eval()

    img = Image.open('bb44.png')
    img_pth = torch.from_numpy(np.array(img))
    image_mean = torch.tensor([0.485, 0.456, 0.406])
    image_std = torch.tensor([0.229, 0.224, 0.225])
    img_pth = normalize_img(img_pth, image_mean, image_std)
    categories = _COCO_CATEGORIES
    colormap = generate_colormap(len(categories))
    boxes, scores, labels = model(img_pth)
    fig, ax = plt.subplots(1, figsize=(12, 9))
    ax.imshow(img)
    for box, label, score in zip(boxes, labels, scores):
        if score <= 0.05:
            continue
        box = box.detach()

        # Convert to numpy array if it's not already
        box_np = box.cpu().numpy() if not isinstance(box, np.ndarray) else box
        category = categories[label]
        color = colormap[label]
        color = [c / 255 for c in color]

        # Now you can safely use box_np for matplotlib operations
        rect = patches.Rectangle(
            (box_np[0], box_np[1]),  # x1, y1
            box_np[2] - box_np[0],   # width
            box_np[3] - box_np[1],   # height
            linewidth=2,
            edgecolor=color,
            facecolor='none'
        )
        ax.add_patch(rect)

        # Convert label and score to CPU and to numpy array if required
        label = label.detach().cpu().numpy() if not isinstance(label, np.ndarray) else label
        score = score.detach().cpu().numpy() if not isinstance(score, np.ndarray) else score

        # Draw the label and score
        plt.text(
            box_np[0],
            box_np[1],
            f'{categories[label]}: {score:.3f}',
            bbox=dict(facecolor='red', alpha=0.5),
            fontsize=8,
            color='black'
        )
    plt.show()