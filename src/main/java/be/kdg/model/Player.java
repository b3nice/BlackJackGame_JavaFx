package be.kdg.model;

import java.util.ArrayList;

public class Player {
    private String name;
    private int balance;
    private int playerNumber;

    public Player(String name, int balance, int playerNumber) {
        this.name = name;
        this.balance = balance;
        this.playerNumber = playerNumber;
    }

    private boolean betConfirm = false;
    public boolean isBetConfirm() {
        return betConfirm;
    }
    public void setBetConfirm(boolean betConfirm) {
        this.betConfirm = betConfirm;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
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

    private String anwser;
    public void setAnwser(String anwser) {
        this.anwser = anwser;
    }
    public String getAnwser() {
        return anwser;
    }

    private int winOrLossValue = 0;
    public int getWinOrLossValue() {
        return winOrLossValue;
    }
    public void setWinOrLossValue(int winOrLossValue) {
        this.winOrLossValue = winOrLossValue;
    }

    private int winOrLossValue2 = 0;
    public int getWinOrLossValue2() {
        return winOrLossValue2;
    }
    public void setWinOrLossValue2(int winOrLossValue2) {
        this.winOrLossValue2 = winOrLossValue2;
    }

    private String splitValidation;
    public String getSplitValidation() {
        return splitValidation;
    }
    public void setSplitValidation(String splitValidation) {
        this.splitValidation = splitValidation;
    }

    private int status = 1;
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    private int secondStatus = 1;
    public int getSecondStatus() {
        return secondStatus;
    }
    public void setSecondStatus(int secondStatus) {
        this.secondStatus = secondStatus;
    }

    private int indexImageSplit1 = 1;
    public int getIndexImageSplit1() {
        return indexImageSplit1;
    }
    public void setIndexImageSplit1(int indexImageSplit1) {
        this.indexImageSplit1 = indexImageSplit1;
    }

    private int indexImageSplit2 = 1;
    public int getIndexImageSplit2() {
        return indexImageSplit2;
    }
    public void setIndexImageSplit2(int indexImageSplit2) {
        this.indexImageSplit2 = indexImageSplit2;
    }

    public void resetPlayer() {
        this.playerPoints = 0;
        this.playerPoints2 = 0;
        this.playerCards.clear();
        this.playerCards2.clear();
        this.bet = 0;
        this.bet2 = 0;
        this.winOrLossValue = 0;
        this.winOrLossValue2 = 0;
        this.status = 1;
        this.secondStatus = 1;
        this.betConfirm = false;
        this.indexImageSplit1 = 1;
        this.indexImageSplit2 = 1;
    }

    @Override
    public String toString() {
        return name + "," + balance;
    }

}
