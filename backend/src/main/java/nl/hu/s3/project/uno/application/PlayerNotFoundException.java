package nl.hu.s3.project.uno.application;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(String username) {
        super("Unknown player: " + username);
    }
}
