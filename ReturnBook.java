package library3;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class ReturnBook extends JFrame {
    JTextField issueIdField;
    JButton returnBtn, cancelBtn;
    JLabel lblId;
    JLabel background;

    public ReturnBook() {
        setTitle("Return Book");
        setSize(1200,800);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Load background image
        ImageIcon bgIcon = new ImageIcon("C:\\Users\\dd646\\eclipse-workspace\\Dharshini\\src\\library3\\image\\returnbook.jpg");
        background = new JLabel(bgIcon);
        background.setBounds(0, 0, 400, 250);
        setContentPane(background);
        background.setLayout(null);

        // Font settings
        Font labelFont = new Font("Arial", Font.BOLD, 25);
        Color fontColor = new Color(255, 0, 0);

        lblId = new JLabel("Issue book ID:");
        lblId.setFont(labelFont);
        lblId.setForeground(fontColor);

        issueIdField = new JTextField();
        returnBtn = new JButton("Return");
        cancelBtn = new JButton("Cancel");

        // Styling buttons
        returnBtn.setFont(new Font("Arial", Font.BOLD, 14));
        returnBtn.setBackground(new Color(0, 153, 76));
        returnBtn.setForeground(Color.WHITE);

        cancelBtn.setFont(new Font("Arial", Font.BOLD, 14));
        cancelBtn.setBackground(new Color(204, 0, 0));
        cancelBtn.setForeground(Color.WHITE);

        // Set positions
        lblId.setBounds(500, 100, 200, 40);
        issueIdField.setBounds(700, 100, 250, 40);
        returnBtn.setBounds(720, 200, 100, 30);
        cancelBtn.setBounds(850, 200, 100, 30);

        // Add components to background label
        background.add(lblId);
        background.add(issueIdField);
        background.add(returnBtn);
        background.add(cancelBtn);

        returnBtn.addActionListener(e -> returnBook());
        cancelBtn.addActionListener(e -> dispose());
    }

    private void returnBook() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "12345")) {
            String sql = "UPDATE issued_books SET return_date = ? WHERE issue_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(LocalDate.now()));
            pst.setInt(2, Integer.parseInt(issueIdField.getText()));
            int rows = pst.executeUpdate();

            if (rows > 0)
                JOptionPane.showMessageDialog(this, "Book Returned!");
            else
                JOptionPane.showMessageDialog(this, "Issue ID not found.");

            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
