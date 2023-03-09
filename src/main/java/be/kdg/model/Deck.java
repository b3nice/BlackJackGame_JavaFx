package be.kdg.model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> deckCards = new ArrayList<>();

    public void setDeckCards(ArrayList<Card> deckCards) {
        this.deckCards = deckCards;
    }

    public ArrayList<Card> getDeckCards() {
        return deckCards;
    }

    public Deck() {
        Suit Hearts = Suit.HEARTS;
        Suit Spades = Suit.SPADES;
        Suit Clubs = Suit.CLUBS;
        Suit Diamonds = Suit.DIAMONDS;

        for (int i = 10; i <= 10; i++) {
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

        for (String nameCard:nameCardQKJA) {
            deckCards.add(new Card(10, Hearts, nameCard));
            deckCards.add(new Card(10, Spades, nameCard));
            deckCards.add(new Card(10, Clubs, nameCard));
            deckCards.add(new Card(11, Diamonds, nameCard));
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
