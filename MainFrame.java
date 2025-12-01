package library3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Library Management System");
        setSize(2000, 1500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon bgImage = new ImageIcon("C:\\Users\\dd646\\eclipse-workspace\\Dharshini\\src\\library3\\image\\book.jpg");
        JLabel background = new JLabel(bgImage);
        background.setBounds(0, 0, 1500, 1000);
        setContentPane(background);
        background.setLayout(null);

        // Custom font
        Font customFont = new Font("Quicksand", Font.ITALIC, 30);

        // Create buttons
        JButton[] buttons = {
            new JButton("Add Book"),
            new JButton("View Books"),
            new JButton("Issue Book"),
            new JButton("Return Book"),
            new JButton("Delete Book"),
            new JButton("Add Member"),
            new JButton("View Member"),
            new JButton("Remove Member"),
            new JButton("Exit")
        };

        int y = 60;
        for (JButton btn : buttons) {
            btn.setBounds(900, y, 450, 60);
            btn.setFont(customFont);
            btn.setForeground(new Color(175, 62, 62));
            btn.setBackground(new Color(255, 170, 170)); // Dark purple
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(new Color(205, 86, 86), 2, true));
            btn.setOpaque(true);
            background.add(btn);
            y += 70;
        }

        // Assign actions
        buttons[0].addActionListener(e -> new AddBook().setVisible(true));
        buttons[1].addActionListener(e -> new ViewBooks().setVisible(true));
        buttons[2].addActionListener(e -> new IssueBook().setVisible(true));
        buttons[3].addActionListener(e -> new ReturnBook().setVisible(true));
        buttons[4].addActionListener(e -> new DeleteBook().setVisible(true));
        buttons[5].addActionListener(e -> new AddMember().setVisible(true));
        buttons[6].addActionListener(e -> new ViewMembers().setVisible(true));
        buttons[7].addActionListener(e -> new RemoveMember().setVisible(true));
        buttons[8].addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        // Optional: Load font from TTF if available
        try {
            Font custom = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("C:\\path\\to\\Quicksand-Bold.ttf")).deriveFont(22f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(custom);
        } catch (Exception e) {
            System.out.println("Custom font not loaded, default used.");
        }

        new MainFrame().setVisible(true);
    }
}
