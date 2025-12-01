package library3;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class IssueBook extends JFrame {
    JTextField bookIdField, memberIdField;
    JButton issueBtn, cancelBtn;

    public IssueBook() {
        setTitle("Issue Book");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Load and resize background image
        ImageIcon bgIcon = new ImageIcon("C:\\Users\\dd646\\eclipse-workspace\\Dharshini\\src\\library3\\image\\issuebook.jpg"); // change path
        Image img = bgIcon.getImage().getScaledInstance(1000,800, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(img));
        background.setLayout(null);
        setContentPane(background);

        // Fonts
        Font labelFont = new Font("Segoe UI", Font.BOLD, 20);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 20);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        // Labels
        JLabel lblBook = new JLabel("Book ID:");
        lblBook.setFont(labelFont);
        lblBook.setForeground(Color.black);

        JLabel lblMember = new JLabel("Member ID:");
        lblMember.setFont(labelFont);
        lblMember.setForeground(Color.black);

        // Fields
        bookIdField = new JTextField();
        bookIdField.setFont(fieldFont);

        memberIdField = new JTextField();
        memberIdField.setFont(fieldFont);

        // Buttons
        issueBtn = new JButton("Issue");
        issueBtn.setFont(buttonFont);
        issueBtn.setBackground(new Color(0, 200, 0));
        issueBtn.setForeground(Color.WHITE);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(buttonFont);
        cancelBtn.setBackground(new Color(204, 0, 0));
        cancelBtn.setForeground(Color.WHITE);

        // Positioning
        lblBook.setBounds(60, 60, 150, 45);
        bookIdField.setBounds(280, 60, 300, 45);
        lblMember.setBounds(60, 130, 200, 45);
        memberIdField.setBounds(280, 130, 300, 45);
        issueBtn.setBounds(300, 200, 100, 45);
        cancelBtn.setBounds(450, 200, 100, 45);

        // Add components
        add(lblBook);
        add(bookIdField);
        add(lblMember);
        add(memberIdField);
        add(issueBtn);
        add(cancelBtn);

        // Actions
        issueBtn.addActionListener(e -> issueBook());
        cancelBtn.addActionListener(e -> dispose());
    }

    private void issueBook() {
        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/library2", "root", "12345")) {

            int bookId = Integer.parseInt(bookIdField.getText());
            int memberId = Integer.parseInt(memberIdField.getText());

            // 1. Check if Book exists
            PreparedStatement checkBook = con.prepareStatement(
                    "SELECT quantity FROM books1 WHERE book_id=?");
            checkBook.setInt(1, bookId);
            ResultSet rsBook = checkBook.executeQuery();
            if (!rsBook.next()) {
                JOptionPane.showMessageDialog(this, "Book ID not found.");
                return;
            }
            int quantity = rsBook.getInt("quantity");
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Book is out of stock.");
                return;
            }

            // 2. Check if Member exists
            PreparedStatement checkMember = con.prepareStatement(
                    "SELECT * FROM members1 WHERE member_id=?");
            checkMember.setInt(1, memberId);
            ResultSet rsMember = checkMember.executeQuery();
            if (!rsMember.next()) {
                JOptionPane.showMessageDialog(this, "Member ID not found.");
                return;
            }

            // 3. Insert into issued_books
            PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO issued_books1 (book_id, member_id, issue_date) VALUES (?, ?, ?)");
            pst.setInt(1, bookId);
            pst.setInt(2, memberId);
            pst.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            pst.executeUpdate();

            // 4. Update book quantity
            PreparedStatement updateBook = con.prepareStatement(
                    "UPDATE books1 SET quantity = quantity - 1 WHERE book_id=?");
            updateBook.setInt(1, bookId);
            updateBook.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book issued successfully!");
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric IDs.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
