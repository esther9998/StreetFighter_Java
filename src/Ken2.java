import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Ken2 extends IJPanel implements Runnable {
	Image source;
	Image source2;
	Image image;
	Image[] base;	// 기본
	Image[] move;
	Image[] attack;	// 공격 3
	Image[] kick;	// 발차기 4
	Image[] uppercut;	// 어퍼컷 5
	Image[] damage;		// 공격당함 6 
	Image[] death;//죽음 7
	

	Ken2() {
		setBackground(new Color(255, 255, 255));
		// setUndecorated(true);
		// setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
		try { // 전체 이미지 로딩
			source = ImageIO.read(new File("actKen.png"));
			source2 = ImageIO.read(new File("actKen1.png"));
			base = new Image[3];
			for (int i = 0; i < base.length; i++) {
				Image t = new BufferedImage(59, 103, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 59, 0, 0, 103, 573 + (65 * i), 267, 632 + (65 * i), 370, this);
				base[i] = t;
			}
			move = new Image[6];
			for (int i = 0; i < move.length; i++) {
				Image t = new BufferedImage(59, 103, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 59, 0, 0, 103, 30 + (50 * i), 267, 73 + (50 * i), 370, this);
				move[i] = t;
			}
			attack = new Image[4];//260,465    100,100
			for (int i = 0; i < attack.length; i++) {
				Image t = new BufferedImage(100, 100, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source2, 100, 0, 0, 100, 270 + (100 * i), 460, 270 + (100+100 * i), 560, this);
				attack[i] = t;
			}
			kick = new Image[3]; //25,650  100,100
			for (int i = 0; i < kick.length; i++) {
				Image t = new BufferedImage(100, 100, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source2, 100, 0, 0, 100, 25 + (100 * i), 650, 25 + (100+100 * i), 750, this);
				kick[i] = t;
			}
			uppercut = new Image[6]; //330 ,1000       75
			for (int i = 0; i < uppercut.length; i++) {
				Image t = new BufferedImage(80, 70, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source2, 80, 0, 0, 70, 330 + (90 * i), 1000, 330 + (90+90 * i), 1090, this);
				uppercut[i] = t;
			}
			damage = new Image[4];
			for (int i = 0; i < damage.length; i++) {
				Image t = new BufferedImage(80, 100, BufferedImage.TRANSLUCENT);//20,2547
				t.getGraphics().drawImage(source, 80, 0, 0, 100, 20 + (77 * i), 2547, 20 + (75+75 * i), 2547+96, this);
				damage[i] = t;
			}
			death = new Image[2];//966,2566    115,80
			for (int i = 0; i < death.length; i++) {
				Image t = new BufferedImage(100, 70, BufferedImage.TRANSLUCENT);//966,2566     80
				t.getGraphics().drawImage(source2, 100, 0, 0, 70, 966 + (115 * i),2566  ,966+ (115+155 * i),2566+80 , this);
				death[i] = t;
			}
		} catch (Exception e) {
			System.out.println("image load fail");
		}
		Thread t = new Thread(this);
		t.start();
	}

	public void paintComponent(Graphics g) { // 스레드를 이용해 변경된 sprite를 화면에다 그리기
		setOpaque(false);
		if (image != null)
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		super.paintComponents(g);
	}

	public void run() {// 스레드를 이용해서 이미지를 조금씩조금씩 바꿔나간다. (sprite 변경)
		int cnt = 0;
		while (true) {
			
			if (di == 0) {
				image = base[cnt];
				cnt++; // 1 // 1
				cnt %= 3; // 1 // 0
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
			} else if (di == 1 || di ==2) {
				for (int i = 0; i < move.length; i++) {
					image = move[cnt];
					cnt++; // 1 // 1
					cnt %= 6; // 1 // 0
					if(di != 1 && di != 2) {
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
				
			} else if(di == 3) {
				for (int i = 0; i < attack.length; i++) {
					image = attack[cnt];
					cnt++; // 1 // 1
					cnt %= 4; // 1 // 0
					
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
					cnt %= 3; // 1 // 0
					
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
					cnt = 1;
					
				try {
					Thread.sleep(400);
				} catch (Exception e) {
					e.printStackTrace();
				}
			updateUI();
				}
			} // 죽음
			
		}
		
	}
}
