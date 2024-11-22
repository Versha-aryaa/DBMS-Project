package kanoon_ke_haath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class admin_login {
    static JFrame frame;
    static JPasswordField txtPass;
    static JButton btnLogin;
    static Connection con;

    static void display() {
        frame = new JFrame("ADMIN LOGIN");
        JLabel lblWelcome = new JLabel("<html><h2>Welcome Back!</h2></html>", JLabel.CENTER);
        JLabel lblPass = new JLabel("<html><h3>Enter Admin Password:</h3></html>");
        txtPass = new JPasswordField(20);

        btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(1, 145, 135));
        btnLogin.addActionListener(new CustomActionListener());

        frame.add(lblWelcome);
        frame.add(lblPass);
        frame.add(txtPass);
        frame.add(btnLogin);

        frame.getContentPane().setBackground(new Color(45, 45, 45));
        frame.setSize(400, 200);
        frame.setVisible(true);
        frame.setLayout(new GridLayout(4, 1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getRootPane().setDefaultButton(btnLogin);
    }

    static class CustomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            char[] passwordChars = txtPass.getPassword();
            String password = new String(passwordChars);
            if (password.equals("password")) {
                JOptionPane.showMessageDialog(null, "Logged in successfully!");
                frame.setVisible(false);
                admin_page.init();
            } else {
                JOptionPane.showMessageDialog(null, "Wrong password!");
            }
        }
    }

    static void init() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        display();
    }

    public static void main(String[] args) {
        init();
    }
}
