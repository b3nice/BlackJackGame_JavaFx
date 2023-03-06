package be.kdg.model;

import java.util.ArrayList;

public class BlackJackModel {
    private Game game;
    private Table table;
    private Player player;

    public BlackJackModel() {
        game = new Game();
        player = new Player();
        player.setBalance(200);
        table = new Table(game, player);
        game.setTable(table);
        game.setPlayer(player);
    }
    public void makeNewTable(){
        table = new Table(game, player);
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

    public ArrayList<Card> getPlayerCards() {
        return this.table.getPlayerCards();
    }

}