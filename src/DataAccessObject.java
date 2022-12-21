import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class DataAccessObject {
    private ArrayList<Course> courseList = new ArrayList<>();
    private ArrayList<Student> studentList = new ArrayList<>();
    private ArrayList<Teacher> teacherList = new ArrayList<>();
    private HashSet<Enrollment> enrollmentSet = new HashSet<>();
    private PersonFactory personFactory = new PersonFactory();
    private CourseFactory courseFactory = new CourseFactory();

    public DataAccessObject() {

        String temp;

        String file = "src/People.txt";
        try(BufferedReader buf = new BufferedReader(new FileReader(file))) {
            while ((temp = buf.readLine()) != null) {
                String[] fileInput = temp.split(",");
                var person = personFactory.createPerson(fileInput[0], fileInput[1], fileInput[2]);
                if (person instanceof Student)
                    studentList.add((Student) person);
                else if (person instanceof Teacher)
                    teacherList.add((Teacher) person);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public Course getCourse(String courseName) {
        for (Course course : courseList) {
            if (course.getName().equalsIgnoreCase(courseName)) {
                return course;
            }
        }
        System.err.println("Det finns ingen kurs med namnet: '" + courseName + "'.");
        return null;
    }

    public void addCourse(String name) {
        courseList.add(courseFactory.createCourse(name));
    }

    public Teacher getTeacher(String name) {
        for (Teacher teacher : teacherList) {
            if (teacher.getName().equalsIgnoreCase(name)) {
                return teacher;
            }
        }
        return null;
    }

    public ArrayList<String> getStudents(String courseName) {
        ArrayList<String> studentList = new ArrayList<>();
        for (Enrollment enrollment : enrollmentSet) {
            if (enrollment.getCourse().equalsIgnoreCase(courseName)) {
                studentList.add(enrollment.getStudent());
            }
        }
        return studentList;
    }

    public ArrayList<String> getStudentCourses(String student) {
        ArrayList<String> courses = new ArrayList<>();
        for (Enrollment enrollment : enrollmentSet) {
            if (enrollment.getStudent().equalsIgnoreCase(student)) {
                courses.add(enrollment.getCourse());
            }
        }
        return courses;
    }

    public ArrayList<String> getTeacherCourses(String teacher) {
        ArrayList<String> teacherCourses = new ArrayList<>();
        for (Course course : courseList) {
            if (course.getTeacher() != null && course.getTeacher().getName().equalsIgnoreCase(teacher)) {
                teacherCourses.add(course.getName());
            }
        }
        return teacherCourses;
    }

    public void removeStudentFromCourse(String studentToRemove, String courseName) {

        for (Enrollment enrollment : enrollmentSet) {
            if(enrollment.getCourse().equalsIgnoreCase(courseName)&& enrollment.getStudent().equalsIgnoreCase(studentToRemove)) {
                enrollmentSet.remove(enrollment);
                System.out.println(ANSI_RED + studentToRemove + " togs bort från kursen: " + courseName + ".\n");
                break;
            } else {
                System.out.println(ANSI_RED +studentToRemove + " Läser för tillfället inte kursen: " + courseName);
            }
        }
    }

    public void removeTeacherFromCourse(String teacherToRemove, String courseName) {

        teacherToRemove = teacherToRemove.trim();

        for (Course course : courseList) {
            if (Objects.equals(courseName, course.getName())) {
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

    public void addStudentToCourse(String studentToAdd, String courseName) {

        if (getStudent(studentToAdd) == null) {
            System.out.println(ANSI_RED + "Hittade ingen elev med namnet " + studentToAdd);
        } else {
                for (Enrollment enrollment : enrollmentSet) {
                    if(enrollment.getCourse().equalsIgnoreCase(courseName)&& enrollment.getStudent().equalsIgnoreCase(studentToAdd)) {
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

    public void addTeacherToCourse(String teacherToAdd, String courseName) {

        teacherToAdd = teacherToAdd.trim();

        for (Course course : courseList) {
            if (Objects.equals(courseName, course.getName())) {
                if (course.getTeacher() != null) {
                    System.out.println(ANSI_RED + course.getTeacher().getName() + " Undervisar redan " + courseName + "!\n");
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

    public String getCourseTeacher(String courseName) {
        for (Course course : courseList) {
            if (course.getName().equalsIgnoreCase(courseName)) {
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

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

}
