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
				moviethread.sendMsg("�߸� �Է��ϼ̽��ϴ�. �ٽ� �Է����ֽñ� �ٶ��ϴ�.");
			}
		}
	}

	// ���� �̿� �޼ҵ�
	////////// �� �� �� �� ////////////////////////�Ⱦ�Ⱦ�Ⱦ�÷��÷��÷��ȴٱ�!!!!!!!!!
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
				String sendStr = String.format("%s\t%-60s\t����:  %-10s", num, rs.getString(2), rs.getString(3));
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
		moviethread.sendMsg("\n��ȭ���� ���õ��� �ʾҽ��ϴ�.\n");
		rs = moviethread.returnRS(query);
		moviethread.sendMsg("-----------��ȭ�� ����-----------");
		count = 0;
		
			while (rs.next()) {
				count++;
				moviethread.sendMsg(count + " : " + rs.getString(2) + "\t ��ġ" + rs.getString(3));
			}
		
		loop = true;
		while (loop) {
			moviethread.sendMsg("���� ��ȭ���� ��ġ��  ������ �ּ��� ");
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
				moviethread.sendMsg("���� ������ ��ġ�� " + nowPos_name + " �Դϴ�.");
				moviethread.sendMsg("���� ���� ��ġ�� �½��ϱ�? Y");
					strtmp = br.readLine();
				if (strtmp.equals("y") || strtmp.equals("Y")) {
					moviethread.sendMsg(nowPos_name + " �Դϴ� ȯ�� �մϴ� ������. ");
				} else {
					moviethread.sendMsg("�߸� �Է� �ϼ̽��ϴ�.");
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
				// ���� �޴� �����ֱ�
				menuShow("popcorn");
				query = "SELECT snack_code, snack_name, snack_price FROM snack_menu WHERE snack_code LIKE 'SP%' ";
				moviethread.sendMsg("\n������ �������ּ���. �������� �����÷��� n�� �Է��ϼ���.");
				select = moviethread.recive();
				isSelect = selectsnack(select);
			} else if (cnt == 1) {
				// ���� �޴� �����ֱ�
				menuShow("drink");
				query = "SELECT snack_code, snack_name, snack_price FROM snack_menu WHERE snack_code LIKE 'SD%' ";
				moviethread.sendMsg("\n���Ḧ �������ּ���. �������� �����÷��� n�� �Է��ϼ���.");
				select = moviethread.recive();
				isSelect = selectsnack(select);
			} else if (cnt == 2) {
				// ���̽�ũ�� �޴� �����ֱ�
				menuShow("icecream");
				query = "SELECT snack_code, snack_name, snack_price FROM snack_menu WHERE snack_code LIKE 'SI%' ";
				moviethread.sendMsg("\n���̽�ũ���� �������ּ���. �������� �����÷��� n�� �Է��ϼ���.");
				select = moviethread.recive();
				isSelect = selectsnack(select);
			} else if (cnt == 3) {
				// ������ ���� �޴� �����ֱ�
				menuShow("snack");
				query = "SELECT snack_code, snack_name, snack_price FROM snack_menu WHERE snack_code LIKE 'SN%' ";
				moviethread.sendMsg("\n������ �������ּ���. �������� �����÷��� n�� �Է��ϼ���.");
				select = moviethread.recive();
				isSelect = selectsnack(select);
				loop = false;
			}

			if (isSelect) {
				if (moviethread.isInteger(select)) {
					int num = Integer.parseInt(select);
					rs = moviethread.returnRS(query);
					moviethread.saveKeyValue(rs, num);
					moviethread.sendMsg("������ �������ּ���.");
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
		moviethread.sendMsg("\t\t ** ���� �����Ͻ� ���� �����Դϴ� **");
		moviethread.sendMsg("-------------------------------------------------------------");
		moviethread.sendMsg(String.format("\t%-50s\t%-5s\t%-10s", "��ǰ��", "����", "�ܰ�"));
		moviethread.sendMsg("-------------------------------------------------------------");
		finalSelect();
		moviethread.sendMsg("-------------------------------------------------------------\n");
		moviethread.sendMsg("���� ������ ������ y �ٽ� �����÷���  n�� �Է��ϼ���.");
		try {
			select = br.readLine();
			if (select.equals("y") || select.equals("Y")) {
				bill();
			} else if (select.equals("n") || select.equals("N")) {
				snack_shopping();
			} else {
				moviethread.sendMsg("�߸� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void finalSelect() {
		int totalPrice = 0;
		// moviethread.sendMsg("������" + clientinfo.getSnack_shopping().size());
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

	// ��ٱ��Ͽ� ������ ��� �ϴ� �޼ҵ�
	// ��ٱ��Ͽ� ��� �ִ�
	void bill() {

		moviethread.sendMsg("\n-------------------------------------------------------------");
		moviethread.sendMsg("\t\t   ** BOOLSA SNACK ** ");
		moviethread.sendMsg("-------------------------------------------------------------");
		moviethread.sendMsg("-------------------------------------------------------------");
		moviethread.sendMsg(String.format("\t%-40s\t%-5s\t%-10s\t%-10s", "��ǰ��", "����", "�ܰ�", "    �ݾ�"));
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
		moviethread.sendMsg("|\t\t\t\t\t������ �� �ݾ� : " + clientinfo.getSnackPrice() + "��    |");
		moviethread.sendMsg("-------------------------------------------------------------");
		moviethread.sendMsg("�����Ͻ÷��� y ����Ͻ÷��� n�� �Է��ϼ���.");

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
				moviethread.sendMsg("������ �Ϸ�Ǿ����ϴ�. �����մϴ�.");
				moviethread.sendMsg("�ܾ��� : "+clientinfo.getCust_money()+" �Դϴ�.");
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
			moviethread.sendMsg("�߸� �Է��ϼ̽��ϴ�.");
		}
		}
		else {
			moviethread.sendMsg("�ܾ��� ���� �մϴ�. �ٽ� ó������ ���� ���ڽ��ϴ�.");
			moviethread.sendMsg(clientinfo.resetSnack());
		}
			
	}
}