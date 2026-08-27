package nl.hu.s3.project.uno.domain;

public enum Color {
    RED, BLUE, GREEN, YELLOW;

    public static Color parse(String color) {
        if (color.length() != 1) throw new IllegalArgumentException("Invalid color: " + color);
        return switch (color) {
            case "R" -> Color.RED;
            case "B" -> Color.BLUE;
            case "G" -> Color.GREEN;
            case "Y" -> Color.YELLOW;
            default -> throw new IllegalArgumentException("Invalid color: " + color);
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case RED -> "R";
            case BLUE -> "B";
            case GREEN -> "G";
            case YELLOW -> "Y";
        };
    }
}
