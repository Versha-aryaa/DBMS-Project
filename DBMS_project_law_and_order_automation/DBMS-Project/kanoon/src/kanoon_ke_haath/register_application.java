package kanoon_ke_haath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

public class register_application {
	static JFrame frame;
	static JButton btnSubmit, btnCancel;

	static JPanel panel, panel1, panel2, mainpanel;

	static JTextArea txtArea;

	static JLabel lblheading, lblspace, lblFatherName, lblName, lblEmail, lblContact, lblDOI, lblPan, lblDepartment, lblAadhar, lblPS;
	static JTextField txtName, txtFatherName, txtEmail, txtDOI, txtPan, txtContact, txtAadhar, txtPS;
	static JComboBox txtDepartment;
	static Border redline = BorderFactory.createLineBorder(Color.RED);
        static Border blackline = BorderFactory.createLineBorder(new Color(235,235,235));
        
	static Connection con;

	public static void user_details() {
		frame = new JFrame("FILL APPLICATION");
		panel = new JPanel(new GridLayout(10, 1, 0, 0));
		panel1 = new JPanel(new GridLayout(10, 1, 0, 0));
		panel2 = new JPanel(new GridLayout(1, 3, 0, 0));
		mainpanel = new JPanel(new BorderLayout(10,10));

                lblheading = new JLabel("<HTML><h1>REGISTER APPLICATION</h1></HTML>", JLabel.CENTER);
		lblheading.setForeground(new Color(255,189,68));
		// **************************************************************************************************************
		// USER DETAILS
		lblName = new JLabel("<HTML><h3>Name: </h3></HTML>", JLabel.CENTER);
		lblFatherName = new JLabel("<HTML><h3>Father's / Husband's Name: </h3></HTML>", JLabel.CENTER);
		lblEmail = new JLabel("<HTML><h3>Email: </h3></HTML>", JLabel.CENTER);
		lblContact = new JLabel("<HTML><h3>Phone: </h3></HTML>", JLabel.CENTER);
		lblDOI = new JLabel("<HTML><h3>Date of Issue: </h3></HTML>", JLabel.CENTER);
		lblPan = new JLabel("<HTML><h3>PAN number: </h3></HTML>", JLabel.CENTER);
		lblDepartment = new JLabel("<HTML><h3>Type of license: </h3></HTML>", JLabel.CENTER);
		lblPS = new JLabel("<HTML><h3>Police Station: </h3></HTML>", JLabel.CENTER);
		lblAadhar = new JLabel("<HTML><h3>Aadhar number: </h3></HTML>", JLabel.CENTER);
                lblspace = new JLabel("				    ", JLabel.CENTER);
                
		btnCancel = new JButton("<HTML><h3>Cancel</h3></HTML>");
		btnCancel.setBackground(new Color(1, 145, 135));

		btnSubmit = new JButton("<HTML><h3>Submit</h3></HTML>");
		btnSubmit.setBackground(new Color(1, 145, 135));

		btnCancel.addActionListener(new CustomActionListener());
		btnSubmit.addActionListener(new CustomActionListener());

		txtName = new JTextField(60);
		txtFatherName = new JTextField(60);
		txtEmail = new JTextField(60);
		txtContact = new JTextField(60);
		txtDOI = new JTextField(60);
		txtPan = new JTextField(60);
		txtPS = new JTextField(60);
		txtAadhar = new JTextField(60);

		String txtDepartmentOptions[] = { "Arms and ammunition", "Loud speaker", "Hotel/lodge", "Browsing center",
				"mass meetings" };
		txtDepartment = new JComboBox(txtDepartmentOptions);
		txtDepartment.setSelectedIndex(0);

		txtName.setInputVerifier(new PassVerifier());
		txtFatherName.setInputVerifier(new PassVerifier());
		txtEmail.setInputVerifier(new PassVerifier());
		txtContact.setInputVerifier(new PassVerifier());
		txtDOI.setInputVerifier(new PassVerifier());
		txtPan.setInputVerifier(new PassVerifier());
		txtPS.setInputVerifier(new PassVerifier());
		txtAadhar.setInputVerifier(new PassVerifier());
                
                lblName.setBorder(blackline);
		lblFatherName.setBorder(blackline);
		lblEmail.setBorder(blackline);
		lblContact.setBorder(blackline);
		lblDOI.setBorder(blackline);
		lblPan.setBorder(blackline);
		lblDepartment.setBorder(blackline);
		lblPS.setBorder(blackline);
		lblAadhar.setBorder(blackline);

		txtName.setBorder(blackline);
		txtFatherName.setBorder(blackline);
		txtEmail.setBorder(blackline);
		txtContact.setBorder(blackline);
		txtDOI.setBorder(blackline);
		txtPan.setBorder(blackline);
		txtPS.setBorder(blackline);
		txtAadhar.setBorder(blackline);
                
		panel2.add(new JLabel("<HTML><h1>REGISTER APPLICATION</h1></HTML>", JLabel.CENTER));
		panel.add(lblName);
		panel1.add(txtName);
		panel.add(lblFatherName);
		panel1.add(txtFatherName);
		panel.add(lblEmail);
		panel1.add(txtEmail);
		panel.add(lblContact);
		panel1.add(txtContact);
		panel.add(lblDOI);
		panel1.add(txtDOI);
		panel.add(lblPan);
		panel1.add(txtPan);
		panel.add(lblDepartment);
		panel1.add(txtDepartment);
		panel.add(lblPS);
		panel1.add(txtPS);
		panel.add(lblAadhar);
		panel1.add(txtAadhar);
		panel.add(btnCancel);
		panel1.add(btnSubmit);
		panel2.add(panel);
		panel2.add(panel1);
		panel2.setBackground(new Color(255, 189, 68));

		mainpanel.add(lblheading, BorderLayout.NORTH);
		mainpanel.add(panel2, BorderLayout.CENTER);
		mainpanel.add(btnCancel, BorderLayout.LINE_START);
		mainpanel.add(lblspace, BorderLayout.SOUTH);
		mainpanel.add(btnSubmit, BorderLayout.LINE_END);
		mainpanel.setBackground(new Color(45,45,45));

		frame.setContentPane(mainpanel);
		frame.setSize(1920, 1080);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.getRootPane().setDefaultButton(btnSubmit);
		// **************************************************************************************************************
	}

