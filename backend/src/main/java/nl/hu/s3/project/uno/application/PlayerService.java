package nl.hu.s3.project.uno.application;

import nl.hu.s3.project.uno.data.PlayerStore;
import nl.hu.s3.project.uno.domain.Player;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerStore playerStore;

    public PlayerService(PlayerStore playerStore) {
        this.playerStore = playerStore;
    }

    public Player create(String username, String displayName, boolean ready) {
        return playerStore.create(new Player(username, displayName, ready));
    }

    public List<Player> findAll() {
        return playerStore.findAll();
    }

    public Player findByUsername(String username) {
        return playerStore.findByUsername(username);
    }

    public Player update(String username, String displayName, boolean ready) {
        return playerStore.update(username, displayName, ready);
    }

    public void delete(String username) {
        playerStore.delete(username);
    }
}
