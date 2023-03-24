package be.kdg.view.game;

import be.kdg.model.BlackJackModel;
import be.kdg.model.Card;
import be.kdg.model.Player;
import be.kdg.view.leaderboard.LeaderBoardPresenter;
import be.kdg.view.start.BlackJackStartPresenter;
import be.kdg.view.start.BlackJackStartView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackJackGamePresenter {
    private final BlackJackModel model;
    private final BlackJackGameView view;
    private final ImageViewMakerAndEditor imageViewMakerAndEditor;
    private final Stage primaryStage;
    private int playerNumber;
    private int counter;
    private Player player;
    private final LeaderBoardPresenter leaderboard;

    /**
     * This class is used to create the presenter for the game.
     * It contains methods to update the view and to add eventhandlers.
     */
    public BlackJackGamePresenter(BlackJackGameView view, BlackJackModel model, ImageViewMakerAndEditor imageViewMakerAndEditor, Stage primaryStage) {
        this.model = model;
        this.view = view;
        this.imageViewMakerAndEditor = imageViewMakerAndEditor;
        this.primaryStage = primaryStage;
        this.playerNumber = 0;
        this.leaderboard = new LeaderBoardPresenter();
        this.counter = 0;
        model.makeNewTable();
        view.gethBoxH_S_D_S().setDisable(true);
        addEventHandlers();
        player = model.getPlayers().get(playerNumber);
        updateView(player);
    }

    /**
     * This method is used to update the view after the user takes some action.
     * It updates the labels and the imageviews.
     */

    private void addEventHandlers() {
        view.getButtonExit().setOnAction(actionEvent -> {
            writeAwayPlayers();
            writeAwayLog();

            leaderboard.showLeaderBoard();

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
                String textAlert = "Bet a value greater than 0.";
                view.setLabelAlertRed(view.getLabelAlert(), textAlert);
            } else {
                player.setBetConfirm(true);
                goNextPlayer();
            }
            int playersReady = 0;
            for (Player player : model.getPlayers()) {
                if (player.isBetConfirm()) {
                    playersReady++;
                }
            }
            if (playersReady == model.getPlayers().size()) {
                for (Player player : model.getPlayers()) {
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
                playerIsFinished(player);
            } else {
                player.setAnwser("Hit");
                addCardView(player);
            }
            disableOrEnableBetButtons();
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
                if (!getIsFirstHand(player)) {
                    swapPlayerDecks(player);
                }
                model.hitStandDoubleOrSplit(player);
                model.winOrLoss(player);
                view.getLabelSumCardsPlayerNumber().setText(String.valueOf(player.getPlayerPoints()));
            }
            disableOrEnableBetButtons();
            allPlayersHavePlayed();
            playerIsFinished(player);

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
                playerIsFinished(player);
            } else {
                addCardView(player);
            }
            disableOrEnableBetButtons();
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
            writeAwayPlayers();
            writeAwayLog();
            leaderboard.showLeaderBoard();
        });

    }

    /**
     * This method is used to go to the next player and switch the view to the next player.
     */
    public void goNextPlayer() {
        if (playerNumber < model.getPlayers().size() - 1) {
            playerNumber++;
            player = model.getPlayers().get(playerNumber);

            disableOrEnableBetButtons();
            if (!player.getPlayerCards().isEmpty()) {
                if (!player.getPlayerCards().isEmpty()) {
                    showCurrentPlayer(player);
                }
            }
            updateView(player);
        }
    }

    /**
     * This method is used to go to the previous player and switch the view to the previous player.
     */
    public void goPreviousPlayer() {
        if (playerNumber > 0) {
            playerNumber--;
            player = model.getPlayers().get(playerNumber);

            disableOrEnableBetButtons();
            if (!player.getPlayerCards().isEmpty()) {
                showCurrentPlayer(player);
            }
            updateView(player);
        }
    }

    /**
     * This method is used to look if the player is finished playing. Is his turn is over yes or no.
     *
     * @param player The current player.
     */
    public void playerIsFinished(Player player) {
        if (player.getSplitValidation().equals("y")) {
            if (player.getWinOrLossValue() != 0 && player.getWinOrLossValue2() != 0) {
                goPreviousPlayer();
            }
        } else if (player.getWinOrLossValue() != 0) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), event -> goPreviousPlayer())
            );
            timeline.play();

        }
    }

    /**
     * This method is used to check if all players have played. If they have the game will do its end loop.
     */
    public void allPlayersHavePlayed() {
        //check if all players have played
        int counter = 0;
        for (Player player : model.getPlayers()) {
            if (player.getSplitValidation().equals("y")) {
                if (player.getWinOrLossValue() != 0 && player.getWinOrLossValue2() != 0) {
                    counter++;
                }
            } else if (player.getWinOrLossValue() != 0) {
                counter++;
            }
        }

        //calculate the wait time for each player
        int playerIndex;
        for (Player player : model.getPlayers()) {
            if (player.getPlayerNumber() == model.getPlayers().size() - 1)
            {
                playerIndex = 0;
            }
            else {
                playerIndex = player.getPlayerNumber() + 1;
            }
            if (model.getPlayers().get(playerIndex).getSplitValidation().equals("y")){
                player.setWaitTimeEndGame(12);
            }else{
                player.setWaitTimeEndGame(6);
            }
        }

        //calculate the duration of the timeline
        int duration = 0;
        for (Player player : model.getPlayers()) {
            duration = duration + player.getWaitTimeEndGame();
        }

        //if all players have played show the dealer cards and restart the game
        if (counter == model.getPlayers().size()) {
            showDealerCards();
            disableButtons();
            showAllPlayers();

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(duration + 2), event -> restartGame())
            );
            timeline.play();
        }
    }

    /**
     * This method is used to show every player and if they have won or not. The timeline is used to show the players one by one.
     */
    public void showAllPlayers() {
        int duration = 0;
        model.getPlayers().get(0).setWaitTimeEndGame(1);
        for (Player player: model.getPlayers()) {
            duration = duration + player.getWaitTimeEndGame();
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(duration), event -> {
                            showCurrentPlayer(player);
                            swapPlayerDecks(player);
                            swapPlayerDecksBack();
                            updateView(player);
                            if (player.getSplitValidation().equals("y")) {
                                checkStatusWinOrLossSplit(player.getPlayerNumber());
                            } else {
                                checkStatusWinOrLoss(player.getWinOrLossValue(), player);
                            }
                    })
            );
            timeline.play();
        }
    }

    /**
     * This method is used to put the imageViews back to the right place. After the player has played.
     */
    public void swapPlayerDecksBack() {
        view.getChildren().remove(view.gethBoxPlayerCards());
        view.getChildren().remove(view.gethBoxPlayerSplitCards());
        view.add(view.gethBoxPlayerSplitCards(), 2, 4);
        view.add(view.gethBoxPlayerCards(), 1, 4);
        view.swapImageSizesBack();
    }

    /**
     * This method is used to see if the player has split and if he has won or lost both hands.
     * @param playerIndex the index of the current player
     */
    public void checkStatusWinOrLossSplit(int playerIndex) {
        if (model.getPlayers().get(playerIndex).getWinOrLossValue() != 0 && model.getPlayers().get(playerIndex).getWinOrLossValue2() != 0) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), event -> checkStatusWinOrLoss(model.getPlayers().get(playerIndex).getWinOrLossValue(), model.getPlayers().get(playerIndex))),
                    new KeyFrame(Duration.seconds(5), event -> {
                        swapPlayerDecks(model.getPlayers().get(playerIndex));
                        model.getPlayers().get(playerIndex).setStatHolder(1);
                        updateView(model.getPlayers().get(playerIndex));
                        checkStatusWinOrLoss(model.getPlayers().get(playerIndex).getWinOrLossValue2(), model.getPlayers().get(playerIndex));
                    })
            );

            timeline.play();
        }
    }

    /**
     * This method is used to check if the player has won or lost.
     * @param player The current player.
     */
    public void checkStatusWinOrLoss(int winOrLossValue, Player player) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
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
        }));
        timeline.play();
    }


    /**
     * This method is used to determine if the buttons should be disabled or not depending on the situation.
     */
    public void disableOrEnableBetButtons() {
        if (player.isBetConfirm()) {
            view.gethBoxH_S_D_S().setDisable(true);
            view.gethBoxBetAmounts().setDisable(true);
        } else {
            view.gethBoxH_S_D_S().setDisable(true);
            view.gethBoxBetAmounts().setDisable(false);
        }
        if (!player.getPlayerCards().isEmpty()) {
            view.gethBoxH_S_D_S().setDisable(false);
            view.gethBoxBetAmounts().setDisable(true);
        }
        if (!player.getPlayerCards().isEmpty()) {
            view.getButtonSplit().setDisable(player.getPlayerCards().get(0).getNumber() != player.getPlayerCards().get(1).getNumber());
        }
        if (player.getSplitValidation().equals("y")) {
            if (player.getWinOrLossValue() != 0 && player.getWinOrLossValue2() != 0) {
                view.gethBoxH_S_D_S().setDisable(true);
                view.gethBoxBetAmounts().setDisable(true);
            }
        } else if (player.getWinOrLossValue() != 0) {
            view.gethBoxH_S_D_S().setDisable(true);
            view.gethBoxBetAmounts().setDisable(true);
        }
        if (player.getSplitValidation().equals("y")){
            view.getButtonSplit().setDisable(true);
        }
        view.getButtonDouble().setDisable(player.getPlayerCards().size() >= 3);
    }

    /**
     * This method is used to swap the players decks by checking if the player has won or lost the first deck or second deck.
     * @param winOrLossValue the value of the win or loss of the player (1 = win, 2 = loss, 3 = draw). The value is -1 if the player has not played yet.
     * @param player The current player.
     */
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

    /**
     * This method is used to swap the players decks.
     * @param player The current player.
     */
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


    /**
     * This method is used when the player has the option to split. If he chooses to do so it will split his deck.
     */
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


    /**
     * This method is used to add a card to the players deck.
     * @param player The current player.
     */
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
        } else {
            swapPlayerDecksBack();
            model.hitStandDoubleOrSplit(player);
            int indexImage = 0;

            for (Card card : player.getPlayerCards()) {
                imageViewMakerAndEditor.setImagePlayer(indexImage, new Image(String.valueOf(card)));
                indexImage++;
                updateView(player);
            }
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), event -> model.winOrLoss(player))
            );
            timeline.play();
            updateView(player);
        }


        if (player.getPlayerPoints() >= 21) {
            model.winOrLoss(player);
        }

        disableOrEnableBetButtons();
        allPlayersHavePlayed();
        playerIsFinished(player);
    }

    /**
     * This method is used to show the current player.
     * @param player The current player.
     */
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


    /**
     * This method is used to show the dealers cards.
     */
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

    /**
     * This method is used to update the view when player has won.
     * @param player The current player.
     */
    public void youHaveWon(Player player) {
        String textAlert = "You WIN!!! :)). This is your new balance:" + player.getBalance();
        updateView(player);
        disableButtons();
        view.setLabelAlertGreen(view.getLabelAlert(), textAlert);
    }

    /**
     * This method is used to update the view when player has lost.
     * @param player The current player.
     */
    public void youHaveLost(Player player) {
        String textAlert = "You LOSE >:(. This is your new balance:" + player.getBalance();
        updateView(player);
        disableButtons();
        view.setLabelAlertRed(view.getLabelAlert(), textAlert);
    }

    /**
     * This method is used to update the view when player has drawn.
     * @param player The current player.
     */
    public void youHaveDrawn(Player player) {
        String textAlert = "You DRAW, -_- .This is your new balance:" + player.getBalance();
        updateView(player);
        disableButtons();
        view.setLabelAlertRed(view.getLabelAlert(), textAlert);
    }

    /**
     * This method will restart the game.
     */
    public void restartGame() {
        for (Player player : model.getPlayers()) {
            player.resetPlayer();
        }
        refreshView();
    }

    public void refreshView() {
        BlackJackGameView viewGame = new BlackJackGameView(primaryStage);
        BlackJackGamePresenter presenterGame = new BlackJackGamePresenter(viewGame, model, viewGame.getImageViewMakerAndEditor(), primaryStage);
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

    /**
     * This method will write away the players to a logfile.
     */
    public void writeAwayLog() {
        for (Player player : model.getPlayers()) {
            if (player.getBet() != 0) {
                player.setBalance(player.getBalance() - player.getBet() - player.getBet2());
            }
            String fileName = "src/main/resources/playerLog.txt";
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                bw.write(player + ";LOG: " + LocalDateTime.now());
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method will write away the players to a player file.
     */
    public void writeAwayPlayers() {
        String fileName = "src/main/resources/player.txt";
        for (Player player : model.getPlayers()) {
            File file = new File(fileName);
            List<String> lines = new ArrayList<>();
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    lines.add(scanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            boolean found = false;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(player.getName())) {
                    lines.set(i, player.toString());
                    found = true;
                    break;
                }
            }
            if (!found) {
                lines.add(player.toString());
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateView(Player player) {
        view.getLabelPlayerName().setText(player.getName());
        view.getLabelBalanceNumber().setText(String.valueOf(player.getBalance()));
        view.getLabelBet().setText(String.valueOf(player.getBet()));

        int textValue = player.getStatHolder() == 2 ? player.getPlayerPoints() : player.getPlayerPoints2();
        view.getLabelSumCardsPlayerNumber().setText(String.valueOf(textValue));
    }
}