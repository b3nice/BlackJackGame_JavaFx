package be.kdg.model;

import java.util.ArrayList;

public class Player {
    private final String name;
    private int balance;
    private final int playerNumber;
    private boolean betConfirm;
    private int statHolder;
    private Card firstCardPlayer;
    private Card secondCardPlayer;
    private int playerPoints;
    private int playerPoints2;
    private int bet;
    private int bet2;
    private ArrayList<Card> playerCards;
    private ArrayList<Card> playerCards2;
    private String anwser;
    private int winOrLossValue;
    private int winOrLossValue2;
    private String splitValidation;
    private int status;
    private int secondStatus;
    private int indexImageSplit1;
    private int indexImageSplit2;

    public Player(String name, int balance, int playerNumber) {
        this.name = name;
        this.balance = balance;
        this.playerNumber = playerNumber;
        this.playerCards = new ArrayList<>();
        this.playerCards2 = new ArrayList<>();
        this.playerPoints = 0;
        this.playerPoints2 = 0;
        this.bet = 0;
        this.bet2 = 0;
        this.winOrLossValue = 0;
        this.winOrLossValue2 = 0;
        this.status = 1;
        this.secondStatus = 1;
        this.statHolder = 2;
        this.betConfirm = false;
        this.indexImageSplit1 = 1;
        this.indexImageSplit2 = 1;
        this.anwser = "";
        this.splitValidation = "";
        this.firstCardPlayer = null;
        this.secondCardPlayer = null;
    }
    public void resetPlayer() {
        this.playerPoints = 0;
        this.playerPoints2 = 0;
        this.bet = 0;
        this.bet2 = 0;
        this.playerCards.clear();
        this.playerCards2.clear();
        this.winOrLossValue = 0;
        this.winOrLossValue2 = 0;
        this.status = 1;
        this.secondStatus = 1;
        this.statHolder = 2;
        this.betConfirm = false;
        this.indexImageSplit1 = 1;
        this.indexImageSplit2 = 1;
        this.anwser = "";
        this.splitValidation = "";
        this.firstCardPlayer = null;
        this.secondCardPlayer = null;
    }

    @Override
    public String toString() {
        return name + "," + balance;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public boolean isBetConfirm() {
        return betConfirm;
    }

    public void setBetConfirm(boolean betConfirm) {
        this.betConfirm = betConfirm;
    }

    public int getStatHolder() {
        return statHolder;
    }

    public void setStatHolder(int statHolder) {
        this.statHolder = statHolder;
    }

    public Card getFirstCardPlayer() {
        return firstCardPlayer;
    }

    public void setFirstCardPlayer(Card firstCardPlayer) {
        this.firstCardPlayer = firstCardPlayer;
    }

    public Card getSecondCardPlayer() {
        return secondCardPlayer;
    }

    public void setSecondCardPlayer(Card secondCardPlayer) {
        this.secondCardPlayer = secondCardPlayer;
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

    public void setPlayerPoints(int playerPoints) {
        this.playerPoints = playerPoints;
    }

    public int getPlayerPoints2() {
        return playerPoints2;
    }

    public void setPlayerPoints2(int playerPoints2) {
        this.playerPoints2 = playerPoints2;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getBet2() {
        return bet2;
    }

    public void setBet2(int bet2) {
        this.bet2 = bet2;
    }

    public ArrayList<Card> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(ArrayList<Card> playerCards) {
        this.playerCards = playerCards;
    }

    public ArrayList<Card> getPlayerCards2() {
        return playerCards2;
    }

    public void setPlayerCards2(ArrayList<Card> playerCards2) {
        this.playerCards2 = playerCards2;
    }

    public String getAnwser() {
        return anwser;
    }

    public void setAnwser(String anwser) {
        this.anwser = anwser;
    }

    public int getWinOrLossValue() {
        return winOrLossValue;
    }

    public void setWinOrLossValue(int winOrLossValue) {
        this.winOrLossValue = winOrLossValue;
    }

    public int getWinOrLossValue2() {
        return winOrLossValue2;
    }

    public void setWinOrLossValue2(int winOrLossValue2) {
        this.winOrLossValue2 = winOrLossValue2;
    }

    public String getSplitValidation() {
        return splitValidation;
    }

    public void setSplitValidation(String splitValidation) {
        this.splitValidation = splitValidation;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSecondStatus() {
        return secondStatus;
    }

    public void setSecondStatus(int secondStatus) {
        this.secondStatus = secondStatus;
    }

    public int getIndexImageSplit1() {
        return indexImageSplit1;
    }

    public void setIndexImageSplit1(int indexImageSplit1) {
        this.indexImageSplit1 = indexImageSplit1;
    }

    public int getIndexImageSplit2() {
        return indexImageSplit2;
    }

    public void setIndexImageSplit2(int indexImageSplit2) {
        this.indexImageSplit2 = indexImageSplit2;
    }
}
