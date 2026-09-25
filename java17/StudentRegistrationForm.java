import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * Assignment 17 - Exercise 1
 * Create a simple Student Registration Form GUI using Swing.
 */
public class StudentRegistrationForm extends JFrame implements ActionListener {

    private final JTextField nameField, rollField, emailField, courseField;
    private final JButton submitButton, clearButton;

    public StudentRegistrationForm() {
        setTitle("Student Registration Form");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Roll Number:"));
        rollField = new JTextField();
        panel.add(rollField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Course:"));
        courseField = new JTextField();
        panel.add(courseField);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        panel.add(submitButton);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        panel.add(clearButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String name = nameField.getText().trim();
            String roll = rollField.getText().trim();
            String email = emailField.getText().trim();
            String course = courseField.getText().trim();

            if (name.isEmpty() || roll.isEmpty() || email.isEmpty() || course.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please fill in all fields.",
                        "Incomplete Form",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String summary = """
        Student Registered Successfully!
        
        Name: %s
        Roll Number: %s
        Email: %s
        Course: %s""".formatted(name, roll, email, course);

            JOptionPane.showMessageDialog(this, summary,
                    "Registration Details", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == clearButton) {
            nameField.setText("");
            rollField.setText("");
            emailField.setText("");
            courseField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentRegistrationForm::new);
    }
}