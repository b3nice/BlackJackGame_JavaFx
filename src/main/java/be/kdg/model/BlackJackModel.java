package be.kdg.model;

public class BlackJackModel {
    private Game game;
    private Player player;
    private Table table = new Table(game, player);

    public BlackJackModel() {
        game = new Game(player = new Player(game.getName(), table);
    }

    public String getName() {
        return this.game.getName();
    }

    public void setName(String name) {
        this.game.setName(name);
    }

    private int bet;

    public void setBet(int bet) {
        this.player.setBet(bet);
    }

    public void startGame(){
        player.setBalance(200);
        table.dealCards(player, table);
    }


}