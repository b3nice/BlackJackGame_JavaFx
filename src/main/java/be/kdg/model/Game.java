package be.kdg.model;


public class Game {
    private  Player player;
    public void setPlayer(Player player) {
        this.player = player;
    }
    private Table table;
    public void setTable(Table table) {
        this.table = table;
    }

    public String getName() {
        return this.player.getName();
    }

    public void setName(String name) {
        this.player.setName(name);
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

