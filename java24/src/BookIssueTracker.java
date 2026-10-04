import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BookIssueTracker extends JFrame implements ActionListener {
    JTextField tfIssueId = new JTextField();
    JTextField tfBookId = new JTextField();
    JTextField tfStudent = new JTextField();
    JTextField tfIssueDate = new JTextField();
    JTextField tfReturnDate = new JTextField();

    JButton btnIssue = new JButton("Issue Book");
    JButton btnUpdate = new JButton("Update");
    JButton btnDelete = new JButton("Delete");

    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);
    Connection con;

    BookIssueTracker() {
        setTitle("Book Issue Tracking System");

        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        form.add(new JLabel("Issue ID (for update/delete):")); form.add(tfIssueId);
        form.add(new JLabel("Book ID:"));                      form.add(tfBookId);
        form.add(new JLabel("Student Name:"));                 form.add(tfStudent);
        form.add(new JLabel("Issue Date (yyyy-mm-dd):"));      form.add(tfIssueDate);
        form.add(new JLabel("Return Date (blank if not returned):")); form.add(tfReturnDate);

        JPanel buttons = new JPanel();
        buttons.add(btnIssue);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        model.setColumnIdentifiers(
            new String[]{"Issue ID", "Book ID", "Student Name", "Issue Date", "Return Date"});

        btnIssue.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
                tfIssueId.setText(model.getValueAt(r, 0).toString());
                tfBookId.setText(model.getValueAt(r, 1).toString());
                tfStudent.setText(model.getValueAt(r, 2).toString());
                tfIssueDate.setText(model.getValueAt(r, 3).toString());
                tfReturnDate.setText(model.getValueAt(r, 4).toString());
            }
        });

        try {
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/college", "root", "root123");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Connection failed: " + e.getMessage());
        }

        viewIssues();

        setSize(750, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == btnIssue) {
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO book_issues (book_id, student_name, issue_date, return_date) VALUES (?, ?, ?, ?)");
                ps.setInt(1, Integer.parseInt(tfBookId.getText()));
                ps.setString(2, tfStudent.getText());
                ps.setDate(3, Date.valueOf(tfIssueDate.getText()));
                if (tfReturnDate.getText().equals("")) {
                    ps.setNull(4, Types.DATE);
                } else {
                    ps.setDate(4, Date.valueOf(tfReturnDate.getText()));
                }
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Book issued");
            } else if (e.getSource() == btnUpdate) {
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE book_issues SET book_id=?, student_name=?, issue_date=?, return_date=? WHERE issue_id=?");
                ps.setInt(1, Integer.parseInt(tfBookId.getText()));
                ps.setString(2, tfStudent.getText());
                ps.setDate(3, Date.valueOf(tfIssueDate.getText()));
                if (tfReturnDate.getText().equals("")) {
                    ps.setNull(4, Types.DATE);
                } else {
                    ps.setDate(4, Date.valueOf(tfReturnDate.getText()));
                }
                ps.setInt(5, Integer.parseInt(tfIssueId.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Record updated");
            } else if (e.getSource() == btnDelete) {
                PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM book_issues WHERE issue_id=?");
                ps.setInt(1, Integer.parseInt(tfIssueId.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Record deleted");
            }
            viewIssues();
            tfIssueId.setText("");
            tfBookId.setText("");
            tfStudent.setText("");
            tfIssueDate.setText("");
            tfReturnDate.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    void viewIssues() {
        try {
            model.setRowCount(0);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM book_issues");
            while (rs.next()) {
                String returnDate = rs.getString(5);
                if (returnDate == null) {
                    returnDate = "";
                }
                model.addRow(new Object[]{
                    rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), returnDate});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new BookIssueTracker();
    }
}