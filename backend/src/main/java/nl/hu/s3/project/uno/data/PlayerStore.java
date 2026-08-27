package nl.hu.s3.project.uno.data;

import nl.hu.s3.project.uno.domain.Player;

import java.util.List;

public interface PlayerStore {
    Player create(Player player);

    List<Player> findAll();

    Player findByUsername(String username);

    Player update(String username, String displayName, boolean ready);

    void delete(String username);
}
