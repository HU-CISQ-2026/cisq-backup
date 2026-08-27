package nl.hu.s3.project.uno.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UnoControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void currentPlayerCanPlayAMatchingNumberCard() throws Exception {
        mockMvc.perform(post("/uno/games/assessment-game/plays")
                        .header("X-User", "alice")
                        .contentType("application/json")
                        .content("""
                                {"card":"R7"}
                                """))
                .andExpect(status().isNoContent());
    }

    @Test
    void rejectsARequestWithoutACard() {
        // TODO: voeg een integratiecontrole voor deze situatie toe.
    }
}
