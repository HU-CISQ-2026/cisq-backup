package nl.hu.s3.project.uno.presentation;

import nl.hu.s3.project.security.UserProfile;
import nl.hu.s3.project.uno.application.GameNotFoundException;
import nl.hu.s3.project.uno.application.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/uno")
public class UnoController {
    private final GameService gameService;

    public UnoController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/games/{gameId}/plays")
    public ResponseEntity<Void> playCard(@PathVariable String gameId, UserProfile user,
                                         @RequestBody Map<String, String> request) {
        String cardCode = request.get("card");
        if (cardCode == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            gameService.playCard(gameId, user.getUsername(), cardCode);
            return ResponseEntity.noContent().build();
        } catch (GameNotFoundException exception) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
