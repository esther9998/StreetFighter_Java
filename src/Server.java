
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Set;

public class Server {
	public static void main(String[] args) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user.dat"));
			ServerThread.users = (Set<Id>) ois.readObject();
		} catch (Exception e1) {
			System.out.println("사용자 정보를 가져오지 못했습니다.");
		}

		try {
			ServerSocket server = new ServerSocket(9999);
			System.out.println("SERVER READY!");
			while (true) {
				Socket soc = server.accept();
				System.out.println("Client CONNECT!");
				Thread t = new ServerThread(soc);

				t.start();

			}

		} catch (IOException e) {
			System.out.println("e==" + e);
		}
	}
}