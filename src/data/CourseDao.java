package data;

import java.util.ArrayList;
import java.util.Objects;

public class CourseDao {

    private ArrayList<Course> courseList = new ArrayList<>();
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

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
    public void removeTeacherFromCourse(String teacherToRemove, CourseName courseName) {

        teacherToRemove = teacherToRemove.trim();

        for (Course course : courseList) {
            if (Objects.equals(courseName.getString(), course.getName())) {
                try {
                    if (course.getTeacher().getName().equalsIgnoreCase(teacherToRemove)) {
                        course.setTeacher(null);
                        System.out.println(ANSI_RED + "Lärare " + teacherToRemove + " togs bort ifrån kursen " + courseName + "!\n");
                        break;
                    }
                } catch (NullPointerException e) {
                    System.out.println(ANSI_RED + "För tillfället undervisar ingen lärare kursen " + courseName);
                }
            }
        }
    }
    public void addTeacherToCourse(String teacherToAdd, CourseName courseName, Teacher teacher) {

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
    public String getCourseTeacher(CourseName courseName) {
        for (Course course : courseList) {
            if (course.getName().equals(courseName)) {
                try {
                    return course.getTeacher().getName();
                } catch (NullPointerException e) {
                    return ANSI_RED + "Det är för tillfället ingen lärare som undervisar i kursen " + courseName + ".";
                }
            }
        }
        return ANSI_RED + "Det är för tillfället ingen lärare som undervisar i kursen " + courseName + ".";
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }
}
