package kanoon_ke_haath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.Border;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class police_login {
    static JFrame frame;
    static JButton btnLogin, btnCancel;
    static JPanel panel, panel1, panel2, mainpanel;
    static JLabel lblheading, lblspace, lblUsername, lblPassword;
    static JTextField txtUsername;
    static JPasswordField txtPassword;
    static Border redline = BorderFactory.createLineBorder(Color.RED);
    static Border blackline = BorderFactory.createLineBorder(new Color(235, 235, 235));

    static Connection con;

    public static void user_details() {
        frame = new JFrame("POLICE LOGIN");
        mainpanel = new JPanel(new BorderLayout(10, 10));
        panel = new JPanel(new GridLayout(3, 1, 0, 0));
        panel1 = new JPanel(new GridLayout(3, 1, 0, 0));
        panel2 = new JPanel(new GridLayout(1, 3, 0, 0));

        lblheading = new JLabel("<HTML><h1>POLICE LOGIN</h1></HTML>", JLabel.CENTER);
        lblheading.setForeground(new Color(255, 189, 68));

        lblUsername = new JLabel("<HTML><h3>Username: </h3></HTML>", JLabel.CENTER);
        lblPassword = new JLabel("<HTML><h3>Password: </h3></HTML>", JLabel.CENTER);
        lblspace = new JLabel("				    ", JLabel.CENTER);

        btnCancel = new JButton("<HTML><h3>Cancel</h3></HTML>");
        btnCancel.setBackground(new Color(1, 145, 135));

        btnLogin = new JButton("<HTML><h3>Login</h3></HTML>");
        btnLogin.setBackground(new Color(1, 145, 135));

        btnCancel.addActionListener(new CustomActionListener());
        btnLogin.addActionListener(new CustomActionListener());

        txtUsername = new JTextField(60);
        txtPassword = new JPasswordField(60);

        lblUsername.setBorder(blackline);
        lblPassword.setBorder(blackline);

        txtUsername.setBorder(blackline);
        txtPassword.setBorder(blackline);

        panel2.add(new JLabel("<HTML><h1>POLICE LOGIN</h1></HTML>", JLabel.CENTER));
        panel.add(lblUsername);
        panel1.add(txtUsername);
        panel.add(lblPassword);
        panel1.add(txtPassword);
        panel.add(btnCancel);
        panel1.add(btnLogin);
        panel2.add(panel);
        panel2.add(panel1);
        panel2.setBackground(new Color(255, 189, 68));

        mainpanel.add(lblheading, BorderLayout.NORTH);
        mainpanel.add(panel2, BorderLayout.CENTER);
        mainpanel.add(btnCancel, BorderLayout.LINE_START);
        mainpanel.add(lblspace, BorderLayout.SOUTH);
        mainpanel.add(btnLogin, BorderLayout.LINE_END);
        mainpanel.setBackground(new Color(45, 45, 45));

        frame.setContentPane(mainpanel);
        frame.setSize(1920, 1080);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.getRootPane().setDefaultButton(btnLogin);
    }

    static class CustomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            Object source = ae.getSource();

            if (source == btnLogin) {
                try {
                    login();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(police_login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(police_login.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (source == btnCancel) {
                frame.setVisible(false);
            }
        }
    }

    public static void login() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw e;
        }
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306//test", "root", "muskan");

            String username = txtUsername.getText();
            String password = String.valueOf(txtPassword.getPassword());

            String sql = "SELECT * FROM police_officer_list WHERE USERNAME=? AND POLICE_PASSWORD=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Login Successful");
                frame.setVisible(false);
                police_application.init();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username or Password");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            con.close();
        }
    }

    static void init() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        user_details();
    }

    public static void main(String[] args) {
        init();
    }
}
