import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JTextArea;



public class UserInfo extends JDialog implements ActionListener {
	WaitingRoom target;
	JButton bt;
	String s;
	JLabel id;
	JTextArea intro;
	JLabel lose;
	JLabel win;
	JLabel winrate;
	JPanel pn;
	int rate;

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt) {
			int num = JOptionPane.showConfirmDialog(this, id.getText()+"님에게 결투신청을 하시겠습니까?", "결투신청", JOptionPane.OK_CANCEL_OPTION);
			System.out.println(num);
			if(num == 0) {
			String request = "message&" + id.getText();
			target.nout.println(request);
			target.nout.flush();
			}
		}
	}

	public UserInfo(WaitingRoom target) {
		this.target = target;
		init();

	}

	void init() {
		setTitle("\uC720\uC800 \uC815\uBCF4");
		getContentPane().setLayout(null);
		setBounds(420, 220, 450, 300);
		
		JLabel lblNewLabel = new JLabel("\uB2C9\uB124\uC784 :");
		lblNewLabel.setBounds(29, 28, 57, 15);
		getContentPane().add(lblNewLabel);

		JLabel label = new JLabel("\uC2B9 :");
		label.setBounds(29, 163, 57, 15);
		getContentPane().add(label);

		JLabel label_1 = new JLabel("\uD328 :");
		label_1.setBounds(197, 163, 57, 15);
		getContentPane().add(label_1);

		JLabel label_2 = new JLabel("\uC2B9\uB960 :");
		label_2.setBounds(29, 205, 57, 15);
		getContentPane().add(label_2);

		JLabel lblNewLabel_1 = new JLabel("\uC790\uAE30\uC18C\uAC1C");
		lblNewLabel_1.setBounds(29, 60, 57, 15);
		getContentPane().add(lblNewLabel_1);

		bt = new JButton("\uACB0\uD22C \uC81C\uC758");
		bt.setBounds(310, 218, 112, 33);
		getContentPane().add(bt);

		id = new JLabel("");
		id.setBounds(98, 28, 57, 15);
		getContentPane().add(id);

		intro = new JTextArea();
		intro.setBounds(29, 85, 393, 47);
		getContentPane().add(intro);
		intro.setEditable(false);
		lose = new JLabel("");
		lose.setBounds(226, 163, 57, 15);
		getContentPane().add(lose);

		win = new JLabel("");
		win.setBounds(54, 163, 57, 15);
		getContentPane().add(win);

		winrate = new JLabel("");
		winrate.setBounds(70, 205, 57, 15);
		getContentPane().add(winrate);
		pn = new ImagePanel("info.jpg");
		getContentPane().add(pn);
		pn.setOpaque(false);
		pn.setBounds(0, 0, 450, 330);
		intro.setOpaque(false);
		setResizable(false);
		bt.addActionListener(this);
	}
}
