package kanoon_ke_haath;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.awt.event.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
class show_police_station_verification
{
    static JFrame frame;
    static JLabel lblWelcome, lblPass, lblUsername;
    static JTextField txtUser;
    static JPasswordField txtPass;
    static JButton btnLogin, btnAdmin, btnSecurity;
    static JCheckBox show_pass;
    
    static Connection con;
    static String edit_ps_flag_arg;
    
    static void display(String edit_ps_flag)
    {
        edit_ps_flag_arg = edit_ps_flag;
        frame = new JFrame("POLICE LOGIN");
        lblWelcome = new JLabel("<HTML><h2>Welcome Admin!</h2></HTML>", JLabel.CENTER);
        lblUsername = new JLabel("<HTML><h3>Name of police station: </h3></HTML>");
        lblPass = new JLabel("<HTML><h3>Admin Password:</h3></HTML>");

        txtUser = new JTextField(60);
        txtPass = new JPasswordField(60);

        show_pass = new JCheckBox("Show password");
        show_pass.setForeground(new Color(255,189,68));

        show_pass.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if(e.getStateChange() == ItemEvent.SELECTED){
                    txtPass.setEchoChar((char)0);
                }
                else{
                    txtPass.setEchoChar('*');
                }
            }
        });

        lblWelcome.setForeground(new Color(255,189,68));
        lblUsername.setForeground(new Color(255,189,68));
        lblPass.setForeground(new Color(255,189,68));

        btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(1, 145, 135));
        btnLogin.addActionListener(new CustomActionListener());

        frame.add(lblWelcome);
        frame.add(lblUsername);
        frame.add(txtUser);
        frame.add(lblPass);
        frame.add(txtPass);
        frame.add(show_pass);
        frame.add(btnLogin);

        frame.getContentPane().setBackground(new Color(45,45,45));
        frame.setSize(550,350);
        frame.setVisible(true);
        frame.setLayout(new GridLayout(8,1));
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.getRootPane().setDefaultButton(btnLogin);
    }
    
//    public static void test_values(String username) throws SQLException, ClassNotFoundException {
////            System.out.println("got to test_values method");
//
//            try {
//              Class.forName("com.mysql.jdbc.Driver");
//            } catch (ClassNotFoundException e) {
//              throw e;
//            }
//            
//            try {
//                con = DriverManager.getConnection("jdbc:mysql://65.1.1.8:3306/test","police","Policemgmt@7police");
//                // Getting info of selected police department
//                String sql_dep="select * from police_dept as t where t.PD_NAME=?";
//                PreparedStatement pst_dep = con.prepareStatement(sql_dep);
//                
//                pst_dep.setString(1, username);
//                ResultSet rs_dep=pst_dep.executeQuery();
//                while(rs_dep.next()){
//                    System.out.println(rs_dep.getString(1));
//                    System.out.println(rs_dep.getString(2));
//                    System.out.println(rs_dep.getString(3));
//                    System.out.println(rs_dep.getString(4));
//                    System.out.println(rs_dep.getString(5));
//                    System.out.println(rs_dep.getString(6));
//                    System.out.println(rs_dep.getString(7));
//                }
//                // Getting info of all police officers of that dep
////                System.out.println("Now printing the police officers info");
//                String sql_police="select * from police_officer as t where t.PO_PS=?";
//                PreparedStatement pst_police = con.prepareStatement(sql_police);
//                
//                pst_police.setString(1, username);
//                
//                ResultSet rs_police=pst_police.executeQuery();
//                while(rs_police.next()){
//                    System.out.println(rs_police.getString(1));
//                    System.out.println(rs_police.getString(2));
//                    System.out.println(rs_police.getString(3));
//                    System.out.println(rs_police.getString(4));
//                    System.out.println(rs_police.getString(5));
//                    System.out.println(rs_police.getString(6));
//                    System.out.println(rs_police.getString(7));
//                    System.out.println(rs_police.getString(8));
//                    System.out.println(rs_police.getString(9));
//                    System.out.println(rs_police.getString(10));
//                    System.out.println(rs_police.getString(11));
//                }
//              } catch (SQLException e) {
//                throw e;
//              } finally {
//                con.close();
//              }
//    }
    
    public static String getSecurePassword(String password, byte[] salt) {

            String generatedPassword = null;
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(salt);
                byte[] bytes = md.digest(password.getBytes());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bytes.length; i++) {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                generatedPassword = sb.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return generatedPassword;
        }
    static class CustomActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            int flag=0;
            String username = txtUser.getText();
            String password = txtPass.getText();
            String database_password_hash = null;
            byte[] database_salt;
            
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","muskan");
                
                String sql="SELECT ADMIN_PASSWORD, ADMIN_PASSWORD_SALT FROM admin;";
                PreparedStatement pst = con.prepareStatement(sql);
                
                ResultSet rs = pst.executeQuery();
                
                if(rs.next()){
                    database_password_hash = rs.getString(1);
                    database_salt = rs.getBytes(2);
                    if(getSecurePassword(password, database_salt).equals(database_password_hash)){
//                        System.out.println("got secure hash");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Wrong password!");
                        flag=1;
                        frame.setVisible(false);
                    }
                }
//                System.out.println("Got after admin login database_password_hash: " + database_password_hash);

                String sql_pd="select * from police_dept where PD_NAME=?";
                PreparedStatement pst_pd = con.prepareStatement(sql_pd);
                
                pst_pd.setString(1, username);
                
                ResultSet rs_pd = pst_pd.executeQuery();
//                System.out.println("flag should be 0 and its: " + flag);
                  if(rs_pd.next()){
                        if(flag == 0){
//                            System.out.println("Logged in successfully");
                            if(edit_ps_flag_arg.trim().equals("Yes")){

                                edit_ps.main(new String[]{username});

                                frame.setVisible(false);
                            }
                            else{
                                show_ps.main(new String[]{username});
                                frame.setVisible(false);
                            } 
                        }
                  }
                    else{
                        JOptionPane.showMessageDialog(null, "Police Station with this name doesn't exist.");
                        frame.setVisible(false);
                    }
            }  
            catch (SQLException ee) {
                try {
                    throw ee;
                } catch (SQLException ex) {
                    Logger.getLogger(police_login.class.getName()).log(Level.SEVERE, null, ex);
                }
              } catch (ClassNotFoundException ex) {
                Logger.getLogger(show_police_station_verification.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(police_login.class.getName()).log(Level.SEVERE, null, ex);
                }
              }
        }
    }
    
    static void init(String edit_ps_flag)
    {
        try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        show_police_station_verification obj = new show_police_station_verification();
        obj.display(edit_ps_flag);
    }
    public static void main(String []args)
    {
        String edit_ps_flag = args[0];
        init(edit_ps_flag);
    }
}

