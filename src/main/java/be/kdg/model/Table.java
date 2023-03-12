package be.kdg.model;

import java.util.ArrayList;

public class Table {

    private final Player player;
    private final Deck deck;

    public Table(Player player) {
        this.player = player;
        this.deck = new Deck();
    }

    private int dealerPoints;

    public int getDealerPoints() {
        return dealerPoints;
    }

    private ArrayList<Card> dealerCards = new ArrayList<>();

    public ArrayList<Card> getDealerCards() {
        return dealerCards;
    }

    public void setDealerCards(ArrayList<Card> dealerCards) {
        this.dealerCards = dealerCards;
    }


    public void dealCards() {
        dealerCards.clear();
        player.setSplitValidation("n");

        player.setFirstCardPlayer(deck.getDeckCards().get(0));
        deck.takeTopCard();

        player.setSecondCardPlayer(deck.getDeckCards().get(0));
        deck.takeTopCard();

        aceChecker(deck.getDeckCards().get(0));
        dealerCards.add(deck.getDeckCards().get(0));
        deck.takeTopCard();

        player.setPlayerPoints((calculateTotalPoints(player.getFirstCardPlayer(), player.getSecondCardPlayer())));
        dealerPoints = (calculateTotalPoints(dealerCards));
    }

    public void splitOption() {
        if (player.getFirstCardPlayer().getNumber() == player.getSecondCardPlayer().getNumber()) {
            if (player.getSplitValidation().equals("y")) {
                player.setBet2(player.getBet());

                ArrayList<Card> cards = player.getPlayerCards();
                cards.remove(1);
                player.setPlayerCards(cards);

                ArrayList<Card> cards2 = player.getPlayerCards2();
                cards2.add(0, player.getSecondCardPlayer());
                player.setPlayerCards2(cards2);


                System.out.println("You split, your second deck is: " + player.getPlayerCards2());
                updatePoints();
            } else {
                ArrayList<Card> cards = player.getPlayerCards();
                cards.add(player.getFirstCardPlayer());
                cards.add(player.getSecondCardPlayer());
                player.setPlayerCards(cards);
            }
        } else {
            ArrayList<Card> cards = player.getPlayerCards();
            cards.add(player.getFirstCardPlayer());
            cards.add(player.getSecondCardPlayer());
            player.setPlayerCards(cards);
        }
    }

    int status = 1;
    int secondStatus = 1;

    public void hitStandDoubleOrSplit() {
        updatePoints();

        int hand1 = 1;

        checkHitStandOrDouble(hand1);
        player.setAnwser(" ");
    }


    public void splitGame() {
        int hand1 = 1;
        int hand2 = 2;

        if (player.getStatHolder() == 2) {
            System.out.println("Do you want to Hit, Stand or Double for your first deck: ");
            checkHitStandOrDouble(hand1);
        } else {
            System.out.println("Do you want to Hit, Stand or Double for your second deck: ");
            switch (player.getAnwser()) {
                case "Hit" -> {
                    hit(hand2);
                    secondStatus = 1;
                }
                case "Stand" -> {

                    updatePoints();
                    System.out.println("you stand");

                    secondStatus = 2;
                }
                case "Double" -> {
                    doubleBet(hand2);
                    secondStatus = 3;
                }
            }
        }
        updatePoints();
    }

    public void checkHitStandOrDouble(int hand1) {
        switch (player.getAnwser()) {
            case "Hit" -> {
                hit(hand1);
                status = 1;
            }
            case "Stand" -> {
                updatePoints();
                System.out.println("you stand");
                status = 2;
            }
            case "Double" -> {
                doubleBet(hand1);
                status = 3;
            }
        }
    }


    public void winOrLoss() {
        updatePoints();
        if (player.getSplitValidation().equals("y")) {
            if (player.getStatHolder() == 2) {
                player.setStatHolder(1);
            } else {
                player.setWinOrLossValue(calculateWinOrLoss(player.getPlayerPoints(), status));
                player.setWinOrLossValue2(calculateWinOrLoss(player.getPlayerPoints2(), secondStatus));
            }
        } else if (status != 1 || player.getPlayerPoints() > 21 || dealerPoints > 21 || player.getPlayerPoints() == 21) {
            player.setWinOrLossValue(calculateWinOrLoss(player.getPlayerPoints(), status));
        }
    }


