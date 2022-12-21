
public class Student implements Person {

    private String name;
    private String personalID;
    private String email;

    public Student(String name, String personalID, String email) {
        this.name = name;
        this.personalID = personalID;
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPID() {
        return personalID;
    }

    @Override
    public String getEmail() {
        return email;
    }

}
