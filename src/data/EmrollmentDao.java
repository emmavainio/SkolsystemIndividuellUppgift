package data;

import java.util.HashSet;

public class EmrollmentDao {
    private HashSet<Enrollment> enrollmentSet = new HashSet<>();

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public HashSet<Enrollment> getEnrollmentSet() {
        return enrollmentSet;
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollmentSet.add(enrollment);
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
    public void addStudentToCourse(Student studentToAdd, CourseName courseName, Course course) {

        if (studentToAdd == null) {
            System.out.println(ANSI_RED + "Hittade ingen elev med namnet " + studentToAdd);
        } else {
            for (Enrollment enrollment : enrollmentSet) {
                if (enrollment.getCourse().equals(courseName) && enrollment.getStudent().equalsIgnoreCase(studentToAdd.getName())) {
                    System.out.println(ANSI_RED + studentToAdd + " läser redan " + courseName + "!\n");
                } else {
                    enrollmentSet.add(new Enrollment(studentToAdd.getName(), course.getName()));
                    System.out.println(ANSI_GREEN + studentToAdd + " lades till i kursen " + courseName + "!\n");
                }
                break;
            }
            if (enrollmentSet.isEmpty()) {
                enrollmentSet.add(new Enrollment(studentToAdd.getName(), course.getName()));
                System.out.println(ANSI_GREEN + studentToAdd + " lades till i kursen " + courseName + "!\n");
            }
        }
    }
}
