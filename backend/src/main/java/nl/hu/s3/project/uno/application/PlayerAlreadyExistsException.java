package nl.hu.s3.project.uno.application;

public class PlayerAlreadyExistsException extends RuntimeException {
    public PlayerAlreadyExistsException(String username) {
        super("Player already exists: " + username);
    }
}
