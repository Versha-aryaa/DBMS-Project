package kanoon_ke_haath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import javax.swing.border.Border;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.plaf.basic.BasicProgressBarUI;
import javax.swing.table.*;
import javax.swing.plaf.nimbus.*;


import kanoon_ke_haath.update_status;
public class police_officer_panel
{
	static JFrame frame;
	static JPanel mainpanel, panel;
	static JLabel lblCase, lblEmailStatus;
	static JButton btnBack;
        static JButton btnUpdateStatus, btnSubmit;
	static JTable table;
	static DefaultTableModel model;
	
        static Connection con;
        static String officer_name_arg;
        
        // setting up progress bar
        private BoundedRangeModel model_pb = new DefaultBoundedRangeModel();
        private JProgressBar bar;
        private BackgroundTask backgroundTask;
        
        
	public void display(String officer_name) throws SQLException, ClassNotFoundException
	{
            officer_name_arg = officer_name;
            
//            System.out.println("Argument made it to police_officer_panel display " + officer_name);
		//**************************************************************************************************************
			frame = new JFrame("VIEW ALL APPOINTED CASES");
			mainpanel = new JPanel(new BorderLayout(10,10));
                        panel = new JPanel(new GridLayout(1,3,0,0));
		//**************************************************************************************************************

		//**************************************************************************************************************
		// ADD BUTTONS AND LABELS
                        
			lblCase = new JLabel("<HTML><h1><u>APPOINTED CASES</u></h1></HTML>", JLabel.CENTER);
			lblCase.setForeground(new Color(255,189,68));

                        btnUpdateStatus = new JButton("<HTML><h3>Update Status</h3></HTML>");
                        btnUpdateStatus.setBackground(new Color(1,145,135));

                        btnSubmit = new JButton("<HTML><h3>Submit</h3></HTML>");
                        btnSubmit.setBackground(new Color(1,145,135));
                        
                        btnUpdateStatus.addActionListener(new CustomActionListener());
                        btnSubmit.addActionListener(new CustomActionListener());
                        
			btnBack = new JButton("<HTML><h3>Back</h3></HTML>");
			btnBack.setBackground(new Color(1,145,135));
			btnBack.addActionListener(new CustomActionListener());
                        
                        lblEmailStatus = new JLabel("Progress for sending emails");
                        panel.add(btnBack);
                        panel.add(btnUpdateStatus);
                        panel.add(btnSubmit);
                        panel.add(lblEmailStatus);
                        
                        UIDefaults d = new UIDefaults();
                        d.put("ProgressBar[Enabled+Indeterminate].foregroundPainter", new IndeterminateRegionPainter());
                        bar = new JProgressBar(model_pb);
                        UIManager.put("nimbusOrange", new Color(1,145,135));
                        bar.putClientProperty("Nimbus.Overrides", d);
                        panel.add(bar);
                        
			mainpanel.add(lblCase, BorderLayout.NORTH);
			mainpanel.add(panel, BorderLayout.SOUTH);
                        

			mainpanel.setBackground(new Color(45, 45, 45));
                        
                        
			model = new DefaultTableModel();
			model.addColumn("<HTML><h3>Case ID</h3></HTML>");
			model.addColumn("<HTML><h3>Name</h3></HTML>");
			model.addColumn("<HTML><h3>Father's / Husband's Name</h3></HTML>");
			model.addColumn("<HTML><h3>Email</h3></HTML>");
			model.addColumn("<HTML><h3>Phone</h3></HTML>");
			model.addColumn("<HTML><h3>Date of Issue</h3></HTML>");
			model.addColumn("<HTML><h3>Police Station</h3></HTML>");
			model.addColumn("<HTML><h3>Department</h3></HTML>");
			model.addColumn("<HTML><h3>Description</h3></HTML>");
			model.addColumn("<HTML><h3>Status</h3></HTML>");
                        
                        // get array of assigned cases (FIR_ID)
                        ArrayList<String> assigned_fir = new ArrayList<String>();
                        String po_ps="";
                        String po_dep="";
                        String po_id="";
//                        System.out.println("Attempting to contact DB ... ");

                        try {
                          Class.forName("com.mysql.cj.jdbc.Driver");
                        } catch (ClassNotFoundException e) {
                          throw e;
                        }

                        try {
                            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","muskan");
                            // getting officer ps
                            String sql_ps="select PO_PS, PO_DEP, PO_ID from police_officer as t where t.PO_NAME=?";
                            PreparedStatement pst_ps = con.prepareStatement(sql_ps);

                            pst_ps.setString(1, officer_name);
                            ResultSet rs_ps=pst_ps.executeQuery();
                            if(rs_ps.next()){
                                po_ps = rs_ps.getString(1);
                                po_dep = rs_ps.getString(2);
                                po_id = rs_ps.getString(3);
                            }
//                            System.out.println("Got po_ps and po_dep successfully " + po_ps + " " + po_dep);
                            
                            // Getting info of selected police department
                            String sql_fir="select FIR_ID from fir as t where t.FIR_PS=? and t.FIR_DEP=?";
                            PreparedStatement pst_fir = con.prepareStatement(sql_fir);

                            pst_fir.setString(1, po_ps);
                            pst_fir.setString(2, po_dep);
                            ResultSet rs_fir=pst_fir.executeQuery();
                            while(rs_fir.next()){
                                assigned_fir.add(rs_fir.getString(1));
                            }
                            // Setting FIR_PO_ID
                            for(String fir_id : assigned_fir){
                                String sql_po="update fir set FIR_PO_ID=? where FIR_ID=?";
                                PreparedStatement pst_po = con.prepareStatement(sql_po);
                                
                                pst_po.setString(1, po_id);
                                pst_po.setString(2, fir_id);
                               
                                pst_po.executeUpdate();
                            }
                            
                            for(String fir_id : assigned_fir){
                                // Getting fir details with FIR_ID stored in assigned_fir arraylist
                                String sql_det="select * from fir as t where t.FIR_ID=?";
                                PreparedStatement pst_det = con.prepareStatement(sql_det);

                                pst_det.setString(1, fir_id);
                                ResultSet rs_det=pst_det.executeQuery();
                                while(rs_det.next()){
                                    String stat="";
                                    if(rs_det.getInt(10) == 0){
                                        stat = "Unsolved";
                                    }
                                    else{
                                        stat = "Solved";
                                    }
                                    String[] options = new String[] {"Unsolved", "Solved"};
                                    model.addRow(new Object[]{rs_det.getString(1), rs_det.getString(2),rs_det.getString(3),rs_det.getString(4),rs_det.getString(5),rs_det.getString(6),rs_det.getString(7),rs_det.getString(8),rs_det.getString(9),stat});
                                }
                            }
                        }
                            catch (SQLException e) {
                            throw e;
                          } finally {
                            con.close();
                          }
                        
                        table = new JTable(model);
                        mainpanel.add(new JScrollPane(table), BorderLayout.CENTER);
			
		//**************************************************************************************************************
		
                bar.setIndeterminate(false);
		frame.add(mainpanel);
		frame.setSize(1920,1080);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	class CustomActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Object msg = e.getSource();
                            if(msg.equals(btnUpdateStatus)){
//                                System.out.println("officer_name_arg is working if: " + officer_name_arg);
			 	update_status.main(new String[]{officer_name_arg});
                            }
                            else if(msg.equals(btnSubmit)){
                                lblEmailStatus.setText("Sending emails to concerned citizens...");
                                backgroundTask = new BackgroundTask();
                                backgroundTask.execute();
                                bar.setIndeterminate(true);
                            }
                            
                            else if(msg.equals(btnBack)){
//                                backgroundTask.cancel(true);
                                frame.setVisible(false);
                            }
		}
	}
	public static void init(String officer_name) throws SQLException, ClassNotFoundException{
            try
		{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		police_officer_panel obj = new police_officer_panel();
		obj.display(officer_name);
	}
        
        private class BackgroundTask extends SwingWorker<Integer, Void> {

            private int status;

            public BackgroundTask() {
//                System.out.println("Started task with status: " + status);
            }

            @Override
            protected Integer doInBackground() {
                try {
                    send_email.init();
                } catch (IOException | ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(police_officer_panel.class.getName()).log(Level.SEVERE, null, ex);
                }
                return status;
            }

            @Override
            protected void done() {
                bar.setIndeterminate(false);
                lblEmailStatus.setText("Done!");
                JOptionPane.showMessageDialog(null, "Sent e-mails successfully!");
                frame.setVisible(false);
            }

        }
        
        class IndeterminateRegionPainter extends AbstractRegionPainter {
            // Copied from javax.swing.plaf.nimbus.ProgressBarPainter.java
            private Color color17 = decodeColor("nimbusOrange",  .0f,           .0f,         .0f,       -156);
            private Color color18 = decodeColor("nimbusOrange", -.015796512f,   .02094239f, -.15294117f,   0);
            private Color color19 = decodeColor("nimbusOrange", -.004321605f,   .02094239f, -.0745098f,    0);
            private Color color20 = decodeColor("nimbusOrange", -.008021399f,   .02094239f, -.10196078f,   0);
            private Color color21 = decodeColor("nimbusOrange", -.011706904f,  -.1790576f,  -.02352941f,   0);
            private Color color22 = decodeColor("nimbusOrange", -.048691254f,   .02094239f, -.3019608f,    0);
            private Color color23 = decodeColor("nimbusOrange",  .003940329f,  -.7375322f,   .17647058f,   0);
            private Color color24 = decodeColor("nimbusOrange",  .005506739f,  -.46764207f,  .109803915f,  0);
            private Color color25 = decodeColor("nimbusOrange",  .0042127445f, -.18595415f,  .04705882f,   0);
            private Color color26 = decodeColor("nimbusOrange",  .0047626942f,  .02094239f,  .0039215684f, 0);
            private Color color27 = decodeColor("nimbusOrange",  .0047626942f, -.15147138f,  .1607843f,    0);
            private Color color28 = decodeColor("nimbusOrange",  .010665476f,  -.27317524f,  .25098038f,   0);
            private Rectangle2D rect = new Rectangle2D.Float(0, 0, 0, 0);
            private Path2D path = new Path2D.Float();
            private PaintContext ctx = new PaintContext(new Insets(5, 5, 5, 5), new Dimension(29, 19), false);
            @Override public void doPaint(Graphics2D g, JComponent c, int width, int height, Object[] extendedCacheKeys) {
              path = decodePath1();
              g.setPaint(color17);
              g.fill(path);
              rect = decodeRect3();
              g.setPaint(decodeGradient5(rect));
              g.fill(rect);
              rect = decodeRect4();
              g.setPaint(decodeGradient6(rect));
              g.fill(rect);
            }
            @Override public PaintContext getPaintContext() {
              return ctx;
            }
            private Path2D decodePath1() {
              path.reset();
              path.moveTo(decodeX(0.6f), decodeY(0.12666667f));
              path.curveTo(decodeAnchorX(0.6000000238418579f, -2.0f), decodeAnchorY(0.12666666507720947f, 0.0f), decodeAnchorX(0.12666666507720947f, 0.0f), decodeAnchorY(0.6000000238418579f, -2.0f), decodeX(0.12666667f), decodeY(0.6f));
              path.curveTo(decodeAnchorX(0.12666666507720947f, 0.0f), decodeAnchorY(0.6000000238418579f, 2.0f), decodeAnchorX(0.12666666507720947f, 0.0f), decodeAnchorY(2.4000000953674316f, -2.0f), decodeX(0.12666667f), decodeY(2.4f));
              path.curveTo(decodeAnchorX(0.12666666507720947f, 0.0f), decodeAnchorY(2.4000000953674316f, 2.0f), decodeAnchorX(0.6000000238418579f, -2.0f), decodeAnchorY(2.8933334350585938f, 0.0f), decodeX(0.6f), decodeY(2.8933334f));
              path.curveTo(decodeAnchorX(0.6000000238418579f, 2.0f), decodeAnchorY(2.8933334350585938f, 0.0f), decodeAnchorX(3.0f, 0.0f), decodeAnchorY(2.8933334350585938f, 0.0f), decodeX(3.0f), decodeY(2.8933334f));
              path.lineTo(decodeX(3.0f), decodeY(2.6f));
              path.lineTo(decodeX(0.4f), decodeY(2.6f));
              path.lineTo(decodeX(0.4f), decodeY(0.4f));
              path.lineTo(decodeX(3.0f), decodeY(0.4f));
              path.lineTo(decodeX(3.0f), decodeY(0.120000005f));
              path.curveTo(decodeAnchorX(3.0f, 0.0f), decodeAnchorY(0.12000000476837158f, 0.0f), decodeAnchorX(0.6000000238418579f, 2.0f), decodeAnchorY(0.12666666507720947f, 0.0f), decodeX(0.6f), decodeY(0.12666667f));
              path.closePath();
              return path;
            }
            private Rectangle2D decodeRect3() {
              rect.setRect(decodeX(0.4f), //x
                           decodeY(0.4f), //y
                           decodeX(3.0f) - decodeX(0.4f), //width
                           decodeY(2.6f) - decodeY(0.4f)); //height
              return rect;
            }
            private Rectangle2D decodeRect4() {
              rect.setRect(decodeX(0.6f), //x
                           decodeY(0.6f), //y
                           decodeX(2.8f) - decodeX(0.6f), //width
                           decodeY(2.4f) - decodeY(0.6f)); //height
              return rect;
            }
            private Paint decodeGradient5(Shape s) {
              Rectangle2D bounds = s.getBounds2D();
              float x = (float)bounds.getX();
              float y = (float)bounds.getY();
              float w = (float)bounds.getWidth();
              float h = (float)bounds.getHeight();
              return decodeGradient((0.5f * w) + x, (0.0f * h) + y, (0.5f * w) + x, (1.0f * h) + y,
                                    new float[] { 0.038709678f, 0.05483871f, 0.07096774f, 0.28064516f, 0.4903226f, 0.6967742f, 0.9032258f, 0.9241935f, 0.9451613f },
                                    new Color[] { color18,
                                                  decodeColor(color18, color19, 0.5f),
                                                  color19,
                                                  decodeColor(color19, color20, 0.5f),
                                                  color20,
                                                  decodeColor(color20, color21, 0.5f),
                                                  color21,
                                                  decodeColor(color21, color22, 0.5f),
                                                  color22
                                                });
            }

            private Paint decodeGradient6(Shape s) {
              Rectangle2D bounds = s.getBounds2D();
              float x = (float)bounds.getX();
              float y = (float)bounds.getY();
              float w = (float)bounds.getWidth();
              float h = (float)bounds.getHeight();
              return decodeGradient((0.5f * w) + x, (0.0f * h) + y, (0.5f * w) + x, (1.0f * h) + y,
                                    new float[] { 0.038709678f, 0.061290324f, 0.08387097f, 0.27258065f, 0.46129033f, 0.4903226f, 0.5193548f, 0.71774197f, 0.91612905f, 0.92419356f, 0.93225807f },
                                    new Color[] { color23,
                                                  decodeColor(color23, color24, 0.5f),
                                                  color24,
                                                  decodeColor(color24, color25, 0.5f),
                                                  color25,
                                                  decodeColor(color25, color26, 0.5f),
                                                  color26,
                                                  decodeColor(color26, color27, 0.5f),
                                                  color27,
                                                  decodeColor(color27, color28, 0.5f),
                                                  color28
                                                });
            }
        }
  
	public static void main(String []args) throws SQLException, ClassNotFoundException
	{
                String officer_name = args[0];
                System.out.println("Argument made it to police_officer_panel main " + officer_name);
		init(officer_name);
	}	
}