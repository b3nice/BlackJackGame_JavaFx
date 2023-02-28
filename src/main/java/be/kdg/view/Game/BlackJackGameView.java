package be.kdg.view.Game;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class BlackJackGameView extends GridPane {

    HBox hBoxTopPane;
    Label labelDealer;
    Label labelBalance;

    HBox hBoxDealerCards;
    Label labelSumCardsDealer;
    ImageView[] imageViewDealerCards;

    HBox hBoxH_S_D_S;
    Button buttonHit;
    Button buttonStand;
    Button buttonDouble;
    Button buttonSplit;

    HBox hBoxAmountBet;
    Label labelAmountBet;

    Label labelBet;

    public Label getLabelBet() {
        return labelBet;
    }

    public Label getLabelAmountBet() {
        return labelAmountBet;
    }

    public void setLabelAmountBet(Label labelAmountBet) {
        this.labelAmountBet = labelAmountBet;
    }

    HBox hBoxPlayerCards;

    Label labelSumCardsPlayer;

    HBox hBoxPlayerSplitCards;

    ImageView[] imageViewPlayerCards;
    ImageView[] imageViewPlayerSplitCards;

    HBox hBoxBetAmounts;
    Button buttonBet5;
    Button buttonBet10;
    Button buttonBet20;
    Button buttonBet50;
    Button buttonBetClear;
    Button buttonExit;
    Label labelPlayerName;



    public Label getLabelPlayerName() {
        return labelPlayerName;
    }

    Region[] spacersTop;
    Region[] spacersCenter;

    public Button getButtonBet5() {
        return buttonBet5;
    }

    public Button getButtonBet10() {
        return buttonBet10;
    }

    public Button getButtonBet20() {
        return buttonBet20;
    }

    public Button getButtonBet50() {
        return buttonBet50;
    }

    public Button getButtonBetClear() {
        return buttonBetClear;
    }

    public Button getButtonExit() {
        return buttonExit;
    }

    public BlackJackGameView(){
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes(){
        hBoxTopPane = new HBox();
        labelDealer = new Label("Dealer");
        labelBalance = new Label("Balance: ");

        hBoxDealerCards = new HBox();
        labelSumCardsDealer = new Label("Sum Cards: ");
        labelSumCardsPlayer = new Label("Sum Cards: ");

        imageViewDealerCards = new ImageView[5];
        imageViewPlayerCards = new ImageView[5];
        imageViewPlayerSplitCards = new ImageView[3];

        for (int i = 0; i < 5; i++) {
            imageViewDealerCards[i] = new ImageView(new Image("/BlueCardBack.PNG"));
            imageViewPlayerCards[i] = new ImageView(new Image("/RedCardBack.PNG"));
        }

        for (int i = 0; i < 3; i++) {
            imageViewPlayerSplitCards[i] = new ImageView(new Image("/RedCardBack.PNG"));
        }

        double factor = 12;
        double factor1 = 18;
        for (ImageView imageView : imageViewDealerCards) {
            imageView.fitWidthProperty().bind(this.widthProperty().divide(factor));
            imageView.fitHeightProperty().bind(this.widthProperty().divide(factor/2 * 1.556));
        }
        for (ImageView imageView : imageViewPlayerCards) {
            imageView.fitWidthProperty().bind(this.widthProperty().divide(factor));
            imageView.fitHeightProperty().bind(this.widthProperty().divide(factor/2 * 1.556));
        }
        for (ImageView imageView : imageViewPlayerSplitCards) {
            imageView.fitWidthProperty().bind(this.widthProperty().divide(factor1));
            imageView.fitHeightProperty().bind(this.widthProperty().divide(factor1/2 * 1.556));
        }

        hBoxH_S_D_S = new HBox();
        buttonHit = new Button("Hit");
        buttonStand = new Button("Stand");
        buttonDouble = new Button("Double");
        buttonSplit = new Button("Split");


        hBoxH_S_D_S.getChildren().addAll(buttonHit, buttonStand, buttonDouble, buttonSplit);

        hBoxAmountBet = new HBox();
        labelAmountBet = new Label("Amount Bet: ");
        labelBet = new Label("");

        hBoxAmountBet.getChildren().addAll(labelAmountBet, labelBet);


        hBoxPlayerCards = new HBox();
        hBoxPlayerSplitCards = new HBox();


        hBoxBetAmounts = new HBox();
        buttonBet5 = new Button("5");
        buttonBet10 = new Button("10");
        buttonBet20 = new Button("20");
        buttonBet50 = new Button("50");
        buttonBetClear = new Button("Clear");
        buttonExit = new Button("Exit");
        labelPlayerName = new Label("Player Name");

    }

    private void layoutNodes(){

        spacersTop = new Region[3];
        spacersCenter = new Region[imageViewDealerCards.length + 3];

        for (int i = 0; i < spacersTop.length; i++) {
            spacersTop[i] = new Region();
            HBox.setHgrow(spacersTop[i], Priority.ALWAYS);
        }

        for (int i = 0; i < spacersCenter.length; i++) {
            spacersCenter[i] = new Region();
            spacersCenter[i].setPrefWidth(5);
            spacersCenter[i].setMaxWidth(Double.MAX_VALUE);
        }

        int ii = 0;
        int iii = 0;
        int iiii = 0;
        for (ImageView imageView : imageViewDealerCards) {
            ++ii;
            hBoxDealerCards.getChildren().addAll(imageView,spacersCenter[ii]);
        }

        for (ImageView imageView : imageViewPlayerCards) {
            ++iii;
            hBoxPlayerCards.getChildren().addAll(imageView,spacersCenter[iii]);
        }

        for (ImageView imageView : imageViewPlayerSplitCards) {
            ++iiii;
            hBoxPlayerSplitCards.getChildren().addAll(imageView,spacersCenter[iiii]);
        }




        hBoxTopPane.getChildren().addAll(spacersTop[0], labelDealer, spacersTop[1], spacersTop[2], labelBalance);
        hBoxBetAmounts.getChildren().addAll(buttonBet5, buttonBet10, buttonBet20, buttonBet50, buttonBetClear);
        hBoxTopPane.setAlignment(Pos.CENTER);

        this.setGridLinesVisible(true);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(25);
        this.getColumnConstraints().addAll(col1, col2, col3);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(15);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(25);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(10);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(10);
        RowConstraints row5 = new RowConstraints();
        row5.setPercentHeight(25);
        RowConstraints row6 = new RowConstraints();
        row6.setPercentHeight(15);
        this.getRowConstraints().addAll(row1, row2, row3, row4, row5, row6);

        this.add(labelDealer,1,0);
        this.add(labelBalance,2,0);
        this.add(labelSumCardsDealer,0,1);
        this.add(hBoxDealerCards, 1, 1);
        this.add(hBoxH_S_D_S, 1, 2);
        this.add(hBoxAmountBet, 1, 3);
        this.add(labelSumCardsPlayer,0,4);
        this.add(hBoxPlayerCards, 1, 4);
        this.add(hBoxPlayerSplitCards, 2, 4);
        this.add(hBoxBetAmounts,0,5);
        this.add(labelPlayerName,1,5);
        this.add(buttonExit,2,5);


        labelBalance.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");
        labelSumCardsPlayer.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");
        labelSumCardsDealer.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");
        labelDealer.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");
        labelPlayerName.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");
        hBoxAmountBet.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");

        labelDealer.setFont(new Font(20));
        labelAmountBet.setFont(new Font(20));
        labelPlayerName.setFont(new Font(20));
        labelBalance.setFont(new Font(20));
        labelSumCardsDealer.setFont(new Font(20));
        labelSumCardsPlayer.setFont(new Font(20));
        labelBet.setFont(new Font(20));
        buttonBet5.setStyle("-fx-font-size: 20px;");
        buttonBet10.setStyle("-fx-font-size: 20px;");
        buttonBet20.setStyle("-fx-font-size: 20px;");
        buttonBet50.setStyle("-fx-font-size: 20px;");
        buttonBetClear.setStyle("-fx-font-size: 20px;");
        buttonExit.setStyle("-fx-font-size: 20px;");
        buttonHit.setStyle("-fx-font-size: 20px;");
        buttonStand.setStyle("-fx-font-size: 20px;");
        buttonDouble.setStyle("-fx-font-size: 20px;");
        buttonSplit.setStyle("-fx-font-size: 20px;");



        GridPane.setHalignment(labelBalance,HPos.CENTER);
        GridPane.setHalignment(labelDealer,HPos.CENTER);
        GridPane.setHalignment(labelSumCardsDealer,HPos.CENTER);
        GridPane.setHalignment(labelSumCardsPlayer,HPos.CENTER);
        GridPane.setHalignment(labelPlayerName,HPos.CENTER);
        GridPane.setHalignment(buttonExit, HPos.RIGHT);
        GridPane.setValignment(buttonExit, VPos.BOTTOM);


        hBoxDealerCards.setAlignment(Pos.CENTER);
        hBoxH_S_D_S.setAlignment(Pos.CENTER);
        hBoxAmountBet.setAlignment(Pos.CENTER);
        hBoxPlayerCards.setAlignment(Pos.CENTER);
        hBoxPlayerSplitCards.setAlignment(Pos.CENTER);
        hBoxBetAmounts.setAlignment(Pos.CENTER);

    }
}
