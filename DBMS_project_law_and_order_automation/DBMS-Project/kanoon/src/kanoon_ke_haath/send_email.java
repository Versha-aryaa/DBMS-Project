package kanoon_ke_haath;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class send_email {
    static Connection con;
    static String connectionString = "jdbc:hsqldb:file:db_data/database;hsqldb.lock_file=false";
    public static void sendEmail() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{
        
        // Getting all fir_id which are set to solved for the first time
//            System.out.println("Attempting to contact DB ... ");
            ArrayList<String> assigned_fir = new ArrayList<String>();
            ArrayList<String> email_body = new ArrayList<String>();
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
              throw e;
            }
            
            try {
                con = DriverManager.getConnection("jdbc:mysql://65.1.1.8:3306/test","police","Policemgmt@7police");
                
                String sql_fir="select FIR_ID from fir as t where t.FIR_STAT=? and t.FIR_EMAIL_SENT=?";
                PreparedStatement pst_fir = con.prepareStatement(sql_fir);
                
                pst_fir.setInt(1, 1);
                pst_fir.setInt(2, 0);
                               
                ResultSet rs_fir=pst_fir.executeQuery();
                while(rs_fir.next()){
                    assigned_fir.add(rs_fir.getString(1));
                }
                
              } catch (SQLException e) {
                throw e;
              } finally {
                con.close();
              }
        for(String fir_id : assigned_fir){
             // Sender's email ID needs to be mentioned
//             System.out.println("Got to assigned firs, fir_id: " + fir_id);
            String from = "policemanagementsystemProject7@gmail.com"; 


            final String username = "policemanagementsystemProject7";
            final String password = "policemgmt7";


            // Assuming you are sending email through relay.jangosmtp.net
            String host = "smtp.gmail.com";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");

            // Get the Session object.
            Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
               protected PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication(username, password);
               }
            });
            try {
                // Create a default MimeMessage object.
                Message message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(from));

                // Set Subject: header field
                message.setSubject("Complaint Information");
//                System.out.println("got to setting subject part");
                // Create the message part
                BodyPart messageBodyPart = new MimeBodyPart();

                // Now set the actual message
                // Getting fir info and assigned police officer info
                String to="";
                String fir_po_id="";
                try {
//              Class.forName("org.hsqldb.jdbc.JDBCDriver");
                    Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
              throw e;
            }
            
            try {
                // will create DB if does not exist
                // "SA" is default user with hypersql
//                con = DriverManager.getConnection(connectionString, "SA", "");
                con = DriverManager.getConnection("jdbc:mysql://65.1.1.8:3306/test","police","Policemgmt@7police");
                // Getting info of selected police department
                String sql_fir="select * from fir as t where t.FIR_ID=?";
                PreparedStatement pst_fir = con.prepareStatement(sql_fir);
                
                pst_fir.setString(1, fir_id);
                ResultSet rs_fir=pst_fir.executeQuery();
                while(rs_fir.next()){
                    email_body.add("Information regarding complaint registered: \n");
                    email_body.add("\tName: ");
                    email_body.add("\t" + rs_fir.getString(2) + "\n");
                    
                    email_body.add("\tFather's name: ");
                    email_body.add(rs_fir.getString(3) + "\n");
                    
                    email_body.add("\tEmail: ");
                    to = rs_fir.getString(4);
                    email_body.add(to + "\n");
                    
                    email_body.add("\tContact number: ");
                    email_body.add(rs_fir.getString(5) + "\n");
                    
                    email_body.add("\tDate of issue: ");
                    email_body.add(rs_fir.getString(6) + "\n");
                    
                    email_body.add("\tConcerned police station: ");
                    email_body.add(rs_fir.getString(7) + "\n");
                    
                    email_body.add("\tConcerned department: ");
                    email_body.add(rs_fir.getString(8) + "\n");
                    
                    email_body.add("\tShort description: ");
                    email_body.add(rs_fir.getString(9) + "\n\n");
                    
                    fir_po_id = rs_fir.getString(12);
                }
//                System.out.println("Got fir_po_id successfully " + fir_po_id);
                // Getting info of police officer who was appointed this
//                System.out.println("Now printing the police officers info");
                String sql_police="select PO_NAME, PO_EMAIL, PO_CONTACT from police_officer as t where t.PO_ID=?";
                PreparedStatement pst_police = con.prepareStatement(sql_police);
                
                pst_police.setString(1, fir_po_id);
                
                ResultSet rs_police=pst_police.executeQuery();
                while(rs_police.next()){
                    email_body.add("Information regarding police officer appointed: \n");
                    email_body.add("\tName: ");
                    email_body.add(rs_police.getString(1) + "\n");
                    
                    email_body.add("\tEmail: ");
                    email_body.add(rs_police.getString(2) + "\n");
                    
                    email_body.add("\tContact number: ");
                    email_body.add(rs_police.getString(3) + "\n\n");
                    
                    email_body.add("Attention: Your complaint has been resolved, please contact the above mentioned police officer at the earliest.");
                }
              } catch (SQLException e) {
                throw e;
              } finally {
                con.close();
              }
            // Set To: header field of the header.
//            System.out.println("Email of receipient got successfully: " + to);
                message.setRecipients(Message.RecipientType.TO,
                   InternetAddress.parse(to));
                
                // Creating string
                StringBuffer body_buffer = new StringBuffer();
                String body="";
                for(String i : email_body){
                    body_buffer.append(i);
                }
                body = body_buffer.toString();
                System.out.println("Email body:\n");
                System.out.println(body);
                messageBodyPart.setText(body);

                // Create a multipart message
                Multipart multipart = new MimeMultipart();

                // Set text message part
                multipart.addBodyPart(messageBodyPart);

//                // Part two is attachment
//                messageBodyPart = new MimeBodyPart();
//                String filename = "visitor_pass.pdf";
//                DataSource source = new FileDataSource(filename);
//                messageBodyPart.setDataHandler(new DataHandler(source));
//                messageBodyPart.setFileName(filename);
//                multipart.addBodyPart(messageBodyPart);

                // Send the complete message parts
                message.setContent(multipart);

                // Send message
                Transport.send(message);

//                System.out.println("Sent message successfully....");
                
                // Setting FIR_EMAIL_SENT to 1
                try {
//                con = DriverManager.getConnection(connectionString, "SA", "");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","muskan");
                
                // Updating value of fir_id status

                String sql_fir="update fir set FIR_EMAIL_SENT=? where FIR_ID=?";
                PreparedStatement pst_fir = con.prepareStatement(sql_fir);
                
                pst_fir.setInt(1, 1);
                pst_fir.setString(2, fir_id);
                               
                pst_fir.executeUpdate();
                
              } catch (SQLException e) {
                throw e;
              } finally {
                con.close();
              }
        }
            catch (MessagingException e) {
              throw new RuntimeException(e);

        }
    }
    }
    public static void init() throws IOException, FileNotFoundException, ClassNotFoundException, SQLException{
        send_email obj = new send_email();
        obj.sendEmail();
    }
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException, SQLException {
        init();
    }
}
