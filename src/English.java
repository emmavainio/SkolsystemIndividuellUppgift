//

public class English implements Course {

    String name = CourseName.ENGLISH.getString();
    Teacher teacher;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Teacher getTeacher() {
        return teacher;
    }

    @Override
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
