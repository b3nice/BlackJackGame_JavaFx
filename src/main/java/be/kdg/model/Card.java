package be.kdg.model;

public class Card {
    private int number;
    private final Suit suit;
    private final String imagePath;


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Card(int number, Suit suit) {
        this.number = number;
        this.suit = suit;
        this.imagePath = "/PNG-cards-1.3/" + number + "_of_" + suit.toString().toLowerCase()+ ".png";
    }
    public Card(int number, Suit suit,String name) {
        this.number = number;
        this.suit = suit;
        this.imagePath = "/PNG-cards-1.3/" + name + "_of_" + suit.toString().toLowerCase()+ ".png";

    }

    @Override
    public String toString() {
        return imagePath;
    }
}
