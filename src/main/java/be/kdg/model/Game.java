package be.kdg.model;


public class Game {

    private Table table;
    public void setTable(Table table) {
        this.table = table;
    }

    public void youLost(Player player){
        System.out.println("You lost good job");
        player.setBalance(player.getBalance() - player.getBet());
        if (player.getWinOrLossValue2() == 2 && player.getSplitValidation().equals("y")){
            player.setBalance(player.getBalance() - player.getBet2());
        }
        if (player.getStatHolder() == 2 && player.getSplitValidation().equals("y"))
        {
            table.winOrLoss(player);
        }
    }

    public void youWon(Player player){
        System.out.println("You won good job");
        player.setBalance(player.getBalance() + player.getBet());
        if (player.getWinOrLossValue2() == 1)
        {
            player.setBalance(player.getBalance() + player.getBet2());
        }
        System.out.println("This is your new balance" + player.getBalance());
        if (player.getStatHolder() == 2)
        {
            table.winOrLoss(player);
        }
    }

    public void youDraw(Player player){
        System.out.println("You draw good job");
        if (player.getStatHolder() == 2)
        {
            table.winOrLoss(player);
        }
    }
}

