package be.kdg.view.game;

import be.kdg.model.BlackJackModel;
import be.kdg.model.Card;
import be.kdg.model.Player;
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
import java.util.*;

public class BlackJackGamePresenter {
    private final BlackJackModel model;
    private final BlackJackGameView view;
    private final ImageViewMakerAndEditor imageViewMakerAndEditor;
    private final Stage primaryStage;
    private int playerNumber;
    private Player player;

    public BlackJackGamePresenter(BlackJackGameView view, BlackJackModel model, ImageViewMakerAndEditor imageViewMakerAndEditor, Stage primaryStage) {
        this.model = model;
        this.view = view;
        this.imageViewMakerAndEditor = imageViewMakerAndEditor;
        this.primaryStage = primaryStage;
        this.playerNumber = 0;
        model.makeNewTable();
        view.gethBoxH_S_D_S().setDisable(true);
        addEventHandlers();
        player = model.getPlayers().get(playerNumber);
        updateView(player);
    }

    private void addEventHandlers() {
        view.getButtonExit().setOnAction(actionEvent -> {
            writeAwayPlayers();
            writeAwayLog();

            showLeaderBoard();

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
                if (!getIsFirstHand(player)) {
                    swapPlayerDecks(player);
                }
            } else {
                model.hitStandDoubleOrSplit(player);
                model.winOrLoss(player);
                view.getLabelSumCardsPlayerNumber().setText(String.valueOf(player.getPlayerPoints()));
            }
            disableOrEnableBetButtons();
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
            writeAwayPlayers();
            writeAwayLog();
        });

    }

    public void goNextPlayer() {
        if (playerNumber < model.getPlayers().size() - 1) {
            System.out.println(model.getPlayers().size());
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

    public void goPreviousPlayer() {
        if (playerNumber > 0) {
            System.out.println(model.getPlayers().size());
            playerNumber--;
            player = model.getPlayers().get(playerNumber);

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
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), event -> goPreviousPlayer())
            );
            timeline.play();

        }
    }

    public void allPlayersHavePlayed() {
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

        if (counter == model.getPlayers().size()) {
            showDealerCards();
            disableButtons();
            showAllPlayers();
            int duration = model.getPlayers().size() * 12;
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(duration), event -> restartGame())
            );
            timeline.play();
        }
    }

    public void showAllPlayers() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> {
                    if (model.getPlayers().size() > 0) {
                        showCurrentPlayer(model.getPlayers().get(0));
                        swapPlayerDecks(model.getPlayers().get(0));
                        swapPlayerDecksBack();
                        updateView(model.getPlayers().get(0));
                        if (model.getPlayers().get(0).getSplitValidation().equals("y")) {
                            checkStatusWinOrLossSplit(model.getPlayers().get(0).getPlayerNumber());
                        } else {
                            checkStatusWinOrLoss(model.getPlayers().get(0).getWinOrLossValue(), model.getPlayers().get(0));
                        }
                    }
                }),
                new KeyFrame(Duration.seconds(12), event -> {
                    if (model.getPlayers().size() > 1) {
                        showCurrentPlayer(model.getPlayers().get(1));
                        swapPlayerDecks(model.getPlayers().get(1));
                        swapPlayerDecksBack();
                        updateView(model.getPlayers().get(1));
                        if (model.getPlayers().get(1).getSplitValidation().equals("y")) {
                            checkStatusWinOrLossSplit(model.getPlayers().get(1).getPlayerNumber());
                        } else {
                            checkStatusWinOrLoss(model.getPlayers().get(1).getWinOrLossValue(), model.getPlayers().get(1));
                        }
                    }
                }),
                new KeyFrame(Duration.seconds(27), event -> {
                    if (model.getPlayers().size() > 2) {
                        showCurrentPlayer(model.getPlayers().get(2));
                        swapPlayerDecks(model.getPlayers().get(2));
                        swapPlayerDecksBack();
                        updateView(model.getPlayers().get(2));
                        if (model.getPlayers().get(2).getSplitValidation().equals("y")) {
                            checkStatusWinOrLossSplit(model.getPlayers().get(2).getPlayerNumber());
                        } else {
                            checkStatusWinOrLoss(model.getPlayers().get(2).getWinOrLossValue(), model.getPlayers().get(2));
                        }
                    }
                }),
                new KeyFrame(Duration.seconds(42), event -> {
                    if (model.getPlayers().size() > 3) {
                        showCurrentPlayer(model.getPlayers().get(3));
                        swapPlayerDecks(model.getPlayers().get(3));
                        swapPlayerDecksBack();
                        updateView(model.getPlayers().get(3));
                        if (model.getPlayers().get(3).getSplitValidation().equals("y")) {
                            checkStatusWinOrLossSplit(model.getPlayers().get(3).getPlayerNumber());
                        } else {
                            checkStatusWinOrLoss(model.getPlayers().get(3).getWinOrLossValue(), model.getPlayers().get(3));
                        }
                    }
                }),
                new KeyFrame(Duration.seconds(57), event -> {
                    if (model.getPlayers().size() > 4) {
                        showCurrentPlayer(model.getPlayers().get(4));
                        swapPlayerDecks(model.getPlayers().get(4));
                        swapPlayerDecksBack();
                        updateView(model.getPlayers().get(4));
                        if (model.getPlayers().get(4).getSplitValidation().equals("y")) {
                            checkStatusWinOrLossSplit(model.getPlayers().get(4).getPlayerNumber());
                        } else {
                            checkStatusWinOrLoss(model.getPlayers().get(4).getWinOrLossValue(), model.getPlayers().get(4));
                        }
                    }
                })
        );
        timeline.play();
    }

    public void swapPlayerDecksBack() {
        view.getChildren().remove(view.gethBoxPlayerCards());
        view.getChildren().remove(view.gethBoxPlayerSplitCards());
        view.add(view.gethBoxPlayerSplitCards(), 2, 4);
        view.add(view.gethBoxPlayerCards(), 1, 4);
        view.swapImageSizesBack();
    }

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

    public void checkStatusWinOrLoss(int winOrLossValue, Player player) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
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

            swapPlayerDecks(player);
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
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), event -> model.winOrLoss(player))
            );
            timeline.play();
            model.winOrLoss(player);
            updateView(player);
        }
        disableOrEnableBetButtons();
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
        String textAlert = "You have a drawn, -_- .This is your new balance:" + player.getBalance();
        disableButtons();
        view.setLabelAlertRed(view.getLabelAlert(), textAlert);
    }

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

    public void calculateLeaderBoard(){
        String fileName = "src/main/resources/player.txt";
        ArrayList<Player> arrayListPlayers = new ArrayList<>();
        String lineFileTxt;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((lineFileTxt = br.readLine()) != null) {
                arrayListPlayers.add(new Player(lineFileTxt.substring(0, lineFileTxt.indexOf(',')), Integer.parseInt(lineFileTxt.substring(lineFileTxt.indexOf(',') + 1)),0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        arrayListPlayers.sort(new Comparator<Player>() {
            @Override
            public int compare(Player player1, Player player2) {
                return player2.getBalance() - player1.getBalance();
            }
        });

        System.out.println(arrayListPlayers);

    }

    public void showLeaderBoard(){
        calculateLeaderBoard();

    }


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