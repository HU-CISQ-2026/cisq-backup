package nl.hu.s3.project.uno.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {

    private final String username;
    private String displayName;
    private boolean ready;
    private List<Card> hand = new ArrayList<>();

    public Player(String username) {
        this(username, username, false);
    }

    public Player(String username, List<Card> hand) {
        this(username, username, false);
        this.hand = new ArrayList<>(hand);
    }

    public Player(String username, String displayName, boolean ready) {
        this.username = username;
        this.displayName = displayName;
        this.ready = ready;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isReady() {
        return ready;
    }

    public List<Card> getHand() {
        return Collections.unmodifiableList(hand);
    }

    boolean hasCard(Card card) {
        return hand.contains(card);
    }

    void removeCard(Card card) {
        hand.remove(card);
    }

    void addCard(Card card) {
        hand.add(card);
    }

    public void updateProfile(String displayName, boolean ready) {
        this.displayName = displayName;
        this.ready = ready;
    }

    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                '}';
    }
}
