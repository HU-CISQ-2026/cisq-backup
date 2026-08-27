package nl.hu.s3.project.uno.application;

import nl.hu.s3.project.uno.data.GameStore;
import nl.hu.s3.project.uno.domain.Card;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private final GameStore gameStore;

    public GameService(GameStore gameStore) {
        this.gameStore = gameStore;
    }

    public void playCard(String gameId, String username, String cardCode) {
        gameStore.findById(gameId).play(username, Card.parse(cardCode));
    }
}
