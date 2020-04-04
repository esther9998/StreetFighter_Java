import java.awt.Color;
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

class Ryu2 extends IJPanel implements Runnable{
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
	
	Ryu2() {
		setBackground(new Color(255, 255, 255));
		try {
			source = ImageIO.read(new File("actRyu.png"));
			source2 = ImageIO.read(new File("actRyu1.png"));
			base = new Image[5];
			for(int i=0; i<base.length; i++) {
				Image t = new BufferedImage(80, 120, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 80, 0, 0, 120, 79*i, 0, 79+79*i ,115, this);
				base[i] = t;
			}
			move1 = new Image[11];
			for(int i=0; i<move1.length; i++) {
				Image t = new BufferedImage(110, 115, BufferedImage.TRANSLUCENT); 
				t.getGraphics().drawImage(source, 110, 0, 0, 115, 113*i, 437, 113+113*i ,437+113, this);
				move1[i] = t;
			}
			move2 = new Image[11];
			for(int i=0; i<move2.length; i++) {
				Image t = new BufferedImage(110, 115, BufferedImage.TRANSLUCENT); 
				t.getGraphics().drawImage(source, 110, 0, 0, 115, 113*i, 553, 113+113*i ,553+113, this);
				move2[i] = t;
			}
			attack = new Image[8];
			for(int i=0; i<attack.length; i++) {
				Image t = new BufferedImage(110, 120, BufferedImage.TRANSLUCENT); 
				t.getGraphics().drawImage(source, 110, 0, 0, 120, 111*i, 1928, 110+110*i ,1928+116, this);
				attack[i] = t;
			}
			kick = new Image[11];
			for(int i=0; i<kick.length; i++) {
				Image t = new BufferedImage(130, 90, BufferedImage.TRANSLUCENT); //4512,77
				t.getGraphics().drawImage(source2, 130, 0, 0, 90, 125*i, 1406, 125+125*i ,1406+85, this);
				kick[i] = t;
			}
			uppercut = new Image[11];
			for(int i=0; i<uppercut.length; i++) {
				Image t = new BufferedImage(90, 133, BufferedImage.TRANSLUCENT); 
				t.getGraphics().drawImage(source, 90, 0, 0, 133, 90*i, 670, 90+90*i ,670+133, this);
				uppercut[i] = t;
			}
			
			damage = new Image[7];
			for(int i=0; i<damage.length; i++) {
				Image t = new BufferedImage(95, 115, BufferedImage.TRANSLUCENT); 
				t.getGraphics().drawImage(source, 95, 0, 0, 115, 1000+101*i, 4000, 1000+101+101*i ,4119, this);
				damage[i] = t;
			}
			death = new Image[7];
			for(int i=0; i<death.length; i++) {
				Image t = new BufferedImage(170, 90, BufferedImage.TRANSLUCENT); //4512,77
				t.getGraphics().drawImage(source, 170, 0, 0, 90, 168*i, 4512, 162+168*i ,4512+75, this);
				death[i] = t;
			}

		} catch (IOException e) {
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
				cnt %= 5; // 1 // 0
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
					cnt++; // 1 // 1
					cnt %= 11; // 1 // 0
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
					cnt++; // 1 // 1
					cnt %= 11; // 1 // 0
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
					cnt %= 8; // 1 // 0
					
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
					cnt %= 11; // 1 // 0
					
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
					cnt %= 11; // 1 // 0
					
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
					cnt %= 7; // 1 // 0
					
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

