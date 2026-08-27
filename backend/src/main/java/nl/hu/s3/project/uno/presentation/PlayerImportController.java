package nl.hu.s3.project.uno.presentation;

import nl.hu.s3.project.uno.application.PlayerNotFoundException;
import nl.hu.s3.project.uno.data.PlayerStore;
import nl.hu.s3.project.uno.domain.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/uno/players/imports")
public class PlayerImportController {
    private final PlayerStore playerStore;

    public PlayerImportController(PlayerStore playerStore) {
        this.playerStore = playerStore;
    }

    @PostMapping(consumes = "text/csv")
    public ResponseEntity<ImportedPlayersResponse> importPlayers(@RequestBody String csv) {
        if (csv.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        List<Player> importedPlayers = new ArrayList<>();
        Set<String> usernames = new HashSet<>();
        for (String line : csv.lines().toList()) {
            String[] values = line.split(",", -1);
            if (values.length != 3 || values[0].isBlank() || values[1].isBlank()
                    || (!values[2].equals("true") && !values[2].equals("false"))
                    || !usernames.add(values[0])) {
                return ResponseEntity.badRequest().build();
            }

            importedPlayers.add(new Player(values[0], values[1], Boolean.parseBoolean(values[2])));
        }

        for (Player player : importedPlayers) {
            if (playerExists(player.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }

        for (Player player : importedPlayers) {
            playerStore.create(player);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ImportedPlayersResponse(importedPlayers.size()));
    }

    private boolean playerExists(String username) {
        try {
            playerStore.findByUsername(username);
            return true;
        } catch (PlayerNotFoundException exception) {
            return false;
        }
    }

    public record ImportedPlayersResponse(int imported) {
    }
}
