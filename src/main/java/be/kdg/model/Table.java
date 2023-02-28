package be.kdg.model;

import java.util.ArrayList;

public class Table {

    private int dealerPoints;

    public int getDealerPoints() {
        return dealerPoints;
    }

    public void setDealerPoints(int dealerPoints) {
        this.dealerPoints = dealerPoints;
    }


    private ArrayList<Card> dealerCards = new ArrayList<>();

    public ArrayList<Card> getDealerCards() {
        return dealerCards;
    }

    public void setDealerCards(ArrayList<Card> dealerCards) {
        this.dealerCards = dealerCards;
    }


    Deck deck = new Deck();
    String SplitValidation = " ";

    public void dealCards(Player player, Table table) {
        dealerCards.clear();
        SplitValidation = " ";

        System.out.println("How many chips would you like to bet: ");
        System.out.println("You have " + player.getBalance() + " chips");

        System.out.println(player.getName() + ":");
        System.out.println("[" + deck.getDeckCards().get(0) + "]");
        player.setFirstCardPlayer(deck.getDeckCards().get(0));
        deck.takeTopCard();

        System.out.println(player.getName() + ":");
        System.out.println("[" + player.getFirstCardPlayer() + "] [" + deck.getDeckCards().get(0) + "]");
        player.setSecondCardPlayer(deck.getDeckCards().get(0));
        deck.takeTopCard();

        System.out.println("Dealer" + ":");
        dealerCards.add(deck.getDeckCards().get(0));
        deck.takeTopCard();
        System.out.println("[???] " + dealerCards);

        splitOption(player, table);
    }

    //De dealer draait de kaarten pas aan het einde en speelt zijn kaarten nadat de player stand of verloren is. zie online blackjack////



    public void splitOption(Player player, Table table) {
        if (player.getFirstCardPlayer().getNumber() == player.getSecondCardPlayer().getNumber()) {
            System.out.println("Would you like to split? (y or n) ");
            SplitValidation = keyboard.nextLine();
            if (SplitValidation.equals("y")) {

                System.out.println("hzhehehhzefhzhefhzf");

                ArrayList<Card> playerFirstCard = new ArrayList<>();
                playerFirstCard.add(player.getFirstCardPlayer());
                player.setPlayerCards(playerFirstCard);

                ArrayList<Card> playerSecondCard = new ArrayList<>();
                playerSecondCard.add(player.getSecondCardPlayer());
                player.setPlayerCards2(playerSecondCard);
                hitStandDoubleOrSplit(player, table);

            } else {
                ArrayList<Card> playerCard = new ArrayList<>();
                playerCard.add(player.getFirstCardPlayer());
                playerCard.add(player.getSecondCardPlayer());

                player.setPlayerCards(playerCard);

                hitStandDoubleOrSplit(player, table);
            }
        } else {
            ArrayList<Card> playerCard = new ArrayList<>();
            playerCard.add(player.getFirstCardPlayer());
            playerCard.add(player.getSecondCardPlayer());

            player.setPlayerCards(playerCard);

            hitStandDoubleOrSplit(player, table);
        }
    }

    int status = 1;
    int secondStatus = 1;
    int statusCopy = 1;
    int secondStatusCopy = 1;

    public void hitStandDoubleOrSplit(Player player, Table table) {
        Game game = new Game();
        player.setPlayerPoints(calculateTotalPoints(player.getPlayerCards()));
        player.setPlayerPoints2(calculateTotalPoints(player.getPlayerCards2()));
        if (SplitValidation.equals("y")) {
            splitGame(player, game, table);
            player.setBet2(player.getBet());
        } else {
            do {
                System.out.println("Do you want to Hit, Stand or Double: ");
                String anwser = keyboard.nextLine();
                int hand = 1;
                switch (anwser) {
                    case "Hit" -> {
                        player.hit(dealerCards, deck, player.getPlayerCards(), hand);
                        status = 1;
                    }
                    case "Stand" -> {
                        player.stand();
                        status = 2;
                    }
                    case "Double" -> {
                        player.doubleBet(dealerCards, player.getBet(), deck, player.getPlayerCards(), hand);
                        status = 3;
                    }
                }

            }
            while (status == 1 && player.getPlayerPoints() < 21);
        }
        conditionDeterminer(player, game, table);
    }

