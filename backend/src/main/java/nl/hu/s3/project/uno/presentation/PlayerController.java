package nl.hu.s3.project.uno.presentation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import nl.hu.s3.project.uno.application.PlayerAlreadyExistsException;
import nl.hu.s3.project.uno.application.PlayerNotFoundException;
import nl.hu.s3.project.uno.application.PlayerService;
import nl.hu.s3.project.uno.domain.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/uno/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<PlayerResponse> create(@Valid @RequestBody CreatePlayerRequest request) {
        Player player = playerService.create(request.username(), request.displayName(), request.ready());
        return ResponseEntity.status(HttpStatus.CREATED).body(PlayerResponse.from(player));
    }

    @GetMapping
    public List<PlayerResponse> findAll() {
        return playerService.findAll().stream().map(PlayerResponse::from).toList();
    }

    @GetMapping("/{username}")
    public PlayerResponse findByUsername(@PathVariable String username) {
        return PlayerResponse.from(playerService.findByUsername(username));
    }

    @PutMapping("/{username}")
    public PlayerResponse update(@PathVariable String username, @Valid @RequestBody UpdatePlayerRequest request) {
        return PlayerResponse.from(playerService.update(username, request.displayName(), request.ready()));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> delete(@PathVariable String username) {
        playerService.delete(username);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void handlePlayerNotFound() {
    }

    @ExceptionHandler(PlayerAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    void handlePlayerAlreadyExists() {
    }

    public record CreatePlayerRequest(@NotBlank String username, @NotBlank String displayName, boolean ready) {
    }

    public record UpdatePlayerRequest(@NotBlank String displayName, boolean ready) {
    }

    public record PlayerResponse(String username, String displayName, boolean ready) {
        static PlayerResponse from(Player player) {
            return new PlayerResponse(player.getUsername(), player.getDisplayName(), player.isReady());
        }
    }
}
