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
        dealerCards.add(deck.getDeckCards().get(0));
        deck.takeTopCard();
        System.out.println("[???] " + dealerCards);

    }

    //De dealer draait de kaarten pas aan het einde en speelt zijn kaarten nadat de player stand of verloren is. zie online blackjack////



    public void splitOption() {
        if (getFirstCardPlayer().getNumber() == getSecondCardPlayer().getNumber()) {
            System.out.println("Would you like to split? (y or n) ");
            //SplitValidation = keyboard.nextLine();
            if (SplitValidation.equals("y")) {

                ArrayList<Card> playerFirstCard = new ArrayList<>();
                playerFirstCard.add(getFirstCardPlayer());
                setPlayerCards(playerFirstCard);

                ArrayList<Card> playerSecondCard = new ArrayList<>();
                playerSecondCard.add(getSecondCardPlayer());
                setPlayerCards2(playerSecondCard);
                hitStandDoubleOrSplit();

            } else {
                ArrayList<Card> playerCard = new ArrayList<>();
                playerCard.add(getFirstCardPlayer());
                playerCard.add(getSecondCardPlayer());

                setPlayerCards(playerCard);

                hitStandDoubleOrSplit();
            }
        } else {
            ArrayList<Card> playerCard = new ArrayList<>();
            playerCard.add(getFirstCardPlayer());
            playerCard.add(getSecondCardPlayer());

            setPlayerCards(playerCard);

            hitStandDoubleOrSplit();
        }
    }

    int status = 1;
    int secondStatus = 1;
    int statusCopy = 1;
    int secondStatusCopy = 1;


    public void hitStandDoubleOrSplit() {
        setPlayerPoints(calculateTotalPoints(getPlayerCards()));
        setPlayerPoints2(calculateTotalPoints(getPlayerCards2()));
        if (SplitValidation.equals("y")) {
            splitGame();
            setBet2(getBet());
        } else {
            do {
                System.out.println("Do you want to Hit, Stand or Double: ");
                int hand = 1;
                switch (anwser) {
                    case "Hit" -> {
                        hit(dealerCards, getPlayerCards(), hand);
                        status = 1;
                    }
                    case "Stand" -> {
                        stand();
                        status = 2;
                    }
                    case "Double" -> {
                        doubleBet(dealerCards, getBet(), getPlayerCards(), hand);
                        status = 3;
                    }
                }

            }
            while (status == 1 && getPlayerPoints() < 21);
        }
        conditionDeterminer();
    }

    public void splitGame() {
        int hand1 = 1;
        int hand2 = 2;
        if (getStatHolder() != 2){
            do {
                System.out.println("Do you want to Hit, Stand or Double for your first deck: ");
                switch (anwser) {
                    case "Hit" -> {
                        hit(dealerCards,getPlayerCards(), hand1);
                        status = 1;
                        statusCopy = 1;
                    }
                    case "Stand" -> {
                        stand();
                        status = 2;
                        statusCopy = 2;
                    }
                    case "Double" -> {
                        doubleBet(dealerCards, getBet(), getPlayerCards(), hand1);
                        status = 3;
                        statusCopy = 3;
                    }
                }
            }
            while (getPlayerPoints() < 21 && status == 1);
            do {
                switch (anwser2) {
                    case "Hit" -> {
                        setPlayerPoints2(calculateTotalPoints(getPlayerCards2()));
                        hit(dealerCards, getPlayerCards2(), hand2);
                        secondStatus = 1;
                        secondStatusCopy = 1;
                    }
                    case "Stand" -> {
                        setPlayerPoints2(calculateTotalPoints(getPlayerCards2()));
                        stand();
                        secondStatus = 2;
                        secondStatusCopy = 2;
                    }
                    case "Double" -> {
                        setPlayerPoints2(calculateTotalPoints(getPlayerCards2()));
                        doubleBet(dealerCards, getBet(),getPlayerCards2(), hand2);
                        secondStatus = 3;
                        secondStatusCopy = 3;
                    }
                }
            }
            while (getPlayerPoints2() < 21 && secondStatus == 1);
        }
    status =0;
    secondStatus =0;
    conditionDeterminer();
}


    public void conditionDeterminer() {
        setPlayerPoints(calculateTotalPoints(getPlayerCards()));
        setPlayerPoints2(calculateTotalPoints(getPlayerCards2()));
        setDealerPoints(calculateTotalPoints(getDealerCards()));

        if (SplitValidation.equals("y")) {
                if (getStatHolder() != 2) {
                    setStatHolder(2);
                    winOrLoss(getPlayerPoints());
                } else if (getStatHolder() == 2){
                    setStatHolder(4);
                    winOrLoss(getPlayerPoints2());
                }
                else {
                    dealCards();
                }

        } else {
            if (statusCopy != 1 || getPlayerPoints() > 21 || dealerPoints > 21 || getPlayerPoints() == 21) {
                    winOrLoss(getPlayerPoints());
            } else {
                hitStandDoubleOrSplit();
            }
        }

    }


    public void winOrLoss(int playerPoints) {
        addDealerCards(dealerCards);
        setPlayerPoints(calculateTotalPoints(getPlayerCards()));
        setPlayerPoints2(calculateTotalPoints(getPlayerCards2()));
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
    public void hit(ArrayList<Card> dealerCards, ArrayList<Card> playerHand, int hand) {

        System.out.println("you hit");
        System.out.println(player.getName() + ":");


        playerHand.add(deck.getDeckCards().get(0));
        deck.takeTopCard();

        int playerPointss = calculateTotalPoints(playerHand);
        setDealerPoints(calculateTotalPoints(dealerCards));

        System.out.println(playerHand);
        System.out.println(playerPointss);



        System.out.println("Dealer" + ":");
        System.out.println("[???] " + dealerCards);
        System.out.println(getDealerPoints());

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
        System.out.println(getBet());

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
            playerHand = getPlayerCards2();
        }
        else{
            playerHand = getPlayerCards();
        }

        int limit = 100;
        for (int i = 0; i < limit; i++) {
            setDealerPoints(calculateTotalPoints(dealerCards));
            setPlayerPoints(calculateTotalPoints(playerHand));

            if (getDealerPoints() < 16) {
                System.out.println(player.getName() + ":");
                System.out.println(playerHand);
                System.out.println(playerPoints);

                dealerCards.add(deck.getDeckCards().get(0));
                deck.takeTopCard();
                setDealerCards(dealerCards);
                setDealerPoints(calculateTotalPoints(dealerCards));

                System.out.println("Dealer" + ":");
                System.out.println(dealerCards);
                setDealerPoints(calculateTotalPoints(dealerCards));
                System.out.println(getDealerPoints());
            }
            else{
                i = 1000;
            }
        }

        System.out.println("playerhand ========== " + playerHand);
        System.out.println("Dealerhand ========== " + dealerCards);

    }
}





