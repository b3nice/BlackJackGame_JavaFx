package be.kdg.model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private final ArrayList<Card> deckCards = new ArrayList<>();

    public ArrayList<Card> getDeckCards() {
        return deckCards;
    }

    public Deck() {
        Suit Hearts = Suit.HEARTS;
        Suit Spades = Suit.SPADES;
        Suit Clubs = Suit.CLUBS;
        Suit Diamonds = Suit.DIAMONDS;

        for (int i = 2; i <= 10; i++) {
            deckCards.add(new Card(i, Hearts));
            deckCards.add(new Card(i, Spades));
            deckCards.add(new Card(i, Clubs));
            deckCards.add(new Card(i, Diamonds));
        }

        ArrayList<String> nameCardQKJA = new ArrayList<>();
        nameCardQKJA.add("queen");
        nameCardQKJA.add("king");
        nameCardQKJA.add("jack");
        nameCardQKJA.add("ace");

        for (String nameCard : nameCardQKJA) {
            for (Suit suit : Suit.values()) {
                int value = nameCard.equals("ace") ? 11 : 10;
                deckCards.add(new Card(value, suit, nameCard));
            }
        }
        shuffleDeckCards(deckCards);
    }

    public void shuffleDeckCards(ArrayList<Card> deckCards){
        Collections.shuffle(deckCards);
    }
    public void takeTopCard(){
        deckCards.remove(0);
    }
}
