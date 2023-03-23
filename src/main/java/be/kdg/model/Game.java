package be.kdg.model;


public class Game {

    public void youLostFirstHand(Player player){
        player.setBalance(player.getBalance() - player.getBet());
    }
    public void youLostSecondHand(Player player){
        player.setBalance(player.getBalance() - player.getBet2());
    }

    public void youWonFirstHand(Player player){
        player.setBalance(player.getBalance() + player.getBet());
    }
    public void youWonSecondHand(Player player){
        player.setBalance(player.getBalance() + player.getBet2());
    }

    public void youDraw(Player player){
        player.setBalance(player.getBalance());
    }
}

