
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Server {

	public static void main(String[] args) {
		
		HashMap<String, ClientInfoClass> clientMap = new HashMap<String, ClientInfoClass>();
		try {
			ServerSocket server = new ServerSocket();
			InetSocketAddress ipep = new InetSocketAddress(10001);
			server.bind(ipep);																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																			
			while (true) {
				System.out.println("서버를 시작 합니다.\n접속을 기다립니다.");
				Socket  sock = server.accept();
				InputStream in = sock.getInputStream();
				System.out.println(in+ " in 해쉬코드");
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				
				OutputStream out = sock.getOutputStream();
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
				System.out.println(sock.getInetAddress() + "님이 접속 하였습니다.");
				Thread.sleep(100);
				// Client 개인 Output 생성

				ServerJoin join = new ServerJoin(sock, clientMap,br,pw);
				Thread.sleep(100);
				join.start();

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("메인 서버 예외처리 발생");
		}
	}
}

class ServerJoin extends Thread{

	private ConnectionPool cp = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	// 클라이언트 정보 저장 HashMap
	HashMap<String, ClientInfoClass> clientMap;
	Socket sock;
	PrintWriter pw;
	BufferedReader br;
	String id;
	
	//생성자
	ServerJoin(Socket sock, HashMap<String, ClientInfoClass> clientMap,BufferedReader br,
			PrintWriter pw) {
		this.sock = sock;
		this.clientMap = clientMap;
		this.br = br;
		this.pw = pw;
		
		

	}

	// 추후 구현
	 void initSetting() {
		String url = "jdbc:mysql://localhost/moviedb?serverTimezone=UTC";
		String user = "boolsa";
		String password = "1234";
		try {
			cp = ConnectionPool.getInstance(url, user, password, 5, 10);
			con = cp.getConnection();
			System.out.println(cp.getNumCons() + "  <<<< 여기 인원수");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void inputClientInfo() {
		ClientInfoClass clientInfo = new ClientInfoClass(pw);
		clientInfo.setLogin(true);
		clientMap.put(id,clientInfo);
	}

	// 서버 시작 메서드
	public void run() {
			initSetting();
			try {
			//login();
			Thread.sleep(100);
			MovieThread movieThread = new MovieThread(br,pw,con,cp);
			ServerRecive recive = new ServerRecive(br,movieThread);

			Thread.sleep(100);
			movieThread.setMovieThread(movieThread);
			Thread.sleep(100);
			//recive.start();
			movieThread.run();
			//new Thread(movieThread).start();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println();
			return;
		}
		 finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					cp.releaseConnection(con);
				System.out.println(sock.getInetAddress()+" 님이 종료 하셨습니다.");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
class ServerRecive extends Thread{
	
	BufferedReader br;
	MovieThread movieThread;
	ServerRecive(BufferedReader br,MovieThread movieThread){
		this.br = br;
	}
	public void run() {
		String tmp;
		while(true) {
			try {
				tmp = br.readLine();
				movieThread.getrecive(tmp);
				System.out.println(tmp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
