package be.kdg.view.game;

import be.kdg.model.BlackJackModel;
import be.kdg.view.start.BlackJackStartPresenter;
import be.kdg.view.start.BlackJackStartView;
import javafx.animation.PauseTransition;
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
    private int counterWinLossDraw;

    public BlackJackGamePresenter(BlackJackGameView view, BlackJackModel model, ImageViewMakerAndEditor imageViewMakerAndEditor, Stage primaryStage) {
        this.model = model;
        this.view = view;
        this.imageViewMakerAndEditor = imageViewMakerAndEditor;
        this.primaryStage = primaryStage;
        indexImage = 2;
        indexImageSplit1 = 1;
        indexImageSplit2 = 1;
        counterSwapCards = 0;
        counterWinLossDraw = 0;
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
        view.getButtonExit().setOnAction(actionEvent -> {
            String fileName = "src/main/resources/player.txt";
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                bw.write(model.getPlayer().toString());
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            BlackJackModel newModel = new BlackJackModel();
            BlackJackStartView viewStart = new BlackJackStartView(primaryStage);
            BlackJackStartPresenter presenterGame = new BlackJackStartPresenter(viewStart, newModel, primaryStage);
            view.getScene().setRoot(viewStart);
        });

        view.getButtonBet5().setOnAction(actionEvent -> {
            String labelText = view.getLabelBet().getText();
            int value = 0;

            if (!labelText.isEmpty()) {
                value = Integer.parseInt(labelText);
            }
            value = value + 5;

            view.getLabelBet().setText(String.valueOf(value));
            model.setBet(value);
        });

        view.getButtonBet10().setOnAction(actionEvent -> {
            String labelText = view.getLabelBet().getText();
            int value = 0;

            if (!labelText.isEmpty()) {
                value = Integer.parseInt(labelText);
            }
            value = value + 10;

            view.getLabelBet().setText(String.valueOf(value));
            model.setBet(value);
        });
        view.getButtonBet20().setOnAction(actionEvent -> {
            String labelText = view.getLabelBet().getText();
            int value = 0;

            if (!labelText.isEmpty()) {
                value = Integer.parseInt(labelText);
            }
            value = value + 20;

            view.getLabelBet().setText(String.valueOf(value));
            model.setBet(value);
        });
        view.getButtonBet50().setOnAction(actionEvent -> {
            String labelText = view.getLabelBet().getText();
            int value = 0;

            if (!labelText.isEmpty()) {
                value = Integer.parseInt(labelText);
            }
            value = value + 50;

            view.getLabelBet().setText(String.valueOf(value));
            model.setBet(value);
        });
        view.getButtonBetClear().setOnAction(actionEvent -> {
            int value = 0;
            view.getLabelBet().setText(String.valueOf(value));
            model.setBet(value);
        });
        view.getButtonBet().setOnAction(actionEvent -> {
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

                view.getLabelSumCardsDealerNumber().setText(String.valueOf(model.getDealerPoints()));
                checkSplitOption();
                updateView();
            }
        });
        view.getButtonHit().setOnAction(actionEvent -> {
            if (model.getSplitValidation().equals("y")) {
                model.setAnwser("Hit");
                addCardView();

                swapCardsByWinOrLossValue(model.calculateWinOrLossForSplit());
                checkStatusWinOrLossSplit();
            } else {
                model.setAnwser("Hit");
                addCardView();
            }
        });
        view.getButtonStand().setOnAction(actionEvent -> {
            model.setAnwser("Stand");
            if (model.getSplitValidation().equals("y")) {
                if (getIsFirstHand()) {
                    model.setStatus(2);
                } else {
                    model.setSecondStatus(2);
                }

                swapCardsByWinOrLossValue(model.calculateWinOrLossForSplit());
                checkStatusWinOrLossSplit();
            } else {
                model.hitStandDoubleOrSplit();

                model.winOrLoss();
                view.getLabelSumCardsPlayerNumber().setText(String.valueOf(model.getPlayerPoints()));
                showDealerCards();
                checkStatusWinOrLoss(model.getWinOrLossValue());
            }
        });
        view.getButtonDouble().setOnAction(actionEvent -> {
            model.setAnwser("Double");
            if (model.getSplitValidation().equals("y")) {
                if (getIsFirstHand()) {
                    addCardView();
                    model.setStatus(3);
                    swapCardsByWinOrLossValue(model.calculateWinOrLossForSplit());
                    checkStatusWinOrLossSplit();
                } else {
                    addCardView();
                    model.setSecondStatus(3);
                    swapCardsByWinOrLossValue(model.calculateWinOrLossForSplit());
                    checkStatusWinOrLossSplit();
                }
            } else {
                addCardView();
            }

        });
        view.getButtonSplit().setOnAction(actionEvent -> {
            model.setSplitValidation("y");
            if (model.getSecondCardPlayer().getNumber() == 1) {
                model.getSecondCardPlayer().setNumber(11);
            }

            checkSplitOption();

            imageViewMakerAndEditor.setImagePlayer(1, new WritableImage(1, 1));
            imageViewMakerAndEditor.setImagePlayerSplit(0, new Image(String.valueOf(model.getPlayerCards2().get(0))));

            updateView();
            view.getButtonSplit().setDisable(true);
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
        if (counterSwapCards <= 3) {
            updateView();
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
        updateView();
    }

    int counter = 0;

    public void checkSplitOption() {
        int number1 = model.getFirstCardPlayer().getNumber();
        int number2 = model.getSecondCardPlayer().getNumber();
        if (number1 == 1) {
            number1 = 11;
        } else if (number2 == 1) {
            number2 = 11;
        }

        if (number1 != number2) {
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

    public void checkStatusWinOrLossSplit() {
        if (model.getWinOrLossValue() != 0 && model.getWinOrLossValue2() != 0) {
            updateView();
            showDealerCards();
            disableButtons();
            checkStatusWinOrLoss(model.getWinOrLossValue());
            System.out.println(model.getWinOrLossValue());
            PauseTransition pause = new PauseTransition(Duration.seconds(4));
            pause.setOnFinished(event -> {
                swapPlayerDecks();
                model.setStatHolder(1);
                updateView();
                checkStatusWinOrLoss(model.getWinOrLossValue2());
                System.out.println(model.getWinOrLossValue2());
            });
            pause.play();
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
            checkStatusWinOrLoss(model.getWinOrLossValue());
        }

    }

    public void checkStatusWinOrLoss(int winOrLossValue) {
        if (winOrLossValue == 1) {
            model.setStatHolder(2);
            model.youWon();
            youHaveWon();
        } else if (winOrLossValue == 2) {
            model.setStatHolder(2);
            model.youLost();
            youHaveLost();
        } else if (winOrLossValue == 3) {
            model.setStatHolder(2);
            model.youDraw();
            youHaveDrawn();
        }
    }

    public void swapPlayerDecks() {
        if (getIsFirstHand()) {
            view.getChildren().remove(view.gethBoxPlayerCards());
            view.getChildren().remove(view.gethBoxPlayerSplitCards());
            view.add(view.gethBoxPlayerSplitCards(), 1, 4);
            view.add(view.gethBoxPlayerCards(), 2, 4);
            view.swapImageSizes();
            updateView();
        } else {
            view.getChildren().remove(view.gethBoxPlayerCards());
            view.getChildren().remove(view.gethBoxPlayerSplitCards());
            view.add(view.gethBoxPlayerSplitCards(), 2, 4);
            view.add(view.gethBoxPlayerCards(), 1, 4);
            view.swapImageSizesBack();
            updateView();
        }
        updateView();
    }

    public void showDealerCards() {
        if (model.getWinOrLossValue() != 0) {
            if (model.getDealerCards().size() > 1) {
                imageViewMakerAndEditor.setImageDealer(0, new Image(String.valueOf(model.getDealerCards().get(1))));
            }
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


    public void youHaveWon() {
        String textAlert = "You have won!!! :)). This is your new balance:" + model.getBalance();
        disableButtons();
        view.setLabelAlertGreen(view.getLabelAlert(), textAlert);
        if (model.getSplitValidation().equals("y")) {
            counterWinLossDraw++;
            if (counterWinLossDraw == 2) {
                restartGame();
            }
        } else {
            restartGame();
        }

    }

    public void youHaveLost() {
        String textAlert = "You have lost  >:(. This is your new balance:" + model.getBalance();
        disableButtons();
        view.setLabelAlertRed(view.getLabelAlert(), textAlert);
        if (model.getSplitValidation().equals("y")) {
            counterWinLossDraw++;
            if (counterWinLossDraw == 2) {
                restartGame();
            }
        } else {
            restartGame();
        }
    }

    public void youHaveDrawn() {

        String textAlert = "You have drawn, -_- .This is your new balance:" + model.getBalance();
        disableButtons();
        view.setLabelAlertRed(view.getLabelAlert(), textAlert);
        if (model.getSplitValidation().equals("y")) {
            counterWinLossDraw++;
            if (counterWinLossDraw == 2) {
                restartGame();
            }
        } else {
            restartGame();
        }
    }

    public void restartGame() {
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            model.resetPlayer();
            refreshView();
        });
        pause.play();
    }

    public void refreshView() {
        BlackJackGameView viewGame = new BlackJackGameView(primaryStage);
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
    }
}