package kanoon_ke_haath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

class show_ps {
  static JFrame frame;
  static JPanel panel, panel1, panel2, mainpanel;
  static JLabel lbladmin, img, space;
  static JButton officerBtn, crimBtn, applicBtn, quitBtn;

  static String uname;

  public void display() {
    frame = new JFrame("Main Screen");
    panel = new JPanel(new BorderLayout(10, 10));
    panel1 = new JPanel(new GridLayout(8, 1, 10, 10));
    panel2 = new JPanel(new GridLayout(1, 2, 10, 10));
    mainpanel = new JPanel(new GridLayout(1, 1, 10, 10));

    lbladmin = new JLabel("<HTML><h1>Police Station</h1><h3><center><i>Duniya k Rakhwaale</i></center></h3></HTML>",
        JLabel.CENTER);
    lbladmin.setForeground(new Color(255, 189, 68));

    officerBtn = new JButton("<HTML><h2>Officers</h2></HTML>");
    crimBtn = new JButton("<HTML><h2>Criminals</h2></HTML>");
    applicBtn = new JButton("<HTML><h2>Applications</h2></HTML>");
    quitBtn = new JButton("<HTML><h2>Quit</h2></HTML>");
    space = new JLabel("    ");

    officerBtn.addActionListener(new CustomActionListener());
    crimBtn.addActionListener(new CustomActionListener());
    applicBtn.addActionListener(new CustomActionListener());
    quitBtn.addActionListener(new CustomActionListener());

    quitBtn.setBackground(new Color(255, 92, 96));

    panel1.add(officerBtn);
    panel1.add(crimBtn);
    panel1.add(applicBtn);
    panel1.add(quitBtn);

    img = new JLabel(" ", JLabel.CENTER);
    BufferedImage image = null;
	try 
	{
            image = ImageIO.read(getClass().getClassLoader().getResource("badge.jpg"));
	} 
	catch (Exception e) 
	{
            e.printStackTrace();
	}

    ImageIcon imageIcon = new ImageIcon(fitimage(image, 550, 620));
    img.setIcon(imageIcon);
    panel2.add(img);
    panel2.add(panel1);

    panel.add(panel2, BorderLayout.CENTER);
    panel.add(lbladmin, BorderLayout.NORTH);
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

      if (source == officerBtn) {
        show_officers.main(new String[] { uname });
      } else if (source == crimBtn) {
        show_criminal.main(new String[] { uname });
      } else if (source == applicBtn) {
        show_application.main(new String[] { uname });
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
    show_ps obj = new show_ps();
    obj.display();
  }

  public static void main(String[] args) {
    uname = args[0];
    init();
  }
}