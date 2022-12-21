
public class CourseFactory {
    public Course createCourse(CourseName courseName) {

        return switch(courseName) {
            case ENGLISH -> new English();
            case HISTORY -> new History();
            case MATH -> new Math();
        };
    }
}
