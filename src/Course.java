import data.CourseName;
import data.Teacher;

public class Course {
    private CourseName courseName;
    private Teacher teacher;

    public Course(CourseName courseName) {
        this.courseName = courseName;
    }

    public CourseName getName() {
        return courseName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
