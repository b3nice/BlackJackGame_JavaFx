package be.kdg.model;

import java.util.ArrayList;

public class Table {

    private final Player player;
    private final Deck deck;

    public Table(Player player) {
        this.player = player;
        this.deck = new Deck();
    }

    private int statHolder = 2;

    public int getStatHolder() {
        return statHolder;
    }

    public void setStatHolder(int statHolder) {
        this.statHolder = statHolder;
    }

    private Card firstCardPlayer;

    public Card getFirstCardPlayer() {
        return firstCardPlayer;
    }

    public void setFirstCardPlayer(Card firstCardPlayer) {
        this.firstCardPlayer = firstCardPlayer;
    }


    private Card secondCardPlayer;

    public Card getSecondCardPlayer() {
        return secondCardPlayer;
    }

    public void setSecondCardPlayer(Card secondCardPlayer) {
        this.secondCardPlayer = secondCardPlayer;
    }


    private int playerPoints;

    public int getPlayerPoints() {
        return playerPoints;
    }

    public void setPlayerPoints(int playerPoints) {
        this.playerPoints = playerPoints;
    }

    private int playerPoints2;

    public int getPlayerPoints2() {
        return playerPoints2;
    }

    public void setPlayerPoints2(int playerPoints2) {
        this.playerPoints2 = playerPoints2;
    }

    private int bet;

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    private int bet2;

    public int getBet2() {
        return bet2;
    }

    public void setBet2(int bet2) {
        this.bet2 = bet2;
    }

    private ArrayList<Card> playerCards = new ArrayList<>();

    public ArrayList<Card> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(ArrayList<Card> playerCards) {
        this.playerCards = playerCards;
    }


    private ArrayList<Card> playerCards2 = new ArrayList<>();

    public ArrayList<Card> getPlayerCards2() {
        return playerCards2;
    }

    public void setPlayerCards2(ArrayList<Card> playerCards2) {
        this.playerCards2 = playerCards2;
    }

    private int dealerPoints;

    public int getDealerPoints() {
        return dealerPoints;
    }

    public void setDealerPoints(int dealerPoints) {
        this.dealerPoints = dealerPoints;
    }


    private ArrayList<Card> dealerCards = new ArrayList<>();

    public ArrayList<Card> getDealerCards() {
        return dealerCards;
    }

    public void setDealerCards(ArrayList<Card> dealerCards) {
        this.dealerCards = dealerCards;
    }

    private String anwser;

    public String getAnwser() {
        return anwser;
    }

    public void setAnwser(String anwser) {
        this.anwser = anwser;
    }

    private int winOrLossValue = 0;

    public int getWinOrLossValue() {
        return winOrLossValue;
    }

    private int winOrLossValue2 = 0;

    public int getWinOrLossValue2() {
        return winOrLossValue2;
    }

    private String splitValidation;

    public String getSplitValidation() {
        return splitValidation;
    }

    public void setSplitValidation(String splitValidation) {
        this.splitValidation = splitValidation;
    }

    public void dealCards() {
        dealerCards.clear();
        splitValidation = "n";

        System.out.println("[" + deck.getDeckCards().get(0) + "]");
        firstCardPlayer = deck.getDeckCards().get(0);
        deck.takeTopCard();

        System.out.println("[" + getFirstCardPlayer() + "] [" + deck.getDeckCards().get(0) + "]");
        secondCardPlayer = deck.getDeckCards().get(0);
        deck.takeTopCard();

        System.out.println("Dealer" + ":");
        aceChecker(deck.getDeckCards().get(0));
        dealerCards.add(deck.getDeckCards().get(0));
        deck.takeTopCard();
        System.out.println("[???] " + dealerCards);

        playerPoints = (calculateTotalPoints(firstCardPlayer, secondCardPlayer));
        dealerPoints = (calculateTotalPoints(dealerCards));
    }

    //De dealer draait de kaarten pas aan het einde en speelt zijn kaarten nadat de player stand of verloren is. zie online blackjack////


    public void splitOption() {
        if (firstCardPlayer.getNumber() == secondCardPlayer.getNumber()) {
            if (splitValidation.equals("y")) {
                setBet2(bet);
                playerCards.remove(1);
                playerCards2.add(0, secondCardPlayer);
                System.out.println("You split, your second deck is: " + playerCards2);
                updatePoints();
            } else {
                playerCards.add(0, firstCardPlayer);
                playerCards.add(1, secondCardPlayer);
            }
        } else {
            playerCards.add(0, firstCardPlayer);
            playerCards.add(1, secondCardPlayer);
        }
    }

    int status = 1;
    int secondStatus = 1;

    public void hitStandDoubleOrSplit() {
        updatePoints();

        int hand1 = 1;

        checkHitStandOrDouble(hand1);
        anwser = " ";
    }


    public void splitGame() {
        int hand1 = 1;
        int hand2 = 2;

        if (statHolder == 2) {
            System.out.println("Do you want to Hit, Stand or Double for your first deck: ");
            checkHitStandOrDouble(hand1);
        } else {
            System.out.println("Do you want to Hit, Stand or Double for your second deck: ");
            switch (anwser) {
                case "Hit" -> {
                    hit(hand2);
                    secondStatus = 1;
                }
                case "Stand" -> {

                    updatePoints();
                    System.out.println("you stand");

                    secondStatus = 2;
                }
                case "Double" -> {
                    doubleBet(hand2);
                    secondStatus = 3;
                }
            }
        }
        updatePoints();
    }

