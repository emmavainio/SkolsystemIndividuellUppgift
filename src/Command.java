public enum Command {
    EXIT(0, "0"),
    ADMIN(1, "1"),
    STUDENT(2, "2"),
    COURSE_LIST(1, "1"),
    TEACHER_LIST(2, "2"),
    STUDENT_LIST(3, "3"),
    ENGLISH(1, "1"),
    HISTORY(2, "2"),
    MATH(3, "3"),
    BACK_OPTION(9, "9"),
    REMOVE_STUDENT(1, "1"),
    ADD_STUDENT(2, "2"),
    REMOVE_TEACHER(3, "3"),
    ADD_TEACHER(4, "4");

    private final int value;
    private final String stringValue;

    Command(int value, String stringValue) {
        this.value = value;
        this.stringValue = stringValue;
    }

    public int getValue() {
        return value;
    }
    public String getStringValue() {
        return stringValue;
    }
}
