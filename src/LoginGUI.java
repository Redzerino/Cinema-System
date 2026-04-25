// Name: Sarah Crotty
// Date: 22/03/2026
// Purpose: Login GUI for admin access to the system

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginGUI extends JFrame
{
    private JTextField emailField;
    private JPasswordField passwordField;
    private Color darkNavy = new Color(13, 13, 43);

    // Sets up the login window UI
    public LoginGUI()
    {
        setTitle("NovaPlex Cinema System - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Sets window icon
        try
        {
            ImageIcon icon = new ImageIcon("images/Logo.png");
            setIconImage(icon.getImage());
        }
        catch(Exception e)
        {
            System.out.println("Logo not found");
        }

        setLayout(new BorderLayout());

        // Banner section with image or fallback text
        JPanel bannerPanel = new JPanel(new BorderLayout());
        bannerPanel.setBackground(darkNavy);

        try
        {
            ImageIcon bannerIcon = new ImageIcon("images/Banner.png");
            Image bannerImg = bannerIcon.getImage().getScaledInstance(450, 80, Image.SCALE_SMOOTH);
            JLabel bannerLabel = new JLabel(new ImageIcon(bannerImg));
            bannerPanel.add(bannerLabel, BorderLayout.CENTER);
        }
        catch(Exception e)
        {
            JLabel fallback = new JLabel("NovaPlex Cinema System", SwingConstants.CENTER);
            fallback.setFont(new Font("Arial", Font.BOLD, 24));
            fallback.setForeground(Color.WHITE);
            fallback.setPreferredSize(new Dimension(450, 80));
            bannerPanel.add(fallback, BorderLayout.CENTER);
        }

        add(bannerPanel, BorderLayout.NORTH);

        // Login form inputs
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));
        loginPanel.setBackground(Color.WHITE);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));

        emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel roleLabel = new JLabel("Admin access only", SwingConstants.CENTER);
        roleLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        roleLabel.setForeground(Color.GRAY);

        loginPanel.add(emailLabel);
        loginPanel.add(emailField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel(""));
        loginPanel.add(roleLabel);

        add(loginPanel, BorderLayout.CENTER);

        // Login button setup
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(13, 13, 43));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setPreferredSize(new Dimension(150, 40));

        buttonPanel.add(loginButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Login button click action
        loginButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                attemptLogin();
            }
        });

        // Login when Enter key is pressed
        passwordField.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                attemptLogin();
            }
        });
    }

    // Handles login validation and navigation
    private void attemptLogin()
    {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if(email.isEmpty() || password.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Please enter your email and password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(checkAdminLogin(email, password))
        {
            JOptionPane.showMessageDialog(this, "Welcome back!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Invalid credentials or you do not have Admin access!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    // Checks database for valid admin login
    private boolean checkAdminLogin(String email, String password)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE email=? AND BINARY password_hash=? AND role='Admin' AND isActive=1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}