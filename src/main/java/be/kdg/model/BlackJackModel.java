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
    public void makeNewTable(){
        this.table = new Table(player);
        game.setTable(table);
    }

    public String getName() {
        return this.game.getName();
    }

    public void setName(String name) {
        this.game.setName(name);
    }

    public void setBet(int bet) {
        this.table.setBet(bet);
    }

    public int getBet() {
        return this.table.getBet();
    }

    public void startGame(){
        table.dealCards();
    }

    public Card getFirstCardPlayer() {
        return this.table.getFirstCardPlayer();
    }

    public Card getSecondCardPlayer() {
        return this.table.getSecondCardPlayer();
    }

    public ArrayList<Card> getDealerCards() {
        return this.table.getDealerCards();
    }

    public int getDealerPoints() {
        return this.table.getDealerPoints();
    }

    public int getPlayerPoints() {
        return this.table.getPlayerPoints();
    }

    public int getBalance(){return this.player.getBalance();}

    public void splitOption(){
        table.splitOption();
    }
    public void setAnwser(String anwser) {
        this.table.setAnwser(anwser);
    }
    public void setAnwser2(String anwser) {
        this.table.setAnwser2(anwser);
    }
    public void setBalance(int balance) {
        this.player.setBalance(balance);
    }

    public ArrayList<Card> getPlayerCards() {
        return this.table.getPlayerCards();
    }

    public int getWinOrLossValue(){
        return this.table.getWinOrLossValue();
    }
    public void hitStandDoubleOrSplit(){
        table.hitStandDoubleOrSplit();
    }
    public void conditionDeterminer(){
        table.conditionDeterminer();
    }
    public void youLost(){
        game.youLost();
    }
    public void youWon(){
        game.youWon();
    }
    public void youDraw(){
        game.youDraw();
    }
    public String getSplitValidation() {
        return this.table.getSplitValidation();
    }
    public void setSplitValidation(String splitValidation) {
        this.table.setSplitValidation(splitValidation);
    }
    public void splitGame()
    {
        this.table.splitGame();
    }

    public void setStatHolder(int statHolder) {
        this.table.setStatHolder(statHolder);
    }
}