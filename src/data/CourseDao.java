package data;

import java.util.ArrayList;
import java.util.List;

public class CourseDao {

    private List<Course> courseList = new ArrayList<>();

    public CourseDao() {
        courseList.add(new Course(CourseName.ENGLISH));
        courseList.add(new Course(CourseName.HISTORY));
        courseList.add(new Course(CourseName.MATH));
    }
    public Course getCourse(CourseName courseName) {
        for (Course course : courseList) {
            if (course.getName().equals(courseName)) {
                return course;
            }
        }
        System.err.println("Det finns ingen kurs med namnet: '" + courseName + "'.");
        return null;
    }
    public void removeTeacherFromCourse(CourseName courseName, Teacher teacher) {

        for (Course course : courseList) {
            if (courseName.equals(course.getName())) {
                if (course.getTeacher() != null) {
                    course.setTeacher(null);
                    System.out.println(ANSI_RED + "Lärare " + teacher.getName() + " togs bort ifrån kursen " + courseName + "!\n");
                } else
                    System.out.println(ANSI_RED + "För tillfället undervisar ingen lärare kursen " + courseName);
                break;
            }
        }
    }
    public void addTeacherToCourse(CourseName courseName, Teacher teacher) {

        for (Course course : courseList) {
            if (course.getName().equals(courseName)) {
                if (course.getTeacher() == null) {
                    course.setTeacher(teacher);
                    System.out.println(ANSI_GREEN + teacher.getName() + " lades till som lärare i kursen " + courseName.getString() + "!\n");
                    break;
                } else if (course.getTeacher() != null) {
                    System.out.println(ANSI_RED + course.getTeacher().getName() + " undervisar redan " + courseName + "!\n");
                    break;
                }
            }
        }
    }
    public Teacher getCourseTeacher(Course course) {
        for (Course c : courseList) {
            if (c.equals(course)) {
                return c.getTeacher();
            }
        }
        return null;
    }

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public List<Course> getCourseList() {
        return courseList;
    }
}
