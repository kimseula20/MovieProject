

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//커넥션 풀을 사용해서 오라클에 쿼리를 전송한다.
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
			System.out.println(cp.getNumCons()+"  <<<< 여기 인원수");
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			count++;
			System.out.println("--------------------------------------");
			System.out.println(con + "  현재 접속 HashCode \n     이름 : "+name);
			
			rs.next();
			for(int i=0; i<3;i++) {
				System.out.print(rs.getString(1)+" | ");
				System.out.print(rs.getString(2)+" | ");
				System.out.print(rs.getString(3)+" | ");
				System.out.print(rs.getString(4)+" | ");			
				System.out.println();
				System.out.println(name+" <<<<<  "+count+" 번째 실행 중");
				System.out.println(name+" : 대기 상태 돌입.");
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

		//Singleton 패턴으로 만드는 클래스는
		//생성자가 private이므로 외부에서 객체를 생성할 수 없다.
		//not visible			
	//	cp = new ConnectionPool("","","",0,0);
			querystart();
			System.out.println("Thread "+name+" 번  종료!");
		
		

		
		cp.closeAll();
	}
	}
