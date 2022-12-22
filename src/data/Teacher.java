package data;//

import data.Person;

public class Teacher implements Person {

    private String name;
    private String SSN;
    private String email;

    public Teacher(String name, String SSN, String email) {
        this.name = name;
        this.SSN = SSN;
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getSSN() {
        return SSN;
    }

}
