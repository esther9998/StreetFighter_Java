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
		System.out.println("여기 들어와");
		char[] c = pwf.getPassword();
		String change = new String(c);
		
		if(e.getSource() == joinbt) { 
			join = new Join(this); // 회원가입창 열기
			join.setVisible(true);
		
			
		}else {
			System.out.println("로그인 결과 :  "+ idf.getText().trim());
			System.out.println("로그인 결과 :  "+ change);
			if (idf.getText().trim() == "" ) {
				JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 입력해주세요.", "로그인 정보 오류",JOptionPane.WARNING_MESSAGE);
			} else {

				String s = "login&" + idf.getText() +"&"+ change;
				nout.println(s);
				nout.flush();
				try {
					String rev = nin.readLine();
					System.out.println("로그인 결과 :  "+ rev);
					if(rev.equals("true")) {
						id = idf.getText();
						wait = new WaitingRoom(this); // 로그인 -> 대기창 열기
						wait.setVisible(true);
						setVisible(false);
					} else if(rev.equals("nopass")){
						JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.", "비밀번호 확인",JOptionPane.WARNING_MESSAGE);
					}else 
						JOptionPane.showMessageDialog(this, "일치하는 아이디가 없습니다.", "아이디 확인",JOptionPane.WARNING_MESSAGE);
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
			System.out.println("고객 IP : "+ ip);
			
			
			nout = new PrintWriter(new OutputStreamWriter(soc.getOutputStream()));
			nin = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "네트워크상태가 불안정합니다.");
			System.exit(0);
		}
	}

	public Login() {
		setTitle("로그인");
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

		joinbt = new JButton(new ImageIcon("회원가입.png"));
		joinbt.setBounds(20, 300, 100, 40);
		getContentPane().add(joinbt);

		loginbt = new JButton(new ImageIcon("로그인.png"));
		loginbt.setBounds(160, 300, 100, 40);
		getContentPane().add(loginbt);
		
		pn = new ImagePanel("login.jpg");
		getContentPane().add(pn);
		pn.setBounds(0, 0, 284, 412);
	//	ip = JOptionPane.showInputDialog(this, "서버 ip주소를 입력해주세요.");
		
	}

	public static void main(String[] args) {
		new Login().setVisible(true);
	
	}

}