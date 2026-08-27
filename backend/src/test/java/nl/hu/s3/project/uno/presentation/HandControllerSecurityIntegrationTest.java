package nl.hu.s3.project.uno.presentation;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HandControllerSecurityIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Value("${security.jwt.secret}")
    private String secret;

    @Test
    void returnsTheAuthenticatedPlayersOwnHand() throws Exception {
        mockMvc.perform(get("/uno/games/assessment-game/players/alice/hand")
                        .header("Authorization", bearerToken("alice", Instant.now().plusSeconds(300))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("alice"))
                .andExpect(jsonPath("$.cards[0]").value("R7"));
    }

    @Test
    void rejectsAMissingToken() throws Exception {
        mockMvc.perform(get("/uno/games/assessment-game/players/alice/hand"));
        // TODO: controleer de HTTP-status.
    }

    @Test
    void rejectsAModifiedToken() throws Exception {
        mockMvc.perform(get("/uno/games/assessment-game/players/alice/hand")
                .header("Authorization", tamperedToken("alice", "mallory")));
        // TODO: controleer de HTTP-status.
    }

    @Test
    void rejectsAnExpiredToken() throws Exception {
        mockMvc.perform(get("/uno/games/assessment-game/players/alice/hand")
                .header("Authorization", bearerToken("alice", Instant.now().minusSeconds(300))));
        // TODO: controleer de HTTP-status.
    }

    @Test
    void forbidsAccessToAnotherPlayersHand() throws Exception {
        mockMvc.perform(get("/uno/games/assessment-game/players/bob/hand")
                .header("Authorization", bearerToken("alice", Instant.now().plusSeconds(300))));
        // TODO: controleer de HTTP-status.
    }

    @Test
    void hidesTechnicalDetailsForAnUnknownGame() throws Exception {
        mockMvc.perform(get("/uno/games/unknown/players/alice/hand")
                        .header("Authorization", bearerToken("alice", Instant.now().plusSeconds(300))))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    private String bearerToken(String username, Instant expiration) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        String token = Jwts.builder()
                .subject(username)
                .expiration(Date.from(expiration))
                .signWith(key)
                .compact();
        return "Bearer " + token;
    }

    private String tamperedToken(String originalUsername, String replacementUsername) {
        String token = bearerToken(originalUsername, Instant.now().plusSeconds(300))
                .substring("Bearer ".length());
        String[] parts = token.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8)
                .replace(originalUsername, replacementUsername);
        parts[1] = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        return "Bearer " + String.join(".", parts);
    }
}
