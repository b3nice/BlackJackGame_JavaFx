package be.kdg.model;

import java.util.ArrayList;

public class Table {

    private final Deck deck;

    public Table() {
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


    public void dealCards(Player player) {
        dealerCards.clear();
        player.setSplitValidation("n");

        ArrayList<Card> cards = player.getPlayerCards();
        player.setFirstCardPlayer(deck.getDeckCards().get(0));
        deck.takeTopCard();
        cards.add(player.getFirstCardPlayer());
        player.setPlayerCards(cards);

        updatePoints(player);

        ArrayList<Card> cards2 = player.getPlayerCards();
        aceChecker(deck.getDeckCards().get(0), player.getPlayerPoints());
        player.setSecondCardPlayer(deck.getDeckCards().get(0));
        deck.takeTopCard();
        cards2.add(player.getSecondCardPlayer());

        player.setPlayerCards(cards2);

        updatePoints(player);
    }

    public void dealDealerCards(Player player){
        aceChecker(deck.getDeckCards().get(0), dealerPoints);
        dealerCards.add(deck.getDeckCards().get(0));
        deck.takeTopCard();

        updatePoints(player);
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
        if (player.getStatus() != 1 || player.getPlayerPoints() > 21 || dealerPoints > 21 || player.getPlayerPoints() == 21) {
            player.setWinOrLossValue(calculateWinOrLoss(player.getPlayerPoints(), player.getStatus(), player));
        }
    }

    private int calculateWinOrLoss(int points, int status, Player player) {
        addDealerCards(player);
        updatePoints(player);
        showCards(player);
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
            addCardPlayerHand(player);
            updatePoints(player);
            showCards(player);

        } else {
            addCardPlayerHand2(player);
            updatePoints(player);
            showCards2(player);
        }
        updatePoints(player);
    }

    public void doubleBet(int hand, Player player) {
        updatePoints(player);
        System.out.println("you double");

        if (hand == 1) {
            addCardPlayerHand(player);
            updatePoints(player);
            player.setBet(player.getBet() * 2);
            showCards(player);
        } else {
            addCardPlayerHand2(player);
            updatePoints(player);
            player.setBet2(player.getBet() * 2);
            showCards2(player);
        }
    }

    public void addCardPlayerHand(Player player) {
        aceChecker(deck.getDeckCards().get(0), player.getPlayerPoints());
        ArrayList<Card> cards = player.getPlayerCards();
        cards.add(deck.getDeckCards().get(0));
        player.setPlayerCards(cards);
        updatePoints(player);
        deck.takeTopCard();
    }

    public void addCardPlayerHand2(Player player) {
        aceChecker(deck.getDeckCards().get(0), player.getPlayerPoints2());
        ArrayList<Card> cards = player.getPlayerCards2();
        cards.add(deck.getDeckCards().get(0));
        player.setPlayerCards2(cards);
        deck.takeTopCard();
    }


    public void addDealerCards(Player player) {
        int limit = 300;
        if (player.getSplitValidation().equals("y")) {
            if (player.getStatHolder() != 1) {
                for (int i = 0; i < limit; i++) {
                    updatePoints(player);
                    if (dealerPoints < 17) {
                        showCards(player);

                        showCards2(player);

                        aceChecker(deck.getDeckCards().get(0), dealerPoints);
                        dealerCards.add(deck.getDeckCards().get(0));
                        deck.takeTopCard();
                        setDealerCards(dealerCards);
                        updatePoints(player);
                    } else {
                        i = 1000;
                    }
                }
            }
        } else {
            for (int i = 0; i < limit; i++) {
                updatePoints(player);
                if (dealerPoints < 17) {
                    aceChecker(deck.getDeckCards().get(0), dealerPoints);
                    dealerCards.add(deck.getDeckCards().get(0));
                    deck.takeTopCard();
                    setDealerCards(dealerCards);
                    updatePoints(player);
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

    public void updatePoints(Player player) {
        player.setPlayerPoints(calculateTotalPoints(player.getPlayerCards()));
        player.setPlayerPoints2(calculateTotalPoints(player.getPlayerCards2()));
        dealerPoints = calculateTotalPoints(dealerCards);
    }

    public void showCards(Player player) {
        System.out.println(player.getPlayerCards());
        System.out.println(player.getPlayerPoints());

        System.out.println(dealerCards);
        System.out.println(dealerPoints);
    }

    public void showCards2(Player player) {
        System.out.println(player.getPlayerCards2());
        System.out.println(player.getPlayerPoints2());

        System.out.println(dealerCards);
        System.out.println(dealerPoints);
    }

}





