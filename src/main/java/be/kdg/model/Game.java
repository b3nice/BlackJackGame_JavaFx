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
        player.setBalance(player.getBalance() - player.getBet());
        if (player.getWinOrLossValue2() == 2 && player.getSplitValidation().equals("y")){
            player.setBalance(player.getBalance() - player.getBet2());
        }
        if (player.getStatHolder() == 2 && player.getSplitValidation().equals("y"))
        {
            table.winOrLoss();
        }
    }

    public void youWon(){
        System.out.println("You won good job");
        player.setBalance(player.getBalance() + player.getBet());
        if (player.getWinOrLossValue2() == 1)
        {
            player.setBalance(player.getBalance() + player.getBet2());
        }
        System.out.println("This is your new balance" + player.getBalance());
        if (player.getStatHolder() == 2)
        {
            table.winOrLoss();
        }
    }

    public void youDraw(){
        System.out.println("You draw good job");
        if (player.getStatHolder() == 2)
        {
            table.winOrLoss();
        }
    }
}

