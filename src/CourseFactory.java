//
public class CourseFactory {
    public Course createCourse(String courseType) {

        return switch(courseType.toUpperCase()) {
            case "ENGLISH" -> new English();
            case "HISTORY" -> new History();
            case "MATH" -> new Math();
            default -> null;
        };
    }
}
