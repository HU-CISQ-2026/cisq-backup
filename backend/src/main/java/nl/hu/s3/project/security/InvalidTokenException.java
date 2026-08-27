package nl.hu.s3.project.security;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Invalid authentication token");
    }
}
