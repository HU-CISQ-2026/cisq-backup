package nl.hu.s3.project.uno.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Game {
    private List<Player> players = new ArrayList<>();
    private List<Card> deck = new ArrayList<>();
    private Player currentPlayer = null;
    private Card lastPlayedCard = null;

    private Game() {}

    public static Game restore(Card lastPlayedCard, String currentPlayerUsername, List<Player> players, List<Card> deck) {
        Game game = new Game();
        game.players = new ArrayList<>(players);
        game.deck = new ArrayList<>(deck);
        game.lastPlayedCard = Objects.requireNonNull(lastPlayedCard);
        game.currentPlayer = game.findPlayer(currentPlayerUsername);
        return game;
    }

    public synchronized void play(String username, Card card) {
        Player player = findPlayer(username);
        if (player != currentPlayer) {
            throw new IllegalArgumentException("It is not this player's turn");
        }
        if (!card.getValue().isNumber()) {
            throw new IllegalArgumentException("Only number cards are supported");
        }
        if (!player.hasCard(card)) {
            throw new IllegalArgumentException("The player does not have this card");
        }
        if (!card.isMatch(lastPlayedCard)) {
            throw new IllegalArgumentException("The card does not match the last played card");
        }

        player.removeCard(card);
        lastPlayedCard = card;
        currentPlayer = players.get((players.indexOf(player) + 1) % players.size());
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Card getLastPlayedCard() {
        return this.lastPlayedCard;
    }

    private Player findPlayer(String username) {
        return players.stream()
                .filter(player -> player.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown player"));
    }
}
