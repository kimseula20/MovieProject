import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadGather {

}

class MovieThread implements Runnable {
	Movie_View m_view;
	BufferedReader br;
	PrintWriter pw;
	String str = "";
	private ClientInfoClass clientinfo;
	private ConnectionPool cp = null;
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private ResultSetMetaData rsmd = null;
	MovieThread moviethread;
	Snack_Shop snack_shop;
	Movie_reserve movie_reserve;

	// ������
	MovieThread(BufferedReader br, PrintWriter pw, Connection con, ConnectionPool cp) {
		m_view = new Movie_View();
		this.br = br;
		this.con = con;
		this.pw = pw;
		this.cp = cp;
		this.clientinfo = new ClientInfoClass(pw);
	}

	void setMovieThread(MovieThread moviethread) {

		this.snack_shop = new Snack_Shop(this.br, this.pw, this.con, this.cp, moviethread, clientinfo);
		this.movie_reserve = new Movie_reserve(br, pw, con, cp, moviethread, clientinfo);
	}

	// �޼��� ������ �޼���
	void sendMsg(String msg) {
		pw.println(msg);
		pw.flush();
	}

	// ������ ��� ���� �޼��� Ȯ�� �޼���
	String recive() {

		try {
			if (str != null)
				str = br.readLine();

		} catch (Exception e) {
			System.out.println("recive �޼��� ����ó�� �߻�");
		}
		return str;

	}

	// String ���� Integer�� ��ȯ ���� ���� Ȯ�� �޼ҵ�
	boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	String getToday() {
		Date day = new Date();
		SimpleDateFormat today = new SimpleDateFormat("yyyy��MM��dd�� HH:mm");
		String todayformat = today.format(day);
		return todayformat;
	}

	// �������� �޾� ���� ResultSet���� ��Ƽ� Result Set���� ��ȯ �ϴ� �޼ҵ�
	ResultSet returnRS(String query) {
		try {
			pstmt = this.con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("returnRS ����ó�� �߻�");
		}
		return rs;
	}

