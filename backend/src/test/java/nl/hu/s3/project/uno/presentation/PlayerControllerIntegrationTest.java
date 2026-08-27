package nl.hu.s3.project.uno.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createsAPlayer() throws Exception {
        mockMvc.perform(post("/uno/players")
                .contentType(APPLICATION_JSON)
                .content("""
                        {
                          "username": "carol",
                          "displayName": "Carol",
                          "ready": true
                        }
                        """));
        // TODO: controleer de status en de belangrijkste velden in de response.
    }

    @Test
    void retrievesAPlayer() throws Exception {
        mockMvc.perform(get("/uno/players/alice"));
        // TODO: controleer de status en het teruggegeven spelersprofiel.
    }

    @Test
    void updatesAPlayer() throws Exception {
        mockMvc.perform(put("/uno/players/bob")
                .contentType(APPLICATION_JSON)
                .content("""
                        {
                          "displayName": "Bobby",
                          "ready": true
                        }
                        """));
        // TODO: controleer de status en de gewijzigde velden.
    }

    @Test
    void deletesAPlayer() throws Exception {
        mockMvc.perform(post("/uno/players")
                .contentType(APPLICATION_JSON)
                .content("""
                        {
                          "username": "dave",
                          "displayName": "Dave",
                          "ready": false
                        }
                        """));

        mockMvc.perform(delete("/uno/players/dave"));
        // TODO: controleer de status van de delete-response.
    }

    @Test
    void returnsNotFoundForAnUnknownPlayer() throws Exception {
        mockMvc.perform(get("/uno/players/unknown"));
        // TODO: controleer dat een onbekende speler de juiste status oplevert.
    }
}
