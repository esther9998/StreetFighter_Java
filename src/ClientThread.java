
import java.io.IOException;

import javax.swing.JOptionPane;

public class ClientThread extends Thread {
	WaitingRoom owner;
	UserInfo info;
	boolean flag;
	ChoiceRoom cr;
	BattleRoom br;
	boolean cnt;
	String mychoice;

	public ClientThread(WaitingRoom owner, UserInfo info) {
		this.owner = owner;
		this.info = info;
		flag = true;
	}

	public void run() {
		while (flag) {
			try {
				String str = owner.nin.readLine();

				String[] ar = str.split("&");
				System.out.println(str);

				switch (ar[0]) {
				case "chat":
					owner.ta.append("[ " + ar[2] + " ]  " + ar[1] + "\n");
					owner.ta.setCaretPosition(owner.ta.getText().length());
					break;
				case "list":
					owner.listModel.addElement(ar[1]);
					owner.list.setModel(owner.listModel);
					break;
				case "logout":
					owner.listModel.removeElement(ar[1]);
					owner.list.setModel(owner.listModel);
					break;
				case "info":

					info.id.setText(ar[1]);
					info.win.setText(ar[2]);
					info.lose.setText(ar[3]);
					info.intro.setText(ar[4]);

					try {
						float rate = Integer.parseInt(ar[2])
								/ (float) (Integer.parseInt(ar[2]) + Integer.parseInt(ar[3])) * 100;
						info.rate = (int) rate;
					} catch (ArithmeticException e) {
						info.rate = 0;
					}
					info.winrate.setText(Integer.toString(info.rate) + "%");
					if (owner.target.id.equals(ar[1]))
						info.bt.setEnabled(false);
					else
						info.bt.setEnabled(true);
					break;
				case "message":

					int no = JOptionPane.showConfirmDialog(owner, ar[1] + "님의 결투를 받아들이시겠습니까", "결투신청",
							JOptionPane.OK_CANCEL_OPTION);
					if (no == 0) {
						owner.nout.println("yes&" + ar[1]); // 초대를 받아서 수락
						owner.nout.flush();
						cr = new ChoiceRoom(owner, info);
						cr.setVisible(true);
						owner.nout.println("logout&" + owner.target.id);
						owner.nout.flush();
						owner.setVisible(false);
						owner.ui.setVisible(false);

					} else if (no == 1 || no == 2) {
						owner.nout.println("no&" + ar[1]);
						owner.nout.flush();
					} else {
						System.out.println("message 에러");
					}
					break;
				case "yes":
					owner.nout.println("opponent&" + ar[1]); // 상대방아이디를 서버스레드에
																// 저장
					owner.nout.flush();
					cr = new ChoiceRoom(owner, info); // 초대를해서 상대방이 수락
					cr.setVisible(true);
					owner.nout.println("logout&" + owner.target.id);
					owner.nout.flush();
					owner.setVisible(false);
					owner.ui.setVisible(false);
					break;
				case "no":
					JOptionPane.showMessageDialog(owner, "상대방이 거절하셨습니다.");
					break;
				case "choice":
					if (cr.cnt == 1) { // 자신이 먼저 선택하고 상대방이 선택했을 때
						System.out.println("3 :" + mychoice);
						System.out.println("5 : " + ar[1]);
						br = new BattleRoom(owner, 2, mychoice, 1, ar[1], this);
						br.setVisible(true); // 이녀석은 오른쪽으로
						cr.setVisible(false);
					} else {
						if (ar[1].equals("ch1")) {
							cr.ch1.setEnabled(false);
							cr.cnt++;
							cr.enemy = ar[1];
						} else if (ar[1].equals("ch2")) {
							cr.ch2.setEnabled(false);
							cr.cnt++;
							cr.enemy = ar[1];
						} else if (ar[1].equals("ch3")) {
							cr.ch3.setEnabled(false);
							cr.cnt++;
							cr.enemy = ar[1];
						} else if (ar[1].equals("ch4")) {
							cr.ch4.setEnabled(false);
							cr.cnt++;
							cr.enemy = ar[1];
						}
					}
					break;
				case "click":
					if ("ch1".equals(ar[1])) {
						if (cnt)
							cr.getContentPane().remove(cr.pch2);
						cr.pch2 = new ImagePanel("chunliChoice.png");
						cr.pch2.setBounds(682, 126, 340, 500);
						cr.getContentPane().add(cr.pch2);
						cr.repaint();
						cnt = true;
					} else if ("ch2".equals(ar[1])) {
						if (cnt)
							cr.getContentPane().remove(cr.pch2);
						cr.pch2 = new ImagePanel("kenChoice.png");
						cr.pch2.setBounds(682, 126, 340, 500);
						cr.getContentPane().add(cr.pch2);
						cr.repaint();
						cnt = true;
					} else if ("ch3".equals(ar[1])) {
						if (cnt)
							cr.getContentPane().remove(cr.pch2);
						cr.pch2 = new ImagePanel("bisonChoice2.png");
						cr.pch2.setBounds(682, 126, 340, 500);
						cr.getContentPane().add(cr.pch2);
						cr.repaint();
						cnt = true;
					} else if ("ch4".equals(ar[1])) {
						if (cnt)
							cr.getContentPane().remove(cr.pch2);
						cr.pch2 = new ImagePanel("ryuChoice.png");
						cr.pch2.setBounds(682, 126, 340, 500);
						cr.getContentPane().add(cr.pch2);
						cr.repaint();
						cnt = true;
					}
					break;
				case "mychoice":
					mychoice = ar[1];
					break;
				case "move1p": // 1p입장에서의 상대방무빙
					br.character2.setLocation(Integer.parseInt(ar[1]), br.character2.getY());
					br.character2.di = Integer.parseInt(ar[2]);
					;
					break;
				case "move2p":
					cr.br.character2.setLocation(Integer.parseInt(ar[1]), cr.br.character2.getY());
					cr.br.character2.di = Integer.parseInt(ar[2]);
					;
					break;
				case "nomove1p":
					br.character2.di = Integer.parseInt(ar[1]);
					break;
				case "nomove2p":
					cr.br.character2.di = Integer.parseInt(ar[1]);
					break;
				case "energy1": // 1p의 에너지가 깎이는거 자신이 1p
					cr.br.energy1.setSize(Integer.parseInt(ar[1]), cr.br.energy1.getHeight());
					cr.br.character.di = 6;
					break;
				case "energy2":
					br.energy2.setSize(Integer.parseInt(ar[1]), br.energy2.getHeight());
					br.character.di = 6;
					break;
				case "death1p":
					cr.br.start = false;
					cr.br.character.di = 7; // 자신 사망
					owner.nout.println("lose");
					owner.nout.flush();
					JOptionPane.showMessageDialog(cr.br, "패배하였습니다..\n" + "대기실로 이동합니다.");
					cr.br.clip2.stop();
					cr.br.setVisible(false);
					owner.setVisible(true);
					owner.clip.start();
					owner.nout.println("list&" + owner.target.id);
					owner.nout.flush();
					break;
				case "death2p":
					br.start = false;
					br.character.di = 7;
					owner.nout.println("lose");
					owner.nout.flush();
					JOptionPane.showMessageDialog(br, "패배하였습니다..\n" + "대기실로 이동합니다.");
					br.clip2.stop();
					br.setVisible(false);
					owner.setVisible(true);
					owner.clip.start();
					owner.nout.println("list&" + owner.target.id);
					owner.nout.flush();
					break;
				case "remove":
					owner.listModel.removeAllElements();
					owner.list.setModel(owner.listModel);
					break;
					
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(owner, "네트워크 연결 문제 감지" + e.toString());
				flag = false;
			}

		}

	}

}
