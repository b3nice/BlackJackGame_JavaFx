package be.kdg.model;

import java.util.ArrayList;

public class Player {


    private final String name;
    private final Table table;

    public String getName() {
        return name;
    }

    public Player(String name, Table table) {
        this.name = name;
        this.table = table;
    }
    private int statHolder = 0;

    public int getStatHolder() {
        return statHolder;
    }

    public void setStatHolder(int statHolder) {
        this.statHolder = statHolder;
    }

    private int balance;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
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

    private int bet2 = 0;

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

    public void hit(ArrayList<Card> dealerCards, Deck deck, ArrayList<Card> playerHand, int hand) {

        System.out.println("you hit");
        System.out.println(getName() + ":");


        playerHand.add(deck.getDeckCards().get(0));
        deck.takeTopCard();

        int playerPointss = table.calculateTotalPoints(playerHand);
        table.setDealerPoints(table.calculateTotalPoints(dealerCards));

        System.out.println(playerHand);
        System.out.println(playerPointss);



        System.out.println("Dealer" + ":");
        System.out.println("[???] " + dealerCards);
        System.out.println(table.getDealerPoints());

        if (hand == 1){
            setPlayerCards(playerHand);
            setPlayerPoints(playerPointss);
        }
        else{
            setPlayerCards2(playerHand);
            setPlayerPoints2(playerPointss);
        }

    }
    public void doubleBet(ArrayList<Card> dealerCards, int bet, Deck deck, ArrayList<Card> playerHand, int hand) {
        System.out.println("you double");

        playerHand.add(deck.getDeckCards().get(0));
        deck.takeTopCard();
        table.calculateTotalPoints(playerCards);

        int playerPointss = table.calculateTotalPoints(playerHand);
        table.setDealerPoints(table.calculateTotalPoints(dealerCards));

        System.out.println(getName() + ":");
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
    public void addDealerCards(ArrayList<Card> dealerCards, Deck deck){
        ArrayList<Card> playerHand;
        if (statHolder == 2){
            playerHand = getPlayerCards2();
        }
        else{
            playerHand = getPlayerCards();
        }

        int limit = 100;
        for (int i = 0; i < limit; i++) {
            table.setDealerPoints(table.calculateTotalPoints(dealerCards));
            setPlayerPoints(table.calculateTotalPoints(playerHand));

            if (table.getDealerPoints() < 16) {
                System.out.println(getName() + ":");
                System.out.println(playerHand);
                System.out.println(playerPoints);

                dealerCards.add(deck.getDeckCards().get(0));
                deck.takeTopCard();
                table.setDealerCards(dealerCards);
                table.setDealerPoints(table.calculateTotalPoints(dealerCards));

                System.out.println("Dealer" + ":");
                System.out.println(dealerCards);
                table.setDealerPoints(table.calculateTotalPoints(dealerCards));
                System.out.println(table.getDealerPoints());
            }
            else{
                i = 1000;
            }
        }

        System.out.println("playerhand ========== " + playerHand);
        System.out.println("Dealerhand ========== " + dealerCards);

    }

}
