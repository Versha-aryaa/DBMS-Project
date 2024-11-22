package kanoon_ke_haath;
import javax.swing.*;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.Border;
import java.util.concurrent.ExecutionException;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.*;

import kanoon_ke_haath.show_ps;
import kanoon_ke_haath.edit_ps;

class show_application {
  static JFrame frame;
  static JPanel mainpanel, panel;
  static JLabel lblCase;
  static JButton btnBack, btnSave, btnForm;
  static JTable table;
  static DefaultTableModel model;
  static Connection con;
  static String pstation;

  public void display() {
    SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
      @Override
      protected Boolean doInBackground() {
        model = new DefaultTableModel();
        model.addColumn("<HTML><h3>ID</h3></HTML>");
        model.addColumn("<HTML><h3>Type</h3></HTML>");
        model.addColumn("<HTML><h3>Name</h3></HTML>");
        model.addColumn("<HTML><h3>Email</h3></HTML>");
        model.addColumn("<HTML><h3>Contact</h3></HTML>");
        model.addColumn("<HTML><h3>Dated</h3></HTML>");
        model.addColumn("<HTML><h3>Aadhar</h3></HTML>");
        model.addColumn("<HTML><h3>Under officer</h3></HTML>");
        model.addColumn("<HTML><h3>Officer ID</h3></HTML>");
        try {
          Class.forName("com.mysql.jdbc.cj.Driver");
        } catch (ClassNotFoundException ae) {
          Logger.getLogger(show_application.class.getName()).log(Level.SEVERE, null, ae);
        }

        try {
          con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","muskan");

          String sql = "select * from applic where APP_PS=?";
          PreparedStatement pst = con.prepareStatement(sql);

          pst.setString(1, pstation);

          ResultSet rs = pst.executeQuery();
          while (rs.next()) {
            model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5),
                rs.getString(6), rs.getString(7), rs.getString(9), rs.getString(10), rs.getInt(11) });
          }
          table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
              // all cells false
              return false;
            }
          };
        } catch (SQLException ee) {
          Logger.getLogger(show_application.class.getName()).log(Level.SEVERE, null, ee);
        } finally {
          try {
            con.close();
          } catch (SQLException ee) {
            Logger.getLogger(show_application.class.getName()).log(Level.SEVERE, null, ee);
          }
        }
        return true;
      }

      @Override
      protected void done() {
        Boolean status;
        try {
          status = get();
          if (status == true) {
            frame = new JFrame("VIEW ALL APPLICATIONS");
            mainpanel = new JPanel(new BorderLayout(10, 10));
            panel = new JPanel(new GridLayout(1, 3, 0, 0));
            lblCase = new JLabel("<HTML><h1>Applications</h1></HTML>", JLabel.CENTER);
            lblCase.setForeground(new Color(255, 189, 68));

            btnBack = new JButton("<HTML><h2>Back</h2></HTML>");
            btnBack.setBackground(new Color(1, 145, 135));
            btnBack.addActionListener(new CustomActionListener());

            mainpanel.add(lblCase, BorderLayout.NORTH);
            mainpanel.add(btnBack, BorderLayout.LINE_START);

            mainpanel.setBackground(new Color(45, 45, 45));
            mainpanel.add(new JScrollPane(table), BorderLayout.CENTER);

            frame.add(mainpanel);
            frame.setSize(1920, 1080);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
          }
        } catch (InterruptedException e) {
          // This is thrown if the thread's interrupted.
          Logger.getLogger(show_application.class.getName()).log(Level.SEVERE, null, e);
        } catch (ExecutionException e) {
          // This is thrown if we throw an exception
          // from doInBackground.
          Logger.getLogger(show_application.class.getName()).log(Level.SEVERE, null, e);
        }
      }
    };
    worker.execute();
  }

  static class CustomActionListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      Object msg = e.getSource();
      if (msg.equals(btnBack)) {
        frame.setVisible(false);
      }
    }
  }

  public static void init() {
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (Exception e) {
      e.printStackTrace();
    }
    show_application obj = new show_application();
    obj.display();
  }

  public static void main(String[] args) {
    pstation = args[0];
    init();
  }
}