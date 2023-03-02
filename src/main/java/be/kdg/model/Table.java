package be.kdg.model;

import java.util.ArrayList;

public class Table {

    private final Player player;
    private final Game game;
    private final Deck deck;

    public Table(Game game, Player player) {
        this.game = game;
        this.player = player;
        this.deck = new Deck();
    }

    private int statHolder = 0;
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

    private String anwser2;
    public String getAnwser2() {
        return anwser2;
    }
    public void setAnwser2(String anwser2) {
        this.anwser2 = anwser2;
    }

    String SplitValidation = " ";

    public void dealCards() {
        dealerCards.clear();
        SplitValidation = " ";

        System.out.println("[" + deck.getDeckCards().get(0) + "]");
        setFirstCardPlayer(deck.getDeckCards().get(0));
        deck.takeTopCard();

        System.out.println("[" + getFirstCardPlayer() + "] [" + deck.getDeckCards().get(0) + "]");
        setSecondCardPlayer(deck.getDeckCards().get(0));
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
            if (SplitValidation.equals("y")) {

                ArrayList<Card> playerFirstCard = new ArrayList<>();
                playerFirstCard.add(firstCardPlayer);
                setPlayerCards(playerFirstCard);

                ArrayList<Card> playerSecondCard = new ArrayList<>();
                playerSecondCard.add(secondCardPlayer);
                setPlayerCards2(playerSecondCard);
                hitStandDoubleOrSplit();

            } else {
                ArrayList<Card> playerCard = new ArrayList<>();
                playerCard.add(firstCardPlayer);
                playerCard.add(secondCardPlayer);

                setPlayerCards(playerCard);

                hitStandDoubleOrSplit();
            }
        } else {
            ArrayList<Card> playerCard = new ArrayList<>();
            playerCard.add(firstCardPlayer);
            playerCard.add(secondCardPlayer);

            setPlayerCards(playerCard);

            hitStandDoubleOrSplit();
        }
    }

    int status = 1;
    int secondStatus = 1;
    int statusCopy = 1;
    int secondStatusCopy = 1;


    public void hitStandDoubleOrSplit() {
        playerPoints = (calculateTotalPoints(playerCards));
        playerPoints2 = (calculateTotalPoints(playerCards2));
        if (SplitValidation.equals("y")) {
            splitGame();
            setBet2(bet);
        } else {
            do {
                playerPoints = (calculateTotalPoints(playerCards));
                playerPoints2 = (calculateTotalPoints(playerCards2));
                int hand = 1;
                switch (anwser) {
                    case "Hit" -> {
                        hit(dealerCards, playerCards, hand);
                        status = 1;
                    }
                    case "Stand" -> {
                        stand();
                        status = 2;
                    }
                    case "Double" -> {
                        doubleBet(dealerCards, bet, playerCards, hand);
                        status = 3;
                    }
                }
                anwser = " ";
            }
            while (status == 1 && playerPoints < 21);
        }
        conditionDeterminer();
    }

    public void splitGame() {
        int hand1 = 1;
        int hand2 = 2;

        if (statHolder != 2){
            do {
                System.out.println("Do you want to Hit, Stand or Double for your first deck: ");
                switch (anwser) {
                    case "Hit" -> {
                        hit(dealerCards,playerCards, hand1);
                        status = 1;
                        statusCopy = 1;
                    }
                    case "Stand" -> {
                        stand();
                        status = 2;
                        statusCopy = 2;
                    }
                    case "Double" -> {
                        doubleBet(dealerCards, bet, playerCards2, hand1);
                        status = 3;
                        statusCopy = 3;
                    }
                }
            }
            while (playerPoints < 21 && status == 1);
            do {
                switch (anwser2) {
                    case "Hit" -> {
                        playerPoints2 = (calculateTotalPoints(playerCards2));
                        hit(dealerCards, playerCards2, hand2);
                        secondStatus = 1;
                        secondStatusCopy = 1;
                    }
                    case "Stand" -> {
                        playerPoints2 = (calculateTotalPoints(playerCards2));
                        stand();
                        secondStatus = 2;
                        secondStatusCopy = 2;
                    }
                    case "Double" -> {
                        playerPoints2 = (calculateTotalPoints(playerCards2));
                        doubleBet(dealerCards, bet,playerCards2, hand2);
                        secondStatus = 3;
                        secondStatusCopy = 3;
                    }
                }
            }
            while (playerPoints2 < 21 && secondStatus == 1);
        }
    status =0;
    secondStatus =0;
    conditionDeterminer();
    playerPoints = (calculateTotalPoints(playerCards));
    playerPoints2 = (calculateTotalPoints(playerCards2));
}


    public void conditionDeterminer() {
        playerPoints = (calculateTotalPoints(playerCards));
        playerPoints2 = (calculateTotalPoints(playerCards2));
        setDealerPoints(calculateTotalPoints(dealerCards));

        if (SplitValidation.equals("y")) {
                if (statHolder != 2) {
                    statHolder = 2;
                    winOrLoss();
                } else if (statHolder == 2){
                    statHolder = 4;
                    winOrLoss();
                }
                else {
                    dealCards();
                }

        } else {
            if (statusCopy != 1 || playerPoints > 21 || dealerPoints > 21 || playerPoints == 21) {
                    winOrLoss();
            } else {
                hitStandDoubleOrSplit();
            }
        }

    }


    public void winOrLoss() {
        addDealerCards(dealerCards);
        playerPoints = (calculateTotalPoints(playerCards));
        playerPoints2 = (calculateTotalPoints(playerCards2));
        dealerPoints = calculateTotalPoints(dealerCards);

        if (playerPoints == 21 || playerPoints > dealerPoints && statusCopy == 2 && playerPoints < 21 || dealerPoints > 21 || statusCopy == 3 && playerPoints > dealerPoints) {
            game.youWon();
        } else if (dealerPoints == 21 || dealerPoints > playerPoints && statusCopy == 2 || playerPoints > 21 || statusCopy == 3 && dealerPoints > playerPoints) {
            game.youLost();
        } else if (dealerPoints == playerPoints) {
            game.youDraw();
        } else {
            hitStandDoubleOrSplit();
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

    public void hit(ArrayList<Card> dealerCards, ArrayList<Card> playerHand, int hand) {

        System.out.println("you hit");
        System.out.println(player.getName() + ":");

        aceChecker(deck.getDeckCards().get(0));
        playerHand.add(deck.getDeckCards().get(0));

        deck.takeTopCard();

        int playerPointss = calculateTotalPoints(playerHand);
        setDealerPoints(calculateTotalPoints(dealerCards));

        System.out.println(playerHand);
        System.out.println(playerPointss);

        System.out.println("Dealer" + ":");
        System.out.println("[???] " + dealerCards);
        System.out.println(dealerPoints);

        if (hand == 1){
            setPlayerCards(playerHand);
            setPlayerPoints(playerPointss);
        }
        else{
            setPlayerCards2(playerHand);
            setPlayerPoints2(playerPointss);
        }

    }
    public void doubleBet(ArrayList<Card> dealerCards, int bet, ArrayList<Card> playerHand, int hand) {
        System.out.println("you double");

        aceChecker(deck.getDeckCards().get(0));
        playerHand.add(deck.getDeckCards().get(0));
        deck.takeTopCard();
        calculateTotalPoints(playerCards);

        int playerPointss = calculateTotalPoints(playerHand);
        setDealerPoints(calculateTotalPoints(dealerCards));

        System.out.println(player.getName() + ":");
        System.out.println(playerHand);
        System.out.println(playerPointss);

        System.out.println("Dealer" + ":");
        System.out.println("[???] " + dealerCards);

        setBet(bet*2);
        System.out.println(bet);

        if (hand == 1){
            setPlayerCards(playerHand);
            setPlayerPoints(playerPointss);
            setBet(bet*2);
        }
        else{
            setPlayerCards2(playerHand);
            setPlayerPoints2(playerPointss);
            setBet2(bet*2);
        }
    }
    public void stand() {
        System.out.println("you stand");
    }
    public void addDealerCards(ArrayList<Card> dealerCards){
        ArrayList<Card> playerHand;
        if (statHolder == 2){
            playerHand = playerCards2;
        }
        else{
            playerHand = playerCards;
        }

        int limit = 100;
        for (int i = 0; i < limit; i++) {
            setDealerPoints(calculateTotalPoints(dealerCards));
            setPlayerPoints(calculateTotalPoints(playerHand));

            if (dealerPoints < 16) {
                System.out.println(player.getName() + ":");
                System.out.println(playerHand);
                System.out.println(playerPoints);

                aceChecker(deck.getDeckCards().get(0));
                dealerCards.add(deck.getDeckCards().get(0));
                deck.takeTopCard();
                setDealerCards(dealerCards);
                setDealerPoints(calculateTotalPoints(dealerCards));

                System.out.println("Dealer" + ":");
                System.out.println(dealerCards);
                setDealerPoints(calculateTotalPoints(dealerCards));
                System.out.println(dealerPoints);
            }
            else{
                i = 1000;
            }
        }

        System.out.println("playerhand ========== " + playerHand);
        System.out.println("Dealerhand ========== " + dealerCards);

    }

    public void aceChecker(Card card){
        if (card.getNumber() == 11){
            if (card.getNumber() + playerPoints > 21){
                card.setNumber(1);
                deck.getDeckCards().set(0,card);
            }
        }
    }

}





