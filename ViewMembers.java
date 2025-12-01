package library3;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewMembers extends JFrame {
    ImageIcon bgImage;

    public ViewMembers() {
        setTitle("View Members");
        setSize(1200, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Load background image (change the path)
        bgImage = new ImageIcon("C:\\Users\\dd646\\eclipse-workspace\\Dharshini\\src\\library3\\image\\viewmember.png");

        // Background panel
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        bgPanel.setLayout(new BorderLayout());

        // Column names
        String[] columnNames = {"ID", "Name", "Email"};
        String[][] data = new String[100][3];
        int row = 0;

        // Load data from DB
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library2", "root", "12345")) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM members1");

            while (rs.next()) {
                int id = rs.getInt("member_id");
                String name = rs.getString("name");
                String email = rs.getString("email");

                data[row][0] = String.valueOf(id);
                data[row][1] = name;
                data[row][2] = email;
                row++;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "SQL Error: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }

        // Table setup
        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(0, 102, 204));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane pane = new JScrollPane(table);
        pane.setPreferredSize(new Dimension(650, 550)); // Smaller table size
        pane.setOpaque(false);
        pane.getViewport().setOpaque(false);

        // Center panel to hold table
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(pane);
        bgPanel.add(centerPanel, BorderLayout.CENTER);

        // Close button
        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("Arial", Font.BOLD, 20));
        closeBtn.setBackground(new Color(150, 0, 0));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.addActionListener(e -> dispose());

        // Panel for close button (bottom-right)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 50));
        bottomPanel.setOpaque(false);
        bottomPanel.add(closeBtn);

        bgPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(bgPanel);
    }
}
