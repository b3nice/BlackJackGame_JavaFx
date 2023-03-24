package be.kdg.view.game;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BlackJackGameView extends GridPane {
    private final Stage primaryStage;
    private ImageViewMakerAndEditor imageViewMakerAndEditor;
    private Label labelAlert;
    private Label labelDealer;
    private HBox hBoxBalance;
    private Label labelBalance;
    private Label labelBalanceNumber;
    private HBox hBoxDealerCards;
    private HBox hBoxSumCardsDealer;
    private Label labelSumCardsDealer;
    private Label labelSumCardsDealerNumber;
    private HBox hBoxH_S_D_S;
    private Button buttonHit;
    private Button buttonStand;
    private Button buttonDouble;
    private Button buttonSplit;
    private HBox hBoxAmountBet;
    private Label labelAmountBet;
    private Label labelBet;
    private HBox hBoxPlayerCards;
    private HBox hBoxSumCardsPlayer;
    private Label labelSumCardsPlayer;
    private Label labelSumCardsPlayerNumber;
    private HBox hBoxPlayerSplitCards;
    private HBox hBoxBetAmounts;
    private Button buttonBet5;
    private Button buttonBet10;
    private Button buttonBet20;
    private Button buttonBet50;
    private Button buttonBetClear;
    private Button buttonExit;
    private Button buttonBet;
    private Label labelPlayerName;
    Region[] spacersCenter;

    public BlackJackGameView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.initialiseNodes();
        this.layoutNodes();
        this.styleNodes();
        this.imageFixing();
    }

    private void initialiseNodes() {
        labelAlert = new Label(":)");
        imageViewMakerAndEditor = new ImageViewMakerAndEditor();
        labelDealer = new Label("Dealer");
        hBoxBalance = new HBox();
        labelBalance = new Label("Balance: ");
        labelBalanceNumber = new Label("200");
        hBoxDealerCards = new HBox();
        hBoxSumCardsDealer = new HBox();
        labelSumCardsDealer = new Label("Sum Cards: ");
        labelSumCardsDealerNumber = new Label("0");
        hBoxSumCardsPlayer = new HBox();
        labelSumCardsPlayer = new Label("Sum Cards: ");
        labelSumCardsPlayerNumber = new Label("0");
        hBoxH_S_D_S = new HBox();
        buttonHit = new Button("Hit");
        buttonStand = new Button("Stand");
        buttonDouble = new Button("Double");
        buttonSplit = new Button("Split");
        hBoxAmountBet = new HBox();
        labelAmountBet = new Label("Amount Bet: ");
        labelBet = new Label("0");
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

        hBoxBalance.getChildren().addAll(labelBalance, labelBalanceNumber);
        hBoxSumCardsDealer.getChildren().addAll(labelSumCardsDealer, labelSumCardsDealerNumber);
        hBoxSumCardsPlayer.getChildren().addAll(labelSumCardsPlayer, labelSumCardsPlayerNumber);
        hBoxH_S_D_S.getChildren().addAll(buttonHit, buttonStand, buttonDouble, buttonSplit);
        hBoxAmountBet.getChildren().addAll(labelAmountBet, labelBet);
        hBoxBetAmounts.getChildren().addAll(buttonBet5, buttonBet10, buttonBet20, buttonBet50, buttonBetClear, buttonBet);
    }

    private void layoutNodes() {
        this.setOnMouseClicked(event -> this.requestFocus());

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
        this.add(labelAlert, 0, 2);
        GridPane.setHalignment(labelDealer, HPos.CENTER);
        GridPane.setHalignment(labelPlayerName, HPos.CENTER);
        GridPane.setHalignment(labelAlert, HPos.CENTER);
        GridPane.setHalignment(buttonExit, HPos.RIGHT);
        GridPane.setValignment(buttonExit, VPos.BOTTOM);
        labelAlert.setTextAlignment(TextAlignment.CENTER);
        labelAlert.setAlignment(Pos.CENTER);
        hBoxDealerCards.setAlignment(Pos.CENTER);
        hBoxBalance.setAlignment(Pos.CENTER);
        hBoxSumCardsPlayer.setAlignment(Pos.CENTER);
        hBoxSumCardsDealer.setAlignment(Pos.CENTER);
        hBoxH_S_D_S.setAlignment(Pos.CENTER);
        hBoxAmountBet.setAlignment(Pos.CENTER);
        hBoxPlayerCards.setAlignment(Pos.CENTER);
        hBoxPlayerSplitCards.setAlignment(Pos.CENTER);
        hBoxBetAmounts.setAlignment(Pos.CENTER);
    }

    public void styleNodes(){
        this.setStyle("-fx-background-image: url('BlackJackTable.png'); -fx-background-repeat: no-repeat; -fx-background-position: center; -fx-background-size: cover;");

        labelAlert.setWrapText(true);
        labelAlert.setMinWidth(500);
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
        labelBet.getStyleClass().add("label");
        buttonBet5.getStyleClass().add("button");
        buttonBet10.getStyleClass().add("button");
        buttonBet20.getStyleClass().add("button");
        buttonBet50.getStyleClass().add("button");
        buttonBetClear.getStyleClass().add("button");
        buttonBet.getStyleClass().add("button");
        buttonExit.getStyleClass().add("button");
        buttonHit.getStyleClass().add("button");
        buttonStand.getStyleClass().add("button");
        buttonDouble.getStyleClass().add("button");
        buttonSplit.getStyleClass().add("button");


        DoubleProperty fontSize = new SimpleDoubleProperty(10);
        labelAlert.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()/1.2), fontSize));
        labelDealer.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        labelAmountBet.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        labelBet.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        labelPlayerName.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        labelBalance.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        labelBalanceNumber.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        labelSumCardsDealer.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        labelSumCardsDealerNumber.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        labelSumCardsPlayer.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        labelSumCardsPlayerNumber.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        buttonBet5.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        buttonBet10.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        buttonBet20.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        buttonBet50.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        buttonBetClear.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        buttonBet.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        buttonExit.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        buttonHit.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        buttonStand.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        buttonDouble.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        buttonSplit.fontProperty().bind(Bindings.createObjectBinding(() -> Font.font(fontSize.get()), fontSize));
        fontSize.bind(primaryStage.widthProperty().multiply(0.015));
    }

    public void imageFixing() {
        spacersCenter = new Region[imageViewMakerAndEditor.getImageViewDealerCards().length + 3];

        double factor = 15;
        double factor1 = 20;
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
    }

    public void swapImageSizes() {
        double factor = 15;
        double factor1 = 20;
        swapImageS(factor1, factor);
    }

    public void swapImageSizesBack() {
        double factor = 15;
        double factor1 = 20;
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

    public void fadeLabels() {
        PauseTransition pause = new PauseTransition(Duration.seconds(2));

        FadeTransition fade = new FadeTransition(Duration.seconds(1), labelAlert);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);

        SequentialTransition sequence = new SequentialTransition(pause, fade);
        sequence.play();
    }
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
    public ImageViewMakerAndEditor getImageViewMakerAndEditor() {
        return imageViewMakerAndEditor;
    }
    public Label getLabelAlert() {
        return labelAlert;
    }
    public Label getLabelBalanceNumber() {
        return labelBalanceNumber;
    }
    public Label getLabelSumCardsDealerNumber() {
        return labelSumCardsDealerNumber;
    }
    public HBox gethBoxH_S_D_S() {
        return hBoxH_S_D_S;
    }
    public Button getButtonHit() {
        return buttonHit;
    }
    public Button getButtonStand() {
        return buttonStand;
    }
    public Button getButtonDouble() {
        return buttonDouble;
    }
    public Button getButtonSplit() {
        return buttonSplit;
    }
    public Label getLabelBet() {
        return labelBet;
    }
    public HBox gethBoxPlayerCards() {
        return hBoxPlayerCards;
    }
    public Label getLabelSumCardsPlayerNumber() {
        return labelSumCardsPlayerNumber;
    }
    public HBox gethBoxPlayerSplitCards() {
        return hBoxPlayerSplitCards;
    }
    public HBox gethBoxBetAmounts() {
        return hBoxBetAmounts;
    }
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
    public Label getLabelPlayerName() {
        return labelPlayerName;
    }
}
