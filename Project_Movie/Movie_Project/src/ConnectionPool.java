

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 *  Singleton ����
 *  �����ͺ��̽� ���� ó�� �Ͽ������� �����Ǿ�� �� ������ 
 *  ���������� ���� ��ü�� ���鵵�� ������� �ʰ� �� 1���� ��ü�� ���鵵�� �����ϰ�
 *  �� 1���� ��ü�θ� ����� ���� ���� �ϴ� ���α׷� ����̴�.
 * 	1) static���� Ŭ���� ������ �����Ѵ�
 * 	2) �����ڸ� private���� �����Ѵ�
 *  3) �Ϲ������� Ŭ���� ��ü�� �����ϴ� getInstance()  �޼��带 �����.
 *  4) getInstance() �޼��忡���� ���ʷ� 1���� ��ü�� �����ϵ��� �Ѵ�.
 * 	5) �ܺ�Ŭ�������� �� Ŭ���� ��ü�� ��û�� ���� �ݵ�� getInstance()
 * 		 �޼��常�� ����ؼ� ��ü�� ���� �� �ִ�.
 *  6) �ƹ��� ���� �����忡�� ��û�� �ص� ��ȯ�ϴ� ��ü�� �����ϰ� ������ ��ü�̴�.
 */

public class ConnectionPool {
//	static {
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
		//�̱��� ���� �ܺ� �� �� �� �� �� �� �� 
		// ���� �� �� �� �� �� ü �� ����� ����.
		ArrayList<Connection> free;// ��������� ���� Connection ��ü
		ArrayList<Connection> used;// ������� Connection ��ü
		private String url,user,password;
		private int initialCons, maxCons, numCons; 	//�ʱ� Ŀ�ؼ� ��, �ִ� Ŀ�ؼ� ��, ���� Ŀ�ؼ� ��
		static int count =0;
		static ConnectionPool cp =null; //singleton ���� ����
		
		public  static ConnectionPool getInstance(			
				String url,String user,String password,
				int initCons,int maxCons) {
			try {
				if(cp==null) {
				// ConnectionPool Ŭ���� ��ũ�γ����� ���� ���� �Ұ� �Ѵ�.
				// �̷��� �Ǹ� cp �� ó���� ����� ������ cp�� null�� �ƴϰ� �ȴ� 
				// �׷� �ٷ� ������ ����Ǽ� ��� ���� cp�� ���� �ϰ� �Ǵ°�
				// �� �޼���� ���� �� �ϳ��� ��ü�� ����� �ִ�.
				// static �޼��忡�� ����ȭ �ϴ� ���
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
					this.initialCons = initCons; //���ʿ� Connection�� � ������
					this.maxCons =maxCons; //�ִ�� ����� �������
					ConnectionPool.count++;
					if(initialCons <0 )//-1 ���Ϸ� �ָ� �˾Ƽ� ���� �ض� 5����
						this.initialCons =1;
					if(maxCons<5) {
						this.maxCons =10;
						System.out.println("�����ο� �ִ� �ο��� 10���� �þ� ���ϴ�.");
					}
				
					
					System.out.println("-----------------------------");
					System.out.println(" ���� ���� �ο� �� "+count);
					
					//�ʱ� Ŀ�ؼ� ������ ������ ArrayList�� ������ �� �ֵ���
					// �ʱ� Ŀ�ؼ� ������ŭ ArrayList�� �����Ѵ�.
					free = new ArrayList<Connection>(this.initialCons);
					used = new ArrayList<Connection>();
					// �ʱ� Ŀ�ؼ� ������ŭ Connection ��ü�� ��������
					while(this.numCons < this.initialCons) {
						addConnection();// 1���� ��ü�� �߰��ϴ� �޼���
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
			
			//�ܺο��� ����Ŭ ���� ��ü�� �䱸�ϴ� �޼���
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
	 ������� ����Ŭ ���� ��ü�� �ݳ��ϴ� �޼���
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
			throw new SQLException ("ConnectionPool: �� ���� �ʽ��ϴ�.");
		}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			
		}
	}		
	//��� Connection�� �����Ѵ�
	//���� ����� ConncetionPool�� �׸� ����Ѵ�.
	public void closeAll() {
		//used�� �ִ� Ŀ�ؼ��� ��� �����ϰ� �ݴ´�.
		for(int i =0; i<used.size();i++) {
			Connection _con=(Connection)used.get(i);
			try{
				used.remove(i--);//
				_con.close(); //����
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		//free�� �ִ� Ŀ�ؼ��� ��� �����ϰ� �ݴ´�.
		for(int i =0; i<free.size();i++) {
			Connection _con=(Connection)free.get(i);
			try{
				free.remove(i--);//
				_con.close(); //����
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//�ִ� ���� ����
	public int getMaxCons() {
		return this.maxCons;
	}
	//���� �Ҵ� ����� ����
	public int getNumCons() {
		return this.used.size();
	}
	
}
		
		

