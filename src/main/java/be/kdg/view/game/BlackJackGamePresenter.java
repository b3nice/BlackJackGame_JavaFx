package be.kdg.view.game;

import be.kdg.model.BlackJackModel;
import be.kdg.model.Card;
import be.kdg.model.Player;
import be.kdg.view.start.BlackJackStartPresenter;
import be.kdg.view.start.BlackJackStartView;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BlackJackGamePresenter {
    private final BlackJackModel model;
    private final BlackJackGameView view;
    private final ImageViewMakerAndEditor imageViewMakerAndEditor;
    private final Stage primaryStage;
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
        model.makeNewTable();
        view.gethBoxH_S_D_S().setDisable(true);
        addEventHandlers();
        player = players.get(indexPlayerHolder);
        updateView(player);
    }


    private void addEventHandlers() {
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
                addCardView(player);

                swapCardsByWinOrLossValue(model.calculateWinOrLossForSplit(player), player);
                playerIsFinished();
            } else {
                player.setAnwser("Hit");
                addCardView(player);
            }
        });
        view.getButtonStand().setOnAction(actionEvent -> {
            player.setAnwser("Stand");
            if (player.getSplitValidation().equals("y")) {
                if (getIsFirstHand(player)) {
                    player.setStatus(2);
                } else {
                    player.setSecondStatus(2);
                }

                model.winOrLoss(player);
                swapCardsByWinOrLossValue(model.calculateWinOrLossForSplit(player), player);
            } else {
                model.hitStandDoubleOrSplit(player);

                model.winOrLoss(player);
                view.getLabelSumCardsPlayerNumber().setText(String.valueOf(player.getPlayerPoints()));
            }
            allPlayersHavePlayed();
            playerIsFinished();

        });
        view.getButtonDouble().setOnAction(actionEvent -> {
            player.setAnwser("Double");
            if (player.getSplitValidation().equals("y")) {
                if (getIsFirstHand(player)) {
                    addCardView(player);
                    player.setStatus(3);
                    swapCardsByWinOrLossValue(model.calculateWinOrLossForSplit(player), player);
                } else {
                    addCardView(player);
                    player.setSecondStatus(3);
                    swapCardsByWinOrLossValue(model.calculateWinOrLossForSplit(player), player);
                }
                playerIsFinished();
            } else {
                addCardView(player);
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
        if (player.getSplitValidation().equals("y")) {
            if (player.getWinOrLossValue() != 0 && player.getWinOrLossValue2() != 0) {
                goPreviousPlayer();
            }
        } else if (player.getWinOrLossValue() != 0) {
            goPreviousPlayer();
        }
    }

    public void allPlayersHavePlayed() {
        int counter = 0;
        for (Player player : players) {
            if (player.getSplitValidation().equals("y")) {
                if (player.getWinOrLossValue() != 0 && player.getWinOrLossValue2() != 0) {
                    counter++;
                }
            } else if (player.getWinOrLossValue() != 0) {
                counter++;
            }
        }

        if (counter == players.size()) {
            showDealerCards();
            disableButtons();
            for (Player player : players) {
                showAllPlayers(player);
            }
            Timer timer1 = new Timer();
            timer1.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        restartGame();
                    });
                }
            }, 30000);
        }
    }

    public void showAllPlayers(Player player) {
        showCurrentPlayer(player);
        updateView(player);
        if (player.getSplitValidation().equals("y")) {
            checkStatusWinOrLossSplit(player.getPlayerNumber());
        } else {
            checkStatusWinOrLoss(player.getWinOrLossValue(),player);
        }
    }

    public void checkStatusWinOrLossSplit(int playerIndex) {
        if (players.get(playerIndex).getWinOrLossValue() != 0 && players.get(playerIndex).getWinOrLossValue2() != 0) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        checkStatusWinOrLoss(players.get(playerIndex).getWinOrLossValue(), players.get(playerIndex));
                    });
                }
            }, 3000);


            Timer timer1 = new Timer();
            timer1.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        swapPlayerDecks(players.get(playerIndex));
                        players.get(playerIndex).setStatHolder(1);
                        updateView(players.get(playerIndex));
                        checkStatusWinOrLoss(players.get(playerIndex).getWinOrLossValue2(), players.get(playerIndex));
                    });
                }
            }, 6000);

        }
    }

    public void checkStatusWinOrLoss(int winOrLossValue, Player player) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (winOrLossValue == 1) {
                        if (getIsFirstHand(player)) {
                            model.youWonFirstHand(player);
                        } else {
                            model.youWonSecondHand(player);
                        }
                        player.setStatHolder(2);
                        youHaveWon(player);
                    } else if (winOrLossValue == 2) {
                        if (getIsFirstHand(player)) {
                            model.youLostFirstHand(player);
                        } else {
                            model.youLostSecondHand(player);
                        }
                        player.setStatHolder(2);
                        youHaveLost(player);
                    } else if (winOrLossValue == 3) {
                        player.setStatHolder(2);
                        model.youDraw(player);
                        youHaveDrawn(player);
                    }
                });
            }
        }, 1000);
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
        if (!player.getPlayerCards().isEmpty()) {
            view.getButtonSplit().setDisable(player.getPlayerCards().get(0).getNumber() != player.getPlayerCards().get(1).getNumber());
        }
    }

    public void swapCardsByWinOrLossValue(int winOrLossValue, Player player) {
        updateView(player);
        if (getIsFirstHand(player)) {
            if (winOrLossValue != -1 && player.getStatus() == 1) {
                swapPlayerDecks(player);
                player.setStatHolder(1);
            } else if (winOrLossValue != -1) {
                swapPlayerDecks(player);
                player.setStatHolder(1);
            }
        } else {
            if (winOrLossValue != -1 && player.getSecondStatus() == 1) {
                swapPlayerDecks(player);
                player.setStatHolder(2);
            } else if (winOrLossValue != -1) {
                swapPlayerDecks(player);
                player.setStatHolder(2);
            }
        }
        updateView(player);
    }

    public void swapPlayerDecks(Player player) {
        if (getIsFirstHand(player)) {
            view.getChildren().remove(view.gethBoxPlayerCards());
            view.getChildren().remove(view.gethBoxPlayerSplitCards());
            view.add(view.gethBoxPlayerSplitCards(), 1, 4);
            view.add(view.gethBoxPlayerCards(), 2, 4);
            view.swapImageSizes();
        } else {
            view.getChildren().remove(view.gethBoxPlayerCards());
            view.getChildren().remove(view.gethBoxPlayerSplitCards());
            view.add(view.gethBoxPlayerSplitCards(), 2, 4);
            view.add(view.gethBoxPlayerCards(), 1, 4);
            view.swapImageSizesBack();
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


    public void addCardView(Player player) {
        if (player.getSplitValidation().equals("y")) {
            updateView(player);
            model.splitGame(player);

            if (getIsFirstHand(player)) {
                imageViewMakerAndEditor.setImagePlayer(player.getIndexImageSplit1(), new Image(String.valueOf(player.getPlayerCards().get(player.getIndexImageSplit1()))));
                player.setIndexImageSplit1(player.getIndexImageSplit1() + 1);
            } else {
                imageViewMakerAndEditor.setImagePlayerSplit(player.getIndexImageSplit2(), new Image(String.valueOf(player.getPlayerCards2().get(player.getIndexImageSplit2()))));
                player.setIndexImageSplit2(player.getIndexImageSplit2() + 1);
            }

            updateView(player);
            model.winOrLoss(player);
        } else {
            model.hitStandDoubleOrSplit(player);
            int indexImage = 0;

            for (Card card : player.getPlayerCards()) {
                imageViewMakerAndEditor.setImagePlayer(indexImage, new Image(String.valueOf(card)));
                indexImage++;
                updateView(player);
            }

            model.winOrLoss(player);
            updateView(player);
        }
        allPlayersHavePlayed();
        playerIsFinished();
    }


    public void showCurrentPlayer(Player player) {
        int indexImage = 0;
        if (player.getSplitValidation().equals("y")) {
            for (int i = 0; i <= 5; i++) {
                imageViewMakerAndEditor.setImagePlayer(indexImage, new WritableImage(1, 1));
                imageViewMakerAndEditor.setImagePlayerSplit(indexImage, new WritableImage(1, 1));
                indexImage++;
            }
            indexImage = 0;
            for (Card card : player.getPlayerCards()) {
                imageViewMakerAndEditor.setImagePlayer(indexImage, new Image(String.valueOf(card)));
                indexImage++;
                updateView(player);
            }
            indexImage = 0;
            for (Card card : player.getPlayerCards2()) {
                imageViewMakerAndEditor.setImagePlayerSplit(indexImage, new Image(String.valueOf(card)));
                indexImage++;
                updateView(player);
            }
        } else {
            for (int i = 0; i <= 5; i++) {
                imageViewMakerAndEditor.setImagePlayer(indexImage, new WritableImage(1, 1));
                imageViewMakerAndEditor.setImagePlayerSplit(indexImage, new WritableImage(1, 1));
                indexImage++;
            }
            indexImage = 0;
            for (Card card : player.getPlayerCards()) {
                imageViewMakerAndEditor.setImagePlayer(indexImage, new Image(String.valueOf(card)));
                indexImage++;
                updateView(player);
            }
        }
    }


    public void showDealerCards() {
        if (player.getWinOrLossValue() != 0) {
            if (model.getDealerCards().size() > 1) {
                imageViewMakerAndEditor.setImageDealer(0, new Image(String.valueOf(model.getDealerCards().get(1))));
            }
            for (int i = 2; i < model.getDealerCards().size(); i++) {
                imageViewMakerAndEditor.setImageDealer(i, new Image(String.valueOf(model.getDealerCards().get(i))));
            }
        }
        view.getLabelSumCardsDealerNumber().setText(String.valueOf(model.getDealerPoints()));
    }


    public void youHaveWon(Player player) {
        String textAlert = "You have won!!! :)). This is your new balance:" + player.getBalance();
        disableButtons();
        view.setLabelAlertGreen(view.getLabelAlert(), textAlert);
    }

    public void youHaveLost(Player player) {
        String textAlert = "You have lost  >:(. This is your new balance:" + player.getBalance();
        disableButtons();
        view.setLabelAlertRed(view.getLabelAlert(), textAlert);
    }

    public void youHaveDrawn(Player player) {
        String textAlert = "You have drawn, -_- .This is your new balance:" + player.getBalance();
        disableButtons();
        view.setLabelAlertRed(view.getLabelAlert(), textAlert);
    }

    public void restartGame() {
        for (Player player : players) {
            player.resetPlayer();
        }
        refreshView();
    }

    public void refreshView() {
        BlackJackGameView viewGame = new BlackJackGameView(primaryStage);
        BlackJackGamePresenter presenterGame = new BlackJackGamePresenter(viewGame, model, viewGame.getImageViewMakerAndEditor(), primaryStage, players);
        view.getScene().setRoot(viewGame);
        model.makeNewTable();
    }

    public void disableButtons() {
        view.getButtonHit().setDisable(true);
        view.getButtonStand().setDisable(true);
        view.getButtonDouble().setDisable(true);
        view.getButtonSplit().setDisable(true);
    }

    public boolean getIsFirstHand(Player player) {
        return player.getStatHolder() == 2;
    }

    public void updateView(Player player) {
        view.getLabelPlayerName().setText(player.getName());
        view.getLabelBalanceNumber().setText(String.valueOf(player.getBalance()));
        view.getLabelBet().setText(String.valueOf(player.getBet()));

        int textValue = player.getStatHolder() == 2 ? player.getPlayerPoints() : player.getPlayerPoints2();
        view.getLabelSumCardsPlayerNumber().setText(String.valueOf(textValue));
    }

    public void pause(double seconds, Runnable onFinished) {
        PauseTransition pause = new PauseTransition(Duration.seconds(seconds));
        pause.setOnFinished(event -> onFinished.run());
        pause.play();
    }
}