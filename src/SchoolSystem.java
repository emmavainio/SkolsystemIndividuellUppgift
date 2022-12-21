import java.util.InputMismatchException;
import java.util.Scanner;

public class SchoolSystem {
    private final DataAccessObject DAO = new DataAccessObject();
    private Scanner userInput = new Scanner(System.in);
    private int id;

    public SchoolSystem() {

        System.out.println(ANSI_GREEN + "Välkommen till skolsystemet! Skriv en siffra för att gå vidare:\n\n" +
                ANSI_RESET + "\"1\" - Skoladministratör.\n\"2\" - Elev.\n\"0\" - Avsluta.");

        id = getNumericInput(2);
        if (id != Command.EXIT.getValue()) {
            if (id == Command.ADMIN.getValue())
                System.out.println("Inloggad som administrarör");
            else if (id ==Command.STUDENT.getValue())
                System.out.println("Inloggad som elev");
            printTerminal();
        }
    }

    public void printTerminal() {
        System.out.println(ANSI_RESET + """

                Vad vill du göra?
                Skriv en siffra för att komma till respektive meny:
                                           
                "1" - Se kurser.
                "2" - Se Lärarlista.
                "3" - Se Elevlista.
                "0" - Avsluta.""");

        int input = getNumericInput(3);

        if (input == Command.COURSE_LIST.getValue()) {
            printCourses();
        } else if (input == Command.TEACHER_LIST.getValue()) {
            printAllTeachers();
        } else if (input == Command.STUDENT_LIST.getValue()) {
            printAllStudents();
        }
    }

    public void printCourses() {

        System.out.println(ANSI_RESET + """
                ** Kurser **

                Skriv en siffra för att välja respektive kurs:
                """);
        for (int i = 0; i < DAO.getCourseList().size(); i++) {
            System.out.println("\"" + (i + 1) + "\" - " + DAO.getCourseList().get(i).getName().getString());
        }
        System.out.println("\"9\" - Backa");
        System.out.println("\"0\" - Avsluta");

        int input = getNumericInput(3);

        if (input == Command.ENGLISH.getValue())
            printCourseInformation(CourseName.ENGLISH);
        else if (input == Command.HISTORY.getValue())
            printCourseInformation(CourseName.HISTORY);
        else if (input == Command.MATH.getValue())
            printCourseInformation(CourseName.MATH);
        else if (input == Command.BACK_OPTION.getValue())
            printTerminal();
    }

    public void printCourseInformation(CourseName courseName) {

        if (id == Command.ADMIN.getValue()) {
            System.out.println(ANSI_RESET + "** " + courseName + " **\nLärare: " + DAO.getCourseTeacher(courseName) +
            ANSI_RESET + "\nElever: ");
            if (!DAO.getEnrollmentSet().isEmpty()) {
                for (Enrollment enrollment : DAO.getEnrollmentSet()) {
                    if (enrollment.getCourse().equals(courseName)) {
                        System.out.println(enrollment.getStudent());
                    }
                }
            } else
                System.out.println(ANSI_RED + "För tillfället läser inte några elever " + courseName + ".\n");
            System.out.println();

            if (id == Command.ADMIN.getValue()) {
                System.out.println(ANSI_RESET + """
                        Skriv en siffra för att välja välja vad du vill göra:
                                        
                        "1" - Ta bort en elev.
                        "2" - Lägg till en elev.
                        "3" - Ta bort lärare.
                        "4" - Lägg till lärare.
                        "9" - Backa.
                        "0" - Avsluta.""");
            } else if (id == Command.STUDENT.getValue()) {
                System.out.println(ANSI_RESET + """
                    Skriv en siffra för att välja välja vad du vill göra:
                                        
                    "9" - Backa.
                    "0" - Avsluta.""");
            }
            loadSelectedCourseAlternatives(courseName);
        }
    }

    public void loadSelectedCourseAlternatives(CourseName courseName) {

        int input;

        if (id == Command.ADMIN.getValue())
            input = getNumericInput(4);
        else
            input = getNumericInput(0);

        if (input == Command.REMOVE_STUDENT.getValue()) {

            System.out.println(ANSI_RESET + "Vilken elev vill du ta bort från kursen?");

            String studentToRemove = userInput.nextLine();
            DAO.removeStudentFromCourse(studentToRemove, courseName);
            printCourseInformation(courseName);

        } else if (input == Command.ADD_STUDENT.getValue()) {

            System.out.println(ANSI_RESET + "Vilken elev vill du lägga till kursen?");

            String studentToAdd = userInput.nextLine();
            DAO.addStudentToCourse(studentToAdd, courseName);
            printCourseInformation(courseName);

        } else if (input == Command.REMOVE_TEACHER.getValue()) {

            System.out.println("Vilken Lärare vill du ta bort från kursen?");

            String teacherToRemove = userInput.nextLine();
            DAO.removeTeacherFromCourse(teacherToRemove, courseName);
            printCourseInformation(courseName);

        } else if (input == Command.ADD_TEACHER.getValue()) {

            System.out.println("Vilken lärare vill du lägga till kursen?");

            String teacherToAdd = userInput.nextLine();
            DAO.addTeacherToCourse(teacherToAdd, courseName);
            printCourseInformation(courseName);

        } else if (input == Command.BACK_OPTION.getValue()) {
            printCourses();
        }
    }

