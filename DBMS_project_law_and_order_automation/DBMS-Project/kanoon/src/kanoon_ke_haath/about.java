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
import java.net.URL;

class about
{
	static JFrame frame;
	static JPanel panel, mainpanel;
	static JLabel img, text;
        static URL url;
	public void display() throws IOException
	{
		frame = new JFrame("About");
		panel = new JPanel(new GridLayout(1,2,10,10));
		mainpanel = new JPanel(new GridLayout(1,1,10,10));

		text = new JLabel("<HTML><h1><b>THE STATION</b></h1><h2><i>Here For Your Safety</i></h2><h3>Kanoon K Haath is divided into multiple units, with each one responsible for a separate area of law enforcement. Our station is located in Allahabad, India, so we are able to quickly respond to local calls and reports. All of our units work extremely hard to prevent crime and accidents in the community to keep all of our residents safe.</h3><h1><b>Development Team:</b></h1><br><br><h2>Avantika soni&emsp;&emsp;&emsp;&ensp;IIB2022045<br>Shweta Pandey&emsp;&emsp;IIB2022011<br>Versha arya &emsp;&emsp;IFI2022007<br>Shreya Sinha&emsp;&emsp;IIB2022034</h2></HTML>");
		text.setForeground(new Color(255,189,68));
		img = new JLabel("", JLabel.CENTER);
		BufferedImage image = null;
		try 
		{
                    image = ImageIO.read(getClass().getClassLoader().getResource("img2.png"));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
                
		ImageIcon imageIcon = new ImageIcon(fitimage(image, 550, 620));
		img.setIcon(imageIcon);
		panel.add(img);
		panel.add(text);
					 
		panel.setBackground(new Color(45, 45, 45));
		
		mainpanel.add(panel);

		frame.add(mainpanel);
		frame.setSize(1920,1080);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	static Image fitimage(Image img , int w , int h)
	{
	    BufferedImage resizedimage = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2 = resizedimage.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(img, 0, 0,w,h,null);
	    g2.dispose();
	    return resizedimage;
	}
	static void init() throws IOException
	{
		try
		{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		about obj = new about();
		obj.display();
	}

	public static void main(String []args) throws IOException
	{
		init();
	}
}