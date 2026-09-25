import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * Assignment 17 - Exercise 2
 * Create a simple Employee Registration Form using Java Swing GUI that takes
 * employee details such as Employee ID, Name, Department, and Salary.
 * Display the entered information in a dialog box.
 */
public class EmployeeRegistrationForm extends JFrame implements ActionListener {

    private final JTextField empIdField, nameField, deptField, salaryField;
    private final JButton submitButton;

    public EmployeeRegistrationForm() {
        setTitle("Employee Registration Form");
        setSize(400, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Employee ID:"));
        empIdField = new JTextField();
        panel.add(empIdField);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Department:"));
        deptField = new JTextField();
        panel.add(deptField);

        panel.add(new JLabel("Salary:"));
        salaryField = new JTextField();
        panel.add(salaryField);

        panel.add(new JLabel());
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        panel.add(submitButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String empId = empIdField.getText().trim();
        String name = nameField.getText().trim();
        String dept = deptField.getText().trim();
        String salaryText = salaryField.getText().trim();

        if (empId.isEmpty() || name.isEmpty() || dept.isEmpty() || salaryText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields.",
                    "Incomplete Form",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        double salary;
        try {
            salary = Double.parseDouble(salaryText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Salary must be a valid number.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String summary = String.format(
                "Employee Registered Successfully!\n\n"
                        + "Employee ID: %s\n"
                        + "Name: %s\n"
                        + "Department: %s\n"
                        + "Salary: %.2f",
                empId, name, dept, salary);

        JOptionPane.showMessageDialog(this, summary,
                "Employee Details", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EmployeeRegistrationForm::new);
    }
}
