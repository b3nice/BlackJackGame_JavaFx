package be.kdg.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

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
    public int checkStatusWinOrLoss(int winOrLossValue, Player player) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (winOrLossValue == 1) {
                if (getIsFirstHand(player)) {
                    youWonFirstHand(player);
                } else {
                    youWonSecondHand(player);
                }
                player.setStatHolder(2);
            } else if (winOrLossValue == 2) {
                if (getIsFirstHand(player)) {
                    youLostFirstHand(player);
                } else {
                    youLostSecondHand(player);
                }
                player.setStatHolder(2);
            } else if (winOrLossValue == 3) {
                player.setStatHolder(2);
                youDraw(player);
            }
        }));
        timeline.play();
        return winOrLossValue;
    }
    public boolean getIsFirstHand(Player player) {
        return player.getStatHolder() == 2;
    }
}

