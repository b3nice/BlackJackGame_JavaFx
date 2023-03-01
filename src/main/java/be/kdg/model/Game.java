package be.kdg.model;



public class Game {

    private Player player;
    private Table table;

    public Game(Player player, Table table) {
        this.player = player;
        this.table = table;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void youLost(Player player, Game game, Table table){
        System.out.println("You lost good job");
        player.setBalance(player.getBalance() - player.getBet());
        if (player.getStatHolder() == 2)
        {
            table.conditionDeterminer(player, game, table);
        }
        table.dealCards(player, table);
    }

    public void youWon(Player player, Game game, Table table){
        System.out.println("You won good job");
        player.setBalance(player.getBalance() + player.getBet());
        System.out.println("This is your new balance" + player.getBalance());
        if (player.getStatHolder() == 2)
        {
            table.conditionDeterminer(player, game, table);
        }
        table.dealCards(player, table);
    }

    public void youDraw(Player player, Game game, Table table){
        System.out.println("You draw good job");
        if (player.getStatHolder() == 2)
        {
            table.conditionDeterminer(player, game, table);
        }
        table.dealCards(player, table);
    }
}

