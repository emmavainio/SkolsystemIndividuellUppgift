import java.util.InputMismatchException;
import java.util.Scanner;

public class SchoolSystem {
    private final DataAccessObject DAO;
    private Scanner userInput = new Scanner(System.in);
    private int id;

    public SchoolSystem() {

        DAO = new DataAccessObject();

        DAO.addCourse("ENGLISH");
        DAO.addCourse("HISTORY");
        DAO.addCourse("MATH");

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
            selectCourse();
        } else if (input == Command.TEACHER_LIST.getValue()) {
            printAllTeachers();
        } else if (input == Command.STUDENT_LIST.getValue()) {
            printAllStudents();
        }
    }

    public void selectCourse() {

        System.out.println(ANSI_RESET + """
                ** Kurser **

                Skriv en siffra för att välja respektive kurs:
                """);
        for (int i = 0; i < DAO.getCourseList().size(); i++) {
            System.out.println("\"" + (i + 1) + "\" - " + DAO.getCourseList().get(i).getName());
        }
        System.out.println("\"9\" - Backa.");
        System.out.println("\"0\" - Avsluta.");

        int input = getNumericInput(3);

        if (input == Command.ENGLISH.getValue())
            printCourseInformation("Engelska");
        else if (input == Command.HISTORY.getValue())
            printCourseInformation("Historia");
        else if (input == Command.MATH.getValue())
            printCourseInformation("Matematik");
        else if (input == Command.BACK_OPTION.getValue())
            printTerminal();
    }

    public void printCourseInformation(String courseName) {

        if (id == Command.ADMIN.getValue()) {
            System.out.println(ANSI_RESET + "** " + courseName + " **\n");
            System.out.println(ANSI_RESET + "Lärare: ");
            System.out.println(DAO.getCourseTeacher(courseName));
            System.out.println(ANSI_RESET + "\nElever: ");
            if (!DAO.getStudents(courseName).isEmpty()) {
                for (String student : DAO.getStudents(courseName)) {
                    System.out.println(student);
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

    public void loadSelectedCourseAlternatives(String courseName) {

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
            selectCourse();
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

        Student student = DAO.getStudent(studentName);

        if (studentName.equalsIgnoreCase(student.getName())) {
            System.out.println("*** Information om " + student.getName() + " ***\n");
            System.out.println("Namn: " + student.getName() + "\nID: " + student.getPID() + "\n");
            System.out.println("Aktiva kurser:");
            for (String course : DAO.getStudentCourses(student.getName())) {
                System.out.println(course);
            }
            System.out.println();

        } else
            System.out.println("Finns ingen information om denna elev\n");

        System.out.println("Välj en siffra för att backa eller avsluta\n");
        System.out.println("\"9\" - Backa\n\"0\" - Avsluta");

        int input = getNumericInput(0);

        if (input == Command.BACK_OPTION.getValue())
            printAllStudents();
    }

    public void printTeacherInformation(String teacherName) {

        Teacher teacher = DAO.getTeacher(teacherName);

        if (DAO.getTeacher(teacherName) != null) {
            System.out.println("*** Information om " + teacher.getName() + " ***\n");
            System.out.println("Namn: " + teacher.getName() + "\nID: " + teacher.getPID() + "\n");

            System.out.println("Undervisar i: ");
            for (String course : DAO.getTeacherCourses(teacher.getName())) {
                System.out.println(course);
            }
            System.out.println();

        } else {
            System.out.println("Finns ingen information om denna lärare\n");
        }
        System.out.println("Välj en siffra för att backa eller avsluta:\n");
        System.out.println("\"9\" - Backa\n\"0\" - Avsluta");

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