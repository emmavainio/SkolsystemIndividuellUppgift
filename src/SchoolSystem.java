import data.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SchoolSystem {
    private Scanner userInput = new Scanner(System.in);
    private int id;
    private EmrollmentDao ed = new EmrollmentDao();
    private CourseDao cd = new CourseDao();
    private PersonDao pd = new PersonDao();

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
        for (int i = 0; i < cd.getCourseList().size(); i++) {
            System.out.println("\"" + (i + 1) + "\" - " + cd.getCourseList().get(i).getName().getString());
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
            System.out.println(ANSI_RESET + "** " + courseName + " **\nLärare: " + cd.getCourseTeacher(courseName) +
            ANSI_RESET + "\nElever: ");
            if (!ed.getEnrollmentSet().isEmpty()) {
                for (Enrollment enrollment : ed.getEnrollmentSet()) {
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
            if (pd.getStudent(studentToRemove) == null)
                System.out.println("Kunde inte hitta en elev med namnet: '" + studentToRemove + "'.");
            else
                ed.removeStudentFromCourse(studentToRemove, courseName);
            printCourseInformation(courseName);

        } else if (input == Command.ADD_STUDENT.getValue()) {

            System.out.println(ANSI_RESET + "Vilken elev vill du lägga till kursen?");

            String studentToAdd = userInput.nextLine();
            if (pd.getStudent(studentToAdd) == null)
                System.out.println("Kunde inte hitta en elev med namnet: '" + studentToAdd + "'.");
            else
                ed.addStudentToCourse(pd.getStudent(studentToAdd), courseName, cd.getCourse(courseName));
            printCourseInformation(courseName);

        } else if (input == Command.REMOVE_TEACHER.getValue()) {

            System.out.println("Vilken Lärare vill du ta bort från kursen?");

            String teacherToRemove = userInput.nextLine();
            if (pd.getTeacher(teacherToRemove) == null)
                System.out.println("Kunde inte hitta en lärare med namnet: '" + teacherToRemove + "'.");
            else
                cd.removeTeacherFromCourse(courseName, pd.getTeacher(teacherToRemove));
            printCourseInformation(courseName);

        } else if (input == Command.ADD_TEACHER.getValue()) {

            System.out.println("Vilken lärare vill du lägga till kursen?");

            String teacherToAdd = userInput.nextLine();
            if (pd.getTeacher(teacherToAdd) == null)
                System.out.println("Kunde inte hitta en lärare med namnet: '" + teacherToAdd + "'.");
            else
                cd.addTeacherToCourse(teacherToAdd, courseName, pd.getTeacher(teacherToAdd));
            printCourseInformation(courseName);

        } else if (input == Command.BACK_OPTION.getValue()) {
            printCourses();
        }
    }

    public void printAllTeachers() {

        System.out.println("** Lärarlista **\n");
        System.out.println("Skriv in ett" + ANSI_RED + " namn " + ANSI_RESET + "från listan för att se mer information\n");
        for (Teacher teacher : pd.getTeacherList()) {
            System.out.println(teacher.getName());
        }
        if (id == Command.ADMIN.getValue())
            System.out.println("1 - Lägg till ny lärare.\n9 - Backa. \n0 - Avsluta.");
        else
            System.out.println("9 - Backa. \n0 - Avsluta.");

        String input = userInput.nextLine();

        if (input.equals(Command.BACK_OPTION.getString()))
            printTerminal();
        else if (input.equals(Command.EXIT.getString()))
            System.exit(0);
        else if (input.equals(Command.ADD_NEW_PERSON.getString())) {
            System.out.println("Skriv in namn, personnummer samt email på personen du vill lägga till");
            String[] data = userInput.nextLine().split(",");
            if (data.length == 3)
                pd.addPerson(data, PersonType.TEACHER);
            else
                System.out.println("Felaktig inmatning");
            printAllTeachers();
        } else
            printTeacherInformation(input);

    }

    public void printAllStudents() {

        System.out.println("** Elevlista **\n");
        System.out.println("Skriv in ett" + ANSI_RED + " namn " + ANSI_RESET + "från listan för att se mer information\n");
        for (Student student: pd.getStudentList()) {
            System.out.println(student.getName());
        }
        if (id == Command.ADMIN.getValue())
            System.out.println("1 - Lägg till ny elev.\n9 - Backa. \n0 - Avsluta.");
        else
            System.out.println("9 - Backa. \n0 - Avsluta.");


        String input = userInput.nextLine();

        if (input.equals(Command.BACK_OPTION.getString()))
            printTerminal();
        else if (input.equals(Command.ADD_NEW_PERSON.getString())) {
            System.out.println("Skriv in namn, personnummer samt email på personen du vill lägga till");
            String[] data = userInput.nextLine().split(",");
            if (data.length == 3)
                pd.addPerson(data, PersonType.STUDENT);
            else
                System.out.println("Felaktig inmatning");
            printAllStudents();
        }
        else if (input.equals(Command.EXIT.getString()))
            System.exit(0);
        else
            printStudentInformation(input);

    }

    public void printStudentInformation(String studentName) {

        Student student = pd.getStudent(studentName);

        if (student != null) {
            System.out.println("*** Information om " + student.getName() + " ***\n" +
            "Namn: " + student.getName() + "\nID: " + student.getSSN() + "\nEmail: "+ student.getEmail() + "\nAktiva kurser:");
            if (!ed.getEnrollmentSet().isEmpty()) {
                for (Enrollment enrollment : ed.getEnrollmentSet()) {
                    if (enrollment.getStudent().equals(student.getName()))
                        System.out.println(enrollment.getCourse());
                }
            }
        } else
            System.out.println("Denna person är inte en registrerad elev");

        System.out.println("\nVälj en siffra för att backa eller avsluta\n");
        System.out.println("\"9\" - Backa\n\"0\" - Avsluta");

        int input = getNumericInput(0);

        if (input == Command.BACK_OPTION.getValue())
            printAllStudents();
    }

    public void printTeacherInformation(String teacherName) {

        Teacher teacher = pd.getTeacher(teacherName);

        if (teacher != null) {
            System.out.println("\nNamn: " + teacher.getName() + "\nID: " + teacher.getSSN() +
                    "\nEmail: " + teacher.getEmail() + "\nUndervisar i: ");
            if (!pd.getTeacherList().isEmpty()) {
                for (Course course : cd.getCourseList()) {
                    if (course.getTeacher() == pd.getTeacher(teacherName))
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