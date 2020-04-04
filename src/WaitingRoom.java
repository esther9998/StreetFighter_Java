import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

class Panel extends JPanel {
	Image img;
	
	Panel(String fileName) {
		try {
			img= ImageIO.read(new File(fileName));
		}catch(Exception e) {
			System.out.println("image load fail..");
		}
	}
	@Override
	protected void paintComponent(Graphics g) {
		setOpaque(false); // 불투명도.. 먼저 객체를 투명하게하고
		// 풀화면으로 그리고 싶다면..?
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		super.paintComponent(g); // 설정하고 그린다.
	} // img, 위치, 크기, 자기자신
}

public class WaitingRoom extends JFrame implements MouseListener, ActionListener, WindowListener {
	Login target;
	JTextField tf;
	JList list;
	JTextArea ta;
	Socket soc;
	PrintWriter nout;
	BufferedReader nin;
	DefaultListModel listModel;
	UserInfo ui;
	ClientThread t;
	Clip clip;
	
	public void Sound(String file, boolean loop) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
			if(loop) 
				clip.loop(-1);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==tf){
			String m = tf.getText();
			nout.println("chat&"+m);
			nout.flush();
		}
		tf.setText("");
	}

	public WaitingRoom(Login target) {
		this.target = target;
		this.soc = target.soc;
		this.nout = target.nout;
		this.nin = target.nin;
		
		initUI();
		addListeners();
		ui = new UserInfo(this);
		t = new ClientThread(this, ui);
		t.start();
		
		nout.println("list&"+target.id);
		nout.flush();
		

	}

	void addListeners() {
		list.addMouseListener(this);
		tf.addActionListener(this);
		this.addWindowListener(this);
	}

	void initUI() {
		Sound("waiting.wav", true);
		JPanel pn = new Panel("wbg.jpg");
		setContentPane(pn);
		setTitle("\uB300\uAE30\uC2E4");
		getContentPane().setLayout(null);
		list = new JList<>(); 
		list.setBackground(new Color(255, 239, 213));
		JScrollPane sp = new JScrollPane();
		sp.setBounds(646, 97, 295, 450);
		getContentPane().add(sp);
		
		sp.setViewportView(list);

		JScrollPane sp2 = new JScrollPane();
		sp2.setBounds(55, 100, 530, 450);
		sp2.setBorder(new LineBorder(Color.black, 2));
		getContentPane().add(sp2);

		ta = new JTextArea();
		ta.setBackground(new Color(255, 204, 153));
		ta.setBorder(new LineBorder(Color.black, 2));
		sp2.setViewportView(ta);

		tf = new JTextField() {
			public void setBorder(Border border) {}	
		};
		//tf.setBackground(new Color(255, 239, 213));
		tf.setBounds(44, 590, 557, 38);
		getContentPane().add(tf);
		tf.setColumns(10);

		

		
		
		listModel = new DefaultListModel(); 
		sp.setOpaque(false);
		sp.getViewport().setOpaque(false);
		sp2.setOpaque(false);
		sp2.getViewport().setOpaque(false);
		ta.setOpaque(false);
		list.setOpaque(false);
		ta.setEditable(false);
		tf.setOpaque(false);
		setResizable(false);
		setBounds(320, 120, 1000, 700);
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) { // 유저닉네임 더블클릭으로 정보확인 초대
			
			String s = "info&"+
					(String)list.getSelectedValue();
			nout.println(s);
			nout.flush();
			
			
			ui.setVisible(true);
			
		}

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		nout.println("logout&"+target.id);
		nout.flush();

	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}
}
