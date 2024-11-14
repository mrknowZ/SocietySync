package cped;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SocietySyncApp extends JFrame {
    private JTable memberTable;
    private DefaultTableModel tableModel;

    public SocietySyncApp() {
        setTitle("SocietySync");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"ID", "Name", "Email", "Category", "Joining Date","Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        memberTable = new JTable(tableModel);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(memberTable), BorderLayout.CENTER);

        JButton addButton = new JButton("Add Member");
        mainPanel.add(addButton, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMember();
            }
        });

        add(mainPanel);
        loadDataToTable();
    }

    private void addMember() {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField joiningDateField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        panel.add(new JLabel("Joining Date (YYYY-MM-DD):"));
        panel.add(joiningDateField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Member", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String sql = "INSERT INTO Members (name, email, category, joining_date) VALUES (?, ?, ?, ?)";
            try (Connection conn = DatabaseSetup.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, nameField.getText());
                pstmt.setString(2, emailField.getText());
                pstmt.setString(3, categoryField.getText());
                pstmt.setString(4, joiningDateField.getText());
                
                int rowsAffected = pstmt.executeUpdate(); // Execute and get affected rows
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Member added successfully!");
                    loadDataToTable(); // Reload table to show new data
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add member. Please try again.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error adding member: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0); // Clear existing data in the table
        String sql = "SELECT * FROM Members";
        try (Connection conn = DatabaseSetup.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("category"),
                    rs.getDate("joining_date")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SocietySyncApp().setVisible(true);
    }
}
