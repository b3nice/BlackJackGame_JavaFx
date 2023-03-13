package be.kdg.view.Game;

import be.kdg.model.BlackJackModel;
import be.kdg.view.Start.BlackJackStartPresenter;
import be.kdg.view.Start.BlackJackStartView;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class BlackJackGamePresenter {
    private final BlackJackModel model;
    private final BlackJackGameView view;
    private final ImageViewMakerAndEditor imageViewMakerAndEditor;
    private final Stage primaryStage;
    private int indexImage;
    private int indexImageSplit1;
    private int indexImageSplit2;
    private int counterSwapCards;

    public BlackJackGamePresenter(BlackJackGameView view, BlackJackModel model, ImageViewMakerAndEditor imageViewMakerAndEditor, Stage primaryStage) {
        this.model = model;
        this.view = view;
        this.imageViewMakerAndEditor = imageViewMakerAndEditor;
        this.primaryStage = primaryStage;
        indexImage = 2;
        indexImageSplit1 = 1;
        indexImageSplit2 = 1;
        counterSwapCards = 0;
        model.makeNewTable();
        view.getLabelSumCardsPlayerNumber().setText(String.valueOf(model.getPlayerPoints()));
        view.getLabelSumCardsDealerNumber().setText(String.valueOf(model.getDealerPoints()));
        view.gethBoxH_S_D_S().setDisable(true);
        addEventHandlers();
        updateView();
    }


    private void addEventHandlers() {
        view.getLabelSumCardsPlayerNumber().setText(String.valueOf(model.getPlayerPoints()));
        view.getLabelSumCardsDealerNumber().setText(String.valueOf(model.getDealerPoints()));
        view.getButtonExit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String fileName = "src/main/resources/player.txt";
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                    bw.write(model.getPlayer().toString());
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                BlackJackModel newModel = new BlackJackModel();
                BlackJackStartView viewStart = new BlackJackStartView();
                BlackJackStartPresenter presenterGame = new BlackJackStartPresenter(viewStart, newModel, primaryStage);
                view.getScene().setRoot(viewStart);
            }
        });

        view.getButtonBet5().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String labelText = view.getLabelBet().getText();
                int value = 0;

                if (!labelText.isEmpty()) {
                    value = Integer.parseInt(labelText);
                }
                value = value + 5;

                view.getLabelBet().setText(String.valueOf(value));
                model.setBet(value);
            }
        });

        view.getButtonBet10().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String labelText = view.getLabelBet().getText();
                int value = 0;

                if (!labelText.isEmpty()) {
                    value = Integer.parseInt(labelText);
                }
                value = value + 10;

                view.getLabelBet().setText(String.valueOf(value));
                model.setBet(value);
            }
        });
        view.getButtonBet20().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String labelText = view.getLabelBet().getText();
                int value = 0;

                if (!labelText.isEmpty()) {
                    value = Integer.parseInt(labelText);
                }
                value = value + 20;

                view.getLabelBet().setText(String.valueOf(value));
                model.setBet(value);
            }
        });
        view.getButtonBet50().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String labelText = view.getLabelBet().getText();
                int value = 0;

                if (!labelText.isEmpty()) {
                    value = Integer.parseInt(labelText);
                }
                value = value + 50;

                view.getLabelBet().setText(String.valueOf(value));
                model.setBet(value);
            }
        });
        view.getButtonBetClear().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int value = 0;
                view.getLabelBet().setText(String.valueOf(value));
                model.setBet(value);
            }
        });
        view.getButtonBet().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (model.getBet() == 0) {
                    String textAlert = "You cannot bet nothing, please bet an amount.";
                    view.setLabelAlertRed(view.getLabelAlert(), textAlert);
                } else {
                    view.gethBoxH_S_D_S().setDisable(false);
                    view.gethBoxBetAmounts().setDisable(true);
                    view.getButtonExit().setDisable(true);

                    model.startGame();

                    imageViewMakerAndEditor.setImagePlayer(0, new Image(String.valueOf(model.getFirstCardPlayer())));
                    imageViewMakerAndEditor.setImagePlayer(1, new Image(String.valueOf(model.getSecondCardPlayer())));

                    imageViewMakerAndEditor.setImageDealer(0, new Image("/RedCardBack.PNG"));
                    imageViewMakerAndEditor.setImageDealer(1, new Image(String.valueOf(model.getDealerCards().get(0))));

                    checkSplitOption();
                    updateView();
                }
            }
        });
        view.getButtonHit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (model.getSplitValidation().equals("y")) {
                    model.setAnwser("Hit");
                    addCardView();
                    swapCardsByWinOrLossValue(model.calculateWinOrLossForSplit());
                } else {
                    model.setAnwser("Hit");
                    addCardView();
                }
            }

        });
        view.getButtonStand().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                model.setAnwser("Stand");
                if (model.getSplitValidation().equals("y")) {
                    if (getIsFirstHand()) {
                        model.setStatus(2);
                    } else {
                        model.setSecondStatus(2);
                    }
                    swapCardsByWinOrLossValue(model.calculateWinOrLossForSplit());
                } else {
                    model.hitStandDoubleOrSplit();

                    model.winOrLoss();
                    view.getLabelSumCardsPlayerNumber().setText(String.valueOf(model.getPlayerPoints()));
                    showDealerCards();
                    checkStatusWinOrLoss();
                }
            }
        });
        view.getButtonDouble().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                model.setAnwser("Double");
                if (model.getSplitValidation().equals("y")) {
                }
                addCardView();
            }
        });
        view.getButtonSplit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                model.setSplitValidation("y");
                checkSplitOption();

                imageViewMakerAndEditor.setImagePlayer(1, new WritableImage(1, 1));
                imageViewMakerAndEditor.setImagePlayerSplit(0, new Image(String.valueOf(model.getPlayerCards2().get(0))));

                updateView();
                view.getButtonSplit().setDisable(true);
            }
        });
        primaryStage.setOnCloseRequest(event -> {
            String fileName = "src/main/resources/player.txt";
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                bw.write(model.getPlayer().toString());
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void swapCardsByWinOrLossValue(int winOrLossValue) {
        if (counterSwapCards <= 3){
            if (getIsFirstHand()) {
                if (winOrLossValue != -1 && model.getStatus() == 1) {
                    counterSwapCards++;
                    swapPlayerDecks();
                    model.setStatHolder(1);
                } else if (winOrLossValue != -1) {
                    counterSwapCards++;
                    swapPlayerDecks();
                    model.setStatHolder(1);
                }
            } else {
                if (winOrLossValue != -1 && model.getSecondStatus() == 1) {
                    counterSwapCards++;
                    swapPlayerDecks();
                    model.setStatHolder(2);
                } else if (winOrLossValue != -1) {
                    counterSwapCards++;
                    swapPlayerDecks();
                    model.setStatHolder(2);
                }
            }
        }
    }

    int counter = 0;

    public void checkSplitOption() {
        if (model.getFirstCardPlayer().getNumber() != model.getSecondCardPlayer().getNumber()) {
            view.getButtonSplit().setDisable(true);
        }
        if (counter == 0) {
            model.splitOption();
            counter++;
        }
        if (model.getSplitValidation().equals("y")) {
            model.splitOption();
        }
    }

    public void addCardView() {
        if (model.getSplitValidation().equals("y")) {
            updateView();
            model.splitGame();

            if (getIsFirstHand()) {
                imageViewMakerAndEditor.setImagePlayer(indexImageSplit1, new Image(String.valueOf(model.getPlayerCards().get(indexImageSplit1))));
                indexImageSplit1++;
            } else {
                imageViewMakerAndEditor.setImagePlayerSplit(indexImageSplit2, new Image(String.valueOf(model.getPlayerCards2().get(indexImageSplit2))));
                indexImageSplit2++;
            }
            updateView();

        } else {
            model.hitStandDoubleOrSplit();

            imageViewMakerAndEditor.setImagePlayer(indexImage, new Image(String.valueOf(model.getPlayerCards().get(indexImage))));
            indexImage++;
            updateView();

            model.winOrLoss();
            showDealerCards();
            checkStatusWinOrLoss();
        }

    }

    public void swapPlayerDecks() {
        view.getChildren().remove(view.gethBoxPlayerCards());
        view.getChildren().remove(view.gethBoxPlayerSplitCards());
        view.add(view.gethBoxPlayerSplitCards(), 1, 4);
        view.add(view.gethBoxPlayerCards(), 2, 4);

        if (getIsFirstHand()) {
            view.swapImageSizes();
        } else {
            view.swapImageSizesBack();
        }
        updateView();
    }

    public void showDealerCards() {
        if (model.getWinOrLossValue() != 0) {
            imageViewMakerAndEditor.setImageDealer(0, new Image(String.valueOf(model.getDealerCards().get(1))));
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.play();
            for (int i = 2; i < model.getDealerCards().size(); i++) {
                imageViewMakerAndEditor.setImageDealer(i, new Image(String.valueOf(model.getDealerCards().get(i))));
                PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
                pause2.play();
            }
        }
        view.getLabelSumCardsDealerNumber().setText(String.valueOf(model.getDealerPoints()));
        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        pause.play();
    }

    public void checkStatusWinOrLoss() {
        if (model.getWinOrLossValue() == 1) {
            model.setStatHolder(2);
            model.youWon();
            youHaveWon();
        } else if (model.getWinOrLossValue() == 2) {
            model.setStatHolder(2);
            model.youLost();
            youHaveLost();
        } else if (model.getWinOrLossValue() == 3) {
            model.setStatHolder(2);
            model.youDraw();
            youHaveDrawn();
        }
    }

    public void youHaveWon() {
        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        String textAlert = "You have won!!! :)). This is your new balance:" + model.getBalance();
        disableButtons();
        view.setLabelAlertGreen(view.getLabelAlert(), textAlert);
        pause.setOnFinished(event -> {
            refreshView();
        });
        pause.play();

    }

    public void youHaveLost() {
        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        String textAlert = "You have lost  >:(. This is your new balance:" + model.getBalance();
        disableButtons();
        view.setLabelAlertRed(view.getLabelAlert(), textAlert);
        pause.setOnFinished(event -> {
            refreshView();
        });
        pause.play();
    }

    public void youHaveDrawn() {
        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        String textAlert = "You have drawn, -_- .This is your new balance:" + model.getBalance();
        disableButtons();
        view.setLabelAlertRed(view.getLabelAlert(), textAlert);
        pause.setOnFinished(event -> {
            refreshView();
        });
        pause.play();
    }

    public void refreshView() {
        BlackJackGameView viewGame = new BlackJackGameView();
        BlackJackGamePresenter presenterGame = new BlackJackGamePresenter(viewGame, model, viewGame.getImageViewMakerAndEditor(), primaryStage);
        model.makeNewTable();
        view.getScene().setRoot(viewGame);
    }

    public void disableButtons() {
        view.getButtonHit().setDisable(true);
        view.getButtonStand().setDisable(true);
        view.getButtonDouble().setDisable(true);
        view.getButtonSplit().setDisable(true);
    }

    public boolean getIsFirstHand() {
        return model.getStatHolder() == 2;
    }

    public void updateView() {
        view.getLabelPlayerName().setText(model.getName());
        view.getLabelBalanceNumber().setText(String.valueOf(model.getBalance()));

        int textValue = model.getStatHolder() == 2 ? model.getPlayerPoints() : model.getPlayerPoints2();
        view.getLabelSumCardsPlayerNumber().setText(String.valueOf(textValue));

        view.getLabelSumCardsDealerNumber().setText(String.valueOf(model.getDealerPoints()));
    }
}