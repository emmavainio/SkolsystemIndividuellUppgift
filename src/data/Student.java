package data;

public class Student implements Person {

    private String name;
    private String SSN;
    private String email;

    public Student(String name, String SSN, String email) {
        this.name = name;
        this.SSN = SSN;
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSSN() {
        return SSN;
    }

    @Override
    public String getEmail() {
        return email;
    }

}
