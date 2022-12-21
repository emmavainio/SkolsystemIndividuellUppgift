public enum CourseName {
    ENGLISH("English"),
    HISTORY("History"),
    MATH("Math");

    private final String stringValue;

    CourseName(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}
