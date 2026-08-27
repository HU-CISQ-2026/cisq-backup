package nl.hu.s3.project.uno.data;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import nl.hu.s3.project.uno.domain.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Te gebruiken in een Entity door:
 *
 * @Convert(converter = CardListConverter.class)
 * private List<Card> cards;
 */
@Converter
public class CardListConverter implements AttributeConverter<List<Card>, String> {
    @Override
    public String convertToDatabaseColumn(List<Card> attribute) {
        return ""; //Hoe ging dat ook alweer...
    }

    @Override
    public List<Card> convertToEntityAttribute(String dbData) {
        return null; // Hoe ging dat ook alweer...
    }
}