	String returnSeq(String tablename) {
		String seq = "";
		String query = "";
		try {
			if (tablename.equals("snack_sales")) {
				query = "SELECT MAX(snack_sales_code) FROM " + tablename + " ";
			} else if (tablename.equals("Customer")) {
				query = "SELECT MAX(cust_num) FROM " + tablename + " ";
			}else if(tablename.equals("reservation")) {
				query ="SELECT MAX(reser_code) FROM reservation WHERE cust_num='"+clientinfo.getCust_num()+"' ";
			}
			rs = returnRS(query);
			rs.next();
			seq = rs.getString(1);
			System.out.println(seq);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(seq + "  �̰� seq ������.");
		return seq;

	}

	// SQL �÷��� Ÿ���� Ȯ���ϰ� �÷��� �´� Ÿ������ ���� �Ϸ��� �޼ҵ� �޼ҵ� ���� ���� ���� ���� ������ ��.
	void inputType(ResultSet rs) {
		int colsCount = 0;
		int inttmp0;
		String strtmp;
		try {
			// ResultMetaData ���̺��� ������ �˾ƿü��ִ� Ŭ����
			rsmd = rs.getMetaData();
			// ����Ǵ� �������� �÷� ������ �˾� �´�
			colsCount = rsmd.getColumnCount();
			for (int i = 1; i < colsCount; i++) {

				if (rsmd.getColumnTypeName(i).equals("NUMBER")) {
					rs.next();
					inttmp0 = rs.getInt(i);
				} else if (rsmd.getColumnTypeName(i).equals("VARCHAR2")) {
					rs.next();
					strtmp = rs.getString(i);
				} else {
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// ������ ��ȣ�� �Է� �ϸ� clientinfo�� ������ �� �ڵ�� �̸� ����
	// ResultSet�� ������ ��ȣ�� �Ѱ� �ָ� clieninfo�� ���� �ϴ� �޼ҵ�
	// ������������ �ؾ� ��
	void saveKeyValue(ResultSet rs, int num) {
		String key, keyname;
		String value = "";
		int price;
		try {
			rsmd = rs.getMetaData();
			rs.absolute(num);
			key = rs.getString(1);
			keyname = rsmd.getColumnName(1);
			keyname = keyname.toLowerCase();
			value = rs.getString(2);
			if (keyname.equals("movie_code")) {
				clientinfo.setMovie_Code(key);
				clientinfo.setMovie_title(value);
				clientinfo.setMoviePrice(rs.getInt(3));
			} else if (keyname.equals("m_center_code")) {
				clientinfo.setM_center_code(key);
				clientinfo.setM_center_name(value);
			}
			// �󿵰� �ڵ� ����
			else if (keyname.equals("sc_center_code")) {
				clientinfo.setSc_center_code(key);
				clientinfo.setSc_center_name(value);
			} else if (keyname.equals("m_center_code")) {
				clientinfo.setM_center_code(key);
				clientinfo.setM_center_name(value);
			} else if (keyname.equals("m_center_code")) {
				clientinfo.setM_center_code(key);
				clientinfo.setM_center_name(value);
			} else if (keyname.equals("screening_code")) {
				clientinfo.setScreening_code(key);
				clientinfo.setScreening_time(value);
				clientinfo.setSc_center_name(rs.getString(3));
				clientinfo.setSc_center_code(rs.getString(4));
			} else if (keyname.equals("seat_r_code")) {
				clientinfo.setSeat_reserve(key, value);
			} // ���� �ڵ� ����
			else if (keyname.equals("snack_code")) {
				price = rs.getInt(3);
				clientinfo.setSnack_item(key, value, price);
			}

		} catch (SQLException e) {

			e.printStackTrace();
			System.out.println("saveKeyValue ����ó�� �߻�");
		}
	}

	// �ڷΰ��� �ؽ�Ʈ �޼���
	// ���� ����
	void stage_move(int movenum) {
		int stage = clientinfo.getStage();
		int move = stage + movenum;
		if (move >= 0) {
			if (move == 11) {
				movie_reserve.endPoint();
			}
			if (move == 21) {
				movie_reserve.movieshow(true);
			}
			if (move == 31) {
				clientinfo.setMovie_Code(null);
				clientinfo.setMovie_title(null);
				clientinfo.setMoviePrice(0);
				movie_reserve.movieChoice();
			}
			if (move == 32) {
				clientinfo.setM_center_code(null);
				clientinfo.setM_center_name(null);
				movie_reserve.movie_centerChoice();
			}
			if (move == 33) {
				clientinfo.setScreening_time(null);
				clientinfo.setScreening_code(null);
				clientinfo.setTotalPrice(0);
				movie_reserve.daytimeselect();
			}
			if (move == 34) {
				clientinfo.resetSeat();
				clientinfo.setTotalPrice(0);
				movie_reserve.selectSeat();
			}
			if (move == 35) {
				movie_reserve.finalCheck();
			}

		} else {
			pw.println("���� ��ġ���� �̵��ϽǼ� �����ϴ�.");
			System.out.println("������ �� ����.");
		}
	}

	void login() {
		String id = "";
		String password = "";
		try {
				sendMsg("ID�� �Է����ּ���");
				id = br.readLine();
				sendMsg("��й�ȣ�� �Է����ּ���");
				password = br.readLine();
				String loginquery = "SELECT cust_ID, cust_PW,cust_name,cust_num,cust_money FROM Customer_Info NATURAL JOIN customer WHERE cust_id='"
						+ id + "' ";
				rs = returnRS(loginquery);
				if (rs.first()) {
					// if (rs.isLast()) {
					if (rs.getString(2).equals(password)) {
						clientinfo.setCust_id(id);
						clientinfo.setCust_name(rs.getString(3));
						clientinfo.setCust_num(rs.getString(4));
						clientinfo.setCust_money(rs.getInt(5));
						clientinfo.setLogin(true);
						sendMsg("\n\nID: " + id + " (" + rs.getString(3) + ")���� �α����ϼ̽��ϴ�\n");
						sendMsg(" *****Boolsa Movie Club***** �� ���� ���� ȯ���մϴ�\n");
					} else {
						sendMsg("��й�ȣ�� Ʋ�Ƚ��ϴ�");
					}

				} else
					sendMsg("�Է� �Ͻ� ���̵�� ���� ���̵� �Դϴ�.");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("login ��� �޼��� ����ó��");
		}

	}

	/// ȸ�� ���� �޼ҵ� ////////////////
	//// ȸ�������� �ؼ� INSERT INTO customer �� ISNERT INTO customer_info�� ���� �� ����� �ȴٿ�
	/// //////
	/// SQL�� �����Ѱ� �״�� �ٿ� ������ �Ǵ°�..........
	void signUp() {
		String tmp;
		String id = "", pw1 = "", pw2 = "", name = "", phone = "";
		boolean loop0 = true, loop1 = true, loop2 = true, loop3 = true;
		while (loop0) {
			try {
				sendMsg("ID �Է�: ");
				id = br.readLine();
				String query = "SELECT cust_id FROM customer_info WHERE cust_id='" + id + "' ";
				rs = returnRS(query);

				if (!rs.first()) {
					pw.println("�ߺ��� ID�� �����ϴ�. ȸ�������� ��� �������ּ���.");
					loop0 = false;
					break;
				} else
					sendMsg("���̵� �̹� ���� �մϴ�.\n �ٽ� �Է��� �ּ���");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		while (loop1) {
			try {
				sendMsg("password �Է�: ");
				pw1 = br.readLine();
				sendMsg("password Ȯ��: ");
				pw2 = br.readLine();
				if (pw1.equals(pw2)) {
					break;
				}
			} catch (Exception e) {
				try {
					br.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}

		}

		while (loop2) {
			try {
				sendMsg("�̸� �Է�: ");
				name = br.readLine();
				sendMsg("�Է��Ͻ� �̸��� " + name + "�� �½��ϱ�?\n 'Y'/'N' �� �Է��ϼ���");
				tmp = br.readLine();
				if (tmp.equals("y") || tmp.equals("Y")) {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		while (loop3) {
			try {
				sendMsg("phoneNumber �Է�: ");
				phone = br.readLine();
				sendMsg("�Է��Ͻ� ��ȣ��" + phone + "�� �½��ϱ�? ������ y�� �Է����ּ���.");
				String y = br.readLine();
				if (y.equals("y")) {
					break;
				}

				sendMsg(" �Է� �Ͻ� ���� \n I D :" + id + "\n�� ��:" + name + "\n��ȭ��ȣ:" + phone + "\n�Է��Ͻ� ���� �½��ϱ�? 'Y'/'N'");
				tmp = br.readLine();
				if (!tmp.equals("y") || !tmp.equals("Y")) {
					signUp();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String insertQuery = "INSERT INTO Customer VALUES('CN'||lpad(reservation_seq.NEXTVAL,6,0),'" + name + "','"
				+ phone + "',116000) ";
		try {
			pstmt = con.prepareStatement(insertQuery);
			pstmt.executeUpdate();
			con.commit();
//			String query ="SELECT MAX(cust_num) FROM Customer ";
//			rs =returnRS(query);
//			rs.next();
			String cust_num = returnSeq("Customer");
//			String cust_num = rs.getString(1);
			System.out.println(cust_num);
			insertQuery = "INSERT INTO customer_info VALUES('" + id + "','" + pw1 + "','" + cust_num + "' ) ";
			pstmt = con.prepareStatement(insertQuery);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clientinfo.setCust_money(116000);
		sendMsg(name + "����  ȸ�� ������ �Ϸ� �Ǿ����ϴ�. \nID: " + id + " �Դϴ�.");
		sendMsg("���� ���ϱ�  116,000���� ���� �Ǿ����ϴ�.");
		System.out.println(name + "����  ȸ�� ������ �Ϸ� �Ǿ����ϴ�. \nID: " + id + " �Դϴ�.");

	}

	String getrecive(String tmp) {
		return tmp;
	}

	void showInfo() {
		boolean loop = true;
		String tmp;
		int num;
		while (loop) {
			try {
				sendMsg(" 1). ��ȭ ���� ��� \n 2). ���� �̿� ��� \n 3). ��ҳ���");
				tmp = br.readLine();
				if (isInteger(tmp)) {
					num = Integer.parseInt(tmp);
					switch (num) {
					case 1:
						loop = false;
						show_movie_reservelist();
						break;
					case 2:
						loop = false;
						showSnack_List();
						break;
					case 3:
						loop =false;
						cancelList();
						break;

					}
				} else
					sendMsg("�߸� �Է� �ϼ̽��ϴ�.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	void cancelList() {
		String str;
		boolean loop = true;
		try {
		while(loop) {
		sendMsg(" 1). ��ȭ ��ҳ��� \n 2). ���� ��ҳ��� \n 3). �ڷ� ����");	
			str = br.readLine();
			switch(str){
			
			case "1":
				loop = false;
				showCancel_movieList();
				break;
				
			case "2":
				loop = false;
				showCancel_snackList();
				break;
			case "3":
				loop = false;
				showInfo();
				break;
			}
		}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		}
		
	

	// ȯ�Ұ� ���� ���
	void deletedata(String tablename, String target) {
		try {
			String delquery, delquery0,backupquery;
			if (tablename.equals("snack_sales")) {
				
				backupquery ="INSERT INTO  cancel_snack_sales SELECT * FROM snack_sales WHERE snack_sales_code='"+target+"' ";
				pstmt = con.prepareStatement(backupquery);
				pstmt.executeUpdate();
				backupquery ="INSERT INTO  cancel_snack_sales_list SELECT * FROM snack_sales_list WHERE snack_sales_code='"+target+"' ";
				pstmt = con.prepareStatement(backupquery);
				pstmt.executeUpdate();
				
				delquery = "DELETE FROM snack_sales WHERE snack_sales_code='" + target + "' ";
				delquery0 = "DELETE FROM snack_sales_list WHERE snack_sales_code='"+target+"' ";
				pstmt = con.prepareStatement(delquery);
				pstmt.executeUpdate();
				pstmt = con.prepareStatement(delquery0);
				pstmt.executeUpdate();
				con.commit();
				
			}
			if (tablename.equals("reservation")) {
				System.out.println(" d���� Ȯ��");
				backupquery = "INSERT INTO  cancel_reser SELECT * FROM reservation WHERE reser_code='"+target+"' "; 
				pstmt = con.prepareStatement(backupquery);
				pstmt.executeUpdate();
				delquery = "DELETE FROM reservation WHERE reser_code='" + target + "' ";
				pstmt = con.prepareStatement(delquery);
				pstmt.executeUpdate();
				con.commit();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	void recovery_seatcode(String reser_code) {
		try {
			
		String query ="SELECT seat_r_code FROM seat_reser_list WHERE reser_code='"+reser_code.toUpperCase()+"' ";
		rs = returnRS(query);
		while(rs.next()) {
			System.out.println(rs.getString(1)+ "seat_r_code");
			String updatequery ="UPDATE seat_reserve SET seat_r_empty = 1  WHERE seat_r_code ='"+rs.getString(1)+"' ";	
			pstmt = con.prepareStatement(updatequery);	
			pstmt.executeUpdate();
			con.commit();
		}
		sendMsg("�¼��� �ǵ��Ƚ��ϴ�.");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void updateMoney(int money) {
		try {
		//String query ="UPDATE "
		String cust_num = clientinfo.getCust_num();
		String updateQuery ="UPDATE customer set cust_money =cust_money+"+money+" WHERE cust_num='"+cust_num+"' ";
		pstmt = con.prepareStatement(updateQuery);
		pstmt.executeUpdate();
		con.commit();
		sendMsg(money+"����  ������ �Ϸ� �Ǿ����ϴ�. ");
		String query ="SELECT cust_money FROM customer WHERE cust_num ='"+cust_num+"' ";
		rs = returnRS(query);
		rs.first();
		clientinfo.setCust_money(rs.getInt(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void show_movie_reservelist() {
		String str = "", query, cust_num;
		int count = 0;
		boolean loop = true;
		cust_num = clientinfo.getCust_num();
		query = "SELECT RESER_CODE,MOVIE_TITLE,SCREENING_TIME,M_CENTER_NAME,SC_CENTER_NAME,PRICE FROM cust_movie_list WHERE CUST_NUM='"
				+ cust_num + "' ";
		rs = returnRS(query);
		sendMsg("-------------------------------------------------");
		try {
			while (rs.next()) {
				count++;
				sendMsg("| " + count + ")  ���� ��ȣ: " + rs.getString(1) + " ��¥ :" + rs.getString(3));
			}
			sendMsg("------------------------------------------------");
			rs = returnRS(query);
		
				sendMsg("Ȯ���Ͻ� ���� ��ȣ�� ������ �ּ���");
				str = br.readLine();
				if (isInteger(str)) {
					int num = Integer.parseInt(str);
					rs.absolute(num);
					String reser_code = rs.getString("RESER_CODE");
					sendMsg("\n------------------------------------------------------------------------------------------\n|");
					sendMsg("| ���Ź�ȣ:" + rs.getString("RESER_CODE") + " ��ȭ����: " + rs.getString("MOVIE_TITLE") + "   "
							+ "���� : " + rs.getString("M_CENTER_NAME") + "  �󿵰�: " + rs.getString("SC_CENTER_NAME")
							+ "  ���� �ݾ�: " + rs.getInt("PRICE"));
					sendMsg("|\n-----------------------------------------------------------------------------------------\n");
					
					sendMsg("��� �Ͻ÷��� ' cancel'  �ƴϸ� '�ƹ�Ű'�� �������� ");
					str = br.readLine();
					if(str.equals("cancel"))
					{	
						deletedata("reservation",rs.getString("RESER_CODE"));
						updateMoney(rs.getInt("PRICE"));
						recovery_seatcode(reser_code.toUpperCase());
						sendMsg(" ��Ұ� �Ϸ� �Ǿ����ϴ�. \n");
						
					}
					
				} else
					sendMsg("�߸� �Է� �ϼ̽��ϴ�. ���ڸ� ��ȣ�� �Է��� �ּ���");
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void showSnack_List() {
		try { // snack_name,snack_price,snack_count,m_center_name,sales_totalprice
			// ���� ��� ���� �ֱ�
			int count = 0, totalprice=0;
			boolean loop = true;
			String tmp, sales_code="";
			String id = clientinfo.getCust_id();
			System.out.println(id + "id Ȯ������");
			String query = "SELECT DISTINCT snack_sales_code, sales_totalprice FROM sn_purchase_list WHERE cust_id='"
					+ id + "' ";
			rs = returnRS(query);
			sendMsg("\n--------------------------------------------------------------\n|");
			while (rs.next()) {
				count++;
				sendMsg("|" + count + ")  �ŷ�����: " + rs.getString(1));
			}
			sendMsg("\n--------------------------------------------------------------\n|");
			sendMsg("Ȯ���Ͻ� �ŷ� ��ȣ�� ���� �ϼ���");
			tmp = br.readLine();
			rs = returnRS(query);
			sendMsg("\n---------------------------------------------------------------------------\n|");
			if (isInteger(tmp)) {
				int num = Integer.parseInt(tmp);
				rs.absolute(num);
				sales_code = rs.getString(1);
				totalprice = rs.getInt(2);
				query = "SELECT snack_name, snack_price, snack_count,m_center_name FROM sn_purchase_list WHERE snack_sales_code='"
						+ sales_code + "' ";
				rs = returnRS(query);
				sendMsg("| ID: " + clientinfo.getCust_id() + "      �̸�:" + clientinfo.getCust_name() + "\n|");
				sendMsg("| �ŷ���ȣ" + sales_code + "                                 �ѱݾ�:" + totalprice + "\n|");
				while (rs.next()) {
					tmp = String.format("%-50s\t", rs.getString(1));
					sendMsg("| ǰ�� : " + tmp + "���� :" + rs.getInt(2) + "  ���� :" + rs.getString(3) + "  �Ѿ� :"
							+ rs.getInt(3) * rs.getInt(2));
				}
			}
			sendMsg("|\n---------------------------------------------------------------------------\n");
			sendMsg(" ���� �Ͻ÷��� 'cancel'   �ƴϸ� '�ƹ�Ű'�� ��������");
			tmp = br.readLine();
			if(tmp.equals("cancel")) {
				deletedata("snack_sales",sales_code);
				updateMoney(totalprice);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	void showCancel_snackList() {
		String cust_id =clientinfo.getCust_id();
		String query,str,snack_sales_code;
		boolean loop = true;
		query = "SELECT * FROM CANCEL_SNACK_SALES WHERE cust_id ='"+cust_id+"' ";
		try {
		rs = returnRS(query);
		
			while(rs.next()) {
				sendMsg("���� ��ȣ: "+rs.getString(1)+"   ���� �ݾ�"+rs.getInt(4));
			}
			while(loop) {
			sendMsg(" Ȯ���Ͻ� ��ȣ�� �Է��� �ּ���");
		    str = br.readLine();
		   if(isInteger(str)) {
			   int num = Integer.parseInt(str);
			   loop = false;
			   rs.absolute(num);
			   snack_sales_code = rs.getString(1);
			   sendMsg(" ���� ��ȣ :"+rs.getString(1)+"  �ѱݾ�"+rs.getInt(4)+"   ��¥"+rs.getString(5));
			   query = "SELECT * FROM CANCEL_SNACK_SALES_LIST WHERE snack_sales_code ='"+snack_sales_code+"' ";
			   rs = returnRS(query);
			   while(rs.next()) {
				   sendMsg(" ��ǰ :"+rs.getString(3)+"  �ݾ�:"+rs.getInt(5)+"  ����:"+rs.getInt(4));
			   }
		   }
		   else
			   sendMsg(" �߸� �Է� �ϼ̽��ϴ�. �ٽ� �Է��� �ּ���");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	void showCancel_movieList() {
		String cust_num = clientinfo.getCust_num();
		String query;
		query = "SELECT * FROM cancel_reser  WHERE cust_num='"+cust_num+"'  ";
		try {
		rs =returnRS(query);
			while(rs.next()) {
			sendMsg(" ���Ź�ȣ: "+rs.getString(1)+"  �ο��� "+rs.getInt(4)+" �¼� :"+rs.getString(5)+" ���� �ݾ�:"+rs.getInt(6)+"��");	
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	////// �������� ���� �޼ҵ� ///////////////////////////////
	///// ���� 5���� clientinfo�� ���� ������ ������ ���� �ϴ� �޼ҵ���.///////
	public void run() {
		sendMsg(m_view.boolsa());
		String msg = "";
		int choice = 0;
		boolean loop = true;
		while (loop) {
			clientinfo.setStage(11);
				if(clientinfo.getLogin()) {
					sendMsg(" ID: "+clientinfo.getCust_id()+   "\t�̸�:"+clientinfo.getCust_name()+"\n\t\t�ܾ� : "+clientinfo.getCust_money()+"��\n");
				}
			
			try {
				sendMsg(m_view._2_1_FirstView());
				msg = br.readLine();
				if(isInteger(msg)) {
						choice = Integer.parseInt(msg);				
						switch (choice) {
						case 1:
							if(clientinfo.getLogin()) { movie_reserve.movieschoiceinfo(); }
							else sendMsg("\n�� ����� ��� �Ͻ÷��� �α����� �ʿ��մϴ�.\n");
							break;
						case 2:
							// ��ȭ ����
							if(clientinfo.getLogin()) movie_reserve.movieChoice();
							else sendMsg("\n�� ����� ��� �Ͻ÷��� �α����� �ʿ��մϴ�.\n");
							break;
						case 3:
							// ���� ���� ���
							if(clientinfo.getLogin())  showInfo();
							else sendMsg("\n�� ����� ��� �Ͻ÷��� �α����� �ʿ��մϴ�.\n");
							break;
						case 4:
							if(clientinfo.getLogin()) snack_shop.snack_shopping();
							// ���� �̿�
							else sendMsg("\n�� ����� ��� �Ͻ÷��� �α����� �ʿ��մϴ�.\n");
							break;							
						case 5:
							sendMsg("�α����� ���� �մϴ�\n");
							login();
							break;
						case 6:
							sendMsg("ȸ�������� ���� �ϼ̽��ϴ�.\n");
							signUp();
							break;
						case 7:
							sendMsg("�α� �ƿ� �Ǿ����ϴ�.");
							clientinfo.setLogin(false);
							clientinfo.allreset();
							clientinfo.resetSnack();
							run();
							break;
						case 8:
							sendMsg(" �ý����� ���� �մϴ�. \n �̿��� �ּż� �����մϴ�.");
							loop = false;
							break;
						}
				}	
				


			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Recive ����ó�� �߻�");
				return;
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						cp.releaseConnection(con);
				} catch (Exception e) {

				}
			}

		}

	}
	void adminSett() {
		int count =0;
		String query ="SELECT screening_code ,movie_code,movie_kind.MOVIE_TITLE,sc_center_name,screening_time "
				+ "FROM screening NATURAL JOIN movie_kind NATURAL JOIN screen_center "
				+ "WHERE screening_code NOT IN (SELECT screening_code FROM seat_reserve) ";
		rs = returnRS(query);
		try {
			sendMsg("�¼��� ������ ���� ���� �ϼ���");
			while(rs.next()) {
				count++;
				sendMsg(count+"). "+"��ȭ : "+rs.getString(3)+" ��  "+rs.getString(1)+"��ũ���ڵ�"+rs.getString(2)+" ���ڵ�");
			}
			String num = br.readLine();
			int tmp = Integer.parseInt(num);
			rs.absolute(tmp);
			String sc_code = rs.getString(1);
			String movie_Code = rs.getString(2);
			char alpha = 'a';
			int tmpchar =65;
			for(int i= 1; i <=5; i++) {
				for(int j = 1;j<=5;j++) {
					alpha = (char)tmpchar;
					query = "INSERT INTO seat VALUES ('S03S0"+num+"','"+alpha+"-0"+j+"',1,'SC009')";
					System.out.println(query);
					pstmt = con.prepareStatement(query);
					pstmt.executeUpdate();
				}
				tmpchar++;
			}	
			con.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e) {
			
		}
		
		
		
	}
}
