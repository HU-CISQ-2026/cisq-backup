package nl.hu.s3.project.uno.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        Player alice = new Player("alice", List.of(
                Card.parse("R7"),
                Card.parse("B1"),
                Card.parse("Y2")
        ));
        Player bob = new Player("bob", List.of(Card.parse("G5")));
        game = Game.restore(Card.parse("R1"), "alice", List.of(alice, bob), List.of());
    }

    @Test
    void rejectsAPlayWhenItIsNotThePlayersTurn() {
        // TODO: voeg een gedragscontrole voor deze situatie toe.
    }

    @Test
    void rejectsACardThatIsNotInThePlayersHand() {
        // TODO: voeg een gedragscontrole voor deze situatie toe.
    }

    @Test
    void rejectsANonMatchingCard() {
        // TODO: voeg een gedragscontrole voor deze situatie toe.
    }

    @Test
    void rejectsAnActionCard() {
        // TODO: voeg een gedragscontrole voor deze situatie toe.
    }

    @ParameterizedTest
    @ValueSource(strings = {"R7", "B1"})
    void currentPlayerCanPlayCardsThatMatchByColorOrValue(String cardCode) {
        // TODO: controleer met dezelfde uitvoering en assertions beide passende kaarten.
    }

    @Test
    void drawsOneCardWhenNoCardInHandMatches() {
        // TODO: schrijf deze test voordat je de trekfunctionaliteit toevoegt.
    }

    @Test
    void allowsTheCurrentPlayerToPlayADrawnMatchingCard() {
        // TODO: schrijf deze test voordat je de trekfunctionaliteit toevoegt.
    }

    @Test
    void endsTheTurnWhenTheDrawnCardDoesNotMatch() {
        // TODO: schrijf deze test voordat je de trekfunctionaliteit toevoegt.
    }

    @Test
    void rejectsADrawWhenItIsNotThePlayersTurn() {
        // TODO: schrijf deze test voordat je de trekfunctionaliteit toevoegt.
    }

    @Test
    void rejectsADrawWhenThePlayerHasAMatchingNumberCard() {
        // TODO: schrijf deze test voordat je de trekfunctionaliteit toevoegt.
    }

    @Test
    void rejectsADrawWhenTheDeckIsEmpty() {
        // TODO: schrijf deze test voordat je de trekfunctionaliteit toevoegt.
    }
}
