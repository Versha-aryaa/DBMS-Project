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
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import org.apache.commons.io.FileUtils;

public class complaint{
	static JFrame frame;
	static JButton btnSubmit, btnCancel;

	static JPanel panel, panel1, panel2, mainpanel;

	static JTextArea txtArea;
	
	static JLabel lblFatherName, lblName, lblEmail, lblContact, lblDOI, lblPS, lblDepartment, lblDesc,lblspace, lblheading;
	static JTextField txtName, txtFatherName, txtEmail, txtDOI, txtPS, txtContact;
	static JComboBox txtDepartment;
	static Border redline = BorderFactory.createLineBorder(Color.RED);
        static Border blackline = BorderFactory.createLineBorder(new Color(235,235,235));
        
        static String name, fname, contact, email, doi, ps, dep, desc;
        static Connection con;
        static UUID idOne;
	public static void user_details()
	{
		frame = new JFrame("FILL DETAILS");
                mainpanel = new JPanel(new BorderLayout(10,10));
		panel = new JPanel(new GridLayout(10,1,0,0));
		panel1 = new JPanel(new GridLayout(10,1,0,0));
		panel2 = new JPanel(new GridLayout(1,3,0,0));

                lblheading = new JLabel("<HTML><h1>FIR</h1></HTML>", JLabel.CENTER);
		lblheading.setForeground(new Color(255,189,68));
		//**************************************************************************************************************
		// USER DETAILS
			lblName = new JLabel("<HTML><h3>Name: </h3></HTML>", JLabel.CENTER);
			lblFatherName = new JLabel("<HTML><h3>Father's / Husband's Name: </h3></HTML>", JLabel.CENTER);
			lblEmail = new JLabel("<HTML><h3>Email: </h3></HTML>", JLabel.CENTER);
			lblContact = new JLabel("<HTML><h3>Phone: </h3></HTML>", JLabel.CENTER);
			lblDOI = new JLabel("<HTML><h3>Date of Issue: </h3></HTML>", JLabel.CENTER);
			lblPS = new JLabel("<HTML><h3>Name of police station </h3></HTML>", JLabel.CENTER);
			lblDepartment = new JLabel("<HTML><h3>Department: </h3></HTML>", JLabel.CENTER);
			lblDesc  = new JLabel("<HTML><h3>Description: </h3></HTML>", JLabel.CENTER);
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
			txtPS = new JTextField(60);

			txtArea = new JTextArea(5, 20);
			JScrollPane scrollPane = new JScrollPane(txtArea); 
			txtArea.setEditable(true);

			String txtDepartmentOptions[] = {"Women Protection", "Cybercrime", "Traffic & Control", "Law and Order"};
			txtDepartment = new JComboBox(txtDepartmentOptions);
			txtDepartment.setSelectedIndex(0);

			txtName.setInputVerifier(new PassVerifier());
			txtFatherName.setInputVerifier(new PassVerifier());
			txtEmail.setInputVerifier(new PassVerifier());
			txtContact.setInputVerifier(new PassVerifier());
			txtDOI.setInputVerifier(new PassVerifier());
			txtPS.setInputVerifier(new PassVerifier());
                        
                        lblName.setBorder(blackline);
                        lblFatherName.setBorder(blackline);
                        lblEmail.setBorder(blackline);
                        lblContact.setBorder(blackline);
                        lblDOI.setBorder(blackline);
                        lblPS.setBorder(blackline);
                        lblDepartment.setBorder(blackline);
                        lblDesc.setBorder(blackline);

                        txtName.setBorder(blackline);
                        txtFatherName.setBorder(blackline);
                        txtEmail.setBorder(blackline);
                        txtContact.setBorder(blackline);
                        txtDOI.setBorder(blackline);
                        txtPS.setBorder(blackline);

                        panel2.add(new JLabel("<HTML><h1>FILE A COMPLAINT</h1></HTML>", JLabel.CENTER));
			panel.add(lblName);			panel1.add(txtName);
			panel.add(lblFatherName);	panel1.add(txtFatherName);
			panel.add(lblEmail);		panel1.add(txtEmail);
			panel.add(lblContact);		panel1.add(txtContact);
			panel.add(lblDOI);			panel1.add(txtDOI);
			panel.add(lblPS);			panel1.add(txtPS);
			panel.add(lblDepartment);	panel1.add(txtDepartment);
			panel.add(lblDesc);			panel1.add(scrollPane);
			panel.add(btnCancel);		panel1.add(btnSubmit);
			panel2.add(panel);
                        
			panel2.add(panel1);
			panel2.setBackground(new Color(255,189,68));
                        
                        mainpanel.add(lblheading, BorderLayout.NORTH);
                        mainpanel.add(panel2, BorderLayout.CENTER);
                        mainpanel.add(btnCancel, BorderLayout.LINE_START);
                        mainpanel.add(lblspace, BorderLayout.SOUTH);
                        mainpanel.add(btnSubmit, BorderLayout.LINE_END);
                        mainpanel.setBackground(new Color(45,45,45));
                        
			frame.setContentPane(mainpanel);
                        
			frame.setSize(1920,1080);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                        frame.getRootPane().setDefaultButton(btnSubmit);
		//**************************************************************************************************************
	}
	static class PassVerifier extends InputVerifier {
		public boolean verify(JComponent input)
		{
			if(input.equals(txtName) || input.equals(txtFatherName) || input.equals(txtPS)) 
			{
				String text = ((JTextField) input).getText();
				if (text.matches("[a-zA-Z ]+")) // Reads: "Any of a-z or A-Z or space one or more times (together, not each)" ---> blank field or field containing anything other than those will return false.
				{
					((JTextField) input).setBorder(BorderFactory.createLineBorder(Color.GRAY));
					return true;
				}
				((JTextField) input).setBorder(BorderFactory.createTitledBorder(redline, "Uppercase and Lowercase letters only."));
				return false;
			}
			else if(input.equals(txtEmail))
			{
				String text = ((JTextField) input).getText();
				if (text.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
				{
					((JTextField) input).setBorder(BorderFactory.createLineBorder(Color.GRAY));
					return true;
				}
				((JTextField) input).setBorder(BorderFactory.createTitledBorder(redline, "Invalid Email."));
				return false;
			}
			else if(input.equals(txtContact))
			{
				String text = ((JTextField) input).getText();
				if (text.matches("^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$"))
				{
					((JTextField) input).setBorder(BorderFactory.createLineBorder(Color.GRAY));
					return true;
				}
				((JTextField) input).setBorder(BorderFactory.createTitledBorder(redline, "Invalid Contact."));
				return false;
			}
			else if(input.equals(txtDOI))
			{
				String text = ((JTextField) input).getText();
				if (text.matches("[0-9]+"))
				{
					((JTextField) input).setBorder(BorderFactory.createLineBorder(Color.GRAY));
					return true;
				}
				((JTextField) input).setBorder(BorderFactory.createTitledBorder(redline, "Invalid Number."));
				return false;
			}
			return true;
		}
	}
        