    public void checkHitStandOrDouble(int hand1) {
        switch (anwser) {
            case "Hit" -> {
                hit(hand1);
                status = 1;
            }
            case "Stand" -> {
                updatePoints();
                System.out.println("you stand");
                status = 2;
            }
            case "Double" -> {
                doubleBet(hand1);
                status = 3;
            }
        }
    }


    public void winOrLoss() {
        updatePoints();
        if (splitValidation.equals("y")) {
            if (statHolder == 2) {
                statHolder = 1;
            } else {
                winOrLossValue = calculateWinOrLoss(playerPoints, status);
                winOrLossValue2 = calculateWinOrLoss(playerPoints2, secondStatus);
            }
        } else if (status != 1 || playerPoints > 21 || dealerPoints > 21 || playerPoints == 21) {
            winOrLossValue = calculateWinOrLoss(playerPoints, status);
        }
    }


    private int calculateWinOrLoss(int points, int status) {
        updatePoints();
        addDealerCards();
        if (points > dealerPoints && status == 2 && points <= 21 || status == 3 && points > dealerPoints && points < 21 || dealerPoints > 21) {
            return 1;
        } else if (dealerPoints > points && status == 2 || status == 3 && dealerPoints > points|| points > 21) {
            return 2;
        } else if (dealerPoints == points) {
            return 3;
        } else {
            return -1;
        }
    }

    public int calculateTotalPoints(ArrayList<Card> cardsOfX) {
        int points = 0;
        for (Card card : cardsOfX) {
            points = card.getNumber() + points;
        }
        return points;
    }

    public int calculateTotalPoints(Card card1, Card card2) {
        int points = 0;

        points = card1.getNumber() + points;
        points = card2.getNumber() + points;

        return points;
    }

    public void hit(int hand) {
        updatePoints();
        System.out.println("you hit");

        if (hand == 1) {
            addCardPlayerHand();
            updatePoints();

        } else {
            addCardPlayerHand2();
            updatePoints();
        }
        updatePoints();
    }

    public void doubleBet(int hand) {
        updatePoints();
        System.out.println("you double");

        if (hand == 1) {
            addCardPlayerHand();
            updatePoints();
            bet = bet * 2;
        } else {
            addCardPlayerHand2();
            bet2 = bet * 2;
        }
        updatePoints();
    }

    public void addCardPlayerHand() {
        aceChecker(deck.getDeckCards().get(0));
        playerCards.add(deck.getDeckCards().get(0));
        updatePoints();
        deck.takeTopCard();
    }

    public void addCardPlayerHand2() {
        aceChecker(deck.getDeckCards().get(0));
        playerCards2.add(deck.getDeckCards().get(0));

        deck.takeTopCard();
    }


    public void addDealerCards() {
        int limit = 20;
        if (splitValidation.equals("y")) {
            for (int i = 0; i < limit; i++) {
                updatePoints();
                if (dealerPoints < 17 && dealerPoints < playerPoints && dealerPoints < playerPoints2) {
                    System.out.println(player.getName() + ":");
                    System.out.println(playerCards);
                    System.out.println(playerPoints);

                    System.out.println(player.getName() + ":+++++++++++++");
                    System.out.println(playerCards2);
                    System.out.println(playerPoints2);

                    aceChecker(deck.getDeckCards().get(0));
                    dealerCards.add(deck.getDeckCards().get(0));
                    deck.takeTopCard();
                    setDealerCards(dealerCards);
                    updatePoints();

                    System.out.println("Dealer" + ":");
                    System.out.println(dealerCards);
                    System.out.println(dealerPoints);
                } else {
                    i = 1000;
                }
            }
            System.out.println("playerhand ========== " + playerCards);
            System.out.println("Dealerhand ========== " + dealerCards);
        } else {
            for (int i = 0; i < limit; i++) {
                updatePoints();
                if (dealerPoints < 17 && dealerPoints < playerPoints){
                    System.out.println(player.getName() + ":");
                    System.out.println(playerCards);
                    System.out.println(playerPoints);

                    aceChecker(deck.getDeckCards().get(0));
                    dealerCards.add(deck.getDeckCards().get(0));
                    deck.takeTopCard();
                    setDealerCards(dealerCards);
                    updatePoints();

                    System.out.println("Dealer" + ":");
                    System.out.println(dealerCards);
                    System.out.println(dealerPoints);
                } else {
                    i = 1000;
                }
            }
            System.out.println("playerhand ========== " + playerCards);
            System.out.println("Dealerhand ========== " + dealerCards);
        }
    }

    public void aceChecker(Card card) {
        if (card.getNumber() == 11 && card.getNumber() + playerPoints > 21) {
            card.setNumber(1);
            deck.getDeckCards().set(0, card);
        }
    }

    public void updatePoints() {
        playerPoints = calculateTotalPoints(playerCards);
        playerPoints2 = calculateTotalPoints(playerCards2);
        dealerPoints = calculateTotalPoints(dealerCards);
    }

}





