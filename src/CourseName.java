public enum CourseName {
    ENGLISH("Engelska"),
    HISTORY("Historia"),
    MATH("Matematik");

    private final String stringValue;

    CourseName(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getString() {
        return stringValue;
    }
}
