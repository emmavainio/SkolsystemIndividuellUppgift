import data.Enrollment;
import data.PersonFactory;
import data.Student;
import data.Teacher;
import data.CourseName;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataAccessObject {
    private ArrayList<Course> courseList = new ArrayList<>();
    private ArrayList<Student> studentList = new ArrayList<>();
    private ArrayList<Teacher> teacherList = new ArrayList<>();
    private HashSet<Enrollment> enrollmentSet = new HashSet<>();

    public DataAccessObject() {

        String temp;

        String file = "src/People.txt";
        try (BufferedReader buf = new BufferedReader(new FileReader(file))) {
            while ((temp = buf.readLine()) != null) {
                String[] fileInput = temp.split(",");
                var person = PersonFactory.createPerson(fileInput[0], fileInput[1], fileInput[2], fileInput[3]);
                if (person instanceof Student) {
                    studentList.add((Student) person);
                }
                else if (person instanceof Teacher)
                    teacherList.add((Teacher) person);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        courseList.add(new Course(CourseName.ENGLISH));
        courseList.add(new Course(CourseName.HISTORY));
        courseList.add(new Course(CourseName.MATH));
    }

    public Student getStudent(String name) {
        for (Student student : studentList) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        System.out.println("Kunde inte hitta en elev med namnet: '" + name + "'.");
        return null;
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

    public Teacher getTeacher(String name) {
        for (Teacher teacher : teacherList) {
            if (teacher.getName().equalsIgnoreCase(name)) {
                return teacher;
            }
        }
        return null;
    }

    public void removeStudentFromCourse(String studentToRemove, CourseName courseName) {

        for (Enrollment enrollment : enrollmentSet) {
            if (enrollment.getCourse().equals(courseName) && enrollment.getStudent().equalsIgnoreCase(studentToRemove)) {
                enrollmentSet.remove(enrollment);
                System.out.println(ANSI_RED + studentToRemove + " togs bort från kursen: " + courseName + ".\n");
                break;
            } else {
                System.out.println(ANSI_RED + studentToRemove + " Läser för tillfället inte kursen: " + courseName);
            }
        }
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

    public void addStudentToCourse(String studentToAdd, CourseName courseName) {

        if (getStudent(studentToAdd) == null) {
            System.out.println(ANSI_RED + "Hittade ingen elev med namnet " + studentToAdd);
        } else {
            for (Enrollment enrollment : enrollmentSet) {
                if (enrollment.getCourse().equals(courseName) && enrollment.getStudent().equalsIgnoreCase(studentToAdd)) {
                    System.out.println(ANSI_RED + studentToAdd + " läser redan " + courseName + "!\n");
                } else {
                    enrollmentSet.add(new Enrollment(getStudent(studentToAdd).getName(), getCourse(courseName).getName()));
                    System.out.println(ANSI_GREEN + studentToAdd + " lades till i kursen " + courseName + "!\n");
                }
                break;
            }
            if (enrollmentSet.isEmpty()) {
                enrollmentSet.add(new Enrollment(getStudent(studentToAdd).getName(), getCourse(courseName).getName()));
                System.out.println(ANSI_GREEN + studentToAdd + " lades till i kursen " + courseName + "!\n");
            }
        }
    }

    public void addTeacherToCourse(String teacherToAdd, CourseName courseName) {

        teacherToAdd = teacherToAdd.trim();

        for (Course course : courseList) {
            if (course.getName().equals(courseName)) {
                if (course.getTeacher() != null) {
                    System.out.println(ANSI_RED + course.getTeacher().getName() + " undervisar redan " + courseName + "!\n");
                } else if (course.getTeacher() == null) {
                    for (Teacher teacher : teacherList) {
                        if (teacher.getName().equalsIgnoreCase(teacherToAdd)) {
                            course.setTeacher(teacher);
                            System.out.println(ANSI_GREEN + teacher.getName() + " lades till som lärare i kursen " + courseName + "!\n");
                            break;
                        }
                    }
                } else {
                    System.out.println("Kunde inte hitta en lärare med namnet " + teacherToAdd);
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

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public ArrayList<Teacher> getTeacherList() {
        return teacherList;
    }

    public HashSet<Enrollment> getEnrollmentSet() {
        return enrollmentSet;
    }

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

}
