package data;

import java.util.HashSet;

public class EmrollmentDao {
    private HashSet<Enrollment> enrollmentSet = new HashSet<>();

    public HashSet<Enrollment> getEnrollmentSet() {
        return enrollmentSet;
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollmentSet.add(enrollment);
    }
}
