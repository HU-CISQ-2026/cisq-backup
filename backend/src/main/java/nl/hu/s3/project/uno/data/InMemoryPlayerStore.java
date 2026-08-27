package nl.hu.s3.project.uno.data;

import nl.hu.s3.project.uno.application.PlayerAlreadyExistsException;
import nl.hu.s3.project.uno.application.PlayerNotFoundException;
import nl.hu.s3.project.uno.domain.Player;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryPlayerStore implements PlayerStore {
    private final Map<String, Player> players = new LinkedHashMap<>();

    public InMemoryPlayerStore() {
        create(new Player("alice", "Alice", true));
        create(new Player("bob", "Bob", false));
    }

    @Override
    public Player create(Player player) {
        if (players.putIfAbsent(player.getUsername(), player) != null) {
            throw new PlayerAlreadyExistsException(player.getUsername());
        }
        return player;
    }

    @Override
    public List<Player> findAll() {
        return new ArrayList<>(players.values());
    }

    @Override
    public Player findByUsername(String username) {
        return getPlayer(username);
    }

    @Override
    public Player update(String username, String displayName, boolean ready) {
        Player player = getPlayer(username);
        player.updateProfile(displayName, ready);
        return player;
    }

    @Override
    public void delete(String username) {
        getPlayer(username);
        players.remove(username);
    }

    private Player getPlayer(String username) {
        Player player = players.get(username);
        if (player == null) {
            throw new PlayerNotFoundException(username);
        }
        return player;
    }
}
