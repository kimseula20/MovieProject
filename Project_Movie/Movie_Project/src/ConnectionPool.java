

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 *  Singleton 패턴
 *  데이터베이스 접근 처럼 일원적으로 관리되어야 할 업무는 
 *  여러군데서 접근 객체를 만들도록 허용하지 않고 단 1개의 객체만 만들도록 강요하고
 *  이 1개의 객체로만 사용할 것을 강요 하는 프로그램 방식이다.
 * 	1) static으로 클래스 변수를 선언한다
 * 	2) 생성자를 private으로 선언한다
 *  3) 일반적으로 클래스 객체를 리턴하는 getInstance()  메서드를 만든다.
 *  4) getInstance() 메서드에서는 최초로 1번만 객체를 생성하도록 한다.
 * 	5) 외부클래스에서 이 클래스 객체를 요청할 때는 반드시 getInstance()
 * 		 메서드만을 사용해서 객체를 얻을 수 있다.
 *  6) 아무리 많은 스레드에서 요청을 해도 반환하는 객체는 유일하고 동일한 객체이다.
 */

public class ConnectionPool {
//	static {
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
		//싱글톤 절대 외부 에 서 건 들 수 없 다 
		// 절대 외 부 에 서 객 체 를 만들수 없다.
		ArrayList<Connection> free;// 사용중이지 않은 Connection 객체
		ArrayList<Connection> used;// 사용중인 Connection 객체
		private String url,user,password;
		private int initialCons, maxCons, numCons; 	//초기 커넥션 수, 최대 커넥션 수, 현재 커넥션 수
		static int count =0;
		static ConnectionPool cp =null; //singleton 패턴 사용시
		
		public  static ConnectionPool getInstance(			
				String url,String user,String password,
				int initCons,int maxCons) {
			try {
				if(cp==null) {
				// ConnectionPool 클래스 싱크로나이즈 동시 접근 불가 한다.
				// 이렇게 되면 cp 가 처음에 만들어 졌으면 cp는 null이 아니게 된다 
				// 그럼 바로 리턴이 실행되서 계속 같은 cp만 리턴 하게 되는것
				// 이 메서드는 절대 딱 하나의 객체만 만들수 있다.
				// static 메서드에서 동기화 하는 방법
				synchronized (ConnectionPool.class) {
					
					cp = new ConnectionPool(url,user,password,initCons,maxCons);
					
				}
				}
			
			}catch(SQLException e) {
				System.out.println(e);
			}
			return cp;
		}
		
		private ConnectionPool(String url,String user,String password,
				int initCons,int maxCons) 
				throws SQLException {
					
					this.url =url;
					this.user = user;
					this.password = password;
					this.initialCons = initCons; //최초에 Connection을 몇개 만들지
					this.maxCons =maxCons; //최대로 몇개까지 만들건지
					ConnectionPool.count++;
					if(initialCons <0 )//-1 이하로 주면 알아서 결정 해라 5개로
						this.initialCons =1;
					if(maxCons<5) {
						this.maxCons =10;
						System.out.println("입장인원 최대 인원이 10으로 늘어 남니다.");
					}
				
					
					System.out.println("-----------------------------");
					System.out.println(" 현재 접속 인원 수 "+count);
					
					//초기 커넥션 개수를 각각의 ArrayList에 저장할 수 있도록
					// 초기 커넥션 개수만큼 ArrayList를 생성한다.
					free = new ArrayList<Connection>(this.initialCons);
					used = new ArrayList<Connection>();
					// 초기 커넥션 개수만큼 Connection 객체를 생성하자
					while(this.numCons < this.initialCons) {
						addConnection();// 1개씩 객체를 추가하는 메서드
					}

					
			}
			private void addConnection() throws SQLException{
				free.add(getNewConnection());
			}
			private Connection getNewConnection() throws SQLException {
				Connection con = null;
				
				con = DriverManager.getConnection(
						this.url,this.user,this.password);
				System.out.println("About to connect to "+con);
				this.numCons++;
				return con;
			}
			
			//외부에서 오라클 연결 객체를 요구하는 메서드
			public synchronized Connection getConnection() throws SQLException{
				if(free.isEmpty()) {
					while(this.numCons < maxCons) {
						addConnection();
					}			
				}
				Connection _con = free.get(free.size()-1);
				free.remove(_con);
				used.add(_con);
				System.out.println("----------------"+used.size());
				return _con;
			}
	/*
	 사용중인 오라클 연결 객체를 반납하는 메서드
	 used -> free
	 
	 */
	public synchronized void releaseConnection(
			Connection _con) {
		try {
		boolean flag = false;
		if(used.contains(_con)) {
			used.remove(_con);
			free.add(_con);
		}else {
			throw new SQLException ("ConnectionPool: 에 있지 않습니다.");
		}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			
		}
	}		
	//모든 Connection을 종료한다
	//서비스 종료시 ConncetionPool을 그만 사용한다.
	public void closeAll() {
		//used에 있는 커넥션을 모두 삭제하고 닫는다.
		for(int i =0; i<used.size();i++) {
			Connection _con=(Connection)used.get(i);
			try{
				used.remove(i--);//
				_con.close(); //종료
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		//free에 있는 커넥션을 모두 삭제하고 닫는다.
		for(int i =0; i<free.size();i++) {
			Connection _con=(Connection)free.get(i);
			try{
				free.remove(i--);//
				_con.close(); //종료
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//최대 연결 개수
	public int getMaxCons() {
		return this.maxCons;
	}
	//현재 할당 연결된 개수
	public int getNumCons() {
		return this.used.size();
	}
	
}
		
		

