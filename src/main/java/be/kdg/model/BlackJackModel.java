package be.kdg.model;

import java.util.ArrayList;

public class BlackJackModel {
    private final Game game;
    private Table table;
    private final ArrayList<Player> players;

    public BlackJackModel() {
        players = new ArrayList<Player>();
        game = new Game();
        table = new Table();
        game.setTable(table);
    }

    public void makeNewTable() {
        this.table = new Table();
        game.setTable(table);
    }

    public int calculateWinOrLossForSplit(Player player) {
        return table.calculateWinOrLossForSplit(player);
    }


    public void startGame(Player player) {
        table.dealCards(player);
    }
    public void dealDealerCards(Player player){
        this.table.dealDealerCards(player);
    }

    public ArrayList<Card> getDealerCards() {
        return this.table.getDealerCards();
    }

    public int getDealerPoints() {
        return this.table.getDealerPoints();
    }

    public void splitOption(Player player) {
        table.splitOption(player);
    }

    public void hitStandDoubleOrSplit(Player player) {
        table.hitStandDoubleOrSplit(player);
    }

    public void winOrLoss(Player player) {
        table.winOrLoss(player);
    }

    public void youLost(Player player) {
        game.youLost(player);
    }

    public void youWon(Player player) {
        game.youWon(player);
    }

    public void youDraw(Player player) {
        game.youDraw(player);
    }

    public void splitGame(Player player) {
        this.table.splitGame(player);
    }

}