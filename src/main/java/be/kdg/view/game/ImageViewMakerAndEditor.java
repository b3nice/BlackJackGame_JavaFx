package be.kdg.view.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageViewMakerAndEditor {

    ImageView[] imageViewDealerCards;
    ImageView[] imageViewPlayerCards;
    ImageView[] imageViewPlayerSplitCards;

    public ImageView[] getImageViewDealerCards() {
        return imageViewDealerCards;
    }
    public ImageView[] getImageViewPlayerCards() {
        return imageViewPlayerCards;
    }
    public ImageView[] getImageViewPlayerSplitCards() {
        return imageViewPlayerSplitCards;
    }

    public void setImageViewDealerCards(ImageView[] imageViewDealerCards) {
        this.imageViewDealerCards = imageViewDealerCards;
    }
    public void setImageViewPlayerCards(ImageView[] imageViewPlayerCards) {
        this.imageViewPlayerCards = imageViewPlayerCards;
    }
    public void setImageViewPlayerSplitCards(ImageView[] imageViewPlayerSplitCards) {
        this.imageViewPlayerSplitCards = imageViewPlayerSplitCards;
    }

    public ImageViewMakerAndEditor() {
        imageViewDealerCards = new ImageView[6];
        imageViewPlayerCards = new ImageView[6];
        imageViewPlayerSplitCards = new ImageView[6];

        for (int i = 0; i < 6; i++) {
            imageViewDealerCards[i] = new ImageView((Image) null);
            imageViewPlayerCards[i] = new ImageView((Image) null);
        }

        for (int i = 0; i < 6; i++) {
            imageViewPlayerSplitCards[i] = new ImageView((Image) null);
        }
    }

    public void setImagePlayer(int index, Image image) {imageViewPlayerCards[index].setImage(image);}
    public void setImagePlayerSplit(int index, Image image) {imageViewPlayerSplitCards[index].setImage(image);}
    public void setImageDealer(int index, Image image) {imageViewDealerCards[index].setImage(image);}

    public Image getImagePlayer(int index) {
        return imageViewPlayerCards[index].getImage();
    }
    public Image getImagePlayerSplit(int index) {
        return imageViewPlayerSplitCards[index].getImage();
    }
    public Image getImageDealer(int index) {
        return imageViewDealerCards[index].getImage();
    }

}


