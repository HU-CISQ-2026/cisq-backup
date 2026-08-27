package nl.hu.s3.project.uno.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Card {
    private final Color color;
    private final Value value;

    private Card(Color color, Value value) {
        this.color = color;
        this.value = value;
    }

    public static Card of(Color color, Value value){
        return new Card(color, value);
    }

    public Color getColor() {
        return color;
    }
    public Value getValue() {
        return value;
    }

    public boolean isMatch(Card card){
        return this.color == card.color || this.value == card.value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Card card)) return false;
        return color == card.color && value == card.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, value);
    }

    @Override
    public String toString() {
        return this.color.toString() + this.value.toString();
    }

    public static Card parse(String card) {
        if (card.length() != 2) throw new IllegalArgumentException("Invalid card: " + card);
        String color = card.substring(0, 1);
        String value = card.substring(1);
        return new Card(Color.parse(color), Value.parse(value));
    }

    public static List<Card> allCards(){
        List<Card> cards = new ArrayList<>();
        for(Color c: Color.values()){
            for(Value v: Value.values()){
                cards.add(new Card(c, v));
            }
        }
        return cards;
    }
}
