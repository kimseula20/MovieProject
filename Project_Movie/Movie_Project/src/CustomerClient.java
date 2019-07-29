import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class CustomerClient {

	String name;
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		try {
			Socket sock = new Socket();
			InetSocketAddress ipep = new InetSocketAddress("127.0.0.1",10001);
			sock.connect(ipep);
			//클라이언트 input 생성
			InputStream in = sock.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			//클라리언트 output 생성
			OutputStream out = sock.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			Thread.sleep(100);
			ClientReciveThread reciveThread = new ClientReciveThread(br);
			ClientSendThread sendThread = new ClientSendThread(pw);
			Thread.sleep(100);
			reciveThread.start();
			sendThread.start();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("client Main 예외처리 발생");
		}
		
	}
}
class ClientReciveThread extends Thread{
	
	BufferedReader br;
	ClientReciveThread(BufferedReader br){
		this.br = br;
	}
	//Thread 시작 메서드
	public void run() {
		String reciveMsg="";
		System.out.println("Recive Thread를 시작 합니다.");
		while(true) {
			try {
			reciveMsg=br.readLine();
			System.out.println(reciveMsg);
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("ClientRecive Thread 예외처리 발생");
				break;
			}
		}
		
	}
	
}
class ClientSendThread extends Thread{
	
	PrintWriter pw;
	BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
	ClientSendThread(PrintWriter pw){
		this.pw = pw;
	}
	public void run() {
		String sendMsg="";
		while(!sendMsg.equals("/exit")) {
			
			try {
				sendMsg = read.readLine();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block1
				
				e.printStackTrace();
			}
			pw.println(sendMsg);
			pw.flush();
			
		}
	}
	
}



