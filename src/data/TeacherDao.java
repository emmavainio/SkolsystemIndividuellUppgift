package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TeacherDao {
    private ArrayList<Teacher> teacherList = new ArrayList<>();

    public TeacherDao() {

        String temp;

        String file = "src/data/Teachers.csv";
        try (BufferedReader buf = new BufferedReader(new FileReader(file))) {
            while ((temp = buf.readLine()) != null) {
                String[] fileInput = temp.split(",");
                var person = PersonFactory.createPerson(fileInput[0], fileInput[0], fileInput[1], fileInput[2]);
                if (person instanceof Teacher) {
                    teacherList.add((Teacher) person);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Teacher> getTeacherList() {
        return teacherList;
    }

    public void addTeacher(Teacher teacher) {
        teacherList.add(teacher);
    }

    public Teacher getTeacher(String name) {
        for (Teacher teacher : teacherList) {
            if (teacher.getName().equalsIgnoreCase(name)) {
                return teacher;
            }
        }
        return null;
    }
}
