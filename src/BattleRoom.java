import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class IJPanel extends JPanel {
	int di = 0;
}

public class BattleRoom extends JDialog implements Runnable, KeyListener {
	JLabel time;
	WaitingRoom owner;
	UserInfo info;
	JLabel Ready;
	JLabel Go;
	int mp;
	String mch;
	int op;
	String och;
	String s;
	boolean start;
	IJPanel character;
	IJPanel character2;
	JPanel energy1;
	JPanel energy2;
	long t1;
	ClientThread ct;
	Thread t;
	Clip clip2;
	// String str;
	
	public void Sound2(String file, boolean loop) {
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip2 = AudioSystem.getClip();
			clip2.open(ais);
			clip2.start();
			if(loop)
				clip2.loop(-1);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	class ImageBattle extends JPanel {
		Image img;

		ImageBattle(String fileName) {
			try {
				img = ImageIO.read(new File(fileName));
			} catch (Exception e) {
				System.out.println("image load fail..");
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			setOpaque(false);
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			super.paintComponent(g);
		}
	}

	public void run() {
		try {

			Thread.sleep(2000);
			getContentPane().remove(Ready);
			getContentPane().repaint();
			getContentPane().add(Go);
			getContentPane().repaint();
			Thread.sleep(1500);
			getContentPane().remove(Go);
			getContentPane().repaint();
			int cnt = 60;
			DecimalFormat df = new DecimalFormat("00");
			start = true;
			while (start) {
				cnt--;
				Thread.sleep(1000);
				String str = df.format(cnt);
				time.setText(str);
				if (cnt == 0) {
					if (mp == 1) {
						if (energy1.getWidth() > energy2.getWidth()) {
							JOptionPane.showMessageDialog(this, "승리하였습니다!!\n" + "대기실로 이동합니다.");
							owner.nout.println("win");
							owner.nout.flush();
							clip2.stop();
							setVisible(false);
							owner.setVisible(true);
							owner.clip.start();
							s = "list&" + owner.target.id;
							owner.nout.println(s);
							owner.nout.flush();
						} else if (energy1.getWidth() < energy2.getWidth()) {
							JOptionPane.showMessageDialog(this, "패배하였습니다..\n" + "대기실로 이동합니다.");
							owner.nout.println("lose");
							owner.nout.flush();
							clip2.stop();
							setVisible(false);
							owner.setVisible(true);
							owner.clip.start();
							s = "list&" + owner.target.id;
							owner.nout.println(s);
							owner.nout.flush();

						} else {
							JOptionPane.showMessageDialog(this, "무승부입니다..\n" + "대기실로 이동합니다.");
							clip2.stop();
							setVisible(false);
							owner.setVisible(true);
							owner.clip.start();
							s = "list&" + owner.target.id;
							owner.nout.println(s);
							owner.nout.flush();
						}
					} else if (mp == 2) {
						if (energy1.getWidth() < energy2.getWidth()) {
							JOptionPane.showMessageDialog(this, "승리하였습니다!!\n" + "대기실로 이동합니다.");
							owner.nout.println("win");
							owner.nout.flush();
							clip2.stop();
							setVisible(false);
							owner.setVisible(true);
							owner.clip.start();
							s = "list&" + owner.target.id;
							owner.nout.println(s);
							owner.nout.flush();
						} else if (energy1.getWidth() > energy2.getWidth()) {
							JOptionPane.showMessageDialog(this, "승리하였습니다!!\n" + "대기실로 이동합니다.");
							owner.nout.println("lose");
							owner.nout.flush();
							clip2.stop();
							setVisible(false);
							owner.setVisible(true);
							owner.clip.start();
							s = "list&" + owner.target.id;
							owner.nout.println(s);
							owner.nout.flush();
						} else {
							JOptionPane.showMessageDialog(this, "무승부입니다..\n" + "대기실로 이동합니다.");
							clip2.stop();
							setVisible(false);
							owner.setVisible(true);
							owner.clip.start();
							s = "list&" + owner.target.id;
							owner.nout.println(s);
							owner.nout.flush();
						}
					}
					break;
				}
			}
		} catch (InterruptedException e1) {
			System.out.println("e == " + e1);
		}
	}

	public BattleRoom(WaitingRoom owner, int mp, String mch, int op, String och, ClientThread ct) {
		this.owner = owner;
		this.mp = mp;
		this.mch = mch;
		this.op = op;
		this.och = och;
		this.ct = ct;
		init();
		t = new Thread(this);
		t.start();
	}

	void init() {
		owner.clip.stop();
		Sound2("battle.wav", true);
		setTitle("BattleRoom");
		setBounds(320, 120, 1000, 700);
		getContentPane().setLayout(null);
		JPanel back = new ImagePanel("dojo.jpg");
		back.setLayout(null); // 배경의 배열을 절대배열로
		setContentPane(back);

		energy1 = new JPanel();
		energy1.setBackground(Color.YELLOW);
		energy1.setBounds(12, 26, 400, 41);
		getContentPane().add(energy1);

		energy2 = new JPanel();
		energy2.setBackground(Color.YELLOW);
		energy2.setBounds(576, 26, 400, 41);
		getContentPane().add(energy2);

		Ready = new JLabel("Ready..");
		Ready.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD | Font.ITALIC, 80));
		Ready.setBounds(343, 196, 366, 187);
		Ready.setForeground(Color.red);
		getContentPane().add(Ready);
		Go = new JLabel("FIGHT!!");
		Go.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD | Font.ITALIC, 80));
		Go.setBounds(343, 196, 366, 187);
		Go.setForeground(Color.red);
		time = new JLabel("60");
		time.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 75));
		time.setBounds(432, 26, 112, 106);
		getContentPane().add(time);
		setResizable(false);
		System.out.println("mp=" + mp + "mch=" + mch + "op=" + op + "och=" + och);
		t1 = 0;
		if (mp == 1) {
			if (mch.equals("ch1")) {
				System.out.println("춘리");
				character = new Chunli();
				character.setBounds(34, 439, 207, 223);
				getContentPane().add(character);
			} else if (mch.equals("ch2")) {
				System.out.println("켄");
				character = new Ken();
				character.setBounds(34, 439, 207, 223);
				getContentPane().add(character);
			} else if (mch.equals("ch3")) {
				System.out.println("바이손");
				character = new Bison();
				character.setBounds(34, 439, 207, 223);
				getContentPane().add(character);
			} else if (mch.equals("ch4")) {
				System.out.println("류");
				character = new Ryu();
				character.setBounds(34, 439, 207, 223);
				getContentPane().add(character);
			}
		} else if (mp == 2) {
			if (mch.equals("ch1")) {
				System.out.println("춘리");
				character = new Chunli2();
				character.setBounds(773, 439, 207, 223);
				getContentPane().add(character);
			} else if (mch.equals("ch2")) {
				System.out.println("켄");
				character = new Ken2();
				character.setBounds(773, 439, 207, 223);
				getContentPane().add(character);
			} else if (mch.equals("ch3")) {
				System.out.println("바이손");
				character = new Bison2();
				character.setBounds(773, 439, 207, 223);
				getContentPane().add(character);
			} else if (mch.equals("ch4")) {
				System.out.println("류");
				character = new Ryu2();
				character.setBounds(773, 439, 207, 223);
				getContentPane().add(character);
			}
		}

		if (op == 1) {
			if (och.equals("ch1")) {
				System.out.println("춘리");
				character2 = new Chunli();
				character2.setBounds(34, 439, 207, 223);
				getContentPane().add(character2);
			} else if (och.equals("ch2")) {
				System.out.println("켄");
				character2 = new Ken();
				character2.setBounds(34, 439, 207, 223);
				getContentPane().add(character2);
			} else if (och.equals("ch3")) {
				System.out.println("바이손");
				character2 = new Bison();
				character2.setBounds(34, 439, 207, 223);
				getContentPane().add(character2);
			} else if (och.equals("ch4")) {
				System.out.println("류");
				character2 = new Ryu();
				character2.setBounds(34, 439, 207, 223);
				getContentPane().add(character2);
			}
		} else if (op == 2) {
			if (och.equals("ch1")) {
				System.out.println("춘리");
				character2 = new Chunli2();
				character2.setBounds(773, 439, 207, 223);
				getContentPane().add(character2);
			} else if (och.equals("ch2")) {
				System.out.println("켄");
				character2 = new Ken2();
				character2.setBounds(773, 439, 207, 223);
				getContentPane().add(character2);
			} else if (och.equals("ch3")) {
				System.out.println("바이손");
				character2 = new Bison2();
				character2.setBounds(773, 439, 207, 223);
				getContentPane().add(character2);
			} else if (och.equals("ch4")) {
				System.out.println("류");
				character2 = new Ryu2();
				character2.setBounds(773, 439, 207, 223);
				getContentPane().add(character2);
			}
		}

		this.addKeyListener(this);

	}
	/*
	 * public static void main(String[] args) { Login target = new Login();
	 * WaitingRoom wr = new WaitingRoom(target); BattleRoom br = new
	 * BattleRoom(wr); br.setVisible(true); }
	 */

	public void keyTyped(KeyEvent e) {

		long t2 = System.currentTimeMillis();
		if (t2 - t1 > 700) {
			if (e.getSource() == this && start == true) {
				Rectangle r = getContentPane().getBounds();
				Rectangle rr = character.getBounds();
				Rectangle rrr = character2.getBounds();
				char c = e.getKeyChar();
				System.out.println(c);
				// System.out.println(n); // 누른키의 번호가 나옴
				switch (c) {

				case 'z': // 발차기 di = 4
					System.out.println("z");
					character.di = 4;
					if (mp == 1)
						s = "nomove1p&" + Integer.toString(character.di);
					else if (mp == 2)
						s = "nomove2p&" + Integer.toString(character.di);
					owner.nout.println(s);
					owner.nout.flush();
					if (rr.intersects(rrr)) { // 때릴 때
						Sound2("attack.wav", false);
						character2.di = 6; // 적 맞는 모션
						if (mp == 2) { // 자신이 오른쪽에 있을 때 왼쪽에 있는 상대를 때림 ->
										// energy1의
										// 에너지가 닳음
							energy1.setSize(energy1.getWidth() - 30, energy1.getHeight());
							s = "energy1&" + energy1.getWidth();
							owner.nout.println(s);
							owner.nout.flush();

							if (energy1.getWidth() <= 0) {
								if (mch.equals("ch1") || och.equals("ch1"))
									Sound2("Female.wav", false);
								else
									Sound2("Male.wav", false);
								start = false;
								character2.di = 7; // 적 사망
								s = "death1p";
								owner.nout.println(s);
								s = "win";
								owner.nout.println(s);
								owner.nout.flush();
								JOptionPane.showMessageDialog(this, "승리하였습니다!!\n" + "대기실로 이동합니다.");
								clip2.stop();
								setVisible(false);
								owner.setVisible(true);
								owner.clip.start();
								s = "list&" + owner.target.id;
								owner.nout.println(s);
								owner.nout.flush();
							}
						} else if (mp == 1) { // 자신이 왼쪽에 있을 때 오른에 있는 상대를 때림 ->
												// energy2의 에너지가 닳음
							energy2.setSize(energy2.getWidth() - 30, energy2.getHeight());
							s = "energy2&" + energy2.getWidth();
							owner.nout.println(s);
							owner.nout.flush();

							if (energy2.getWidth() <= 0) {
								if (mch.equals("ch1") || och.equals("ch1"))
									Sound2("Female.wav", false);
								else
									Sound2("Male.wav", false);
								start = false;
								character2.di = 7; // 적 사망
								s = "death2p";
								owner.nout.println(s);
								s = "win";
								owner.nout.println(s);
								owner.nout.flush();
								JOptionPane.showMessageDialog(this, "승리하였습니다!!\n" + "대기실로 이동합니다.");
								clip2.stop();
								setVisible(false);
								owner.setVisible(true);
								owner.clip.start();
								s = "list&" + owner.target.id;
								owner.nout.println(s);
								owner.nout.flush();
							}
						}
					}
					break;
				case 'x': // 주먹 di = 3
					System.out.println("x");
					character.di = 3;
					if (mp == 1)
						s = "nomove1p&" + Integer.toString(character.di);
					else if (mp == 2)
						s = "nomove2p&" + Integer.toString(character.di);
					owner.nout.println(s);
					owner.nout.flush();
					if (rr.intersects(rrr)) {
						Sound2("attack.wav", false);
						character2.di = 6;
						if (mp == 2) {
							energy1.setSize(energy1.getWidth() - 20, energy1.getHeight());
							s = "energy1&" + energy1.getWidth();
							owner.nout.println(s);
							owner.nout.flush();

							if (energy1.getWidth() <= 0) {
								if (mch.equals("ch1") || och.equals("ch1"))
									Sound2("Female.wav", false);
								else
									Sound2("Male.wav", false);
								start = false;
								character2.di = 7;
								s = "death1p";
								owner.nout.println(s);
								s = "win";
								owner.nout.println(s);
								owner.nout.flush();
								JOptionPane.showMessageDialog(this, "승리하였습니다!!\n" + "대기실로 이동합니다.");
								clip2.stop();
								setVisible(false);
								owner.setVisible(true);
								owner.clip.start();
								s = "list&" + owner.target.id;
								owner.nout.println(s);
								owner.nout.flush();

							}
						} else if (mp == 1) {
							energy2.setSize(energy2.getWidth() - 20, energy1.getHeight());
							s = "energy2&" + energy2.getWidth();
							owner.nout.println(s);
							owner.nout.flush();

							if (energy2.getWidth() <= 0) {
								if (mch.equals("ch1") || och.equals("ch1"))
									Sound2("Female.wav", false);
								else
									Sound2("Male.wav", false);
								start = false;
								character2.di = 7;
								s = "death2p";
								owner.nout.println(s);
								s = "win";
								owner.nout.println(s);
								owner.nout.flush();
								JOptionPane.showMessageDialog(this, "승리하였습니다!!\n" + "대기실로 이동합니다.");
								clip2.stop();
								setVisible(false);
								owner.setVisible(true);
								owner.clip.start();
								s = "list&" + owner.target.id;
								owner.nout.println(s);
								owner.nout.flush();
							}
						}
					}
					break;
				case 'c': // 필살기 di = 5
					System.out.println("c");
					character.di = 5;
					if (mp == 1)
						s = "nomove1p&" + Integer.toString(character.di);
					else if (mp == 2)
						s = "nomove2p&" + Integer.toString(character.di);
					owner.nout.println(s);
					owner.nout.flush();
					if (rr.intersects(rrr)) {
						Sound2("attack2.wav", false);
						character2.di = 6;
						if (mp == 2) {
							energy1.setSize(energy1.getWidth() - 40, energy1.getHeight());
							s = "energy1&" + energy1.getWidth();
							owner.nout.println(s);
							owner.nout.flush();

							if (energy1.getWidth() <= 0) {
								if (mch.equals("ch1") || och.equals("ch1"))
									Sound2("Female.wav", false);
								else
									Sound2("Male.wav", false);
								start = false;
								character2.di = 7;
								s = "death1p";
								owner.nout.println(s);
								s = "win";
								owner.nout.println(s);
								owner.nout.flush();
								JOptionPane.showMessageDialog(this, "승리하였습니다!!\n" + "대기실로 이동합니다.");
								clip2.stop();
								setVisible(false);
								owner.setVisible(true);
								owner.clip.start();
								s = "list&" + owner.target.id;
								owner.nout.println(s);
								owner.nout.flush();

							}
						} else if (mp == 1) {
							energy2.setSize(energy2.getWidth() - 40, energy1.getHeight());
							s = "energy2&" + energy2.getWidth();
							owner.nout.println(s);
							owner.nout.flush();

							if (energy2.getWidth() <= 0) {
								if (mch.equals("ch1") || och.equals("ch1"))
									Sound2("Female.wav", false);
								else
									Sound2("Male.wav", false);
								start = false;
								character2.di = 7;
								s = "death2p";
								owner.nout.println(s);
								s = "win";
								owner.nout.println(s);
								owner.nout.flush();
								JOptionPane.showMessageDialog(this, "승리하였습니다!!\n" + "대기실로 이동합니다.");
								clip2.stop();
								setVisible(false);
								owner.setVisible(true);
								owner.clip.start();
								s = "list&" + owner.target.id;
								owner.nout.println(s);
								owner.nout.flush();
							}
						}
					}
					break;
				default:

				}
			}
			t1 = System.currentTimeMillis();
		}

	}

	public void keyPressed(KeyEvent e) {

		if (e.getSource() == this && start == true) {

			int n = e.getKeyCode();
			Rectangle r = getContentPane().getBounds(); // 전체의 내부 사각형 좌표
			Rectangle rr = character.getBounds();
			// System.out.println(n); // 누른키의 번호가 나옴
			switch (n) {
			case KeyEvent.VK_LEFT:
				rr.setLocation(rr.x - 10, rr.y);
				if (mp == 1)
					character.di = 2;
				else if (mp == 2)
					character.di = 1;
				break;
			case KeyEvent.VK_RIGHT:
				rr.setLocation(rr.x + 10, rr.y);
				if (mp == 1)
					character.di = 1;
				else if (mp == 2)
					character.di = 2;
				break;

			default:

			}
			if (r.contains(rr)) {
				character.setBounds(rr); // 포함안하면 아예 세팅을 안하게
				if (mp == 1)
					s = "move1p&" + rr.x + "&" + Integer.toString(character.di);
				else if (mp == 2)
					s = "move2p&" + rr.x + "&" + Integer.toString(character.di);
				owner.nout.println(s);
				owner.nout.flush();
				System.out.println(s);
			}
		}
	}

	public void keyReleased(KeyEvent e) {

		if (e.getSource() == this && start == true
				&& (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)) {
			character.di = 0;
			if (mp == 1)
				s = "nomove1p&" + Integer.toString(character.di);
			else if (mp == 2)
				s = "nomove2p&" + Integer.toString(character.di);
			owner.nout.println(s);
			owner.nout.flush();
		}

	}

}
