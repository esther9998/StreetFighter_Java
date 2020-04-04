import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Chunli extends IJPanel implements Runnable {

	Image[] base; // 서있을 때
	Image[] go;
	Image[] back;
	Image[] punch;
	Image[] kick;
	Image[] special;
	Image[] pain;
	Image[] die;

	Image source; // 전체 풀 이미지를 관리할 이미지 객체
	Image image; // 실제 반영
	Image[] move;

	Chunli() {
		setBackground(new Color(255, 255, 255));
		// setUndecorated(true);
		// setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
		try { // 전체 이미지 로딩
			source = ImageIO.read(new File("actChunli.png"));
			base = new Image[3];
			for (int i = 0; i < base.length; i++) {
				Image t = new BufferedImage(220, 260, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 0, 0, 220, 260, 0 + (220 * i), 0, 220 + (220 * i), 260, this);
				base[i] = t;
			}
			go = new Image[8];// 1
			for (int i = 0; i < go.length; i++) {
				Image t = new BufferedImage(250, 260, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 0, 0, 250, 260, 0 + (250 * i), 260, 250 + (250 * i), 520, this);
				go[i] = t;
			}
			back = new Image[8];// 2
			for (int i = 0; i < back.length; i++) {
				Image t = new BufferedImage(250, 260, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 0, 0, 250, 260, 0 + (250 * i), 520, 250 + (250 * i), 780, this);
				back[i] = t;
			}
			punch = new Image[6];
			for (int i = 0; i < punch.length; i++) {
				Image t = new BufferedImage(250, 260, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 0, 0, 250, 260, 0 + (250 * i), 780, 250 + (250 * i), 1040, this);
				punch[i] = t;
			}
			kick = new Image[13];
			for (int i = 0; i < kick.length; i++) {
				Image t = new BufferedImage(300, 520, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 0, 0, 300, 520, 0 + (300 * i), 1040, 300 + (300 * i), 1560, this);
				kick[i] = t;
			}
			special = new Image[27];// 5
			for (int i = 0; i < special.length; i++) {
				Image t = new BufferedImage(400, 650, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 0, 0, 400, 650, 0 + (300 * i), 1560, 300 + (300 * i), 2080, this);
				special[i] = t;
			}
			pain = new Image[11];// 5
			for (int i = 0; i < pain.length; i++) {
				Image t = new BufferedImage(250, 260, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 0, 0, 250, 260, 0 + (250 * i), 2080, 250 + (250 * i), 2340, this);
				pain[i] = t;
			}
			die = new Image[6];// 5
			for (int i = 0; i < die.length; i++) {
				Image t = new BufferedImage(250, 260, BufferedImage.TRANSLUCENT);
				t.getGraphics().drawImage(source, 0, 0, 250, 260, 0 + (250 * i), 2600, 250 + (250 * i), 2860, this);
				die[i] = t;
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
			} else if (di == 1) {
				for (int i = 0; i < go.length; i++) {
					image = go[cnt];
					cnt++; // 1 // 1
					cnt %= 8; // 1 // 0
					if (di == 0) {
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
					cnt %= 8; // 1 // 0
					if (di == 0) {
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
					cnt %= 6; // 1 // 0

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
					cnt %= 13; // 1 // 0

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
				for (int i = 0; i < special.length; i++) {
					image = special[cnt];
					cnt++; // 1 // 1
					cnt %= 27; // 1 // 0

					try {
						Thread.sleep(70);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					updateUI();
				}
				di = 0;
				cnt = 0;
			} else if (di == 6) {
				for (int i = 0; i < pain.length; i++) {
					image = pain[cnt];
					cnt++; // 1 // 1
					cnt %= 11; // 1 // 0

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
			} else if (di == 7) {
				for (int i = 0; i < die.length; i++) {
					image = die[cnt];
					cnt++; // 1 // 1
					if (cnt == die.length) {
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