import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * Assignment 18 - Exercise 2
 * Create a Bank Balance Calculator GUI application using Java Swing where
 * the user enters the initial balance and transaction amount. Use buttons
 * to perform addition (deposit) and subtraction (withdrawal) and display
 * the updated balance.
 */
public class BankBalanceCalculator extends JFrame implements ActionListener {

    private JTextField initialBalanceField, transactionField, updatedBalanceField;
    private JButton depositButton, withdrawButton;
    private double currentBalance = 0.0;
    private boolean balanceInitialized = false;

    public BankBalanceCalculator() {
        setTitle("Bank Balance Calculator");
        setSize(400, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Initial Balance:"));
        initialBalanceField = new JTextField();
        panel.add(initialBalanceField);

        panel.add(new JLabel("Transaction Amount:"));
        transactionField = new JTextField();
        panel.add(transactionField);

        depositButton = new JButton("Deposit (+)");
        depositButton.addActionListener(this);
        panel.add(depositButton);

        withdrawButton = new JButton("Withdraw (-)");
        withdrawButton.addActionListener(this);
        panel.add(withdrawButton);

        panel.add(new JLabel("Updated Balance:"));
        updatedBalanceField = new JTextField();
        updatedBalanceField.setEditable(false);
        panel.add(updatedBalanceField);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double transactionAmount;

        // Initialize balance from the field on the first transaction only,
        // so subsequent transactions accumulate on the running balance.
        if (!balanceInitialized) {
            try {
                currentBalance = Double.parseDouble(initialBalanceField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid initial balance.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            balanceInitialized = true;
        }

        try {
            transactionAmount = Double.parseDouble(transactionField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid transaction amount.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (transactionAmount < 0) {
            JOptionPane.showMessageDialog(this,
                    "Transaction amount cannot be negative.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (e.getSource() == depositButton) {
            currentBalance += transactionAmount;
        } else if (e.getSource() == withdrawButton) {
            if (transactionAmount > currentBalance) {
                JOptionPane.showMessageDialog(this,
                        "Insufficient balance for this withdrawal.",
                        "Transaction Failed", JOptionPane.WARNING_MESSAGE);
                return;
            }
            currentBalance -= transactionAmount;
        }

        updatedBalanceField.setText(String.format("%.2f", currentBalance));
        initialBalanceField.setText(String.format("%.2f", currentBalance));
        transactionField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BankBalanceCalculator::new);
    }
}