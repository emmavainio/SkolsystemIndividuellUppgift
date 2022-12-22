import javax.swing.*;
import java.awt.*;

public class SchoolSystemSwing extends JFrame {

    EventHandler eventHandler = new EventHandler(this);
    JButton student = new JButton("Student");
    JButton admin = new JButton("Admin");
    JButton courses = new JButton("Se kurser");
    JButton students = new JButton("Se studentlista");
    JButton teachers = new JButton("Se lärarlista");
    JPanel panel = new JPanel();

    public SchoolSystemSwing() {

        JLabel welcomeLb = new JLabel("Välkommen till skolsystemet!");
        JLabel loginLb = new JLabel("Är du skoladministratör eller elev?");
        student.addActionListener(eventHandler);

        admin.addActionListener(eventHandler);
        JPanel buttonPl = new JPanel();
        buttonPl.setLayout(new GridLayout(1,1));
        buttonPl.add(student); buttonPl.add(admin);

        panel.setLayout(new BorderLayout());
        panel.add(welcomeLb, BorderLayout.NORTH);
        panel.add(loginLb, BorderLayout.CENTER);
        panel.add(buttonPl, BorderLayout.SOUTH);
        add(panel);

        setSize(400,400);
        //pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void setStartPage() {
        panel.removeAll();
        JLabel label = new JLabel("Vad vill du göra?");
        courses.addActionListener(eventHandler);students.addActionListener(eventHandler);teachers.addActionListener(eventHandler);
        JPanel buttonPl = new JPanel();
        buttonPl.add(courses); buttonPl.add(students); buttonPl.add(teachers);
        panel.add(label, BorderLayout.NORTH); panel.add(buttonPl, BorderLayout.CENTER);
        pack();
        revalidate();
        repaint();
    }
    public static void main(String[] args) {
        new SchoolSystemSwing();
    }
}
