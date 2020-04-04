import javax.swing.JFrame;

import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;



public class Login extends JFrame implements ActionListener {
	JTextField idf;
	JPasswordField pwf;
	JButton joinbt;
	JButton loginbt;
	Join join;
	WaitingRoom wait;
	Socket soc;
	PrintWriter nout;
	BufferedReader nin;
	String id;
	JPanel pn;
	InetAddress ipAddr;
	String ip;
	public void actionPerformed(ActionEvent e) {
		System.out.println("���� ����");
		char[] c = pwf.getPassword();
		String change = new String(c);
		
		if(e.getSource() == joinbt) { 
			join = new Join(this); // ȸ������â ����
			join.setVisible(true);
		
			
		}else {
			System.out.println("�α��� ��� :  "+ idf.getText().trim());
			System.out.println("�α��� ��� :  "+ change);
			if (idf.getText().trim() == "" ) {
				JOptionPane.showMessageDialog(this, "���̵�� ��й�ȣ�� �Է����ּ���.", "�α��� ���� ����",JOptionPane.WARNING_MESSAGE);
			} else {

				String s = "login&" + idf.getText() +"&"+ change;
				nout.println(s);
				nout.flush();
				try {
					String rev = nin.readLine();
					System.out.println("�α��� ��� :  "+ rev);
					if(rev.equals("true")) {
						id = idf.getText();
						wait = new WaitingRoom(this); // �α��� -> ���â ����
						wait.setVisible(true);
						setVisible(false);
					} else if(rev.equals("nopass")){
						JOptionPane.showMessageDialog(this, "��й�ȣ�� ��ġ���� �ʽ��ϴ�.", "��й�ȣ Ȯ��",JOptionPane.WARNING_MESSAGE);
					}else 
						JOptionPane.showMessageDialog(this, "��ġ�ϴ� ���̵� �����ϴ�.", "���̵� Ȯ��",JOptionPane.WARNING_MESSAGE);
				} catch(IOException ee) {
					System.out.println("ee ==" + ee);
				}
			}
		}
	}
	
	void initNetWork() {
		try {
			ipAddr = InetAddress.getLocalHost();
			ip = ipAddr.getHostAddress();
			soc = new Socket(ip, 9999);
			System.out.println("�� IP : "+ ip);
			
			
			nout = new PrintWriter(new OutputStreamWriter(soc.getOutputStream()));
			nin = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "��Ʈ��ũ���°� �Ҿ����մϴ�.");
			System.exit(0);
		}
	}

	public Login() {
		setTitle("�α���");
		init();
		initNetWork();
		joinbt.addActionListener(this);
		loginbt.addActionListener(this);
	}
	
	void init() {
		getContentPane().setLayout(new BorderLayout());
		setResizable(false);
		
		idf = new JTextField() {
			public void setBorder(Border border) {}
		};
		idf.setBounds(43,197,199,22);
		idf.setOpaque(false);
		getContentPane().add(idf);
		idf.setColumns(10);
		setBounds(100, 150, 300, 450);
		pwf = new JPasswordField() {
			public void setBorder(Border border) {}
		};
		pwf.setBounds(43,243,199,22);
		pwf.setOpaque(false);
		getContentPane().add(pwf);

		joinbt = new JButton(new ImageIcon("ȸ������.png"));
		joinbt.setBounds(20, 300, 100, 40);
		getContentPane().add(joinbt);

		loginbt = new JButton(new ImageIcon("�α���.png"));
		loginbt.setBounds(160, 300, 100, 40);
		getContentPane().add(loginbt);
		
		pn = new ImagePanel("login.jpg");
		getContentPane().add(pn);
		pn.setBounds(0, 0, 284, 412);
	//	ip = JOptionPane.showInputDialog(this, "���� ip�ּҸ� �Է����ּ���.");
		
	}

	public static void main(String[] args) {
		new Login().setVisible(true);
	
	}

}