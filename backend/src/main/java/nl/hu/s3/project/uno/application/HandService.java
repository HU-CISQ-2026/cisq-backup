package nl.hu.s3.project.uno.application;

import nl.hu.s3.project.uno.data.GameStore;
import nl.hu.s3.project.uno.domain.Card;
import nl.hu.s3.project.uno.domain.Game;
import nl.hu.s3.project.uno.domain.Player;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandService {
    private final GameStore gameStore;

    public HandService(GameStore gameStore) {
        this.gameStore = gameStore;
    }

    public List<Card> findHand(String gameId, String username) {
        Game game = gameStore.findById(gameId);
        return game.getPlayers().stream()
                .filter(player -> player.getUsername().equals(username))
                .findFirst()
                .map(Player::getHand)
                .orElseThrow(() -> new PlayerNotFoundException(username));
    }
}