    public void printAllTeachers() {

        System.out.println("** Lärarlista **\n");
        System.out.println("Skriv in ett" + ANSI_RED + " namn " + ANSI_RESET + "från listan för att se mer information\n");
        for (Teacher teacher: DAO.getTeacherList()) {
            System.out.println(teacher.getName());
        }
        System.out.println("\"9\" - Backa.");
        System.out.println("\"0\" - Avsluta.");

        String input = userInput.nextLine();

        if (input.equals(Command.BACK_OPTION.getStringValue())) {
            if (id == Command.ADMIN.getValue()) {
                printTerminal();
            } else if (id == Command.STUDENT.getValue()) {
                printTerminal();
            }
        } else if (input.equals(Command.EXIT.getStringValue())) {
            System.out.println("Stänger program");
            System.exit(0);
        } else {
            printTeacherInformation(input);
        }
    }

    public void printAllStudents() {

        System.out.println("** Elevlista **\n");
        System.out.println("Skriv in ett" + ANSI_RED + " namn " + ANSI_RESET + "från listan för att se mer information\n");
        for (Student student: DAO.getStudentList()) {
            System.out.println(student.getName());
        }
        System.out.println("\"9\" - Backa.");
        System.out.println("\"0\" - Avsluta.");

        String input = userInput.nextLine();

        if (input.equals(Command.BACK_OPTION.getStringValue())) {
            if (id == Command.ADMIN.getValue()) {
                printTerminal();
            } else if (id == Command.STUDENT.getValue()) {
                printTerminal();
            }
        } else if (input.equals(Command.EXIT.getStringValue())) {
            System.exit(0);
        } else {
            printStudentInformation(input);
        }
    }

    public void printStudentInformation(String studentName) {

        if (DAO.getStudent(studentName) != null) {
            System.out.println("*** Information om " + DAO.getStudent(studentName).getName() + " ***\n" +
            "Namn: " + DAO.getStudent(studentName).getName() + "\nID: " + DAO.getStudent(studentName).getPID() + "\nAktiva kurser:");
            if (!DAO.getEnrollmentSet().isEmpty()) {
                for (Enrollment enrollment : DAO.getEnrollmentSet()) {
                    if (enrollment.getStudent().equals(DAO.getStudent(studentName).getName()))
                        System.out.println(enrollment.getCourse());
                }
            }
        } else
            System.out.println("Finns ingen information om denna elev");

        System.out.println("\nVälj en siffra för att backa eller avsluta\n");
        System.out.println("\"9\" - Backa\n\"0\" - Avsluta");

        int input = getNumericInput(0);

        if (input == Command.BACK_OPTION.getValue())
            printAllStudents();
    }

    public void printTeacherInformation(String teacherName) {

        if (DAO.getTeacher(teacherName) != null) {
            System.out.println("*** Information om " + DAO.getTeacher(teacherName).getName() + " ***\n");
            System.out.println("Namn: " + DAO.getTeacher(teacherName).getName() + "\nID: " +
                    DAO.getTeacher(teacherName).getPID() + "\n");
            System.out.println("Undervisar i: ");
            if (!DAO.getTeacherList().isEmpty()) {
                for (Course course : DAO.getCourseList()) {
                    if (course.getTeacher() == DAO.getTeacher(teacherName))
                        System.out.println(course.getName());
                }
            }
        } else {
            System.out.println("Finns ingen information om denna lärare\n");
        }
        System.out.println("\nVälj en siffra för att backa eller avsluta:\n\"9\" - Backa\n\"0\" - Avsluta");

        int input = getNumericInput(0);

        if (input == Command.BACK_OPTION.getValue()) {
            printAllTeachers();
        }
    }

    public int getNumericInput(int maxValue) {

        while (true) {
            try {
                int input = userInput.nextInt();
                if (input >= 0 && input <= maxValue || input == Command.BACK_OPTION.getValue()) {
                    userInput.nextLine();
                    return input;
                }
                System.out.println(ANSI_RED + "Felaktig siffra. Försök igen\n" + ANSI_RESET);
            } catch (InputMismatchException e) {
                System.out.println(ANSI_RED + "Endast siffror tillåtna. Försök igen!\n" + ANSI_RESET);
                userInput.nextLine();
            }
        }
    }

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) {
        new SchoolSystem();
    }
}