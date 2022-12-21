public class PersonFactory {

    public static Person createPerson(String personType, String name, String PID, String email) {

        return switch (personType.toUpperCase()) {
            case "STUDENT" -> new Student(name,PID,email);
            case "TEACHER" -> new Teacher(name, PID,email);
            default -> null;
        };
    }
}
