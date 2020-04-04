import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TEstFrame extends JFrame {
	
	
	public static void main(String[] args) { 
		JFrame f = new JFrame();
		
		Ryu k = new Ryu();
		f.add(k);
		f.setVisible(true);
		
	}
}
