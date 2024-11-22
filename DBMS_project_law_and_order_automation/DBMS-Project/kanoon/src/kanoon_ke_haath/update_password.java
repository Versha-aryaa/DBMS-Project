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
import kanoon_ke_haath.police_officer_panel;
class update_password
{
    static JFrame frame;
    static JLabel lblWelcome, lblPass, lblUsername;
    static JPasswordField txtNew;
    static JTextField txtUser;
    static JButton btnLogin, btnAdmin, btnSecurity;
    static JCheckBox show_pass;
    
    static Connection con;
    
    static void display()
    {
        frame = new JFrame("Update police officer password");
        lblWelcome = new JLabel("<HTML><h2>Welcome Admin!</h2></HTML>", JLabel.CENTER);
        lblUsername = new JLabel("<HTML><h3>Enter police officer name: </h3></HTML>");
        lblPass = new JLabel("<HTML><h3>New Password:</h3></HTML>");

        txtUser = new JTextField(60);
        txtNew = new JPasswordField(60);

        show_pass = new JCheckBox("Show password");
        show_pass.setForeground(new Color(255,189,68));

        show_pass.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
                if(e.getStateChange() == ItemEvent.SELECTED){
                    txtNew.setEchoChar((char)0);
                }
                else{
                    txtNew.setEchoChar('*');
                }
            }
        });

        lblWelcome.setForeground(new Color(255,189,68));
        lblUsername.setForeground(new Color(255,189,68));
        lblPass.setForeground(new Color(255,189,68));

        btnLogin = new JButton("Update");
        btnLogin.setBackground(new Color(1, 145, 135));
        btnLogin.addActionListener(new CustomActionListener());

        frame.add(lblWelcome);
        frame.add(lblUsername);
        frame.add(txtUser);
        frame.add(lblPass);
        frame.add(txtNew);
        frame.add(show_pass);
        frame.add(btnLogin);

        frame.getContentPane().setBackground(new Color(45,45,45));
        frame.setSize(550,350);
        frame.setVisible(true);
        frame.setLayout(new GridLayout(8,1));
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.getRootPane().setDefaultButton(btnLogin);
    }
    
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
    private static byte[] getSalt() throws NoSuchAlgorithmException {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            return salt;
        }
    static class CustomActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String user = txtUser.getText();
            String new_pass = txtNew.getText();
            try {
              Class.forName("com.mysql.jdbc.cj.Driver");
            } catch (ClassNotFoundException ae) {
                try {
                    throw ae;
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(police_login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","muskan");
                // Getting current officer's department
                String sql="update police_officer_list set POLICE_PASSWORD=?, POLICE_PASSWORD_SALT=? where USERNAME=?;";
                byte[] salt_po = getSalt();
                String password_po = getSecurePassword(new_pass, salt_po);
                PreparedStatement pst = con.prepareStatement(sql);
                
                pst.setString(1, password_po);
                pst.setBytes(2, salt_po);
                pst.setString(3, user);
                
                pst.executeUpdate();
                
                String sql_check = "select POLICE_PASSWORD from police_officer_list where USERNAME=?;";
                PreparedStatement pst_check = con.prepareStatement(sql_check);
                pst_check.setString(1, user);
                ResultSet rs_check = pst_check.executeQuery();
//                System.out.println("Got to before the rs_check");
                if(rs_check.next()){
//                    System.out.println("Got inside first if");
//                    System.out.println("password: " + rs_check.getString(1));
                    if(rs_check.getString(1).equals(password_po)){
//                        System.out.println("Got inside second if");
                        JOptionPane.showMessageDialog(null, "Password updated successfully!");
                        frame.setVisible(false);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Police officer doesn't exist!");
                }
            }  
            catch (SQLException ee) {
                try {
                    throw ee;
                } catch (SQLException ex) {
                    Logger.getLogger(police_login.class.getName()).log(Level.SEVERE, null, ex);
                }
              } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(update_password.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(police_login.class.getName()).log(Level.SEVERE, null, ex);
                }
              }
        }
    }
    
    static void init()
    {
        try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        update_password obj = new update_password();
        obj.display();
    }
    public static void main(String []args)
    {
        init();
    }
}

