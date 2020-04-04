
import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Join extends JDialog implements ActionListener {
	Login target;
	JTextField id;
	JPasswordField pass;
	JTextArea intro;
	JButton bt1;
	JButton bt2;
	JButton confirm;
	Socket soc;
	PrintWriter nout;
	BufferedReader nin;
	JPanel pn;

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt1) { // 취소버튼
			setVisible(false);
			
		} else if(e.getSource() == bt2){ // 회원가입 완료버튼
			
			char[] ch = pass.getPassword();
			String s = "join&"+id.getText()+"&"+new String(ch)+"&"+intro.getText();
			
			nout.println(s);
			nout.flush();
			try {
				String rev = nin.readLine();
				System.out.println(rev);
				if(rev.equals("true")) {
					JOptionPane.showMessageDialog(this, "회원가입 완료!");
					setVisible(false);
					
					
				}
				else {
					JOptionPane.showMessageDialog(this, "아이디가 중복되었습니다. 다른 아이디를 사용해주세요!");
				}
			} catch (IOException e1) {
				
				System.out.println("e== "+e1);
			} 
			
		}else if(e.getSource() == confirm){ // 중복확인버튼
			String s = "confirm&"+id.getText();
			
			nout.println(s);
			nout.flush();
			try {
				String rev = nin.readLine();
				
				if(rev.equals("true")) {
					JOptionPane.showMessageDialog(this, "이 아이디는 사용가능합니다.");
					
				}
				else {
					JOptionPane.showMessageDialog(this, "아이디가 중복되었습니다. 다른 아이디로 만들어주세요!");
				}
			} catch (IOException e1) {
				
				System.out.println("e== "+e1);
			} 
		}
			
	}

	public Join(Login target) {
		init();
		this.target = target;
		this.soc = target.soc;
		this.nout = target.nout;
		this.nin = target.nin;
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		confirm.addActionListener(this);
	}

	void init() {
		setTitle("\uD68C\uC6D0\uAC00\uC785");
		getContentPane().setLayout(null);

		confirm = new JButton("\uC911\uBCF5\uD655\uC778"); 
		confirm.setBounds(185, 50, 87, 23); 
		getContentPane().add(confirm);

		id = new JTextField();
		id.setBounds(87, 51, 86, 21);
		getContentPane().add(id);
		id.setColumns(10);

		bt1 = new JButton("\uCDE8\uC18C");
		bt1.setBounds(12, 379, 81, 23);
		getContentPane().add(bt1);

		bt2 = new JButton("\uD68C\uC6D0\uAC00\uC785 \uC644\uB8CC");
		bt2.setBounds(142, 379, 124, 23);
		getContentPane().add(bt2);

		JLabel lblNewLabel = new JLabel("ID :");
		lblNewLabel.setBounds(58, 54, 35, 15);
		getContentPane().add(lblNewLabel);

		JLabel lblPassward = new JLabel("PASSWARD :");
		lblPassward.setBounds(3, 88, 81, 15);
		getContentPane().add(lblPassward);

		pass = new JPasswordField();
		pass.setBounds(87, 82, 86, 21);
		getContentPane().add(pass);

		JLabel lblNewLabel_1 = new JLabel("\uC790\uAE30\uC18C\uAC1C");
		lblNewLabel_1.setBounds(12, 134, 57, 15);
		getContentPane().add(lblNewLabel_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 160, 260, 176);
		getContentPane().add(scrollPane);

		intro = new JTextArea();
		scrollPane.setColumnHeaderView(intro);
		
		pn = new ImagePanel("dojo2.jpg");
		getContentPane().add(pn);
		pn.setOpaque(false);
		pn.setBounds(0, 0, 300, 450);
		
		setBounds(200, 300, 300, 450);
		setResizable(false);
	}

	
}
