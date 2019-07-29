

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//Ŀ�ؼ� Ǯ�� ����ؼ� ����Ŭ�� ������ �����Ѵ�.
public class ConnectionPoolEx extends Thread {
	
	String query;
	ConnectionPool cp =null;
	Connection con =null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String url ="jdbc:oracle:thin:@localhost:1521:xe";
	String user = "bitcamp";
	String password ="bitcamp";
	int initCons=2;
	int maxCons =10;
	String name;
	int count =0;

	ConnectionPoolEx(String query,String name,ConnectionPool cp){
		this.query = query;
		this.name = name;
		this.cp = cp;
	}
	
	void querystart() {
		try {
			
			con = cp.getConnection();
			System.out.println(cp.getNumCons()+"  <<<< ���� �ο���");
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			count++;
			System.out.println("--------------------------------------");
			System.out.println(con + "  ���� ���� HashCode \n     �̸� : "+name);
			
			rs.next();
			for(int i=0; i<3;i++) {
				System.out.print(rs.getString(1)+" | ");
				System.out.print(rs.getString(2)+" | ");
				System.out.print(rs.getString(3)+" | ");
				System.out.print(rs.getString(4)+" | ");			
				System.out.println();
				System.out.println(name+" <<<<<  "+count+" ��° ���� ��");
				System.out.println(name+" : ��� ���� ����.");
				Thread.sleep(5000);
					
			}
			System.out.println("--------------------------------------");
			Thread.sleep(10000);
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt !=null) pstmt.close();
				if(con!=null)cp.releaseConnection(con);
			}catch(Exception e) {
				e.printStackTrace();
			}	
	}
	}
	public void run() {	

		//Singleton �������� ����� Ŭ������
		//�����ڰ� private�̹Ƿ� �ܺο��� ��ü�� ������ �� ����.
		//not visible			
	//	cp = new ConnectionPool("","","",0,0);
			querystart();
			System.out.println("Thread "+name+" ��  ����!");
		
		

		
		cp.closeAll();
	}
	}
