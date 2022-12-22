package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDao {
    private List<Teacher> teacherList = new ArrayList<>();
    private ArrayList<Student> studentList = new ArrayList<>();
    String file = "src/data/People.csv";

    public PersonDao() {
        String temp;

        try (BufferedReader buf = new BufferedReader(new FileReader(file))) {
            while ((temp = buf.readLine()) != null) {
                String[] fileInput = temp.split(",");
                var person = PersonFactory.createPerson(fileInput[0], fileInput[1], fileInput[2], fileInput[3]);
                if (person instanceof Teacher) {
                    teacherList.add((Teacher) person);
                } else if (person instanceof Student)
                    studentList.add((Student) person);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void addPerson(String[] data, PersonType personType) {

        var person = PersonFactory.createPerson(personType.toString(), data[0], data[1], data[2]);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write("\n" + personType + "," + data[0] + "," + data[1] + "," +data[2]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (person instanceof Teacher)
            teacherList.add((Teacher) person);
        else if (person instanceof Student)
            studentList.add((Student) person);

        System.out.println(data[0] + " har lagts till i systemet");
    }

    public Teacher getTeacher(String name) {
        for (Teacher teacher : teacherList) {
            if (teacher.getName().equalsIgnoreCase(name)) {
                return teacher;
            }
        }
        return null;
    }
    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public Student getStudent(String name) {
        for (Student student : studentList) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }
}
