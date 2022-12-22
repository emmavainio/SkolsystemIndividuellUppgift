package data;

import java.util.HashSet;

public class EmrollmentDao {
    private HashSet<Enrollment> enrollmentSet = new HashSet<>();

    public HashSet<Enrollment> getEnrollmentSet() {
        return enrollmentSet;
    }

    public void removeStudentFromCourse(Student student, Course course) {

        for (Enrollment enrollment : enrollmentSet) {
            if (enrollment.getCourse().equals(course) && enrollment.getStudent().equals(student)) {
                enrollmentSet.remove(enrollment);
                System.out.println(ANSI_RED + student.getName() + " togs bort från kursen: " + course.getName() + ".\n");
                break;
            } else {
                System.out.println(ANSI_RED + student.getName() + " Läser för tillfället inte kursen: " + course.getName());
            }
        }
    }

    public void addStudentToCourse(Student student, Course course) {

        for (Enrollment enrollment : enrollmentSet) {
            if (enrollment.getCourse().equals(course) && enrollment.getStudent().equals(student))
                System.out.println(ANSI_RED + student.getName() + " läser redan " + course.getName().getString() + "!\n");
            else {
                enrollmentSet.add(new Enrollment(student, course));
                System.out.println(ANSI_GREEN + student.getName() + " lades till i kursen " + course.getName().getString() + "!\n");
            }
            break;
        }
        if (enrollmentSet.isEmpty()) {
            enrollmentSet.add(new Enrollment(student, course));
            System.out.println(ANSI_GREEN + student.getName() + " lades till i kursen " + course.getName().getString() + "!\n");
        }
    }

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
}
