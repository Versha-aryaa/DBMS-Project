package kanoon_ke_haath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

public class track_complaint{
	static JFrame frame;
	static JButton btnSubmit, btnCancel;

	static JPanel panel, panel1, panel2, mainpanel;

//	static JTextArea txtArea;
	
	static JLabel lblspace, lblheading, lblFatherName, lblName, lblEmail, lblContact, lblDOI, lblPS, lblDepartment, lblDesc, lblStatus;
	static JLabel txtName, txtFatherName, txtEmail, txtDOI, txtPS, txtContact, txtDepartment, txtArea, txtStatus;
//	static JComboBox txtDepartment;
	static Border redline = BorderFactory.createLineBorder(Color.RED);
        static Border blackline = BorderFactory.createLineBorder(new Color(235,235,235));
        
        static String name, fname, contact, email, doi, ps, dep, desc, status;
        static int stat;
        static Connection con;
        static UUID idOne;
	public static void user_details(String uid) throws ClassNotFoundException, SQLException
	{   
                System.out.println("uid in user_details: " + uid);
		frame = new JFrame("TRACK COMPLAINT");
		mainpanel = new JPanel(new BorderLayout(10,10));
		panel = new JPanel(new GridLayout(9,1,0,0));
		panel1 = new JPanel(new GridLayout(9,1,0,0));
		panel2 = new JPanel(new GridLayout(1,3,0,0));

		//**************************************************************************************************************
		// USER DETAILS
                        lblheading = new JLabel("<HTML><h1>TRACK COMPLAINT </h1></HTML>", JLabel.CENTER);
                        lblheading.setForeground(new Color(255,189,68));
			lblName = new JLabel("<HTML><h3>Name: </h3></HTML>", JLabel.CENTER);
			lblFatherName = new JLabel("<HTML><h3>Father's / Husband's Name: </h3></HTML>", JLabel.CENTER);
			lblEmail = new JLabel("<HTML><h3>Email: </h3></HTML>", JLabel.CENTER);
			lblContact = new JLabel("<HTML><h3>Phone: </h3></HTML>", JLabel.CENTER);
			lblDOI = new JLabel("<HTML><h3>Date of Issue: </h3></HTML>", JLabel.CENTER);
			lblPS = new JLabel("<HTML><h3>Name of police station </h3></HTML>", JLabel.CENTER);
			lblDepartment = new JLabel("<HTML><h3>Department: </h3></HTML>", JLabel.CENTER);
			lblDesc  = new JLabel("<HTML><h3>Description: </h3></HTML>", JLabel.CENTER);
                        lblStatus  = new JLabel("<HTML><h3>Status of complaint: </h3></HTML>", JLabel.CENTER);
                        lblspace = new JLabel("				    ", JLabel.CENTER);
                        
			btnCancel = new JButton("<HTML><h3>Cancel</h3></HTML>");
			btnCancel.setBackground(new Color(1, 145, 135));

			
			btnCancel.addActionListener(new CustomActionListener());

                        txtName = new JLabel("test");
			txtFatherName = new JLabel("test");
			txtEmail = new JLabel("test");
			txtContact = new JLabel("test");
			txtDOI = new JLabel("test");
			txtPS = new JLabel("test");
                        txtArea = new JLabel("test");
                        txtDepartment = new JLabel("test");
                        txtStatus = new JLabel("test");
//                        panel2.add("jProgressBar", jProgressBar);
                        
                        ArrayList<String> values = get_values(uid);
                        
                        panel2.add(new JLabel("<HTML><h1>TRACK COMPLAINT</h1></HTML>", JLabel.CENTER));
			panel.add(lblName);			panel1.add(txtName);
			panel.add(lblFatherName);	panel1.add(txtFatherName);
			panel.add(lblEmail);		panel1.add(txtEmail);
			panel.add(lblContact);		panel1.add(txtContact);
			panel.add(lblDOI);			panel1.add(txtDOI);
			panel.add(lblPS);			panel1.add(txtPS);
			panel.add(lblDepartment);	panel1.add(txtDepartment);
			panel.add(lblDesc);             panel1.add(txtArea);
                        panel.add(lblStatus);           panel1.add(txtStatus);                  
			
                        lblName.setBorder(blackline);			txtName.setBorder(blackline);
                        lblFatherName.setBorder(blackline);	txtFatherName.setBorder(blackline);
                        lblEmail.setBorder(blackline);		txtEmail.setBorder(blackline);
                        lblContact.setBorder(blackline);		txtContact.setBorder(blackline);
                        lblDOI.setBorder(blackline);			txtDOI.setBorder(blackline);
                        lblPS.setBorder(blackline);			txtPS.setBorder(blackline);
                        lblDepartment.setBorder(blackline);	txtDepartment.setBorder(blackline);
                        lblDesc.setBorder(blackline);			txtArea.setBorder(blackline);
                        lblStatus.setBorder(blackline);		txtStatus.setBorder(blackline);
			
                        
			panel2.add(panel);
                        panel2.add(panel1);
                        panel2.setBackground(new Color(255,189,68));


                        mainpanel.add(lblheading, BorderLayout.NORTH);
                        mainpanel.add(panel2, BorderLayout.CENTER);
                        mainpanel.add(btnCancel, BorderLayout.LINE_START);
                        mainpanel.add(lblspace, BorderLayout.SOUTH);
                        mainpanel.add(new JLabel("		 ", JLabel.CENTER), BorderLayout.LINE_END);


                        mainpanel.setBackground(new Color(45,45,45));
                        frame.setContentPane(mainpanel);
                        
			frame.setSize(1920,1080);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                        
                        txtName.setText("<HTML><h3>" + values.get(0)  + "</h3></HTML>");
			txtFatherName.setText("<HTML><h3>" + values.get(1) + "</h3></HTML>");
			txtEmail.setText("<HTML><h3>" + values.get(2)  + "</h3></HTML>");
			txtContact.setText("<HTML><h3>" + values.get(3)  + "</h3></HTML>");
			txtDOI.setText("<HTML><h3>" + values.get(4)  + "</h3></HTML>");
			txtPS.setText("<HTML><h3>" + values.get(5)  + "</h3></HTML>");
                        txtArea.setText("<HTML><h3>" + values.get(6)  + "</h3></HTML>");
                        txtDepartment.setText("<HTML><h3>" + values.get(7)  + "</h3></HTML>");
                        txtStatus.setText("<HTML><h3>" + values.get(8)  + "</h3></HTML>");
		//**************************************************************************************************************
	}
        
