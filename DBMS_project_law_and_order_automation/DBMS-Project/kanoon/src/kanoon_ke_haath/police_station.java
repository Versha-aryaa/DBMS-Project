package kanoon_ke_haath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class police_station {
    static JFrame frame;
    static JTextField txtStationName, txtLocation;
    static JButton btnSave;
    static Connection con;

    static void display() {
        frame = new JFrame("Register Police Station");
        JLabel lblStationName = new JLabel("<html><h3>Station Name:</h3></html>");
        JLabel lblLocation = new JLabel("<html><h3>Location:</h3></html>");
        txtStationName = new JTextField(20);
        txtLocation = new JTextField(20);
        btnSave = new JButton("Save");
        btnSave.addActionListener(new SaveActionListener());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(lblStationName);
        panel.add(txtStationName);
        panel.add(lblLocation);
        panel.add(txtLocation);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(btnSave);

        frame.getContentPane().add(panel);
        frame.getContentPane().setBackground(new Color(45, 45, 45));
        frame.setSize(400, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    static class SaveActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String stationName = txtStationName.getText();
            String location = txtLocation.getText();

            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "muskan");
                String sql = "INSERT INTO police_station (station_name, location) VALUES (?, ?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, stationName);
                pst.setString(2, location);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Police Station Registered Successfully!");
                frame.setVisible(false);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            } finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
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
