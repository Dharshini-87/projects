package library3;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RemoveMember extends JFrame {
    JTextField memberIdField;
    JButton deleteBtn, cancelBtn;
    ImageIcon bgImage;

    public RemoveMember() {
        setTitle("Remove Member");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Load background image (update with your path)
        bgImage = new ImageIcon("C:\\Users\\dd646\\eclipse-workspace\\Dharshini\\src\\library3\\image\\removemember.jpg");

        // Background panel
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        bgPanel.setLayout(null);

        // Label
        JLabel lblId = new JLabel("Member ID:");
        lblId.setFont(new Font("Arial", Font.BOLD, 25));
        lblId.setForeground(Color.white); // white text for visibility

        // Text field
        memberIdField = new JTextField();
        memberIdField.setFont(new Font("Arial", Font.PLAIN, 20));

        // Buttons
        deleteBtn = new JButton("Remove");
        deleteBtn.setFont(new Font("Arial", Font.BOLD, 13));
        deleteBtn.setBackground(new Color(200, 0, 0)); // red
        deleteBtn.setForeground(Color.WHITE);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 13));
        cancelBtn.setBackground(new Color(0, 200, 0)); // grey
        cancelBtn.setForeground(Color.WHITE);

        // Positioning
        lblId.setBounds(400, 250, 200, 40);
        memberIdField.setBounds(600, 250, 300, 40);
        deleteBtn.setBounds(620, 310, 100, 40);
        cancelBtn.setBounds(740, 310, 100, 40);

        // Add components
        bgPanel.add(lblId);
        bgPanel.add(memberIdField);
        bgPanel.add(deleteBtn);
        bgPanel.add(cancelBtn);

        // Actions
        deleteBtn.addActionListener(e -> deleteMember());
        cancelBtn.addActionListener(e -> dispose());

        setContentPane(bgPanel);
    }

    private void deleteMember() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library2", "root", "12345")) {
            String sql = "DELETE FROM members1 WHERE member_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(memberIdField.getText()));
            int result = pst.executeUpdate();

            if (result > 0)
                JOptionPane.showMessageDialog(this, "Member removed successfully");
            else
                JOptionPane.showMessageDialog(this, "Member not found.");
            
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
