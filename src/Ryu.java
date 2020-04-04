import java.awt.Graphics;
import java.awt.Image;
import java.awt.KeyEventPostProcessor;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Ryu extends IJPanel implements Runnable {
	Image source;
	Image source2;
	Image[] sprite;
	Image[] kick;
	Image[] death;
	Image[] back;
	Image[] go;
	Image[] base;
	Image[] punch;
	Image[] hit;
	Image[] punch2;

	Image image;


	Ryu() {

		try {
			source = ImageIO.read(new File("actRyu.png"));
			source2 = ImageIO.read(new File("actRyu1.png"));
			kick = new Image[11];
			for (int i = 0; i < kick.length; i++) {
				Image t = new BufferedImage(130, 90, BufferedImage.TRANSLUCENT); // 4512,77
				t.getGraphics().drawImage(source2, 0, 0, 130, 90, 125 * i, 1406, 125 + 125 * i, 1406 + 85, this);
				
				kick[i] = t;
			}

			death = new Image[7];
			for (int i = 0; i < death.length; i++) {
				Image t = new BufferedImage(170, 90, BufferedImage.TRANSLUCENT); // 4512,77
				t.getGraphics().drawImage(source, 0, 0, 170, 90, 168 * i, 4512, 162 + 168 * i, 4512 + 75, this);
				death[i] = t;
			}

			back = new Image[11];
			for (int i = 0; i < back.length; i++) {
				Image t = new BufferedImage(110, 115, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 0, 0, 110, 115, 113 * i, 553, 113 + 113 * i, 553 + 113, this);
				back[i] = t;
			}

			go = new Image[11];
			for (int i = 0; i < go.length; i++) {
				Image t = new BufferedImage(110, 115, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 0, 0, 110, 115, 113 * i, 437, 113 + 113 * i, 437 + 113, this);
				go[i] = t;
			}

			base = new Image[5];
			for (int i = 0; i < base.length; i++) {
				Image t = new BufferedImage(80, 120, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 0, 0, 80, 120, 79 * i, 0, 79 + 79 * i, 115, this);
				base[i] = t;
			}

			punch = new Image[8];
			for (int i = 0; i < punch.length; i++) {
				Image t = new BufferedImage(110, 120, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 0, 0, 110, 120, 111 * i, 1928, 110 + 110 * i, 1928 + 116, this);
				punch[i] = t;
			}

			hit = new Image[7];
			for (int i = 0; i < hit.length; i++) {
				Image t = new BufferedImage(95, 115, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 0, 0, 95, 115, 1000 + 101 * i, 4000, 1000 + 101 + 101 * i, 4119, this);
				hit[i] = t;
			}

			punch2 = new Image[11];
			for (int i = 0; i < punch2.length; i++) {
				Image t = new BufferedImage(90, 133, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 0, 0, 90, 133, 90 * i, 670, 90 + 90 * i, 670 + 133, this);
				punch2[i] = t;
			}

		} catch (IOException e) {
			System.out.println("image load fail");
		}
		Thread t = new Thread(this);
		t.start();
	}

	protected void paintComponent(Graphics g) {
		setOpaque(false);
		if (image != null)
			g.drawImage(image, 45, 0, 162, getHeight(), this);
		super.paintComponent(g);
	}

	public void run() {
		int cnt = 0;
		
		while (true) {
			if (di == 0) {
				image = base[cnt];
				cnt++;
				cnt %= base.length;
				if(di != 0) {
					cnt = 0;
					break;
				}
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
				updateUI();
			} else if (di == 1) {
				for (int i = 0; i < go.length; i++) {
					image = go[cnt];
					cnt++; // 1 // 1
					cnt %= go.length; // 1 // 0
					if (di != 1) {
						cnt = 0;
						break;
					}
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					updateUI();
				}
				
			} else if (di == 2) {
				for (int i = 0; i < back.length; i++) {
					image = back[cnt];
					cnt++; // 1 // 1
					cnt %= back.length; // 1 // 0
					if (di != 2) {
						cnt = 0;
						break;
					}
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					updateUI();
				}
				
			} else if (di == 3) {
				for (int i = 0; i < punch.length; i++) {
					image = punch[cnt];
					cnt++; // 1 // 1
					cnt %= punch.length; // 1 // 0
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					updateUI();
				}
				di = 0;
				cnt = 0;
			} else if (di == 4) {
				for (int i = 0; i < kick.length; i++) {
					image = kick[cnt];
					cnt++; // 1 // 1
					cnt %= kick.length; // 1 // 0
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					updateUI();
				}
				di = 0;
				cnt = 0;
			} else if (di == 5) {
				for (int i = 0; i < punch2.length; i++) {
					image = punch2[cnt];
					cnt++; // 1 // 1
					cnt %= punch2.length; // 1 // 0
				
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					updateUI();
				}
				di = 0;
				cnt = 0;
			} else if (di == 6) {
				for (int i = 0; i < hit.length; i++) {
					image = hit[cnt];
					cnt++; // 1 // 1
					cnt %= hit.length; // 1 // 0
					if (di != 6) {
						cnt = 0;
						break;
					}
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					updateUI();
				}
				di = 0;
				cnt = 0;
			} else if (di == 7) {
				for (int i = 0; i < death.length; i++) {
					image = death[cnt];
					cnt++;
					if(cnt == death.length) {
						cnt--;
					}
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					updateUI();
				}
			}
		}
	}

}
