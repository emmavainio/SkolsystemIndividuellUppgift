//

public class Teacher implements Person {

    private String name;
    private String personalID;
    private String email;

    public Teacher(String name, String personalID, String email) {
        this.name = name;
        this.personalID = personalID;
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
    public String getPID() {
        return personalID;
    }

}
