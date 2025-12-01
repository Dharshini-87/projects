package library3;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddMember extends JFrame {
    JTextField nameField, emailField;
    JButton saveBtn, cancelBtn;
    ImageIcon bgImage;

    public AddMember() {
        setTitle("Add Member");
        setSize(1200, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Load background image (change path as needed)
        bgImage = new ImageIcon("C:\\Users\\dd646\\eclipse-workspace\\Dharshini\\src\\library3\\image\\addmember.jpg");

        // Panel with background
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        bgPanel.setLayout(null);

        // Labels
        JLabel nameLabel = new JLabel("Name:");
        JLabel emailLabel = new JLabel("Email:");

        Font labelFont = new Font("Arial", Font.BOLD, 25);
        nameLabel.setFont(labelFont);
        emailLabel.setFont(labelFont);

        nameLabel.setForeground(Color.blue);
        emailLabel.setForeground(Color.blue);

        // Text fields
        nameField = new JTextField();
        emailField = new JTextField();

        Font fieldFont = new Font("Arial", Font.PLAIN, 25);
        nameField.setFont(fieldFont);
        emailField.setFont(fieldFont);

        // Buttons
        saveBtn = new JButton("Save");
        cancelBtn = new JButton("Cancel");

        Font btnFont = new Font("Arial", Font.BOLD, 20);
        saveBtn.setFont(btnFont);
        cancelBtn.setFont(btnFont);

        saveBtn.setBackground(new Color(0, 150, 0));  // green
        saveBtn.setForeground(Color.WHITE);

        cancelBtn.setBackground(new Color(150, 0, 0)); // red
        cancelBtn.setForeground(Color.WHITE);

        // Positioning
        nameLabel.setBounds(400, 250, 300, 40);
        nameField.setBounds(600, 250, 300, 40);
        emailLabel.setBounds(400, 350, 300, 40);
        emailField.setBounds(600, 350, 300, 40);
        saveBtn.setBounds(480, 450, 100, 50);
        cancelBtn.setBounds(680, 450, 100, 50);

        // Add to panel
        bgPanel.add(nameLabel);
        bgPanel.add(nameField);
        bgPanel.add(emailLabel);
        bgPanel.add(emailField);
        bgPanel.add(saveBtn);
        bgPanel.add(cancelBtn);

        // Action listeners
        saveBtn.addActionListener(e -> saveMember());
        cancelBtn.addActionListener(e -> dispose());

        setContentPane(bgPanel);
    }

    private void saveMember() {
        String name = nameField.getText();
        String email = emailField.getText();

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/library2", "root", "12345")) {

            String sql = "INSERT INTO members1 (name, email) VALUES (?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Member added successfully!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
