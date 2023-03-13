package be.kdg.model;

import java.util.ArrayList;

public class BlackJackModel {
    private Game game;
    private Table table;
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public BlackJackModel() {
        game = new Game();
        player = new Player(" ", 200);
        player.setBalance(200);
        table = new Table(player);
        game.setTable(table);
        game.setPlayer(player);
    }

    public void makeNewTable() {
        this.table = new Table(player);
        game.setTable(table);
    }
    public int calculateWinOrLossForSplit() {
        return table.calculateWinOrLossForSplit();
    }

    public String getName() {
        return this.game.getName();
    }

    public void setName(String name) {
        this.game.setName(name);
    }

    public void setBet(int bet) {
        this.player.setBet(bet);
    }

    public int getBet() {
        return this.player.getBet();
    }

    public void startGame() {
        table.dealCards();
    }

    public Card getFirstCardPlayer() {
        return this.player.getFirstCardPlayer();
    }

    public Card getSecondCardPlayer() {
        return this.player.getSecondCardPlayer();
    }

    public ArrayList<Card> getDealerCards() {
        return this.table.getDealerCards();
    }

    public int getDealerPoints() {
        return this.table.getDealerPoints();
    }

    public int getPlayerPoints() {
        return this.player.getPlayerPoints();
    }

    public int getBalance() {
        return this.player.getBalance();
    }

    public void splitOption() {
        table.splitOption();
    }

    public void setAnwser(String anwser) {
        this.player.setAnwser(anwser);
    }

    public void setBalance(int balance) {
        this.player.setBalance(balance);
    }

    public ArrayList<Card> getPlayerCards() {
        return this.player.getPlayerCards();
    }

    public int getWinOrLossValue() {
        return this.player.getWinOrLossValue();
    }

    public int getWinOrLossValue2() {
        return this.player.getWinOrLossValue2();
    }

    public void hitStandDoubleOrSplit() {
        table.hitStandDoubleOrSplit();
    }

    public void winOrLoss() {
        table.winOrLoss();
    }

    public void youLost() {
        game.youLost();
    }

    public void youWon() {
        game.youWon();
    }

    public void youDraw() {
        game.youDraw();
    }

    public String getSplitValidation() {
        return this.player.getSplitValidation();
    }

    public void setSplitValidation(String splitValidation) {
        this.player.setSplitValidation(splitValidation);
    }

    public void splitGame() {
        this.table.splitGame();
    }

    public void setStatHolder(int statHolder) {
        this.player.setStatHolder(statHolder);
    }

    public ArrayList<Card> getPlayerCards2() {
        return this.player.getPlayerCards2();
    }

    public int getStatHolder() {
        return this.player.getStatHolder();
    }

    public int getPlayerPoints2() {
        return this.player.getPlayerPoints2();
    }

    public int getStatus() {
        return this.player.getStatus();
    }
    public void setStatus(int status) {
        this.player.setStatus(status);
    }

    public int getSecondStatus() {
        return this.player.getSecondStatus();
    }
    public void setSecondStatus(int secondStatus) {
        this.player.setSecondStatus(secondStatus);
    }

}