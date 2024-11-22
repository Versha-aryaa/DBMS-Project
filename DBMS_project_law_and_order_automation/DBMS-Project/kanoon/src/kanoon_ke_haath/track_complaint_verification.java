package kanoon_ke_haath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class track_complaint_verification {
    static JFrame frame;
    static JLabel lblWelcome, lblPass;
    static JTextField txtPass;
    static JButton btnLogin;
    static String uid;
    static Connection con;

    static void display() {
        frame = new JFrame("Track Complaint");
        lblWelcome = new JLabel("<HTML><h2>Welcome!</h2></HTML>", JLabel.CENTER);
        lblPass = new JLabel("<HTML><h3>Enter your unique tracking id:</h3></HTML>");
        txtPass = new JTextField(60);
        btnLogin = new JButton("Submit");

        btnLogin.setBackground(new Color(1, 145, 135));
        btnLogin.addActionListener(new CustomActionListener());

        frame.setLayout(new GridLayout(6, 1));
        frame.getContentPane().setBackground(new Color(45, 45, 45));
        frame.setSize(550, 350);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        frame.add(lblWelcome);
        frame.add(lblPass);
        frame.add(txtPass);
        frame.add(btnLogin);

        frame.setVisible(true);
    }

    static int check_uid(String uid) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw e;
        }

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "muskan");
            String sql_track = "select * from fir as t where t.FIR_TRACK=?";
            PreparedStatement pst_track = con.prepareStatement(sql_track);
            pst_track.setString(1, uid);

            ResultSet rs_track = pst_track.executeQuery();

            return rs_track.next() ? 1 : 0;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    static class CustomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            uid = txtPass.getText();
            try {
                if (check_uid(uid) == 1) {
                    frame.setVisible(false);
                    track_complaint.init(uid);
                } else {
                    JOptionPane.showMessageDialog(null, "Tracking id doesn't exist.");
                }
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(track_complaint_verification.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    static void init() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        track_complaint_verification obj = new track_complaint_verification();
        obj.display();
    }

    public static void main(String[] args) {
        init();
    }
}
