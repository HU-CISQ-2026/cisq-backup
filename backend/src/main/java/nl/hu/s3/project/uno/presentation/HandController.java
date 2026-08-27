package nl.hu.s3.project.uno.presentation;

import nl.hu.s3.project.security.InvalidTokenException;
import nl.hu.s3.project.security.JwtService;
import nl.hu.s3.project.uno.application.GameNotFoundException;
import nl.hu.s3.project.uno.application.HandService;
import nl.hu.s3.project.uno.application.PlayerNotFoundException;
import nl.hu.s3.project.uno.domain.Card;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/uno/games/{gameId}/players/{username}/hand")
public class HandController {
    private final HandService handService;
    private final JwtService jwtService;

    public HandController(HandService handService, JwtService jwtService) {
        this.handService = handService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public HandResponse findHand(@PathVariable String gameId, @PathVariable String username,
                                 @RequestHeader(name = "Authorization", required = false)
                                 String authorizationHeader) {
        jwtService.readSubject(authorizationHeader);
        List<String> cards = handService.findHand(gameId, username).stream()
                .map(Card::toString)
                .toList();
        return new HandResponse(username, cards);
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    void handleInvalidToken() {
    }

    @ExceptionHandler({GameNotFoundException.class, PlayerNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void handleNotFound() {
    }

    public record HandResponse(String username, List<String> cards) {
    }
}
