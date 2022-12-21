public class Enrollment {
    private final String STUDENT_NAME;
    private final String COURSE_NAME;

    public Enrollment(String STUDENT_NAME, String COURSE_NAME) {
        this.STUDENT_NAME = STUDENT_NAME;
        this.COURSE_NAME = COURSE_NAME;
    }

    public String getStudent() {
        return STUDENT_NAME;
    }
    public String getCourse() {
        return COURSE_NAME;
    }

}
