import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * Assignment 18 - Exercise 1
 * Create a GUI calculator where buttons perform addition and subtraction.
 */
public class GUICalculator extends JFrame implements ActionListener {

    private JTextField num1Field, num2Field, resultField;
    private JButton addButton, subButton;

    public GUICalculator() {
        setTitle("GUI Calculator - Addition & Subtraction");
        setSize(380, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Number 1:"));
        num1Field = new JTextField();
        panel.add(num1Field);

        panel.add(new JLabel("Number 2:"));
        num2Field = new JTextField();
        panel.add(num2Field);

        addButton = new JButton("Add (+)");
        addButton.addActionListener(this);
        panel.add(addButton);

        subButton = new JButton("Subtract (-)");
        subButton.addActionListener(this);
        panel.add(subButton);

        panel.add(new JLabel("Result:"));
        resultField = new JTextField();
        resultField.setEditable(false);
        panel.add(resultField);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double num1, num2;
        try {
            num1 = Double.parseDouble(num1Field.getText().trim());
            num2 = Double.parseDouble(num2Field.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers in both fields.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double result;
        if (e.getSource() == addButton) {
            result = num1 + num2;
        } else {
            result = num1 - num2;
        }

        resultField.setText(String.valueOf(result));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUICalculator::new);
    }
}
