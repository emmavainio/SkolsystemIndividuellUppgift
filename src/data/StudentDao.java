package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//Ändra fileinput till enum!!

public class StudentDao {
    private ArrayList<Student> studentList = new ArrayList<>();
    public StudentDao() {

        String temp;

        String file = "src/data/Students.csv";
        try (BufferedReader buf = new BufferedReader(new FileReader(file))) {
            while ((temp = buf.readLine()) != null) {
                String[] fileInput = temp.split(",");
                var person = PersonFactory.createPerson("STUDENT", fileInput[0], fileInput[1], fileInput[2]);
                if (person instanceof Student) {
                    studentList.add((Student) person);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public void addStudent(Student student) {
        studentList.add(student);
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
