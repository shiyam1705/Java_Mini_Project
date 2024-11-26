package ExpenseTracker.src;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import ExpenseTracker.ExpenseAndIncomeTrackerApp;

public class Login {
    private JFrame loginFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final String CORRECT_USERNAME = "system"; // Predefined username
    private final String CORRECT_PASSWORD = "rec"; // Predefined password

    public Login() {
        // Create and set up the login window
        loginFrame = new JFrame("AURA - Login");
        loginFrame.setSize(400, 300);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setUndecorated(true); // Remove window decorations
        loginFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(52, 73, 94)));

        // Create main panel with a nice background color
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(211, 211, 211));

        // Create title label
        JLabel titleLabel = new JLabel("AURA EXPENSE TRACKER");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(90, 30, 250, 30);
        titleLabel.setForeground(new Color(52, 73, 94));

        // Create username label and field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 90, 80, 25);
        usernameField = new JTextField();
        usernameField.setBounds(140, 90, 200, 25);

        // Create password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 130, 80, 25);
        passwordField = new JPasswordField();
        passwordField.setBounds(140, 130, 200, 25);

        // Create login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(140, 180, 100, 30);
        loginButton.setBackground(new Color(41, 128, 185));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Create exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(250, 180, 90, 30);
        exitButton.setBackground(new Color(231, 80, 60));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add action listener to login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.equals(CORRECT_USERNAME) && password.equals(CORRECT_PASSWORD)) {
                    loginFrame.dispose(); // Close login window
                    // Start the main application
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new ExpenseAndIncomeTrackerApp();
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(loginFrame,
                            "Invalid username or password!",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                    // Clear the password field
                    passwordField.setText("");
                }
            }
        });

        // Add action listener to exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Add components to the panel
        mainPanel.add(titleLabel);
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(loginButton);
        mainPanel.add(exitButton);

        // Add panel to frame
        loginFrame.add(mainPanel);
        loginFrame.setVisible(true);
    }

    // Modify your main method to start with the login page
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login();
            }
        });
    }
}