        static class CustomActionListener implements ActionListener{
		public void actionPerformed(ActionEvent ae) {
			Object source = ae.getSource();

			if(source == btnSubmit){
                            SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
@Override
protected Boolean doInBackground() throws Exception {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "muskan");

        name = txtName.getText();
        fname = txtFatherName.getText();
        contact = txtContact.getText();
        email = txtEmail.getText();
        doi = txtDOI.getText();
        ps = txtPS.getText();
        dep = String.valueOf(txtDepartment.getSelectedItem());
        desc = txtArea.getText();
        int stat = 0;

        // Generating tracking ID from FIR_ID
        idOne = UUID.randomUUID();
        String sql = "INSERT INTO fir (FIR_NAME, FIR_FNAME, FIR_EMAIL, FIR_CONTACT, FIR_DOI, FIR_PS, FIR_DEP, FIR_DESC, FIR_STAT, FIR_EMAIL_SENT, FIR_PO_ID, FIR_TRACK) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, name);
        pst.setString(2, fname);
        pst.setString(3, email);
        pst.setString(4, contact);
        pst.setString(5, doi);
        pst.setString(6, ps);
        pst.setString(7, dep);
        pst.setString(8, desc);
        pst.setInt(9, stat);
        pst.setInt(10, stat);
        pst.setString(11, "");
        pst.setString(12, idOne.toString());
        pst.executeUpdate();

    } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
        return false;
    } finally {
        if (con != null) {
            con.close();
        }
    }

    return true;
}

                            protected void done() {
    
                                boolean status;
                                try {
                                 // Retrieve the return value of doInBackground.
                                    status = get();
                                    
                                    StringSelection stringSelection = new StringSelection(idOne.toString());
                                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                                    clipboard.setContents(stringSelection, null);
                                    JTextArea text = new JTextArea("Your tracking id is: " +idOne.toString() + "\n\tKeep this id safe with you.\n\nWe have copied the traking id to your clipboard!");

                                    JOptionPane.showMessageDialog(null, text);
                                    frame.setVisible(false);
                                } catch (InterruptedException e) {
                                 // This is thrown if the thread's interrupted.
                                } catch (ExecutionException e) {
                                 // This is thrown if we throw an exception
                                 // from doInBackground.
                                }
   }
                           };
  
                    worker.execute();
                                    
                            }
                        else if(source == btnCancel){
                            frame.setVisible(false);
                        }
		}
	}
        
	static void init() {
		try
		{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		complaint obj = new complaint();
		obj.user_details();
	}
	public static void main(String []args)
	{
		init();
	}
}