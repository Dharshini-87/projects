package library3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddBook extends JFrame {
    JTextField titleField, authorField, quantityField;
    JButton saveBtn;

    public AddBook() {
        setTitle("Add Book");
        setSize(1200, 800);
        setLayout(null);

        // Background image
        ImageIcon bgImage = new ImageIcon("C:\\Users\\dd646\\eclipse-workspace\\Dharshini\\src\\library3\\image\\addbook.jpg");
        JLabel background = new JLabel(bgImage);
        background.setBounds(0, 0, 1200, 800);
        setContentPane(background);
        background.setLayout(null);

        // Custom font
        Font labelFont = new Font("Serif", Font.BOLD, 25);
        Color fontColor = new Color(255,106,77); // Dark purple

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(labelFont);
        titleLabel.setForeground(fontColor);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setFont(labelFont);
        authorLabel.setForeground(fontColor);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(labelFont);
        quantityLabel.setForeground(fontColor);

        titleField = new JTextField();
        authorField = new JTextField();
        quantityField = new JTextField();

        // Optional: Font for text fields
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 16);
        titleField.setFont(fieldFont);
        authorField.setFont(fieldFont);
        quantityField.setFont(fieldFont);

        saveBtn = new JButton("Save");
        saveBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setBackground(new Color(230,76,0)); 
        saveBtn.setFocusPainted(false);
        saveBtn.setBorder(BorderFactory.createLineBorder(new Color(90, 0, 0), 2));
        saveBtn.setOpaque(true);

        // Set bounds
        titleLabel.setBounds(30, 50, 150, 25);
        titleField.setBounds(200, 50, 400, 50);

        authorLabel.setBounds(30,120, 150, 25);
        authorField.setBounds(200, 120, 400, 50);

        quantityLabel.setBounds(30, 190, 150, 25);
        quantityField.setBounds(200, 190, 400, 50);

        saveBtn.setBounds(250, 280, 150, 50);

        // Add components
        background.add(titleLabel);
        background.add(titleField);
        background.add(authorLabel);
        background.add(authorField);
        background.add(quantityLabel);
        background.add(quantityField);
        background.add(saveBtn);

        // Save action
        saveBtn.addActionListener(e -> saveBook());
    }

    private void saveBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        int quantity = Integer.parseInt(quantityField.getText());

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library2", "root", "12345")) {
            String sql = "INSERT INTO books1 (title, author, quantity) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, title);
            pst.setString(2, author);
            pst.setInt(3, quantity);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book added successfully!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