	static class PassVerifier extends InputVerifier {
		public boolean verify(JComponent input) {
			if (input.equals(txtName) || input.equals(txtFatherName) || input.equals(txtPS)) {
				String text = ((JTextField) input).getText();
				if (text.matches("[a-zA-Z ]+")) // Reads: "Any of a-z or A-Z or space one or more times (together, not each)"
																				// ---> blank field or field containing anything other than those will return
																				// false.
				{
					((JTextField) input).setBorder(BorderFactory.createLineBorder(Color.GRAY));
					return true;
				}
				((JTextField) input)
						.setBorder(BorderFactory.createTitledBorder(redline, "Uppercase and Lowercase letters only."));
				return false;
			} else if (input.equals(txtEmail)) {
				String text = ((JTextField) input).getText();
				if (text.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
					((JTextField) input).setBorder(BorderFactory.createLineBorder(Color.GRAY));
					return true;
				}
				((JTextField) input).setBorder(BorderFactory.createTitledBorder(redline, "Invalid Email."));
				return false;
			} else if (input.equals(txtContact)) {
				String text = ((JTextField) input).getText();
				if (text.matches("^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$")) {
					((JTextField) input).setBorder(BorderFactory.createLineBorder(Color.GRAY));
					return true;
				}
				((JTextField) input).setBorder(BorderFactory.createTitledBorder(redline, "Invalid Contact."));
				return false;
			} else if (input.equals(txtDOI)) {
				String text = ((JTextField) input).getText();
				if (text.matches("[0-9]+")) {
					((JTextField) input).setBorder(BorderFactory.createLineBorder(Color.GRAY));
					return true;
				}
				((JTextField) input).setBorder(BorderFactory.createTitledBorder(redline, "Invalid Number."));
				return false;
			} else if (input.equals(txtAadhar)) {
				String text = ((JTextField) input).getText();
				if (text.matches("\\b\\d{12}\\b")) {
					((JTextField) input).setBorder(BorderFactory.createLineBorder(Color.GRAY));
					return true;
				}
				((JTextField) input).setBorder(BorderFactory.createTitledBorder(redline, "Invalid Aadhar number."));
				return false;
			} else if (input.equals(txtPan)) {
				String text = ((JTextField) input).getText();
				if (text.matches("[A-Z]{5}\\d{4}[A-Z]{1}")) {
					((JTextField) input).setBorder(BorderFactory.createLineBorder(Color.GRAY));
					return true;
				}
				((JTextField) input).setBorder(BorderFactory.createTitledBorder(redline, "Invalid PAN Number."));
				return false;
			}
			return true;
		}
	}

	static class CustomActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			Object source = ae.getSource();

			if (source == btnSubmit) {
				try {
					add_values();
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(register_application.class.getName()).log(Level.SEVERE, null, ex);
				} catch (SQLException ex) {
					Logger.getLogger(register_application.class.getName()).log(Level.SEVERE, null, ex);
				}
				JOptionPane.showMessageDialog(null, "Application Submitted");
				frame.setVisible(false);
			} else if (source == btnCancel) {
				frame.setVisible(false);
			}
		}
	}

	public static void add_values() throws ClassNotFoundException, SQLException {
//		System.out.println("Attempting to contact DB ... ");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw e;
		}

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","muskan");
			
			String name = txtName.getText();
			String fname = txtFatherName.getText();
			String contact = txtContact.getText();
			String email = txtEmail.getText();
			String doi = txtDOI.getText();
			String pan = txtPan.getText();
			String type = String.valueOf(txtDepartment.getSelectedItem());
			String ps = txtPS.getText();
			String adh = txtAadhar.getText();
                        
                        String sql_ps = "SELECT PO_NAME, PO_ID FROM police_officer WHERE PO_PS=?";
                        PreparedStatement pst_ps = con.prepareStatement(sql_ps);
                        
                        pst_ps.setString(1, ps);
                        ResultSet r = pst_ps.executeQuery();
                        String off_name="";
                        int off_id=0;
			if(r.next()){
                            off_name = r.getString(1);
                            off_id = r.getInt(2);
                        }
			String sql = "INSERT INTO applic (APP_TYPE, APP_NAM, APP_FNAME, APP_EMAIL, APP_CONTACT, APP_DOI, APP_PAN, APP_AADHAR, APP_PO_NAME, APP_PO_ID, APP_PS) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(sql);

			pst.setString(1, type);
			pst.setString(2, name);
			pst.setString(3, fname);
			pst.setString(4, email);
			pst.setString(5, contact);
			pst.setString(6, doi);
			pst.setString(7, pan);
			pst.setString(8, adh);
			pst.setString(9, off_name);
			pst.setInt(10, off_id);
			pst.setString(11, ps);

			pst.executeUpdate();

//			System.out.println("Database updated, check it.");
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
		register_application obj = new register_application();
		obj.user_details();
	}

	public static void main(String[] args) {
		init();
	}
}