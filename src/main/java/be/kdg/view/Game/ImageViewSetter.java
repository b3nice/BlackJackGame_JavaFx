package be.kdg.view.Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageViewSetter {
    private ImageView[] imageViews;

    public ImageViewSetter(ImageView[] imageViews) {
        this.imageViews = imageViews;
    }

    public void setImage(int index, Image image) {
        imageViews[index].setImage(image);
    }

    public Image getImage(int index) {
        return imageViews[index].getImage();
    }
}