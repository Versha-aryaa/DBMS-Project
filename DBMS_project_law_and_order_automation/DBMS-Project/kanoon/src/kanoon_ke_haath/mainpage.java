package kanoon_ke_haath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

class mainpage {
    static JFrame frame;
    static JPanel panel, panel1, panel2, mainpanel;
    static JLabel lblkkh, img, space;
    static JButton aboutBtn, quitBtn, admin_loginBtn, police_loginBtn, register_complaintBtn, register_applicationBtn,
            track_complaintBtn;

    static Connection con;

    public void display() throws IOException {
        frame = new JFrame("Main Screen");
        panel = new JPanel(new BorderLayout(10, 10));
        panel1 = new JPanel(new GridLayout(9, 1, 10, 10));
        panel2 = new JPanel(new GridLayout(1, 2, 10, 10));
        mainpanel = new JPanel(new GridLayout(1, 1, 10, 10));

        lblkkh = new JLabel(
                "<HTML><h1>KANOON KE HAATH</h1><h3><center><i>Delivering Excellence</i></center></h3></HTML>",
                JLabel.CENTER);
        lblkkh.setForeground(new Color(255, 189, 68));

        aboutBtn = new JButton("<HTML><h2>About</h2></HTML>");
        quitBtn = new JButton("<HTML><h2>Quit</h2></HTML>");
        admin_loginBtn = new JButton("<HTML><h2>Admin Login</h2></HTML>");
        police_loginBtn = new JButton("<HTML><h2>Police Login</h2></HTML>");
        register_complaintBtn = new JButton("<HTML><h2>Register Complaint</h2></HTML>");
        register_applicationBtn = new JButton("<HTML><h2>Register Application</h2></HTML>");
        track_complaintBtn = new JButton("<HTML><h2>Track Complaint</h2></HTML>");

        space = new JLabel("    ");

        aboutBtn.addActionListener(new CustomActionListener());
        admin_loginBtn.addActionListener(new CustomActionListener());
        police_loginBtn.addActionListener(new CustomActionListener());
        quitBtn.addActionListener(new CustomActionListener());
        register_complaintBtn.addActionListener(new CustomActionListener());
        register_applicationBtn.addActionListener(new CustomActionListener());
        track_complaintBtn.addActionListener(new CustomActionListener());

        quitBtn.setBackground(new Color(255, 92, 96));

        panel1.add(aboutBtn);
        panel1.add(admin_loginBtn);
        panel1.add(police_loginBtn);
        panel1.add(register_complaintBtn);
        panel1.add(register_applicationBtn);
        panel1.add(track_complaintBtn);
        panel1.add(quitBtn);

        img = new JLabel(" ", JLabel.CENTER);
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getClassLoader().getResource("img.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageIcon imageIcon = new ImageIcon(fitimage(image, 750, 820));
        img.setIcon(imageIcon);
        panel2.add(img);
        panel2.add(panel1);

        panel.add(panel2, BorderLayout.CENTER);
        panel.add(lblkkh, BorderLayout.NORTH);
        panel.add(space, BorderLayout.LINE_END);

        panel.setBackground(new Color(45, 45, 45));
        panel1.setBackground(new Color(45, 45, 45));
        panel2.setBackground(new Color(45, 45, 45));

        mainpanel.add(panel);
        frame.add(mainpanel);
        frame.setSize(1920, 1080);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    static Image fitimage(Image img, int w, int h) {
        BufferedImage resizedimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedimage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0, w, h, null);
        g2.dispose();
        return resizedimage;
    }

    static class CustomActionListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            Object source = ae.getSource();

            if (source == admin_loginBtn) {
                admin_login.init();
            } else if (source == aboutBtn) {
                try {
                    about.init();
                } catch (IOException ex) {
                    Logger.getLogger(mainpage.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (source == police_loginBtn) {
                police_login.init();
            } else if (source == register_complaintBtn) {
                complaint.init();
            } else if (source == register_applicationBtn) {
                register_application.init();
            } else if (source == track_complaintBtn) {
                track_complaint_verification.init();
            } else if (source == quitBtn) {
                System.exit(0);
            }
        }
    }

    public static void set_up_database() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw e;
        }

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "muskan");

            // set up tables
            // Your table creation and insertion code...

        } catch (SQLException e) {
            throw e;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    // Log or handle the exception
                    e.printStackTrace();
                }
            }
        }
    }

    static void init() throws IOException, Exception {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainpage obj = new mainpage();
        obj.display();
        obj.set_up_database();
    }

    public static void main(String[] args) throws IOException, Exception {
        init();
    }
}