        static class CustomActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			Object source = ae.getSource();
                        if(source == btnCancel){
                            frame.setVisible(false);
                        }
		}
	}
        
        public static ArrayList<String> get_values(String uid) throws ClassNotFoundException, SQLException {
//            System.out.println("Attempting t/o contact DB ... ");
            
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
              throw e;
            }
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","muskan");
//                System.out.println("Got uid: " + uid);
                String sql_fir="select FIR_NAME, FIR_FNAME, FIR_EMAIL, FIR_CONTACT, FIR_DOI, FIR_PS, FIR_DESC, FIR_DEP, FIR_STAT from fir where FIR_TRACK=?";
                PreparedStatement pst_fir = con.prepareStatement(sql_fir);
                pst_fir.setString(1, uid);
                
                ResultSet rs_fir = pst_fir.executeQuery();
                if(rs_fir.next()){
                    name = rs_fir.getString(1);
                    fname = rs_fir.getString(2);
                    email = rs_fir.getString(3);
                    contact = rs_fir.getString(4);
                    doi = rs_fir.getString(5);
                    ps = rs_fir.getString(6);
                    desc = rs_fir.getString(7);
                    dep = rs_fir.getString(8);
                    stat = rs_fir.getInt(9);
                }
//                System.out.println("Got name: " + name);
              } catch (SQLException e) {
                throw e;
              } finally {
                con.close();
              }
                ArrayList<String> values = new ArrayList<String>();
                values.add(name);
                values.add(fname);
                values.add(email);
                values.add(contact);
                values.add(doi);
                values.add(ps);
                values.add(desc);
                values.add(dep);
                if(stat == 1){
                    status = "Solved";
                }
                else{
                    status = "Unsolved";
                }
                values.add(status);
//                System.out.println("values in get_values method");
                for(String x : values){
                System.out.println(x);
            }
                return values;
        }
	static void init(String uid) throws ClassNotFoundException, SQLException {
		try
		{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		track_complaint obj = new track_complaint();
		obj.user_details(uid);
	}
	public static void main(String []args) throws ClassNotFoundException, SQLException
	{   
            String uid = args[0];
            System.out.println("uid in main: " + uid);
            init(uid);
	}
}