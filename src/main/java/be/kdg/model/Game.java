package be.kdg.model;


public class Game {

    private final Player player;
    private Table table;

    public Game(Table table, Player player) {
        this.table = table;
        this.player = player;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void startGame(){
        player.setBalance(200);
        table.dealCards();
    }

    public void youLost(){
        System.out.println("You lost good job");
        player.setBalance(player.getBalance() - table.getBet());
        if (table.getStatHolder() == 2)
        {
            table.conditionDeterminer();
        }
        table.dealCards();
    }

    public void youWon(){
        System.out.println("You won good job");
        player.setBalance(player.getBalance() + table.getBet());
        System.out.println("This is your new balance" + player.getBalance());
        if (table.getStatHolder() == 2)
        {
            table.conditionDeterminer();
        }
        table.dealCards();
    }

    public void youDraw(){
        System.out.println("You draw good job");
        if (table.getStatHolder() == 2)
        {
            table.conditionDeterminer();
        }
        table.dealCards();
    }
}

