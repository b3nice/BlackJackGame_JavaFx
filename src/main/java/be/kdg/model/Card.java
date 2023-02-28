package be.kdg.model;

public class Card {
    private int number;
    private Suit suit;
    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public int getNumber() {
        return number;
    }

    public Card(int number, Suit suit) {
        this.number = number;
        this.suit = suit;
        this.imagePath = "PNG-cards-1.3/" + number + "_of_" + suit.toString().toLowerCase()+ ".png";
    }
    public Card(int number, Suit suit,String name) {
        this.number = number;
        this.suit = suit;
        this.imagePath = "PNG-cards-1.3/" + name + "_of_" + suit.toString().toLowerCase()+ "2.png";

    }

    @Override
    public String toString() {
        return  number + " " + suit + " " + imagePath;
    }
}
