package library3;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DeleteBook extends JFrame {
    JTextField bookIdField;
    JButton deleteBtn, cancelBtn;
    JLabel lblId;
    ImageIcon bgImage;

    public DeleteBook() {
        setTitle("Delete Book");
        setSize(1200, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Load background image
        bgImage = new ImageIcon("C:\\Users\\dd646\\eclipse-workspace\\Dharshini\\src\\library3\\image\\deleteimage.jpg"); // <-- change path

        // Panel with background
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        bgPanel.setLayout(null);

        // Label
        lblId = new JLabel("Book ID:");
        lblId.setFont(new Font("Arial", Font.BOLD, 25));
        lblId.setForeground(Color.white); // Font color

        // Text field
        bookIdField = new JTextField();
        bookIdField.setFont(new Font("Arial", Font.PLAIN, 25));

        // Buttons
        deleteBtn = new JButton("Delete");
        cancelBtn = new JButton("Cancel");

        Font btnFont = new Font("Arial", Font.BOLD, 20);
        deleteBtn.setFont(btnFont);
        cancelBtn.setFont(btnFont);

        deleteBtn.setBackground(new Color(200, 0, 0));  // red
        deleteBtn.setForeground(Color.WHITE);

        cancelBtn.setBackground(new Color(100, 100, 100)); // grey
        cancelBtn.setForeground(Color.WHITE);

        // Positioning
        lblId.setBounds(400, 200, 300, 40);
        bookIdField.setBounds(550, 200, 300, 40);
        deleteBtn.setBounds(480, 270, 100, 40);
        cancelBtn.setBounds(650, 270, 100, 40);

        // Add to panel
        bgPanel.add(lblId);
        bgPanel.add(bookIdField);
        bgPanel.add(deleteBtn);
        bgPanel.add(cancelBtn);

        // Action listeners
        deleteBtn.addActionListener(e -> deleteBook());
        cancelBtn.addActionListener(e -> dispose());

        setContentPane(bgPanel);
    }

    private void deleteBook() {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/library2", "root", "12345")) {

            String sql = "DELETE FROM books1 WHERE book_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(bookIdField.getText()));
            int result = pst.executeUpdate();

            if (result > 0)
                JOptionPane.showMessageDialog(this, "Book deleted!");
            else
                JOptionPane.showMessageDialog(this, "Book not found.");

            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
