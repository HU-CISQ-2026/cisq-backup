package nl.hu.s3.project.uno.data;

import nl.hu.s3.project.uno.domain.Game;

public interface GameStore {
    Game findById(String gameId);
}
