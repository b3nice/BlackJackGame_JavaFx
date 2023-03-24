package be.kdg.model;

import java.util.ArrayList;

public class BlackJackModel {
    private final Game game;
    private Table table;
    private final Dealer dealer;
    private final ArrayList<Player> players;

    public BlackJackModel() {
        players = new ArrayList<Player>();
        game = new Game();
        dealer = new Dealer();
        table = new Table(dealer);
        setTable(table);
    }

    public void makePlayers(String name, int money, int playerNumber) {
            players.add(new Player(name, money, playerNumber));
    }
    public void makeNewTable() {
        this.table = new Table(dealer);
    }
    public int calculateWinOrLossForSplit(Player player) {
        return table.calculateWinOrLossForSplit(player);
    }
    public void startGame(Player player) {
        dealer.dealCards(player);
    }
    public void dealDealerCards(Player player){
        dealer.dealDealerCards(player);
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
    public void youWonFirstHand(Player player) {
        game.youWonFirstHand(player);
    }
    public void youWonSecondHand(Player player) {
        game.youWonSecondHand(player);
    }
    public void youLostFirstHand(Player player) {
        game.youLostFirstHand(player);
    }
    public void youLostSecondHand(Player player) {
        game.youLostSecondHand(player);
    }
    public void youDraw(Player player) {
        game.youDraw(player);
    }

    public void splitGame(Player player) {
        table.splitGame(player);
    }

    public ArrayList<Card> getDealerCards() {
        return this.dealer.getDealerCards();
    }

    public int getDealerPoints() {
        return this.dealer.getDealerPoints();
    }
    public void setTable(Table table) {
        this.dealer.setTable(table);
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }

}