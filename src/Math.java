//

public class Math implements Course {

    String name = CourseName.MATH.getString();
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
