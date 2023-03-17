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

        ArrayList<Card> cards = player.getPlayerCards();
        player.setFirstCardPlayer(deck.getDeckCards().get(0));
        deck.takeTopCard();
        cards.add(player.getFirstCardPlayer());
        player.setPlayerCards(cards);

        updatePoints();

        ArrayList<Card> cards2 = player.getPlayerCards();
        aceChecker(deck.getDeckCards().get(0), player.getPlayerPoints());
        player.setSecondCardPlayer(deck.getDeckCards().get(0));
        deck.takeTopCard();
        cards2.add(player.getSecondCardPlayer());

        player.setPlayerCards(cards2);

        aceChecker(deck.getDeckCards().get(0), dealerPoints);
        dealerCards.add(deck.getDeckCards().get(0));
        deck.takeTopCard();

        updatePoints();
    }

    public void splitOption() {
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
        }
    }


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
                    player.setSecondStatus(1);
                }
                case "Stand" -> {
                    updatePoints();
                    System.out.println("you stand");
                    player.setSecondStatus(2);
                }
                case "Double" -> {
                    doubleBet(hand2);
                    player.setSecondStatus(2);
                }
            }
        }
        updatePoints();
    }

    public void checkHitStandOrDouble(int hand1) {
        switch (player.getAnwser()) {
            case "Hit" -> {
                hit(hand1);
                player.setStatus(1);
            }
            case "Stand" -> {
                updatePoints();
                System.out.println("you stand");
                player.setStatus(2);
            }
            case "Double" -> {
                doubleBet(hand1);
                player.setStatus(2);
            }
        }
    }


    public void winOrLoss() {
        updatePoints();
        if (player.getStatus() != 1 || player.getPlayerPoints() > 21 || dealerPoints > 21 || player.getPlayerPoints() == 21) {
            player.setWinOrLossValue(calculateWinOrLoss(player.getPlayerPoints(), player.getStatus()));
        }
    }

    private int calculateWinOrLoss(int points, int status) {
        addDealerCards();
        updatePoints();
        showCards();
        if (points > 21) {
            return 2;
        } else if (points > dealerPoints && status == 2 || status == 3 && points > dealerPoints || dealerPoints > 21) {
            return 1;
        } else if (dealerPoints > points && status == 2 || status == 3 && dealerPoints > points) {
            return 2;
        } else if (dealerPoints == points) {
            return 3;
        } else {
            return -1;
        }
    }


    public int calculateWinOrLossForSplit() {
        updatePoints();
        if (player.getStatHolder() == 2) {
            if (player.getPlayerPoints() > 21 || player.getStatus() == 2 || player.getStatus() == 3) {
                player.setWinOrLossValue(calculateWinOrLoss(player.getPlayerPoints(), player.getStatus()));
                return 1;
            } else {
                return -1;
            }
        } else {
            if (player.getPlayerPoints2() > 21 || player.getSecondStatus() == 2 || player.getSecondStatus() == 3) {
                player.setWinOrLossValue2(calculateWinOrLoss(player.getPlayerPoints2(), player.getSecondStatus()));
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

    public void hit(int hand) {
        updatePoints();
        System.out.println("you hit");

        if (hand == 1) {
            addCardPlayerHand();
            updatePoints();
            showCards();

        } else {
            addCardPlayerHand2();
            updatePoints();
            showCards2();
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
            showCards();
        } else {
            addCardPlayerHand2();
            updatePoints();
            player.setBet2(player.getBet() * 2);
            showCards2();
        }
    }

    public void addCardPlayerHand() {
        aceChecker(deck.getDeckCards().get(0), player.getPlayerPoints());
        ArrayList<Card> cards = player.getPlayerCards();
        cards.add(deck.getDeckCards().get(0));
        player.setPlayerCards(cards);
        updatePoints();
        deck.takeTopCard();
    }

    public void addCardPlayerHand2() {
        aceChecker(deck.getDeckCards().get(0), player.getPlayerPoints2());
        ArrayList<Card> cards = player.getPlayerCards2();
        cards.add(deck.getDeckCards().get(0));
        player.setPlayerCards2(cards);
        deck.takeTopCard();
    }


    public void addDealerCards() {
        int limit = 300;
        if (player.getSplitValidation().equals("y")) {
            if (player.getStatHolder() != 1) {
                for (int i = 0; i < limit; i++) {
                    updatePoints();
                    if (dealerPoints < 17) {
                        showCards();

                        showCards2();

                        aceChecker(deck.getDeckCards().get(0), dealerPoints);
                        dealerCards.add(deck.getDeckCards().get(0));
                        deck.takeTopCard();
                        setDealerCards(dealerCards);
                        updatePoints();
                    } else {
                        i = 1000;
                    }
                }
            }
        } else {
            for (int i = 0; i < limit; i++) {
                updatePoints();
                if (dealerPoints < 17) {
                    aceChecker(deck.getDeckCards().get(0), dealerPoints);
                    dealerCards.add(deck.getDeckCards().get(0));
                    deck.takeTopCard();
                    setDealerCards(dealerCards);
                    updatePoints();
                } else {
                    i = 1000;
                }
            }
        }
    }

    public void aceChecker(Card card, int playerPoints) {
        if (card.getNumber() == 11 && card.getNumber() + playerPoints > 21) {
            card.setNumber(1);
            deck.getDeckCards().set(0, card);
        }
    }

    public void updatePoints() {
        player.setPlayerPoints(calculateTotalPoints(player.getPlayerCards()));
        player.setPlayerPoints2(calculateTotalPoints(player.getPlayerCards2()));
        dealerPoints = calculateTotalPoints(dealerCards);
    }

    public void showCards() {
        System.out.println(player.getPlayerCards());
        System.out.println(player.getPlayerPoints());

        System.out.println(dealerCards);
        System.out.println(dealerPoints);
    }

    public void showCards2() {
        System.out.println(player.getPlayerCards2());
        System.out.println(player.getPlayerPoints2());

        System.out.println(dealerCards);
        System.out.println(dealerPoints);
    }

}





