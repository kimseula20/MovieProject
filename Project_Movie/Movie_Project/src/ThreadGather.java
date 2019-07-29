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

	// 생성자
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

	// 메세지 보내는 메서드
	void sendMsg(String msg) {
		pw.println(msg);
		pw.flush();
	}

	// 서버로 들어 오는 메세지 확인 메서드
	String recive() {

		try {
			if (str != null)
				str = br.readLine();

		} catch (Exception e) {
			System.out.println("recive 메서드 예외처리 발생");
		}
		return str;

	}

	// String 값을 Integer로 변환 가능 여부 확인 메소드
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
		SimpleDateFormat today = new SimpleDateFormat("yyyy년MM월dd일 HH:mm");
		String todayformat = today.format(day);
		return todayformat;
	}

	// 쿼리문을 받아 오면 ResultSet으로 담아서 Result Set으로 반환 하는 메소드
	ResultSet returnRS(String query) {
		try {
			pstmt = this.con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("returnRS 예외처리 발생");
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
		System.out.println(seq + "  이건 seq 리턴임.");
		return seq;

	}

	// SQL 컬럼의 타입을 확인하고 컬럼에 맞는 타입으로 저장 하려는 메소드 메소드 현재 쓰지 않을 예정 구현만 함.
	void inputType(ResultSet rs) {
		int colsCount = 0;
		int inttmp0;
		String strtmp;
		try {
			// ResultMetaData 테이블의 정보를 알아올수있는 클래스
			rsmd = rs.getMetaData();
			// 실행되는 쿼리문의 컬럼 개수를 알아 온다
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

	// 선택한 번호를 입력 하면 clientinfo에 선택한 값 코드랑 이름 저장
	// ResultSet과 선택한 번호를 넘겨 주면 clieninfo에 저장 하는 메소드
	// 매점쪽은구현 해야 함
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
			// 상영관 코드 저장
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
			} // 스낵 코드 저장
			else if (keyname.equals("snack_code")) {
				price = rs.getInt(3);
				clientinfo.setSnack_item(key, value, price);
			}

		} catch (SQLException e) {

			e.printStackTrace();
			System.out.println("saveKeyValue 예외처리 발생");
		}
	}

	// 뒤로가기 넥스트 메서드
	// 구현 예정
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
			pw.println("현재 위치에서 이동하실수 없습니다.");
			System.out.println("움직일 수 없다.");
		}
	}

	void login() {
		String id = "";
		String password = "";
		try {
				sendMsg("ID를 입력해주세요");
				id = br.readLine();
				sendMsg("비밀번호를 입력해주세요");
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
						sendMsg("\n\nID: " + id + " (" + rs.getString(3) + ")님이 로그인하셨습니다\n");
						sendMsg(" *****Boolsa Movie Club***** 에 오신 것을 환영합니다\n");
					} else {
						sendMsg("비밀번호가 틀렸습니다");
					}

				} else
					sendMsg("입력 하신 아이디는 없는 아이디 입니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("login 어디 메서드 예외처리");
		}

	}

	/// 회원 가입 메소드 ////////////////
	//// 회원가입을 해서 INSERT INTO customer 과 ISNERT INTO customer_info에 저장 걸 만들면 된다용
	/// //////
	/// SQL로 연습한걸 그대로 붙여 놓으면 되는것..........
	void signUp() {
		String tmp;
		String id = "", pw1 = "", pw2 = "", name = "", phone = "";
		boolean loop0 = true, loop1 = true, loop2 = true, loop3 = true;
		while (loop0) {
			try {
				sendMsg("ID 입력: ");
				id = br.readLine();
				String query = "SELECT cust_id FROM customer_info WHERE cust_id='" + id + "' ";
				rs = returnRS(query);

				if (!rs.first()) {
					pw.println("중복된 ID가 없습니다. 회원가입을 계속 진행해주세요.");
					loop0 = false;
					break;
				} else
					sendMsg("아이디가 이미 존재 합니다.\n 다시 입력해 주세요");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		while (loop1) {
			try {
				sendMsg("password 입력: ");
				pw1 = br.readLine();
				sendMsg("password 확인: ");
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
				sendMsg("이름 입력: ");
				name = br.readLine();
				sendMsg("입력하신 이름이 " + name + "이 맞습니까?\n 'Y'/'N' 을 입력하세요");
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
				sendMsg("phoneNumber 입력: ");
				phone = br.readLine();
				sendMsg("입력하신 번호가" + phone + "가 맞습니까? 맞으면 y를 입력해주세요.");
				String y = br.readLine();
				if (y.equals("y")) {
					break;
				}

				sendMsg(" 입력 하신 정보 \n I D :" + id + "\n이 름:" + name + "\n전화번호:" + phone + "\n입력하신 정보 맞습니까? 'Y'/'N'");
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
		sendMsg(name + "님의  회원 가입이 완료 되었습니다. \nID: " + id + " 입니다.");
		sendMsg("가입 축하금  116,000원이 지급 되었습니다.");
		System.out.println(name + "님의  회원 가입이 완료 되었습니다. \nID: " + id + " 입니다.");

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
				sendMsg(" 1). 영화 예매 목록 \n 2). 매점 이용 목록 \n 3). 취소내역");
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
					sendMsg("잘못 입력 하셨습니다.");
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
		sendMsg(" 1). 영화 취소내역 \n 2). 매점 취소내역 \n 3). 뒤로 가기");	
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
		
	

	// 환불과 삭제 기능
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
				System.out.println(" d여기 확인");
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
		sendMsg("좌석을 되돌렸습니다.");

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
		sendMsg(money+"원이  결제가 완료 되었습니다. ");
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
				sendMsg("| " + count + ")  예매 번호: " + rs.getString(1) + " 날짜 :" + rs.getString(3));
			}
			sendMsg("------------------------------------------------");
			rs = returnRS(query);
		
				sendMsg("확인하실 예매 번호를 선택해 주세요");
				str = br.readLine();
				if (isInteger(str)) {
					int num = Integer.parseInt(str);
					rs.absolute(num);
					String reser_code = rs.getString("RESER_CODE");
					sendMsg("\n------------------------------------------------------------------------------------------\n|");
					sendMsg("| 예매번호:" + rs.getString("RESER_CODE") + " 영화제목: " + rs.getString("MOVIE_TITLE") + "   "
							+ "지점 : " + rs.getString("M_CENTER_NAME") + "  상영관: " + rs.getString("SC_CENTER_NAME")
							+ "  결제 금액: " + rs.getInt("PRICE"));
					sendMsg("|\n-----------------------------------------------------------------------------------------\n");
					
					sendMsg("취소 하시려면 ' cancel'  아니면 '아무키'나 누르세요 ");
					str = br.readLine();
					if(str.equals("cancel"))
					{	
						deletedata("reservation",rs.getString("RESER_CODE"));
						updateMoney(rs.getInt("PRICE"));
						recovery_seatcode(reser_code.toUpperCase());
						sendMsg(" 취소가 완료 되었습니다. \n");
						
					}
					
				} else
					sendMsg("잘못 입력 하셨습니다. 숫자를 번호를 입력해 주세요");
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void showSnack_List() {
		try { // snack_name,snack_price,snack_count,m_center_name,sales_totalprice
			// 구매 목록 보여 주기
			int count = 0, totalprice=0;
			boolean loop = true;
			String tmp, sales_code="";
			String id = clientinfo.getCust_id();
			System.out.println(id + "id 확인인인");
			String query = "SELECT DISTINCT snack_sales_code, sales_totalprice FROM sn_purchase_list WHERE cust_id='"
					+ id + "' ";
			rs = returnRS(query);
			sendMsg("\n--------------------------------------------------------------\n|");
			while (rs.next()) {
				count++;
				sendMsg("|" + count + ")  거래내역: " + rs.getString(1));
			}
			sendMsg("\n--------------------------------------------------------------\n|");
			sendMsg("확인하실 거래 번호를 선택 하세요");
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
				sendMsg("| ID: " + clientinfo.getCust_id() + "      이름:" + clientinfo.getCust_name() + "\n|");
				sendMsg("| 거래번호" + sales_code + "                                 총금액:" + totalprice + "\n|");
				while (rs.next()) {
					tmp = String.format("%-50s\t", rs.getString(1));
					sendMsg("| 품목 : " + tmp + "가격 :" + rs.getInt(2) + "  수량 :" + rs.getString(3) + "  총액 :"
							+ rs.getInt(3) * rs.getInt(2));
				}
			}
			sendMsg("|\n---------------------------------------------------------------------------\n");
			sendMsg(" 삭제 하시려면 'cancel'   아니면 '아무키'나 누르세요");
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
				sendMsg("예매 번호: "+rs.getString(1)+"   결제 금액"+rs.getInt(4));
			}
			while(loop) {
			sendMsg(" 확인하실 번호를 입력해 주세요");
		    str = br.readLine();
		   if(isInteger(str)) {
			   int num = Integer.parseInt(str);
			   loop = false;
			   rs.absolute(num);
			   snack_sales_code = rs.getString(1);
			   sendMsg(" 예매 번호 :"+rs.getString(1)+"  총금액"+rs.getInt(4)+"   날짜"+rs.getString(5));
			   query = "SELECT * FROM CANCEL_SNACK_SALES_LIST WHERE snack_sales_code ='"+snack_sales_code+"' ";
			   rs = returnRS(query);
			   while(rs.next()) {
				   sendMsg(" 물품 :"+rs.getString(3)+"  금액:"+rs.getInt(5)+"  개수:"+rs.getInt(4));
			   }
		   }
		   else
			   sendMsg(" 잘못 입력 하셨습니다. 다시 입력해 주세요");
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
			sendMsg(" 예매번호: "+rs.getString(1)+"  인원수 "+rs.getInt(4)+" 좌석 :"+rs.getString(5)+" 결제 금액:"+rs.getInt(6)+"원");	
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	////// 쓰레드의 메인 메소드 ///////////////////////////////
	///// 현재 5번은 clientinfo에 내가 선택한 값들을 리셋 하는 메소드임.///////
	public void run() {
		sendMsg(m_view.boolsa());
		String msg = "";
		int choice = 0;
		boolean loop = true;
		while (loop) {
			clientinfo.setStage(11);
				if(clientinfo.getLogin()) {
					sendMsg(" ID: "+clientinfo.getCust_id()+   "\t이름:"+clientinfo.getCust_name()+"\n\t\t잔액 : "+clientinfo.getCust_money()+"원\n");
				}
			
			try {
				sendMsg(m_view._2_1_FirstView());
				msg = br.readLine();
				if(isInteger(msg)) {
						choice = Integer.parseInt(msg);				
						switch (choice) {
						case 1:
							if(clientinfo.getLogin()) { movie_reserve.movieschoiceinfo(); }
							else sendMsg("\n이 기능을 사용 하시려면 로그인이 필요합니다.\n");
							break;
						case 2:
							// 영화 예매
							if(clientinfo.getLogin()) movie_reserve.movieChoice();
							else sendMsg("\n이 기능을 사용 하시려면 로그인이 필요합니다.\n");
							break;
						case 3:
							// 예매 내역 취소
							if(clientinfo.getLogin())  showInfo();
							else sendMsg("\n이 기능을 사용 하시려면 로그인이 필요합니다.\n");
							break;
						case 4:
							if(clientinfo.getLogin()) snack_shop.snack_shopping();
							// 매점 이용
							else sendMsg("\n이 기능을 사용 하시려면 로그인이 필요합니다.\n");
							break;							
						case 5:
							sendMsg("로그인을 시작 합니다\n");
							login();
							break;
						case 6:
							sendMsg("회원가입을 선택 하셨습니다.\n");
							signUp();
							break;
						case 7:
							sendMsg("로그 아웃 되었습니다.");
							clientinfo.setLogin(false);
							clientinfo.allreset();
							clientinfo.resetSnack();
							run();
							break;
						case 8:
							sendMsg(" 시스템을 종료 합니다. \n 이용해 주셔서 감사합니다.");
							loop = false;
							break;
						}
				}	
				


			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Recive 예외처리 발생");
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
			sendMsg("좌석을 생성할 관을 선택 하세요");
			while(rs.next()) {
				count++;
				sendMsg(count+"). "+"영화 : "+rs.getString(3)+" 관  "+rs.getString(1)+"스크린코드"+rs.getString(2)+" 상영코드");
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
