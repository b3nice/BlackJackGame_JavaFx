package be.kdg.model;

public class BlackJackModel {
    private Game game;
    private Table table;
    private Player player;

    public BlackJackModel() {

        game = new Game(table = new Table(game,player = new Player(getName(), table)), player);

    }

    public String getName() {
        return this.game.getName();
    }

    public void setName(String name) {
        this.game.setName(name);
    }

    private int bet;

    public void setBet(int bet) {
        this.table.setBet(bet);
    }

    public void startGame(){
        game.startGame();
    }


}