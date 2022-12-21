public class PersonFactory {

    public static Person createPerson(String personType, String name, String PID) {

        return switch (personType.toUpperCase()) {
            case "STUDENT" -> new Student(name,PID);
            case "TEACHER" -> new Teacher(name, PID);
            default -> null;
        };
    }
}
