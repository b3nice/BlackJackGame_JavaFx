package be.kdg.model;

public class Application {
    private final Game game = new Game();
    private Player player;

    public Application() {

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
        Table table;
        player = new Player(game.getName(), table = new Table());
        player.setBalance(200);
        table.dealCards(player, table);
    }


}