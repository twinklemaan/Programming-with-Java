import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LibraryManagementGUI extends JFrame implements ActionListener {
    JTextField tfId = new JTextField();
    JTextField tfTitle = new JTextField();
    JTextField tfAuthor = new JTextField();
    JTextField tfCopies = new JTextField();

    JButton btnAdd = new JButton("Add");
    JButton btnUpdate = new JButton("Update");
    JButton btnDelete = new JButton("Delete");

    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);
    Connection con;

    LibraryManagementGUI() {
        setTitle("Library Management System");

        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        form.add(new JLabel("Book ID:"));  form.add(tfId);
        form.add(new JLabel("Title:"));    form.add(tfTitle);
        form.add(new JLabel("Author:"));   form.add(tfAuthor);
        form.add(new JLabel("Copies:"));   form.add(tfCopies);

        JPanel buttons = new JPanel();
        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        model.setColumnIdentifiers(new String[]{"Book ID", "Title", "Author", "Copies"});

        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);

        // clicking a row copies it into the text fields
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
                tfId.setText(model.getValueAt(r, 0).toString());
                tfTitle.setText(model.getValueAt(r, 1).toString());
                tfAuthor.setText(model.getValueAt(r, 2).toString());
                tfCopies.setText(model.getValueAt(r, 3).toString());
            }
        });

        try {
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/college", "root", "root123");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Connection failed: " + e.getMessage());
        }

        viewBooks();

        setSize(600, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == btnAdd) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO books VALUES (?, ?, ?, ?)");
                ps.setInt(1, Integer.parseInt(tfId.getText()));
                ps.setString(2, tfTitle.getText());
                ps.setString(3, tfAuthor.getText());
                ps.setInt(4, Integer.parseInt(tfCopies.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Book added");
            } else if (e.getSource() == btnUpdate) {
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE books SET title=?, author=?, copies=? WHERE book_id=?");
                ps.setString(1, tfTitle.getText());
                ps.setString(2, tfAuthor.getText());
                ps.setInt(3, Integer.parseInt(tfCopies.getText()));
                ps.setInt(4, Integer.parseInt(tfId.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Book updated");
            } else if (e.getSource() == btnDelete) {
                PreparedStatement ps = con.prepareStatement("DELETE FROM books WHERE book_id=?");
                ps.setInt(1, Integer.parseInt(tfId.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Book deleted");
            }
            viewBooks();
            tfId.setText("");
            tfTitle.setText("");
            tfAuthor.setText("");
            tfCopies.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    void viewBooks() {
        try {
            model.setRowCount(0);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM books");
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new LibraryManagementGUI();
    }
}