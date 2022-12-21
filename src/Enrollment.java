public class Enrollment {
    private final String STUDENT_NAME;
    private final CourseName COURSE_NAME;

    public Enrollment(String STUDENT_NAME, CourseName COURSE_NAME) {
        this.STUDENT_NAME = STUDENT_NAME;
        this.COURSE_NAME = COURSE_NAME;
    }

    public String getStudent() {
        return STUDENT_NAME;
    }
    public CourseName getCourse() {
        return COURSE_NAME;
    }

}
