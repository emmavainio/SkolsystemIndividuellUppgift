public enum CourseName {
    ENGLISH("Engelska"),
    HISTORY("Historia"),
    MATH("Matematik");

    private final String string;

    CourseName(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
