package be.kdg.view.game;

import be.kdg.model.BlackJackModel;
import be.kdg.model.Card;
import be.kdg.model.Player;
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
import java.util.ArrayList;

public class BlackJackGamePresenter {
    private final BlackJackModel model;
    private final BlackJackGameView view;
    private final ImageViewMakerAndEditor imageViewMakerAndEditor;
    private final Stage primaryStage;
    private int indexImageSplit1;
    private int indexImageSplit2;
    private int counterSwapCards;
    private int counterWinLossDraw;
    private final ArrayList<Player> players;
    private int indexPlayerHolder;
    private Player player;

    public BlackJackGamePresenter(BlackJackGameView view, BlackJackModel model, ImageViewMakerAndEditor imageViewMakerAndEditor, Stage primaryStage, ArrayList<Player> players) {
        this.model = model;
        this.view = view;
        this.imageViewMakerAndEditor = imageViewMakerAndEditor;
        this.primaryStage = primaryStage;
        this.players = players;
        indexPlayerHolder = 0;
        indexImageSplit1 = 1;
        indexImageSplit2 = 1;
        counterSwapCards = 0;
        counterWinLossDraw = 0;
        model.makeNewTable();
        view.gethBoxH_S_D_S().setDisable(true);
        addEventHandlers();
        player = players.get(indexPlayerHolder);
        updateView(player);
    }