    private int calculateWinOrLoss(int points, int status) {
        if (status != 10) {
            updatePoints();
            addDealerCards();
        }
        if (points > dealerPoints && status == 2 && points <= 21 || status == 3 && points > dealerPoints && points < 21 || dealerPoints > 21) {
            return 1;
        } else if (dealerPoints > points && status == 2 || status == 3 && dealerPoints > points || points > 21) {
            return 2;
        } else if (dealerPoints == points) {
            return 3;
        } else {
            return -1;
        }
    }

    public int calculateTotalPoints(ArrayList<Card> cardsOfX) {
        int points = 0;
        for (Card card : cardsOfX) {
            points = card.getNumber() + points;
        }
        return points;
    }

    public int calculateTotalPoints(Card card1, Card card2) {
        int points = 0;

        points = card1.getNumber() + points;
        points = card2.getNumber() + points;

        return points;
    }

    public void hit(int hand) {
        updatePoints();
        System.out.println("you hit");

        if (hand == 1) {
            addCardPlayerHand();
            updatePoints();

        } else {
            addCardPlayerHand2();
            updatePoints();
        }
        updatePoints();
    }

    public void doubleBet(int hand) {
        updatePoints();
        System.out.println("you double");

        if (hand == 1) {
            addCardPlayerHand();
            updatePoints();
            player.setBet(player.getBet() * 2);
        } else {
            addCardPlayerHand2();
            player.setBet2(player.getBet() * 2);
        }
        updatePoints();
    }

    public void addCardPlayerHand() {
        aceChecker(deck.getDeckCards().get(0));
        ArrayList<Card> cards = player.getPlayerCards();
        cards.add(deck.getDeckCards().get(0));
        player.setPlayerCards(cards);
        updatePoints();
        deck.takeTopCard();
    }

    public void addCardPlayerHand2() {
        aceChecker(deck.getDeckCards().get(0));
        ArrayList<Card> cards = player.getPlayerCards2();
        cards.add(deck.getDeckCards().get(0));
        player.setPlayerCards2(cards);
        deck.takeTopCard();
    }


    public void addDealerCards() {
        int limit = 20;
        if (player.getSplitValidation().equals("y")) {
            for (int i = 0; i < limit; i++) {
                updatePoints();
                if (dealerPoints < 17 && dealerPoints < player.getPlayerPoints() && dealerPoints < player.getPlayerPoints2()) {
                    System.out.println(player.getName() + ":+++++++++++++");
                    System.out.println(player.getPlayerCards2());
                    System.out.println(player.getPlayerPoints2());

                    aceChecker(deck.getDeckCards().get(0));
                    dealerCards.add(deck.getDeckCards().get(0));
                    deck.takeTopCard();
                    setDealerCards(dealerCards);
                    updatePoints();
                } else {
                    i = 1000;
                }
            }
        } else {
            for (int i = 0; i < limit; i++) {
                updatePoints();
                if (dealerPoints < 17 && dealerPoints < player.getPlayerPoints()) {
                    aceChecker(deck.getDeckCards().get(0));
                    dealerCards.add(deck.getDeckCards().get(0));
                    deck.takeTopCard();
                    setDealerCards(dealerCards);
                    updatePoints();
                } else {
                    i = 1000;
                }
            }
        }
        System.out.println(player.getName() + ":");
        System.out.println(player.getPlayerCards());
        System.out.println(player.getPlayerPoints());

        System.out.println("Dealer" + ":");
        System.out.println(dealerCards);
        System.out.println(dealerPoints);

        System.out.println("playerhand ========== " + player.getPlayerCards());
        System.out.println("Dealerhand ========== " + dealerCards);
    }

    public void aceChecker(Card card) {
        if (card.getNumber() == 11 && card.getNumber() + player.getPlayerPoints() > 21) {
            card.setNumber(1);
            deck.getDeckCards().set(0, card);
        }
    }

    public void updatePoints() {
        player.setPlayerPoints(calculateTotalPoints(player.getPlayerCards()));
        player.setPlayerPoints2(calculateTotalPoints(player.getPlayerCards2()));
        dealerPoints = calculateTotalPoints(dealerCards);
    }

}





