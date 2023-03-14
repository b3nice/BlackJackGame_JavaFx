package be.kdg.view.Game;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class BlackJackGameView extends GridPane {

    private ImageViewMakerAndEditor imageViewMakerAndEditor;

    public ImageViewMakerAndEditor getImageViewMakerAndEditor() {
        return imageViewMakerAndEditor;
    }


    Label labelAlert;

    public void setLabelAlertRed(Label labelAlert, String text) {
        labelAlert.setText(text);
        labelAlert.setStyle("-fx-text-fill: red;");
        fadeLabels();
        this.labelAlert = labelAlert;
    }

    public void setLabelAlertGreen(Label labelAlert, String text) {
        labelAlert.setText(text);
        labelAlert.setStyle("-fx-text-fill: green;");
        fadeLabels();
        this.labelAlert = labelAlert;
    }

    public Label getLabelAlert() {
        return labelAlert;
    }

    StackPane stackPane;

    Label labelDealer;
    HBox hBoxBalance;
    Label labelBalance;
    Label labelBalanceNumber;

    public Label getLabelBalanceNumber() {
        return labelBalanceNumber;
    }

    HBox hBoxDealerCards;

    HBox hBoxSumCardsDealer;
    Label labelSumCardsDealer;
    Label labelSumCardsDealerNumber;

    public Label getLabelSumCardsDealerNumber() {
        return labelSumCardsDealerNumber;
    }


    HBox hBoxH_S_D_S;

    public HBox gethBoxH_S_D_S() {
        return hBoxH_S_D_S;
    }

    Button buttonHit;

    public Button getButtonHit() {
        return buttonHit;
    }

    Button buttonStand;

    public Button getButtonStand() {
        return buttonStand;
    }

    Button buttonDouble;

    public Button getButtonDouble() {
        return buttonDouble;
    }

    Button buttonSplit;

    public Button getButtonSplit() {
        return buttonSplit;
    }

    HBox hBoxAmountBet;
    Label labelAmountBet;

    Label labelBet;

    public Label getLabelBet() {
        return labelBet;
    }

    HBox hBoxPlayerCards;

    HBox hBoxSumCardsPlayer;
    Label labelSumCardsPlayer;
    Label labelSumCardsPlayerNumber;

    public Label getLabelSumCardsPlayerNumber() {
        return labelSumCardsPlayerNumber;
    }

    HBox hBoxPlayerSplitCards;


    HBox hBoxBetAmounts;

    public HBox gethBoxBetAmounts() {
        return hBoxBetAmounts;
    }

    Button buttonBet5;
    Button buttonBet10;
    Button buttonBet20;
    Button buttonBet50;
    Button buttonBetClear;
    Button buttonExit;
    Button buttonBet;
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

    public Button getButtonBet() {
        return buttonBet;
    }

    public HBox gethBoxPlayerCards() {
        return hBoxPlayerCards;
    }

    public HBox gethBoxPlayerSplitCards() {
        return hBoxPlayerSplitCards;
    }

    public BlackJackGameView() {
        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        stackPane = new StackPane();
        labelAlert = new Label(":)");
        imageViewMakerAndEditor = new ImageViewMakerAndEditor();
        labelDealer = new Label("Dealer");

        hBoxBalance = new HBox();
        labelBalance = new Label("Balance: ");
        labelBalanceNumber = new Label("200");
        hBoxBalance.getChildren().addAll(labelBalance, labelBalanceNumber);

        hBoxDealerCards = new HBox();

        hBoxSumCardsDealer = new HBox();
        labelSumCardsDealer = new Label("Sum Cards: ");
        labelSumCardsDealerNumber = new Label("0");
        hBoxSumCardsDealer.getChildren().addAll(labelSumCardsDealer, labelSumCardsDealerNumber);

        hBoxSumCardsPlayer = new HBox();
        labelSumCardsPlayer = new Label("Sum Cards: ");
        labelSumCardsPlayerNumber = new Label("0");
        hBoxSumCardsPlayer.getChildren().addAll(labelSumCardsPlayer, labelSumCardsPlayerNumber);

        double factor = 12;
        double factor1 = 18;
        for (ImageView imageView : imageViewMakerAndEditor.getImageViewDealerCards()) {
            imageView.fitWidthProperty().bind(this.widthProperty().divide(factor));
            imageView.fitHeightProperty().bind(this.widthProperty().divide(factor / 2 * 1.556));
        }
        for (ImageView imageView : imageViewMakerAndEditor.getImageViewPlayerCards()) {
            imageView.fitWidthProperty().bind(this.widthProperty().divide(factor));
            imageView.fitHeightProperty().bind(this.widthProperty().divide(factor / 2 * 1.556));
        }
        for (ImageView imageView : imageViewMakerAndEditor.getImageViewPlayerSplitCards()) {
            imageView.fitWidthProperty().bind(this.widthProperty().divide(factor1));
            imageView.fitHeightProperty().bind(this.widthProperty().divide(factor1 / 2 * 1.556));
        }

        hBoxH_S_D_S = new HBox();
        buttonHit = new Button("Hit");
        buttonStand = new Button("Stand");
        buttonDouble = new Button("Double");
        buttonSplit = new Button("Split");

        hBoxH_S_D_S.getChildren().addAll(buttonHit, buttonStand, buttonDouble, buttonSplit);

        hBoxAmountBet = new HBox();
        labelAmountBet = new Label("Amount Bet: ");
        labelBet = new Label("0");

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
        buttonBet = new Button("Bet");
        labelPlayerName = new Label("Player Name");
        hBoxBetAmounts.getChildren().addAll(buttonBet5, buttonBet10, buttonBet20, buttonBet50, buttonBetClear, buttonBet);
        this.setStyle("-fx-background-image: url('BlackJackTable.png'); -fx-background-repeat: no-repeat; -fx-background-position: center; -fx-background-size: cover;");
    }

    public void swapImageSizes() {
        double factor = 12;
        double factor1 = 18;
        swapImageS(factor1, factor);
    }

    public void swapImageSizesBack() {
        double factor = 12;
        double factor1 = 18;
        swapImageS(factor, factor1);
    }

    public void swapImageS(double factor, double factor1) {
        for (ImageView imageView : imageViewMakerAndEditor.getImageViewPlayerCards()) {
            imageView.fitWidthProperty().bind(this.widthProperty().divide(factor));
            imageView.fitHeightProperty().bind(this.widthProperty().divide(factor / 2 * 1.556));
        }
        for (ImageView imageView : imageViewMakerAndEditor.getImageViewPlayerSplitCards()) {
            imageView.fitWidthProperty().bind(this.widthProperty().divide(factor1));
            imageView.fitHeightProperty().bind(this.widthProperty().divide(factor1 / 2 * 1.556));
        }
    }


    private void layoutNodes() {
        this.setOnMouseClicked(event -> this.requestFocus());


        spacersTop = new Region[3];
        spacersCenter = new Region[imageViewMakerAndEditor.getImageViewDealerCards().length + 3];

        for (int i = 0; i < spacersTop.length; i++) {
            spacersTop[i] = new Region();
            HBox.setHgrow(spacersTop[i], Priority.ALWAYS);
        }

        for (int i = 0; i < spacersCenter.length; i++) {
            spacersCenter[i] = new Region();
            spacersCenter[i].setPrefWidth(5);
            spacersCenter[i].setMaxWidth(Double.MAX_VALUE);
        }

        int indexImageviewDealerCards = 0;
        int indexImageViewPlayerCards = 0;
        int indexImageViewPlayerSplitCards = 0;
        for (ImageView imageView : imageViewMakerAndEditor.getImageViewDealerCards()) {
            ++indexImageviewDealerCards;
            hBoxDealerCards.getChildren().addAll(imageView, spacersCenter[indexImageviewDealerCards]);
        }

        for (ImageView imageView : imageViewMakerAndEditor.getImageViewPlayerCards()) {
            ++indexImageViewPlayerCards;
            hBoxPlayerCards.getChildren().addAll(imageView, spacersCenter[indexImageViewPlayerCards]);
        }

        for (ImageView imageView : imageViewMakerAndEditor.getImageViewPlayerSplitCards()) {
            ++indexImageViewPlayerSplitCards;
            hBoxPlayerSplitCards.getChildren().addAll(imageView, spacersCenter[indexImageViewPlayerSplitCards]);
        }


        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(27.5);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(45);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(27.5);
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

        GridPane.setRowIndex(stackPane, 2);
        GridPane.setColumnIndex(stackPane, 0);
        GridPane.setRowSpan(stackPane, 2);

        this.add(labelDealer, 1, 0);
        this.add(hBoxBalance, 2, 0);
        this.add(hBoxSumCardsDealer, 0, 1);
        this.add(hBoxDealerCards, 1, 1);
        this.add(hBoxH_S_D_S, 1, 2);
        this.add(hBoxAmountBet, 1, 3);
        this.add(hBoxSumCardsPlayer, 0, 4);
        this.add(hBoxPlayerCards, 1, 4);
        this.add(hBoxPlayerSplitCards, 2, 4);
        this.add(hBoxBetAmounts, 0, 5);
        this.add(labelPlayerName, 1, 5);
        this.add(buttonExit, 2, 5);
        this.add(stackPane, 0, 2);

        stackPane.getChildren().add(labelAlert);

        labelAlert.getStyleClass().add("label_alert");
        labelDealer.getStyleClass().add("label");
        labelAmountBet.getStyleClass().add("label");
        labelPlayerName.getStyleClass().add("label");
        labelBalance.getStyleClass().add("label");
        labelBalanceNumber.getStyleClass().add("label");
        labelSumCardsDealer.getStyleClass().add("label");
        labelSumCardsDealerNumber.getStyleClass().add("label");
        labelSumCardsPlayer.getStyleClass().add("label");
        labelSumCardsPlayerNumber.getStyleClass().add("label");
        buttonBet5.getStyleClass().add("button");
        buttonBet10.getStyleClass().add("button");
        buttonBet20.getStyleClass().add("button");
        buttonBet50.getStyleClass().add("button");
        buttonBetClear.getStyleClass().add("button");
        buttonExit.getStyleClass().add("button");


        GridPane.setHalignment(labelDealer, HPos.CENTER);
        GridPane.setHalignment(labelPlayerName, HPos.CENTER);
        GridPane.setHalignment(buttonExit, HPos.RIGHT);
        GridPane.setValignment(buttonExit, VPos.BOTTOM);

        labelAlert.setTextAlignment(TextAlignment.CENTER);
        labelAlert.setAlignment(Pos.CENTER);
        stackPane.setAlignment(Pos.CENTER);
        hBoxDealerCards.setAlignment(Pos.CENTER);
        hBoxBalance.setAlignment(Pos.CENTER);
        hBoxSumCardsPlayer.setAlignment(Pos.CENTER);
        hBoxSumCardsDealer.setAlignment(Pos.CENTER);
        hBoxH_S_D_S.setAlignment(Pos.CENTER);
        hBoxAmountBet.setAlignment(Pos.CENTER);
        hBoxPlayerCards.setAlignment(Pos.CENTER);
        hBoxPlayerSplitCards.setAlignment(Pos.CENTER);
        hBoxBetAmounts.setAlignment(Pos.CENTER);

        hBoxBalance.prefWidthProperty().bind(BlackJackGameView.this.widthProperty());
    }

    public void fadeLabels() {
        PauseTransition pause = new PauseTransition(Duration.seconds(2));

        FadeTransition fade = new FadeTransition(Duration.seconds(1), labelAlert);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);

        SequentialTransition sequence = new SequentialTransition(pause, fade);
        sequence.play();
    }
}
