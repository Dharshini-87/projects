package library3;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class ViewBooks extends JFrame {
    public ViewBooks() {
        setTitle("View Books");
        setSize(1300, 900);
        setLocationRelativeTo(null);
        setLayout(null); // We'll use absolute positioning since we're using a background image

        // Set background image
        ImageIcon bgImage = new ImageIcon("C:\\Users\\dd646\\eclipse-workspace\\Dharshini\\src\\library3\\image\\image2.jpg");
        JLabel background = new JLabel(bgImage);
        background.setBounds(0, 0, 1200, 800);
        setContentPane(background);
        background.setLayout(null);

        String[] columnNames = {"ID", "Title", "Author", "Quantity"};
        String[][] data = new String[100][4];
        int row = 0;

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library2", "root", "12345")) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books1");

            while (rs.next()) {
                int id = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int quantity = rs.getInt("quantity");

                data[row][0] = String.valueOf(id);
                data[row][1] = title;
                data[row][2] = author;
                data[row][3] = String.valueOf(quantity);
                row++;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "SQL Error: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }

        // Table creation
        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Serif", Font.PLAIN, 18)); // Font for table rows
        table.setForeground(new Color(60, 0, 90));        // Font color
        table.setRowHeight(30);

        // Set table header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(128, 0, 0));

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(100, 100, 1000, 500);
        background.add(pane);

        // Close button styling
        JButton closeBtn = new JButton("Close");
        closeBtn.setBounds(520, 630, 120, 40);
        closeBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setBackground(new Color(128, 0, 0));
        closeBtn.setFocusPainted(false);
        closeBtn.setBorder(BorderFactory.createLineBorder(new Color(90, 0, 0), 2));
        closeBtn.setOpaque(true);
        closeBtn.addActionListener(e -> dispose());
        background.add(closeBtn);
    }
}
