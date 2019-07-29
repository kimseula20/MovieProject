import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Snack_Shop {
	Movie_View m_view;
	BufferedReader br;
	PrintWriter pw;
	String str = "";
	int cnt;
	private ClientInfoClass clientinfo;
	private ConnectionPool cp = null;
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private ResultSetMetaData rsmd = null;
	MovieThread moviethread;

	Snack_Shop(BufferedReader br, PrintWriter pw, Connection con, ConnectionPool cp, MovieThread moviethread,
			ClientInfoClass clientinfo) {
		m_view = new Movie_View();
		this.br = br;
		this.con = con;
		this.pw = pw;
		this.cp = cp;
		this.clientinfo = clientinfo;
		this.moviethread = moviethread;
	}

	boolean selectsnack(String select) {
		while (true) {
			if (moviethread.isInteger(select)) {
				return true;
			} else if (select.equals("n") || select.equals("N")) {
				this.cnt++;
				return false;
			} else {
				moviethread.sendMsg("잘못 입력하셨습니다. 다시 입력해주시기 바랍니다.");
			}
		}
	}

	// 매점 이용 메소드
	////////// 슬 아 담 당 ////////////////////////싫어싫어싫어시러시러시러싫다구!!!!!!!!!
	void menuShow(String menu) {
		int indexNum = 0, count = 0;
		boolean loop = true;
		String foodquery = "";
		String query;
		if (menu.equals("popcorn")) {
			foodquery = "SP%";
		} else if (menu.equals("drink")) {
			foodquery = "SD%";
		} else if (menu.equals("icecream")) {
			foodquery = "SI%";
		} else if (menu.equals("snack")) {
			foodquery = "SN%";
		}

		query = "SELECT snack_code, snack_name, snack_price FROM snack_menu WHERE snack_code LIKE '" + foodquery + "' ";
		rs = moviethread.returnRS(query);
		try {
			while (rs.next()) {
				indexNum++;
				String num = String.format("%02d", indexNum);
				String sendStr = String.format("%s\t%-60s\t가격:  %-10s", num, rs.getString(2), rs.getString(3));
				moviethread.sendMsg(sendStr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	void snack_poschoice() {
		try {
		String strtmp = "";
		String nowPos_name = clientinfo.getSnack_pos_name();
		String nowPos_code;
		String query = "SELECT m_center_code, m_center_name,m_center_addr FROM movie_center ";
		boolean loop = true;
		int count =0;
		moviethread.sendMsg("\n영화관이 선택되지 않았습니다.\n");
		rs = moviethread.returnRS(query);
		moviethread.sendMsg("-----------영화관 선택-----------");
		count = 0;
		
			while (rs.next()) {
				count++;
				moviethread.sendMsg(count + " : " + rs.getString(2) + "\t 위치" + rs.getString(3));
			}
		
		loop = true;
		while (loop) {
			moviethread.sendMsg("현재 영화관의 위치를  선택해 주세요 ");
			strtmp = br.readLine();
			if (moviethread.isInteger(strtmp)) {
				int num = Integer.parseInt(strtmp);
				rs.absolute(num);
				nowPos_code = rs.getString(1);
				nowPos_name = rs.getString(2);
				clientinfo.setSnack_pos_code(nowPos_code);
				clientinfo.setSnack_pos_name(nowPos_name);
				loop = false;
				break;
			}

		}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	void snack_shopping() {
		boolean loop = true;
		this.cnt = 0;
		String select = "",strtmp;
		String nowPos_name = clientinfo.getSnack_pos_name();
		boolean isSelect = true;
		int count = 0;
		
		try {		
			if (nowPos_name == null) {
				snack_poschoice();
			} else {
				moviethread.sendMsg("현재 매점의 위치는 " + nowPos_name + " 입니다.");
				moviethread.sendMsg("현재 매점 위치가 맞습니까? Y");
					strtmp = br.readLine();
				if (strtmp.equals("y") || strtmp.equals("Y")) {
					moviethread.sendMsg(nowPos_name + " 입니다 환영 합니다 고객님. ");
				} else {
					moviethread.sendMsg("잘못 입력 하셨습니다.");
					return;
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (loop) {
			String cntSelect = "";
			String query = "";
			count = 1;
			if (cnt == 0) {
				// 팝콘 메뉴 보여주기
				menuShow("popcorn");
				query = "SELECT snack_code, snack_name, snack_price FROM snack_menu WHERE snack_code LIKE 'SP%' ";
				moviethread.sendMsg("\n팝콘을 선택해주세요. 선택하지 않으시려면 n을 입력하세요.");
				select = moviethread.recive();
				isSelect = selectsnack(select);
			} else if (cnt == 1) {
				// 음료 메뉴 보여주기
				menuShow("drink");
				query = "SELECT snack_code, snack_name, snack_price FROM snack_menu WHERE snack_code LIKE 'SD%' ";
				moviethread.sendMsg("\n음료를 선택해주세요. 선택하지 않으시려면 n을 입력하세요.");
				select = moviethread.recive();
				isSelect = selectsnack(select);
			} else if (cnt == 2) {
				// 아이스크림 메뉴 보여주기
				menuShow("icecream");
				query = "SELECT snack_code, snack_name, snack_price FROM snack_menu WHERE snack_code LIKE 'SI%' ";
				moviethread.sendMsg("\n아이스크림을 선택해주세요. 선택하지 않으시려면 n을 입력하세요.");
				select = moviethread.recive();
				isSelect = selectsnack(select);
			} else if (cnt == 3) {
				// 나머지 스낵 메뉴 보여주기
				menuShow("snack");
				query = "SELECT snack_code, snack_name, snack_price FROM snack_menu WHERE snack_code LIKE 'SN%' ";
				moviethread.sendMsg("\n스낵을 선택해주세요. 선택하지 않으시려면 n을 입력하세요.");
				select = moviethread.recive();
				isSelect = selectsnack(select);
				loop = false;
			}

			if (isSelect) {
				if (moviethread.isInteger(select)) {
					int num = Integer.parseInt(select);
					rs = moviethread.returnRS(query);
					moviethread.saveKeyValue(rs, num);
					moviethread.sendMsg("수량을 선택해주세요.");
					cntSelect = moviethread.recive();
					if (moviethread.isInteger(cntSelect)) {
						count = Integer.parseInt(cntSelect);
						clientinfo.getSnack_shopping().get(clientinfo.getSnack_shopping().size() - 1)
								.setSnack_count(count);
					}
				}
				cnt++;
				isSelect = false;
			}
		}
		moviethread.sendMsg("-------------------------------------------------------------");
		moviethread.sendMsg("\t\t ** 최종 선택하신 스낵 종류입니다 **");
		moviethread.sendMsg("-------------------------------------------------------------");
		moviethread.sendMsg(String.format("\t%-50s\t%-5s\t%-10s", "상품명", "수량", "단가"));
		moviethread.sendMsg("-------------------------------------------------------------");
		finalSelect();
		moviethread.sendMsg("-------------------------------------------------------------\n");
		moviethread.sendMsg("위의 내용이 맞으면 y 다시 고르시려면  n을 입력하세요.");
		try {
			select = br.readLine();
			if (select.equals("y") || select.equals("Y")) {
				bill();
			} else if (select.equals("n") || select.equals("N")) {
				snack_shopping();
			} else {
				moviethread.sendMsg("잘못 입력하셨습니다. 다시 입력해주세요.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void finalSelect() {
		int totalPrice = 0;
		// moviethread.sendMsg("사이즈" + clientinfo.getSnack_shopping().size());
		for (int i = 0; i < clientinfo.getSnack_shopping().size(); i++) {
			if (clientinfo.getSnack_shopping().size() > 0) {
				String foodcode = clientinfo.getSnack_shopping().get(i).getSnack_code();
				String foodname = clientinfo.getSnack_shopping().get(i).getSnack_name();
				int foodprice = clientinfo.getSnack_shopping().get(i).getSnack_price();
				int foodcount = clientinfo.getSnack_shopping().get(i).getSnack_count();
				if (foodcode.substring(0, 2).equals("SP")) {
					moviethread.sendMsg(
							String.format("|  - " + "%-50s\t%-5s\t%-10s", foodname, foodcount, foodprice) + "  |");
					totalPrice += foodprice * foodcount;
				}
				if (foodcode.substring(0, 2).equals("SD")) {
					moviethread.sendMsg(
							String.format("|  - " + "%-50s\t%-5s\t%-10s", foodname, foodcount, foodprice) + "  |");
					totalPrice += foodprice * foodcount;
				}
				if (foodcode.substring(0, 2).equals("SI")) {
					moviethread.sendMsg(
							String.format("|  - " + "%-50s\t%-5s\t%-10s", foodname, foodcount, foodprice) + "  |");
					totalPrice += foodprice * foodcount;
				}
				if (foodcode.substring(0, 2).equals("SN")) {
					moviethread.sendMsg(
							String.format("|  - " + "%-50s\t%-5s\t%-10s", foodname, foodcount, foodprice) + "  |");
					totalPrice += foodprice * foodcount;
				}
			}
		}
		clientinfo.setSnackPrice(totalPrice);
	}

	// 장바구니에 담은걸 계산 하는 메소드
	// 장바구니에 담겨 있는
	void bill() {

		moviethread.sendMsg("\n-------------------------------------------------------------");
		moviethread.sendMsg("\t\t   ** BOOLSA SNACK ** ");
		moviethread.sendMsg("-------------------------------------------------------------");
		moviethread.sendMsg("-------------------------------------------------------------");
		moviethread.sendMsg(String.format("\t%-40s\t%-5s\t%-10s\t%-10s", "상품명", "수량", "단가", "    금액"));
		moviethread.sendMsg("-------------------------------------------------------------");
		for (int i = 0; i < clientinfo.getSnack_shopping().size(); i++) {
			if (clientinfo.getSnack_shopping().size() > 0) {
				String foodcode = clientinfo.getSnack_shopping().get(i).getSnack_code();
				String foodname = clientinfo.getSnack_shopping().get(i).getSnack_name();
				int foodprice = clientinfo.getSnack_shopping().get(i).getSnack_price();
				int foodcount = clientinfo.getSnack_shopping().get(i).getSnack_count();

				if (foodcode.substring(0, 2).equals("SP")) {
					moviethread.sendMsg(String.format("|" + "%-40s\t%-5s\t%-10s%-10s", foodname, foodcount, foodprice,
							foodcount * foodprice) + "|");
				}
				if (foodcode.substring(0, 2).equals("SD")) {
					moviethread.sendMsg(String.format("|" + "%-40s\t%-5s\t%-10s%-10s", foodname, foodcount, foodprice,
							foodcount * foodprice) + "|");
				}
				if (foodcode.substring(0, 2).equals("SI")) {
					moviethread.sendMsg(String.format("|" + "%-40s\t%-5s\t%-10s%-10s", foodname, foodcount, foodprice,
							foodcount * foodprice) + "|");
				}
				if (foodcode.substring(0, 2).equals("SN")) {
					moviethread.sendMsg(String.format("|" + "%-40s\t%-5s\t%-10s%-10s", foodname, foodcount, foodprice,
							foodcount * foodprice) + "|");
				}
			}
		}
		moviethread.sendMsg("-------------------------------------------------------------");
		moviethread.sendMsg("|\t\t\t\t\t결제할 총 금액 : " + clientinfo.getSnackPrice() + "원    |");
		moviethread.sendMsg("-------------------------------------------------------------");
		moviethread.sendMsg("결제하시려면 y 취소하시려면 n을 입력하세요.");

		String select = moviethread.recive();

		if(clientinfo.getCust_money()>=clientinfo.getSnackPrice()) {
		
		if (select.equals("y") || select.equals("Y")) {

			try {
				String cust_id = clientinfo.getCust_id();
				int snack_total_price = clientinfo.getSnackPrice();
				String date = moviethread.getToday();
				String insertQuery = "INSERT INTO snack_sales VALUES ('SS'||lpad(snack_sales_seq.NEXTVAL,6,0)," + "'"
						+ cust_id + "','" + clientinfo.getSnack_pos_code() + "', '" + snack_total_price + "','" + date
						+ "' ) ";
				pstmt = con.prepareStatement(insertQuery);
				pstmt.executeUpdate();
				con.commit();
				String seq = moviethread.returnSeq("snack_sales");
				for (int i = 0; i < clientinfo.getSnack_shopping().size(); i++) {
					String snack_code = clientinfo.getSnack_shopping().get(i).getSnack_code();
					System.out.println(snack_code + "snack_code");
					String snack_name = clientinfo.getSnack_shopping().get(i).getSnack_name();
					System.out.println(snack_name + "  snack_name");
					int snack_count = clientinfo.getSnack_shopping().get(i).getSnack_count();
					System.out.println(snack_count + " snack_count");
					int snack_price = clientinfo.getSnack_shopping().get(i).getSnack_price();
					System.out.println(snack_price + " snack_price ");
					insertQuery = "INSERT INTO snack_sales_list VALUES ('" + seq + "'," + "'" + snack_code + "','"
							+ snack_name + "', '" + snack_count + "','" + snack_price + "' ) ";
					pstmt = con.prepareStatement(insertQuery);
					pstmt.executeUpdate();
				}
				con.commit();
				moviethread.updateMoney(snack_total_price*-1);
				moviethread.sendMsg("결제가 완료되었습니다. 감사합니다.");
				moviethread.sendMsg("잔액이 : "+clientinfo.getCust_money()+" 입니다.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (select.equals("n") || select.equals("N")) {
			moviethread.sendMsg(clientinfo.resetSnack());
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			moviethread.sendMsg("잘못 입력하셨습니다.");
		}
		}
		else {
			moviethread.sendMsg("잔액이 부족 합니다. 다시 처음으로 돌아 가겠습니다.");
			moviethread.sendMsg(clientinfo.resetSnack());
		}
			
	}
}
