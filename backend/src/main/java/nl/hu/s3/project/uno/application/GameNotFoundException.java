package nl.hu.s3.project.uno.application;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String gameId) {
        super("Unknown game: " + gameId);
    }
}