    private void addEventHandlers() {
      /*  view.getButtonNextPlayer().setOnAction(actionEvent -> {
            if (indexPlayerHolder < players.size() - 1) {
                System.out.println(players.size());
                indexPlayerHolder++;
                player = players.get(indexPlayerHolder);

                disableOrEnableBetButtons();

                if (!player.getPlayerCards().isEmpty()) {
                    int indexImage = 0;
                    for (Card card : player.getPlayerCards()) {
                        imageViewMakerAndEditor.setImagePlayer(indexImage, new Image(String.valueOf(player.getPlayerCards().get(indexImage))));
                        indexImage++;
                        updateView();
                    }
                }

                updateView();
            } else {
                String textAlert = "There is no next player!!!";
                view.setLabelAlertRed(view.getLabelAlert(), textAlert);
            }
        });


        view.getButtonPrevPlayer().setOnAction(actionEvent -> {
            if (indexPlayerHolder > 0) {
                System.out.println(players.size());
                indexPlayerHolder--;
                player = players.get(indexPlayerHolder);

                disableOrEnableBetButtons();

                if (!player.getPlayerCards().isEmpty()) {
                    int indexImage = 0;
                    for (Card card : player.getPlayerCards()) {
                        imageViewMakerAndEditor.setImagePlayer(indexImage, new Image(String.valueOf(player.getPlayerCards().get(indexImage))));
                        indexImage++;
                        updateView();
                    }
                }

                updateView();
            } else {
                String textAlert = "There is no previous player!!!";
                view.setLabelAlertRed(view.getLabelAlert(), textAlert);
            }
        });
        */
        view.getButtonExit().setOnAction(actionEvent -> {
            for (Player player : players) {
                String fileName = "src/main/resources/player.txt";
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                    bw.write(player.toString());
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            BlackJackStartView viewStart = new BlackJackStartView(primaryStage);
            BlackJackStartPresenter presenterGame = new BlackJackStartPresenter(viewStart, primaryStage);
            view.getScene().setRoot(viewStart);
        });


        view.getButtonBet5().setOnAction(actionEvent -> {
            player.setBet(player.getBet() + 5);
            view.getLabelBet().setText(String.valueOf(player.getBet()));
        });

        view.getButtonBet10().setOnAction(actionEvent -> {
            player.setBet(player.getBet() + 10);
            view.getLabelBet().setText(String.valueOf(player.getBet()));
        });
        view.getButtonBet20().setOnAction(actionEvent -> {
            player.setBet(player.getBet() + 20);
            view.getLabelBet().setText(String.valueOf(player.getBet()));
        });
        view.getButtonBet50().setOnAction(actionEvent -> {
            player.setBet(player.getBet() + 50);
            view.getLabelBet().setText(String.valueOf(player.getBet()));
        });
        view.getButtonBetClear().setOnAction(actionEvent -> {
            int value = 0;
            view.getLabelBet().setText(String.valueOf(value));
            player.setBet(value);
        });
        view.getButtonBet().setOnAction(actionEvent -> {
            if (player.getBet() == 0) {
                String textAlert = "You cannot bet nothing, please bet an amount.";
                view.setLabelAlertRed(view.getLabelAlert(), textAlert);
            } else {
                player.setBetConfirm(true);
                goNextPlayer();
            }
            int playersReady = 0;
            for (Player player : players) {
                if (player.isBetConfirm()) {
                    playersReady++;
                }
            }
            if (playersReady == players.size()) {
                for (Player player : players) {
                    model.startGame(player);
                }
                model.dealDealerCards(player);
                imageViewMakerAndEditor.setImagePlayer(0, new Image(String.valueOf(player.getFirstCardPlayer())));
                imageViewMakerAndEditor.setImagePlayer(1, new Image(String.valueOf(player.getSecondCardPlayer())));

                imageViewMakerAndEditor.setImageDealer(0, new Image("/RedCardBack.PNG"));
                imageViewMakerAndEditor.setImageDealer(1, new Image(String.valueOf(model.getDealerCards().get(0))));
                view.getLabelSumCardsDealerNumber().setText(String.valueOf(model.getDealerPoints()));
                checkSplitOption();
                disableOrEnableBetButtons();
                updateView(player);
            }
        });
        view.getButtonHit().setOnAction(actionEvent -> {
            if (player.getSplitValidation().equals("y")) {
                player.setAnwser("Hit");
                addCardView();

                swapCardsByWinOrLossValue(model.calculateWinOrLossForSplit(player));
                checkStatusWinOrLossSplit();
            } else {
                player.setAnwser("Hit");
                addCardView();
            }
        });
        view.getButtonStand().setOnAction(actionEvent -> {
            player.setAnwser("Stand");
            if (player.getSplitValidation().equals("y")) {
                if (getIsFirstHand()) {
                    player.setStatus(2);
                } else {
                    player.setSecondStatus(2);
                }

                swapCardsByWinOrLossValue(model.calculateWinOrLossForSplit(player));
                checkStatusWinOrLossSplit();
            } else {
                model.hitStandDoubleOrSplit(player);

                model.winOrLoss(player);
                view.getLabelSumCardsPlayerNumber().setText(String.valueOf(player.getPlayerPoints()));
                allPlayersHavePlayed();
                playerIsFinished();
            }
        });
        view.getButtonDouble().setOnAction(actionEvent -> {
            player.setAnwser("Double");
            if (player.getSplitValidation().equals("y")) {
                if (getIsFirstHand()) {
                    addCardView();
                    player.setStatus(3);
                    swapCardsByWinOrLossValue(model.calculateWinOrLossForSplit(player));
                    checkStatusWinOrLossSplit();
                } else {
                    addCardView();
                    player.setSecondStatus(3);
                    swapCardsByWinOrLossValue(model.calculateWinOrLossForSplit(player));
                    checkStatusWinOrLossSplit();
                }
            } else {
                addCardView();
            }

        });
        view.getButtonSplit().setOnAction(actionEvent -> {
            player.setSplitValidation("y");
            if (player.getSecondCardPlayer().getNumber() == 1) {
                player.getSecondCardPlayer().setNumber(11);
            }

            checkSplitOption();

            imageViewMakerAndEditor.setImagePlayer(1, new WritableImage(1, 1));
            imageViewMakerAndEditor.setImagePlayerSplit(0, new Image(String.valueOf(player.getPlayerCards2().get(0))));

            updateView(player);
            view.getButtonSplit().setDisable(true);
        });
        primaryStage.setOnCloseRequest(event -> {
            for (Player player : players) {
                String fileName = "src/main/resources/player.txt";
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                    bw.write(player.toString());
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void goNextPlayer() {
        if (indexPlayerHolder < players.size() - 1) {
            System.out.println(players.size());
            indexPlayerHolder++;
            player = players.get(indexPlayerHolder);

            disableOrEnableBetButtons();
            if (!player.getPlayerCards().isEmpty()) {
                if (!player.getPlayerCards().isEmpty()) {
                    showCurrentPlayer(player);
                }
            }
            updateView(player);
        }
    }

    public void goPreviousPlayer() {
        if (indexPlayerHolder > 0) {
            System.out.println(players.size());
            indexPlayerHolder--;
            player = players.get(indexPlayerHolder);

            disableOrEnableBetButtons();
            if (!player.getPlayerCards().isEmpty()) {
                showCurrentPlayer(player);
            }
            updateView(player);
        }
    }

    public void playerIsFinished() {
        if (player.getWinOrLossValue() != 0) {
            goPreviousPlayer();
        }
    }

    public void allPlayersHavePlayed() {
        int counter = 0;
        for (Player player : players) {
            if (player.getWinOrLossValue() != 0) {
                counter++;
            }
        }
        System.out.println(counter);
        System.out.println(players.size() + " +++++++");
        if (counter == players.size()) {
            showDealerCards();
            for (Player player : players) {
                showCurrentPlayer(player);
                checkStatusWinOrLoss(player.getWinOrLossValue(), player);
            }
            conditionRestartGame();
        }
    }

    public void disableOrEnableBetButtons() {
        if (player.isBetConfirm()) {
            view.gethBoxH_S_D_S().setDisable(true);
            view.gethBoxBetAmounts().setDisable(true);
            view.getButtonExit().setDisable(true);
        } else {
            view.gethBoxH_S_D_S().setDisable(true);
            view.gethBoxBetAmounts().setDisable(false);
            view.getButtonExit().setDisable(true);
        }
        if (!player.getPlayerCards().isEmpty()) {
            view.gethBoxH_S_D_S().setDisable(false);
            view.gethBoxBetAmounts().setDisable(true);
            view.getButtonExit().setDisable(true);
        }
    }

    public void swapCardsByWinOrLossValue(int winOrLossValue) {
        if (counterSwapCards <= 3) {
            updateView(player);
            if (getIsFirstHand()) {
                if (winOrLossValue != -1 && player.getStatus() == 1) {
                    counterSwapCards++;
                    swapPlayerDecks();
                    player.setStatHolder(1);
                } else if (winOrLossValue != -1) {
                    counterSwapCards++;
                    swapPlayerDecks();
                    player.setStatHolder(1);
                }
            } else {
                if (winOrLossValue != -1 && player.getSecondStatus() == 1) {
                    counterSwapCards++;
                    swapPlayerDecks();
                    player.setStatHolder(2);
                } else if (winOrLossValue != -1) {
                    counterSwapCards++;
                    swapPlayerDecks();
                    player.setStatHolder(2);
                }
            }
        }
        updateView(player);
    }

    int counter = 0;

    public void checkSplitOption() {
        int number1 = player.getFirstCardPlayer().getNumber();
        int number2 = player.getSecondCardPlayer().getNumber();
        if (number1 == 1) {
            number1 = 11;
        } else if (number2 == 1) {
            number2 = 11;
        }

        if (number1 != number2) {
            view.getButtonSplit().setDisable(true);
        }
        if (counter == 0) {
            model.splitOption(player);
            counter++;
        }
        if (player.getSplitValidation().equals("y")) {
            model.splitOption(player);
        }
    }

    public void checkStatusWinOrLossSplit() {
        if (player.getWinOrLossValue() != 0 && player.getWinOrLossValue2() != 0) {
            updateView(player);
            showDealerCards();
            disableButtons();
            checkStatusWinOrLoss(player.getWinOrLossValue(), player);
            System.out.println(player.getWinOrLossValue());
            PauseTransition pause = new PauseTransition(Duration.seconds(4));
            pause.setOnFinished(event -> {
                swapPlayerDecks();
                player.setStatHolder(1);
                updateView(player);
                checkStatusWinOrLoss(player.getWinOrLossValue2(), player);
                System.out.println(player.getWinOrLossValue2());
            });
            pause.play();
        }
    }

    public void addCardView() {
        if (player.getSplitValidation().equals("y")) {
            updateView(player);
            model.splitGame(player);

            if (getIsFirstHand()) {
                imageViewMakerAndEditor.setImagePlayer(indexImageSplit1, new Image(String.valueOf(player.getPlayerCards().get(indexImageSplit1))));
                indexImageSplit1++;
            } else {
                imageViewMakerAndEditor.setImagePlayerSplit(indexImageSplit2, new Image(String.valueOf(player.getPlayerCards2().get(indexImageSplit2))));
                indexImageSplit2++;
            }
            updateView(player);

        } else {
            model.hitStandDoubleOrSplit(player);
            int indexImage = 0;

            for (Card card : player.getPlayerCards()) {
                imageViewMakerAndEditor.setImagePlayer(indexImage, new Image(String.valueOf(player.getPlayerCards().get(indexImage))));
                indexImage++;
                updateView(player);
            }

            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.play();

            model.winOrLoss(player);
            allPlayersHavePlayed();
            playerIsFinished();
        }

    }

    public void checkStatusWinOrLoss(int winOrLossValue, Player player) {
        if (winOrLossValue == 1) {
            player.setStatHolder(2);
            model.youWon(player);
            youHaveWon(player);
        } else if (winOrLossValue == 2) {
            player.setStatHolder(2);
            model.youLost(player);
            youHaveLost(player);
        } else if (winOrLossValue == 3) {
            player.setStatHolder(2);
            model.youDraw(player);
            youHaveDrawn(player);
        }
    }

    public void showCurrentPlayer(Player player) {
        int indexImage = 0;
        for (int i = 0; i <= 5; i++) {
            imageViewMakerAndEditor.setImagePlayer(indexImage, new WritableImage(1, 1));
            indexImage++;
        }
        indexImage = 0;
        for (Card card : player.getPlayerCards()) {
            imageViewMakerAndEditor.setImagePlayer(indexImage, new Image(String.valueOf(card)));
            indexImage++;
            updateView(player);
        }
    }

    public void swapPlayerDecks() {
        if (getIsFirstHand()) {
            view.getChildren().remove(view.gethBoxPlayerCards());
            view.getChildren().remove(view.gethBoxPlayerSplitCards());
            view.add(view.gethBoxPlayerSplitCards(), 1, 4);
            view.add(view.gethBoxPlayerCards(), 2, 4);
            view.swapImageSizes();
            updateView(player);
        } else {
            view.getChildren().remove(view.gethBoxPlayerCards());
            view.getChildren().remove(view.gethBoxPlayerSplitCards());
            view.add(view.gethBoxPlayerSplitCards(), 2, 4);
            view.add(view.gethBoxPlayerCards(), 1, 4);
            view.swapImageSizesBack();
            updateView(player);
        }
        updateView(player);
    }

    public void showDealerCards() {
        if (player.getWinOrLossValue() != 0) {
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


    public void youHaveWon(Player player) {
        String textAlert = "You have won!!! :)). This is your new balance:" + player.getBalance();
        disableButtons();
        view.setLabelAlertGreen(view.getLabelAlert(), textAlert);
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {

        });
        pause.play();
    }

    public void youHaveLost(Player player) {
        String textAlert = "You have lost  >:(. This is your new balance:" + player.getBalance();
        disableButtons();
        view.setLabelAlertRed(view.getLabelAlert(), textAlert);
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {

        });
        pause.play();
    }

    public void youHaveDrawn(Player player) {
        String textAlert = "You have drawn, -_- .This is your new balance:" + player.getBalance();
        disableButtons();
        view.setLabelAlertRed(view.getLabelAlert(), textAlert);
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> {

        });
        pause.play();
    }

    public void conditionRestartGame() {
        if (player.getSplitValidation().equals("y")) {
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
            for (Player player : players) {
                player.resetPlayer();
            }
            refreshView();
        });
        pause.play();
    }

    public void refreshView() {
        BlackJackGameView viewGame = new BlackJackGameView(primaryStage);
        BlackJackGamePresenter presenterGame = new BlackJackGamePresenter(viewGame, model, viewGame.getImageViewMakerAndEditor(), primaryStage, players);
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
        return player.getStatHolder() == 2;
    }

    public void updateView(Player player) {
        view.getLabelPlayerName().setText(player.getName());
        view.getLabelBalanceNumber().setText(String.valueOf(player.getBalance()));
        view.getLabelBet().setText(String.valueOf(player.getBet()));

        int textValue = player.getStatHolder() == 2 ? player.getPlayerPoints() : player.getPlayerPoints2();
        view.getLabelSumCardsPlayerNumber().setText(String.valueOf(textValue));
    }
}