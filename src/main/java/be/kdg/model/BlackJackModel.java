package be.kdg.model;

import java.util.ArrayList;

public class BlackJackModel {
    private Game game;
    private Table table;
    private Player player;

    public BlackJackModel() {
        game = new Game();
        player = new Player();
        table = new Table(game, player);
        game.setTable(table);
        game.setPlayer(player);
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
        player.setBalance(200);
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

}