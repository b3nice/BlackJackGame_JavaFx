package be.kdg.view.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageViewMakerAndEditor {

    private final ImageView[] imageViewDealerCards;
    private final ImageView[] imageViewPlayerCards;
    private final ImageView[] imageViewPlayerSplitCards;

    /**
     * This class is used to create the imageviews for the cards.
     * It also contains methods to set the images of the imageviews.
     */
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
    public ImageView[] getImageViewDealerCards() {
        return imageViewDealerCards;
    }
    public ImageView[] getImageViewPlayerCards() {
        return imageViewPlayerCards;
    }
    public ImageView[] getImageViewPlayerSplitCards() {
        return imageViewPlayerSplitCards;
    }
}