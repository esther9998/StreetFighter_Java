import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

class ImagePanel extends JPanel {
	Image img;

	ImagePanel(String fileName) {
		try {
			img = ImageIO.read(new File(fileName));
		} catch (Exception e) {
			System.out.println("image load fail..");
		}
	}

	protected void paintComponent(Graphics g) {
		setOpaque(false);
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		super.paintComponent(g); // 설정하고 그린다.
	} // img, 위치, 크기, 자기자신
}

public class ChoiceRoom extends JDialog implements ActionListener {
	WaitingRoom target;
	Socket soc;
	int cnt = 0;
	JButton mychoice;
	JButton ch1, ch2, ch3, ch4;
	Image img;
	JPanel pch1;
	JPanel pch2;
	PrintWriter nout;
	BufferedReader nin;
	String ch;
	JButton bt;
	boolean cnt2;
	String name;
	UserInfo info;
	String enemy;
	BattleRoom br;

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == mychoice) {
			nout.println("choice&" + ch);
			nout.flush();
			ch1.setEnabled(false);
			ch2.setEnabled(false);
			ch3.setEnabled(false);
			ch4.setEnabled(false);
			mychoice.setEnabled(false);
			cnt++;
			if (cnt == 2) {
				br = new BattleRoom(target, 1, ch, 2, enemy, target.t); // 자기가누른거
				br.setVisible(true); // 이녀석은 왼쪽으로
				setVisible(false);
			}
		}

		if (e.getSource() == ch1) {
			if (cnt2)
				getContentPane().remove(pch1);
			nout.println("click&ch1");
			nout.flush();
			ch = "ch1";
			name = "chunliChoice.png";

			pch1 = new ImagePanel(name);
			pch1.setBounds(12, 126, 340, 500);
			getContentPane().add(pch1);
			getContentPane().repaint();
			cnt2 = true;
		}
		if (e.getSource() == ch2) {
			if (cnt2)
				getContentPane().remove(pch1);
			nout.println("click&ch2");
			nout.flush();
			ch = "ch2";
			name = "kenChoice.png";

			pch1 = new ImagePanel(name);
			pch1.setBounds(12, 126, 340, 500);
			getContentPane().add(pch1);
			getContentPane().repaint();
			cnt2 = true;
		}
		if (e.getSource() == ch3) {
			if (cnt2)
				getContentPane().remove(pch1);
			nout.println("click&ch3");
			nout.flush();
			ch = "ch3";
			name = "bisonChoice2.png";
			pch1 = new ImagePanel(name);
			pch1.setBounds(12, 126, 340, 500);
			getContentPane().add(pch1);
			getContentPane().repaint();
			cnt2 = true;
		}
		if (e.getSource() == ch4) {
			if (cnt2)
				getContentPane().remove(pch1);
			nout.println("click&ch4");
			nout.flush();
			ch = "ch4";
			name = "ryuChoice.png";
			pch1 = new ImagePanel(name);
			pch1.setBounds(12, 126, 340, 500);
			getContentPane().add(pch1);
			getContentPane().repaint();
			cnt2 = true;
		}
	}

	ChoiceRoom(WaitingRoom target, UserInfo info) {
		this.target = target;
		this.info = info;
		this.soc = target.soc;
		this.nout = target.nout;
		this.nin = target.nin;
		JPanel pn = new ImagePanel("waiting.jpg");

		setContentPane(pn);
		

	
		init();
		setVisible(true);
		ch1.addActionListener(this);
		ch2.addActionListener(this);
		ch3.addActionListener(this);
		ch4.addActionListener(this);
		mychoice.addActionListener(this);
		setResizable(false);

	}

	void init() {
		getContentPane().setLayout(null);
		JPanel panel = new JPanel();
		setBounds(320, 120, 1040, 700);

		mychoice = new JButton(new ImageIcon("select.png"));
		mychoice.setBounds(431, 513, 170, 64);
		getContentPane().add(mychoice);

		// 클릭한후 큰 이미지 보이기

		JLabel lblI = new JLabel("My Character");
		lblI.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD | Font.ITALIC, 30));
		lblI.setForeground(Color.RED);
		lblI.setBounds(77, 21, 251, 53);
		getContentPane().add(lblI);

		JLabel lblNewLabel = new JLabel("Enemy Character");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("나눔스퀘어 ExtraBold", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel.setBounds(695, 15, 308, 64);
		getContentPane().add(lblNewLabel);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(new GridLayout(2, 2));
		panel_3.setBounds(388, 171, 251, 251);
		getContentPane().add(panel_3);

		ch1 = new JButton(new ImageIcon("choiceChun.jpg"));
		// ch1.setOpaque(false);
		panel_3.add(ch1);

		ch2 = new JButton(new ImageIcon("choiceKen.jpg"));
		panel_3.add(ch2);

		ch3 = new JButton(new ImageIcon("choiceBison.jpg"));
		panel_3.add(ch3);

		ch4 = new JButton(new ImageIcon("choiceRyu.jpg"));
		panel_3.add(ch4);

	}

}