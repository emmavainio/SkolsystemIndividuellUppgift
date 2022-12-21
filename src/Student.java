//

public class Student implements Person {

    private String name;
    private String personalID;

    public Student(String name, String personalID) {
        this.name = name;
        this.personalID = personalID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPID() {
        return personalID;
    }

}
