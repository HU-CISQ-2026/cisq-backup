package nl.hu.s3.project.uno.presentation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerImportControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void importsPlayerProfilesFromCsv() throws Exception {
        mockMvc.perform(post("/uno/players/imports")
                        .contentType("text/csv")
                        .content("""
                                carol,Carol,true
                                dave,Dave,false
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.imported").value(2));

        mockMvc.perform(get("/uno/players/carol"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.displayName").value("Carol"))
                .andExpect(jsonPath("$.ready").value(true));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "invalid",
            ",Eve,true",
            "eve,,true",
            "eve,Eve,ready"
    })
    void rejectsInvalidCsvWithoutAddingEarlierRows(String invalidLine) throws Exception {
        mockMvc.perform(post("/uno/players/imports")
                        .contentType("text/csv")
                        .content("frank,Frank,false\n" + invalidLine))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/uno/players/frank"))
                .andExpect(status().isNotFound());
    }

    @Test
    void rejectsAnExistingUsernameWithoutAddingEarlierRows() throws Exception {
        mockMvc.perform(post("/uno/players/imports")
                        .contentType("text/csv")
                        .content("""
                                grace,Grace,false
                                alice,Changed Alice,true
                                """))
                .andExpect(status().isConflict());

        mockMvc.perform(get("/uno/players/grace"))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/uno/players/alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.displayName").value("Alice"))
                .andExpect(jsonPath("$.ready").value(true));
    }
}
