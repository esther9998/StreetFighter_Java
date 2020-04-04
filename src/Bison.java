import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Bison extends IJPanel implements Runnable {
	Image source;
	Image source2;
	Image image;
	Image[] base;	// 기본
	Image[] move1;	// 앞 1
	Image[] move2;	// 뒤 2
	Image[] attack;	// 공격 3
	Image[] kick;	// 발차기 4
	Image[] uppercut;	// 어퍼컷 5
	Image[] damage;		// 공격당함 6 
	Image[] death;//죽음 7
	
	Bison() {
		setBackground(new Color(255, 255, 255));
		try {
			source = ImageIO.read(new File("actBison.png"));
			source2 = ImageIO.read(new File("actBison1.png"));
			base = new Image[2];
			for(int i=0; i<base.length; i++) {
				Image t = new BufferedImage(110, 100, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 0, 0, 110, 100, 100*i, 0, 99+99*i ,100, this);
				base[i] = t;
			}
			move1 = new Image[2];
			for(int i=0; i<move1.length; i++) {
					Image t = new BufferedImage(140, 140, BufferedImage.TRANSLUCENT); // 542 ,140, 
					t.getGraphics().drawImage(source2, 0, 0, 140, 140, 140*i, 542, 140+140*i ,542+140, this);
				move1[i] = t;
			}
			move2 = new Image[2];
			for(int i=0; i<move2.length; i++) {
				Image t = new BufferedImage(145, 130, BufferedImage.TRANSLUCENT); 
				t.getGraphics().drawImage(source2, 0, 0, 145, 130, 294+(145*i), 552, 294+(145+145*i) ,552+132, this);
				move2[i] = t;
			}
			attack = new Image[6];
			for(int i=0; i<attack.length; i++) {
				Image t = new BufferedImage(140, 85, BufferedImage.TRANSLUCENT); //137.85
				t.getGraphics().drawImage(source, 0, 0, 140, 85, 137*i, 480,136+136*i ,480+90, this);
				attack[i] = t;
			}
			kick = new Image[5];
			for(int i=0; i<kick.length; i++) {
				Image t = new BufferedImage(130, 85, BufferedImage.TRANSLUCENT); //128,83
				t.getGraphics().drawImage(source, 0, 0, 130, 85, 128*i, 830, 128+128*i ,830+90, this);
				kick[i] = t;
			}
			uppercut = new Image[6];
			for(int i=0; i<uppercut.length; i++) {
				Image t = new BufferedImage(110, 100, BufferedImage.TRANSLUCENT); 
				t.getGraphics().drawImage(source, 0, 0, 110, 100, 5+(100*i), 563, 100+100*i ,563+100, this);
				uppercut[i] = t;
			}
			
			damage = new Image[4];
			for(int i=0; i<damage.length; i++) {
				Image t = new BufferedImage(166, 115, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source2, 0, 0, 166, 115, 166*i, 2472, 166+166*i ,2472+120, this);
				damage[i] = t;
			}
			death = new Image[4];
			for(int i=0; i<death.length; i++) {
				Image t = new BufferedImage(130, 90, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 0, 0, 130, 90, 123*i, 1755, 123+123*i ,1755+90, this);
				death[i] = t;
			}
		
		}catch (IOException e) {
			System.out.println("image load fail");
		}
			Thread t = new Thread(this);
			t.start();
	}
	
	protected void paintComponent(Graphics g) {
		setOpaque(false);
		if(image != null)
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		super.paintComponent(g);
	}
	
	public void run() {
		int cnt = 0;
		while(true) {
			if (di == 0) {
				image = base[cnt];
				cnt++; // 1 // 1
				cnt %= base.length; // 1 // 0
				
				if(di != 0) {
					cnt = 0;
					break;
				}
				
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				updateUI();
			} // 기본
			else if (di == 1) {
				for (int i = 0; i < move1.length; i++) {
					image = move1[cnt];
					//cnt++; // 1 // 1
					cnt = 1; // 1 // 0
					if(di != 1) {
						cnt = 0;
						break;
					}
				try {
					Thread.sleep(150);
				} catch (Exception e) {
					e.printStackTrace();
				}
			updateUI();
				}
				
			} // 앞
			else if(di == 2) {
				for (int i = 0; i < move2.length; i++) {
					image = move2[cnt];
					//cnt++; // 1 // 1
					cnt = 1; // 1 // 0
					if(di != 2) {
						cnt = 0;
						break;
					}
				try {
					Thread.sleep(150);
				} catch (Exception e) {
					e.printStackTrace();
				}
			updateUI();
				}
				
			} // 뒤
			else if(di == 3) {
				for (int i = 0; i < attack.length; i++) {
					image = attack[cnt];
					cnt++; // 1 // 1
					cnt %= 6; // 1 // 0
					
					
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			updateUI();
				}
				di = 0;
				cnt = 0;
			} // 주먹
			else if(di == 4) {
				for (int i = 0; i < kick.length; i++) {
					image = kick[cnt];
					cnt++; // 1 // 1
					cnt %= 5;
					
					
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			updateUI();
				}
				di = 0;
				cnt = 0;
			} // 발차기 
			else if(di == 5) {
				for (int i = 0; i < uppercut.length; i++) {
					image = uppercut[cnt];
					cnt++; // 1 // 1
					cnt %= 6; // 1 // 0
					
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			updateUI();
				}
				di = 0;
				cnt = 0;
			} // 어퍼컷
			else if(di == 6) {
				for (int i = 0; i < damage.length; i++) {
					image = damage[cnt];
					cnt++; // 1 // 1
					cnt %= 4; // 1 // 0
					
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
			updateUI();
				}
				di = 0;
				cnt = 0;
			} // 공격당함
			else if(di == 7) {
				for (int i = 0; i < death.length; i++) {
					image = death[cnt];
					cnt++; // 1 // 1
					if(cnt == death.length) {
						cnt--;
					}
					
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
			updateUI();
				}
			} // 죽음
		}
	}
}
