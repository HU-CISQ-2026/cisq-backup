package nl.hu.s3.project.uno.data;

import nl.hu.s3.project.uno.application.GameNotFoundException;
import nl.hu.s3.project.uno.domain.Card;
import nl.hu.s3.project.uno.domain.Color;
import nl.hu.s3.project.uno.domain.Game;
import nl.hu.s3.project.uno.domain.Player;
import nl.hu.s3.project.uno.domain.Value;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryGameStore implements GameStore {
    public static final String ASSESSMENT_GAME_ID = "assessment-game";

    private final Map<String, Game> games = new HashMap<>();

    public InMemoryGameStore() {
        Player alice = new Player("alice", List.of(
                Card.of(Color.RED, Value.SEVEN),
                Card.of(Color.BLUE, Value.ONE),
                Card.of(Color.YELLOW, Value.TWO)
        ));
        Player bob = new Player("bob", List.of(Card.of(Color.GREEN, Value.FIVE)));

        games.put(ASSESSMENT_GAME_ID, Game.restore(
                Card.of(Color.RED, Value.ONE),
                "alice",
                List.of(alice, bob),
                List.of()
        ));
    }

    @Override
    public Game findById(String gameId) {
        Game game = games.get(gameId);
        if (game == null) {
            throw new GameNotFoundException(gameId);
        }
        return game;
    }
}
