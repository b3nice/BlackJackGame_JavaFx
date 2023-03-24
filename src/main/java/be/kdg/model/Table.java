package be.kdg.model;

import java.util.ArrayList;

public class Table {

    private final Dealer dealer;

    public Table(Dealer dealer) {
        this.dealer = dealer;
    }

    public void splitOption(Player player) {
        if (player.getSplitValidation().equals("y")) {
            player.setBet2(player.getBet());

            ArrayList<Card> cards = player.getPlayerCards();
            cards.remove(1);
            player.setPlayerCards(cards);

            ArrayList<Card> cards2 = player.getPlayerCards2();
            cards2.add(0, player.getSecondCardPlayer());
            player.setPlayerCards2(cards2);


            System.out.println("You split, your second deck is: " + player.getPlayerCards2());
            updatePoints(player);
        }
    }
    public void hitStandDoubleOrSplit(Player player) {
        updatePoints(player);

        int hand1 = 1;

        checkHitStandOrDouble(hand1, player);
        player.setAnwser(" ");
    }
    public void splitGame(Player player) {
        int hand1 = 1;
        int hand2 = 2;

        if (player.getStatHolder() == 2) {
            System.out.println("Do you want to Hit, Stand or Double for your first deck: ");
            checkHitStandOrDouble(hand1, player);
        } else {
            System.out.println("Do you want to Hit, Stand or Double for your second deck: ");
            switch (player.getAnwser()) {
                case "Hit" -> {
                    hit(hand2, player);
                    player.setSecondStatus(1);
                }
                case "Stand" -> {
                    updatePoints(player);
                    System.out.println("you stand");
                    player.setSecondStatus(2);
                }
                case "Double" -> {
                    doubleBet(hand2, player);
                    player.setSecondStatus(2);
                }
            }
        }
        updatePoints(player);
    }
    public void checkHitStandOrDouble(int hand1, Player player) {
        switch (player.getAnwser()) {
            case "Hit" -> {
                hit(hand1, player);
                player.setStatus(1);
            }
            case "Stand" -> {
                updatePoints(player);
                System.out.println("you stand");
                player.setStatus(2);
            }
            case "Double" -> {
                doubleBet(hand1, player);
                player.setStatus(2);
            }
        }
    }
    public void winOrLoss(Player player) {
        updatePoints(player);
        if (player.getSplitValidation().equals("y")) {
            if (player.getStatHolder() == 2){
                if (player.getStatus() != 1 || player.getPlayerPoints() > 21 || dealer.getDealerPoints() > 21 || player.getPlayerPoints() == 21){
                    player.setWinOrLossValue(calculateWinOrLoss(player.getPlayerPoints(), player.getStatus(), player));
                }
            } else if (player.getStatHolder() == 1) {
                if (player.getSecondStatus() != 1 || player.getPlayerPoints2() > 21 || dealer.getDealerPoints() > 21 || player.getPlayerPoints2() == 21){
                    player.setWinOrLossValue2(calculateWinOrLoss(player.getPlayerPoints2(), player.getSecondStatus(), player));
                }
            }
        } else if (player.getStatus() != 1 || player.getPlayerPoints() > 21 || dealer.getDealerPoints() > 21 || player.getPlayerPoints() == 21) {
            player.setWinOrLossValue(calculateWinOrLoss(player.getPlayerPoints(), player.getStatus(), player));
        }
    }
    private int calculateWinOrLoss(int points, int status, Player player) {
        dealer.addDealerCards(player);
        updatePoints(player);
        if (points > 21) {
            return 2;
        } else if (points > dealer.getDealerPoints() && status == 2 || status == 3 && points > dealer.getDealerPoints() || dealer.getDealerPoints() > 21) {
            return 1;
        } else if (dealer.getDealerPoints() > points && status == 2 || status == 3 && dealer.getDealerPoints() > points) {
            return 2;
        } else if (dealer.getDealerPoints() == points) {
            return 3;
        } else {
            return -1;
        }
    }
    public int calculateWinOrLossForSplit(Player player) {
        updatePoints(player);
        if (player.getStatHolder() == 2) {
            if (player.getPlayerPoints() > 21 || player.getStatus() == 2 || player.getStatus() == 3) {
                player.setWinOrLossValue(calculateWinOrLoss(player.getPlayerPoints(), player.getStatus(), player));
                return 1;
            } else {
                return -1;
            }
        } else {
            if (player.getPlayerPoints2() > 21 || player.getSecondStatus() == 2 || player.getSecondStatus() == 3) {
                player.setWinOrLossValue2(calculateWinOrLoss(player.getPlayerPoints2(), player.getSecondStatus(), player));
                return 1;
            } else {
                return -1;
            }
        }
    }

    public int calculateTotalPoints(ArrayList<Card> cardsOfX) {
        int points = 0;
        for (Card card : cardsOfX) {
            points = card.getNumber() + points;
        }
        return points;
    }
    public void hit(int hand, Player player) {
        updatePoints(player);
        System.out.println("you hit");

        if (hand == 1) {
            dealer.addCardPlayerHand(player);
            updatePoints(player);


        } else {
            dealer.addCardPlayerHand2(player);
            updatePoints(player);
        }
        updatePoints(player);
    }
    public void doubleBet(int hand, Player player) {
        updatePoints(player);
        System.out.println("you double");

        if (hand == 1) {
            dealer.addCardPlayerHand(player);
            updatePoints(player);
            player.setBet(player.getBet() * 2);
        } else {
            dealer.addCardPlayerHand2(player);
            updatePoints(player);
            player.setBet2(player.getBet() * 2);

        }
    }
    public void updatePoints(Player player) {
        player.setPlayerPoints(calculateTotalPoints(player.getPlayerCards()));
        player.setPlayerPoints2(calculateTotalPoints(player.getPlayerCards2()));
        dealer.setDealerPoints(calculateTotalPoints(dealer.getDealerCards()));
    }
}