    public void splitGame(Player player, Game game, Table table) {
        int hand1 = 1;
        int hand2 = 2;
        if (player.getStatHolder() != 2){
            do {
                System.out.println("Do you want to Hit, Stand or Double for your first deck: ");
                String anwser = keyboard.nextLine();
                switch (anwser) {
                    case "Hit" -> {
                        player.hit(dealerCards, deck, player.getPlayerCards(), hand1);
                        status = 1;
                        statusCopy = 1;
                    }
                    case "Stand" -> {
                        player.stand();
                        status = 2;
                        statusCopy = 2;
                    }
                    case "Double" -> {
                        player.doubleBet(dealerCards, player.getBet(), deck, player.getPlayerCards(), hand1);
                        status = 3;
                        statusCopy = 3;
                    }
                }
            }
            while (player.getPlayerPoints() < 21 && status == 1);
            do {
                System.out.println("Do you want to Hit, Stand or Double for your second deck: ");
                String anwser2 = keyboard.nextLine();
                switch (anwser2) {
                    case "Hit" -> {
                        player.setPlayerPoints2(calculateTotalPoints(player.getPlayerCards2()));
                        player.hit(dealerCards, deck, player.getPlayerCards2(), hand2);
                        secondStatus = 1;
                        secondStatusCopy = 1;
                    }
                    case "Stand" -> {
                        player.setPlayerPoints2(calculateTotalPoints(player.getPlayerCards2()));
                        player.stand();
                        secondStatus = 2;
                        secondStatusCopy = 2;
                    }
                    case "Double" -> {
                        player.setPlayerPoints2(calculateTotalPoints(player.getPlayerCards2()));
                        player.doubleBet(dealerCards, player.getBet(), deck, player.getPlayerCards2(), hand2);
                        secondStatus = 3;
                        secondStatusCopy = 3;
                    }
                }
            }
            while (player.getPlayerPoints2() < 21 && secondStatus == 1);
        }
    status =0;
    secondStatus =0;
    conditionDeterminer(player, game, table);
}


    public void conditionDeterminer(Player player, Game game, Table table) {
        player.setPlayerPoints(calculateTotalPoints(player.getPlayerCards()));
        player.setPlayerPoints2(calculateTotalPoints(player.getPlayerCards2()));
        setDealerPoints(calculateTotalPoints(getDealerCards()));

        if (SplitValidation.equals("y")) {
                if (player.getStatHolder() != 2) {
                    player.setStatHolder(2);
                    winOrLoss(player, game, player.getPlayerPoints(), table);
                } else if (player.getStatHolder() == 2){
                    player.setStatHolder(4);
                    winOrLoss(player, game, player.getPlayerPoints2(), table);
                }
                else {
                    dealCards(player, table);
                }

        } else {
            if (statusCopy != 1 || player.getPlayerPoints() > 21 || dealerPoints > 21 || player.getPlayerPoints() == 21) {
                    winOrLoss(player, game, player.getPlayerPoints(), table);
            } else {
                hitStandDoubleOrSplit(player, table);
            }
        }

    }


    public void winOrLoss(Player player, Game game, int playerPoints, Table table) {
        player.addDealerCards(dealerCards, deck);
        player.setPlayerPoints(calculateTotalPoints(player.getPlayerCards()));
        player.setPlayerPoints2(calculateTotalPoints(player.getPlayerCards2()));
        dealerPoints = calculateTotalPoints(dealerCards);

        if (playerPoints == 21 || playerPoints > dealerPoints && statusCopy == 2 && playerPoints < 21 || dealerPoints > 21 || statusCopy == 3 && playerPoints > dealerPoints) {
            game.youWon(player, game, table);
        } else if (dealerPoints == 21 || dealerPoints > playerPoints && statusCopy == 2 || playerPoints > 21 || statusCopy == 3 && dealerPoints > playerPoints) {
            game.youLost(player, game, table);
        } else if (dealerPoints == playerPoints) {
            game.youDraw(player, game, table);
        } else {
            hitStandDoubleOrSplit(player, table);
        }

    }

    public int calculateTotalPoints(ArrayList<Card> cardsOfX) {
        int points = 0;
        for (Card card : cardsOfX) {

            points = card.getNumber() + points;
        }
        return points;
    }
}





