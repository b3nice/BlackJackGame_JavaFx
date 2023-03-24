package be.kdg.model;
import java.util.ArrayList;

public class Dealer {
    private int dealerPoints;
    private final ArrayList<Card> dealerCards = new ArrayList<>();
    private Deck deck;
    private Table table;

    public Dealer() {
        this.dealerPoints = 0;
        this.deck = new Deck();
        this.dealerCards.clear();
    }

    /**
     * Deals the cards to the player and the dealer
     * @param player current player
     */
    public void dealCards(Player player) {
        setDeck(new Deck());
        dealerCards.clear();
        player.setSplitValidation("n");

        ArrayList<Card> cards = player.getPlayerCards();
        player.setFirstCardPlayer(deck.getDeckCards().get(0));
        deck.takeTopCard();
        cards.add(player.getFirstCardPlayer());
        player.setPlayerCards(cards);

        table.updatePoints(player);

        ArrayList<Card> cards2 = player.getPlayerCards();
        aceChecker(deck.getDeckCards().get(0), player.getPlayerPoints());
        player.setSecondCardPlayer(deck.getDeckCards().get(0));
        deck.takeTopCard();
        cards2.add(player.getSecondCardPlayer());

        player.setPlayerCards(cards2);

        table.updatePoints(player);
    }

    public void dealDealerCards(Player player) {
        aceChecker(deck.getDeckCards().get(0), dealerPoints);
        dealerCards.add(deck.getDeckCards().get(0));
        deck.takeTopCard();

        table.updatePoints(player);
    }

    public void addCardPlayerHand(Player player) {
        aceChecker(deck.getDeckCards().get(0), player.getPlayerPoints());
        ArrayList<Card> cards = player.getPlayerCards();
        cards.add(deck.getDeckCards().get(0));
        player.setPlayerCards(cards);
        table.updatePoints(player);
        deck.takeTopCard();
    }

    /**
     * Adds a card to the player's second hand
     * @param player current player
     */
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
                    table.updatePoints(player);
                    if (dealerPoints < 17) {
                        aceChecker(deck.getDeckCards().get(0), dealerPoints);
                        dealerCards.add(deck.getDeckCards().get(0));
                        deck.takeTopCard();
                        table.updatePoints(player);
                    } else {
                        i = 1000;
                    }
                }
            }
        } else {
            for (int i = 0; i < limit; i++) {
                table.updatePoints(player);
                if (dealerPoints < 17) {
                    aceChecker(deck.getDeckCards().get(0), dealerPoints);
                    dealerCards.add(deck.getDeckCards().get(0));
                    deck.takeTopCard();
                    table.updatePoints(player);
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

    public int getDealerPoints() {
        return dealerPoints;
    }

    public void setDealerPoints(int dealerPoints) {
        this.dealerPoints = dealerPoints;
    }

    public ArrayList<Card> getDealerCards() {
        return dealerCards;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
