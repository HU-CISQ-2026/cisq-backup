package nl.hu.s3.project.uno.domain;

public enum Value {
    ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, REVERSE, DRAW_TWO;

    public static Value parse(String value) {
        return switch (value) {
            case "0" -> Value.ZERO;
            case "1" -> Value.ONE;
            case "2" -> Value.TWO;
            case "3" -> Value.THREE;
            case "4" -> Value.FOUR;
            case "5" -> Value.FIVE;
            case "6" -> Value.SIX;
            case "7" -> Value.SEVEN;
            case "8" -> Value.EIGHT;
            case "9" -> Value.NINE;
            case "S" -> Value.SKIP;
            case "R" -> Value.REVERSE;
            case "D" -> Value.DRAW_TWO;
            default -> throw new IllegalArgumentException("Invalid value: " + value);
        };
    }

    public boolean isNumber() {
        return switch (this) {
            case ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE -> true;
            case SKIP, REVERSE, DRAW_TWO -> false;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case ZERO -> "0";
            case ONE -> "1";
            case TWO -> "2";
            case THREE -> "3";
            case FOUR -> "4";
            case FIVE -> "5";
            case SIX -> "6";
            case SEVEN -> "7";
            case EIGHT -> "8";
            case NINE -> "9";
            case SKIP -> "S";
            case REVERSE -> "R";
            case DRAW_TWO -> "D";
        };
    }
}
