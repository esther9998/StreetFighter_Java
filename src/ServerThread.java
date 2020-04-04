
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServerThread extends Thread {

	static Set<ServerThread> subs;
	static Set<Id> users;
	static List<String> list;
	static {
		subs = new HashSet<>();
		users = new HashSet<>();
		list = new ArrayList<>();

	}

	boolean status;
	Socket target;
	PrintWriter nout;
	BufferedReader nin;
	String myId;
	String opponent;
	boolean wait;

	public ServerThread(Socket target) {
		this.target = target;
		try {
			nout = new PrintWriter(new OutputStreamWriter(target.getOutputStream()));
			nin = new BufferedReader(new InputStreamReader(target.getInputStream()));
			status = true;
			subs.add(this);
		} catch (IOException e) {
			status = false;
		}
	}

	public void run() {
		while (status) {
			try {
				String req = nin.readLine();
				System.out.println(req);
				String[] ar = req.split("&");
				switch (ar[0]) {
				case "join":
					Id user = new Id();
					user.id = ar[1];
					user.passward = ar[2];
					user.intro = ar[3];
					boolean b = users.add(user);
					if (b) {
						nout.println("true");
						nout.flush();
						ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user.dat"));
						oos.writeObject(users);
						oos.close();

					} else {
						nout.println("false");
						nout.flush();
					}
					break;
				case "confirm":
					Id con = new Id();
					con.id = ar[1];
					boolean bb = users.add(con);
					if (bb) {
						users.remove(con);
						nout.println("true");
						nout.flush();
					} else {
						nout.println("false");
						nout.flush();
					}
					break;
				case "chat":
					for (ServerThread s : subs) {
						s.nout.println(req + "&" + myId);
						s.nout.flush();
					}
					break;
				case "login":
					String rev = "";
					for (Id id : users) {
						//아이디 조회.
						if(ar[1]==null ) {
//						if(!id.id.equals(ar[1]) ) {
							
							System.out.println(" 서버 단에서 ID받기 : "+ar[1]);
							rev = "false";
							break;
						}else  {
							
							if(id.id.equals(ar[1]) ) {
								if (id.passward.equals(ar[2])) {
									rev = "true";
									myId = ar[1];
									wait = true;
									break;
								} else {
									rev = "nopass";
									break;
								}
							}else {
								System.out.println(" 서버 단에서 ID확인 안됨 : "+ar[1]);
							}
						} 
					}
					nout.println(rev);
					nout.flush();
					break;

				case "list":
					wait = true;
					for (ServerThread s : subs) {
						if (s.wait) {
							System.out.println("s=" + s + s.wait);
							s.nout.println(req); // 자신의 아이디를 모든사람에게 전송
							s.nout.flush();
						}
					}
					for (String id : list) { // 자신의 접속이전에 접속하던 아이디목록 불러오기
						nout.println("list&" + id);
						nout.flush();
					}

					list.add(ar[1]);
					break;
				case "logout":
					for (ServerThread s : subs) {
						s.nout.println(req);
						s.nout.flush();
					}
					list.remove(ar[1]);
					nout.println("remove");
					nout.flush();
					wait = false;
					break;
				case "info":
					for (Id id : users) {

						if (id.id.equals(ar[1])) {

							nout.println("info&" + id.id + "&" + id.win + "&" + id.lose + "&" + id.intro);
							nout.flush();
							break;
						}
					}
					break;
				case "message":
					for (ServerThread s : subs) {
						if (s.myId.equals(ar[1])) {
							System.out.println("상대 : " + s.myId);
							System.out.println("내아이디 : " + myId);
							String msg = "message&" + myId;
							s.nout.println(msg);
							s.nout.flush();
							break;
						} else {
							System.out.println("일치하지 않습니다");
						}
					}
					break;
				case "yes":
					for (ServerThread s : subs) {
						if (s.myId.equals(ar[1])) {
							s.nout.println("yes&" + myId);
							s.nout.flush();
							opponent = ar[1];
						}
					}
					break;
				case "no":
					for (ServerThread s : subs) {
						if (s.myId.equals(ar[1])) {
							s.nout.println("no&" + myId);
							s.nout.flush();
						}
					}
					break;
				case "opponent":
					opponent = ar[1];
					break;
				case "click":
					for (ServerThread s : subs) {
						if (s.myId.equals(opponent)) {
							s.nout.println(req);
							s.nout.flush();
						}
					}
					break;
				case "choice":
					for (ServerThread s : subs) {
						if (s.myId.equals(opponent)) {
							s.nout.println(req);
							s.nout.flush();
						}
					}
					nout.println("mychoice&" + ar[1]);
					nout.flush();
					break;
				case "move1p":
					for (ServerThread s : subs) {
						if (s.myId.equals(opponent)) {
							s.nout.println(req);
							s.nout.flush();
						}
					}
					break;
				case "move2p":
					for (ServerThread s : subs) {
						if (s.myId.equals(opponent)) {
							s.nout.println(req);
							s.nout.flush();
						}
					}
					break;
				case "nomove1p":
					for (ServerThread s : subs) {
						if (s.myId.equals(opponent)) {
							s.nout.println(req);
							s.nout.flush();
						}
					}
					break;
				case "nomove2p":
					for (ServerThread s : subs) {
						if (s.myId.equals(opponent)) {
							s.nout.println(req);
							s.nout.flush();
						}
					}
					break;
				case "energy1":
					for (ServerThread s : subs) {
						if (s.myId.equals(opponent)) {
							s.nout.println(req);
							s.nout.flush();
						}
					}
					break;
				case "energy2":
					for (ServerThread s : subs) {
						if (s.myId.equals(opponent)) {
							s.nout.println(req);
							s.nout.flush();
						}
					}
					break;
				case "death1p":
					for (ServerThread s : subs) {
						if (s.myId.equals(opponent)) {
							s.nout.println(req);
							s.nout.flush();
						}
					}
					break;
				case "death2p":
					for (ServerThread s : subs) {
						if (s.myId.equals(opponent)) {
							s.nout.println(req);
							s.nout.flush();
						}
					}
					break;
				case "win":
					for (Id id : users) {
						if (id.id.equals(myId)) {
							id.win++;
							ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user.dat"));
							oos.writeObject(users);
							oos.close();
						}
					}
					break;
				case "lose":
					for (Id id : users) {
						if (id.id.equals(myId)) {
							id.lose++;
							ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user.dat"));
							oos.writeObject(users);
							oos.close();
						}
					}
					break;
				}

			} catch (Exception e) {
				status = false;
				subs.remove(this);

				System.out.println("te==" + e);
				e.printStackTrace();
			}
		}

	}
}
