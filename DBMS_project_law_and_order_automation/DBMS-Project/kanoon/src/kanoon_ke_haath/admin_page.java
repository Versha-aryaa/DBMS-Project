package kanoon_ke_haath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class admin_page {
    static JFrame frame;
    static JButton addofficerBtn, quitBtn, showpsBtn, addpsBtn, addcrimBtn, editpsBtn, updatePasswordBtn;

    public static void display() {
        frame = new JFrame("Main Screen");
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel panel1 = new JPanel(new GridLayout(8, 1, 10, 10));
        JPanel panel2 = new JPanel(new GridLayout(1, 2, 10, 10));
        JPanel mainpanel = new JPanel(new GridLayout(1, 1, 10, 10));

        JLabel lbladmin = new JLabel("<html><h1>ADMINISTRATOR</h1><h3><center><i>Duniya k Rakhwaale</i></center></h3></html>",
                JLabel.CENTER);
        lbladmin.setForeground(new Color(255, 189, 68));

        addpsBtn = new JButton("<html><h2>Add Police Station</h2></html>");
        showpsBtn = new JButton("<html><h2>Show Police Station</h2></html>");
        addofficerBtn = new JButton("<html><h2>Appoint a Police Officer</h2></html>");
        quitBtn = new JButton("<html><h2>Quit</h2></html>");
        addcrimBtn = new JButton("<html><h2>Register Criminal</h2></html>");
        editpsBtn = new JButton("<html><h2>Edit Police Station</h2></html>");
        updatePasswordBtn = new JButton("<html><h2>Update Police Officer Password</h2></html>");

        addpsBtn.addActionListener(new CustomActionListener());
        showpsBtn.addActionListener(new CustomActionListener());
        addofficerBtn.addActionListener(new CustomActionListener());
        quitBtn.addActionListener(new CustomActionListener());
        addcrimBtn.addActionListener(new CustomActionListener());
        editpsBtn.addActionListener(new CustomActionListener());
        updatePasswordBtn.addActionListener(new CustomActionListener());

        quitBtn.setBackground(new Color(255, 92, 96));

        panel1.add(addpsBtn);
        panel1.add(showpsBtn);
        panel1.add(addofficerBtn);
        panel1.add(addcrimBtn);
        panel1.add(editpsBtn);
        panel1.add(updatePasswordBtn);
        panel1.add(quitBtn);

        panel2.add(new JLabel(" "));
        panel2.add(panel1);

        panel.add(panel2, BorderLayout.CENTER);
        panel.add(lbladmin, BorderLayout.NORTH);
        panel.add(new JLabel(" "), BorderLayout.LINE_END);

        panel.setBackground(new Color(45, 45, 45));
        panel1.setBackground(new Color(45, 45, 45));
        panel2.setBackground(new Color(45, 45, 45));

        mainpanel.add(panel);
        frame.add(mainpanel);
        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static class CustomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            Object source = ae.getSource();

            if (source == addofficerBtn) {
                police_application.init();
            } else if (source == addpsBtn) {
                police_station.init();
            } else if (source == showpsBtn) {
                show_police_station_verification.main(new String[]{"No"});
            } else if (source == addcrimBtn) {
                register_criminal.init();
            } else if (source == editpsBtn) {
                show_police_station_verification.main(new String[]{"Yes"});
            } else if (source == updatePasswordBtn) {
                update_password.init();
            } else if (source == quitBtn) {
                frame.setVisible(false);
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
