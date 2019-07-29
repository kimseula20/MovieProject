import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class Movie_reserve {

	Movie_View m_view;
	BufferedReader br;
	PrintWriter pw;
	String str = "";
	private ClientInfoClass clientinfo;
	private ConnectionPool cp = null;
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	MovieThread moviethread;

	Movie_reserve(BufferedReader br, PrintWriter pw, Connection con, ConnectionPool cp, MovieThread moviethread,
			ClientInfoClass clientinfo) {
		m_view = new Movie_View();
		this.br = br;
		this.con = con;
		this.pw = pw;
		this.cp = cp;
		this.clientinfo = clientinfo;
		this.moviethread = moviethread;
	}

	void movieschoiceinfo() {
		movieshow(false);
		boolean loop = true;
		try {
			while(loop) {
			moviethread.sendMsg("�� �ϰ� ���� ��ȭ�� ������ �ּ���");
			String str = br.readLine();
			if(moviethread.isInteger(str)) {
				loop = false;
				int tmp = Integer.parseInt(str);
				movie_detail(tmp);
			}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ���� �� �� ��ȭ�� ���� �ִ� �޼ҵ�
	void movieshow(boolean show) {
		clientinfo.setStage(21);
		int indexNum = 0;
		String query = "SELECT movie_title,movie_info FROM movie_kind ";
		rs = moviethread.returnRS(query);
		try {
			while (rs.next()) {
				indexNum++;
				moviethread.sendMsg(indexNum + "      : " + rs.getString(1));
				if (show) {
					moviethread.sendMsg(rs.getString(2));
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void movie_detail(int num) {
		try {
			String query = "SELECT movie_title,movie_info FROM movie_kind ";
			rs = moviethread.returnRS(query);
			rs.absolute(num);
			moviethread.sendMsg("���� : " + rs.getString(1) + "\n\n");
			moviethread.sendMsg("�ٰŸ� : " + rs.getString(2));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void seat_emptyReset() {
		String getSeat_r_code, updateQuery;
		for (int i = 0; i < clientinfo.getSeat_reserve().size(); i++) {
			getSeat_r_code = clientinfo.getSeat_reserve().get(i).getSeat_r_code();
			updateQuery = "UPDATE seat_reserve SET SEAT_R_EMPTY = 1 WHERE SEAT_R_CODE='" + getSeat_r_code + "'";
			try {
				pstmt = con.prepareStatement(updateQuery);
				pstmt.executeUpdate();
				con.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("seat_empty ����ó�� �߻�");
			}

		}
	}

	// ��ȭ�� ���� �ϴ� �޼ҵ�
	void movieChoice() {
		clientinfo.setStage(31);
		boolean loop = true;
		// ��ȭ ��� ��
		movieshow(false);
		// ��ȭ�� �����ߴ��� ����
		if (clientinfo.getMovie_Code() != null) {
			moviethread.sendMsg("********************************************\n");
			moviethread.sendMsg("���� ���� �Ͻ� ��ȭ�� : " + clientinfo.getMovie_title() + "�Դϴ�.");
			moviethread.sendMsg("\n******************************************\n\n\n");
		}
		String query = "SELECT movie_code,movie_title,movie_price FROM movie_kind ";
		while (loop) {
			moviethread.sendMsg("��ȭ�� ������ �ּ���\n�ڷΰ��÷��� 'back' �Է��ϼ��� ");
			String select = moviethread.recive();
			if (select.equals("back")) {
				loop = false;
				moviethread.stage_move(-20);

				break;
			} else if (moviethread.isInteger(select)) {
				int num = Integer.parseInt(select);
				rs = moviethread.returnRS(query);
				moviethread.saveKeyValue(rs, num);
				loop = false;
				break;
			} else {
				moviethread.sendMsg(" �߸� �Է� �ϼ̽��ϴ�. �ٽ� �Է��� �ּ���");
			}
		}
		System.out.println("��ȭ ���� �Ϸ� ---------------- ��� ");
		System.out.println(clientinfo.getMovie_Code() + "movie_code\n" + clientinfo.getMovie_title() + " movie �̸�");
		movie_centerChoice();

	}

	//////////// ��ȭ�� ���� ���� //////////////////////////////////////////
	void movie_centerChoice() {
		clientinfo.setStage(32);
		int count = 0;
		boolean loop = true;
		String tmp, query, already = "";
		;
		query = "SELECT m_center_code, m_center_name,m_center_addr FROM movie_center ";
		rs = moviethread.returnRS(query);
		moviethread.sendMsg("-----------��ȭ�� ����-----------");
		try {
			count = 0;
			while (rs.next()) {
				count++;
				moviethread.sendMsg(count + " : " + rs.getString(2) + "\t ��ġ" + rs.getString(3));
			}
			while (loop) {
				moviethread.sendMsg("\n�� �Ͻ� ��ȭ���� ������ �ּ���\n�ڷΰ��÷��� 'back' �Է��ϼ��� ");
				tmp = moviethread.recive();
				if (tmp.equals("back")) {
					loop = false;
					moviethread.stage_move(-1);
					break;
				} else if (moviethread.isInteger(tmp)) {
					int num = Integer.parseInt(tmp);
					moviethread.saveKeyValue(rs, num);
					loop = false;
					break;
				} else
					moviethread.sendMsg("�߸� �Է� �ϼ̽��ϴ�.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		moviethread.sendMsg(already + " ��ȭ�� : " + clientinfo.getM_center_name() + " ������ ���� �ϼ̽��ϴ�.");
		daytimeselect();
		System.out.println(clientinfo.getMovie_Code() + " clientinfo.getMovie_Code()" + clientinfo.getM_center_code()
				+ " clientinfo.getM_center_code()");
	}

	////////// ��¥�� �ð��� ���� �ϴ� �޼ҵ� ///////////////////////////////////
	void daytimeselect() {
		clientinfo.setStage(33);
		int indexnum = 0;
		boolean loop = true;
		pw.println("\n\n�󿵰����� ��ȭ ��¥ �ð�\n\n");
		pw.flush();
		String query, movie_code, m_center_code;
		movie_code = clientinfo.getMovie_Code();
		m_center_code = clientinfo.getM_center_code();
		query = "SELECT screening_code, TO_CHAR(screening_time,'MM/DD:HH:MI'),sc_center_name,sc_center_code ��¥ FROM screening JOIN screen_center USING(sc_center_code) "
				+ "WHERE m_center_code='" + m_center_code + "'" + " AND movie_code='" + movie_code
				+ "' ORDER By ��¥ ASC ";
		rs = moviethread.returnRS(query);
		try {
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					indexnum++;
					String tmp = rs.getString(2);
					moviethread.sendMsg(indexnum + " ) " + tmp.substring(0, 2) + "��" + tmp.substring(3, 5) + "�� "
							+ tmp.substring(6, 8) + "��" + tmp.substring(9, tmp.length()) + "��\n");
				}
				while (loop) {
					moviethread.sendMsg("���Ͻô� ��¥�� ������ �ּ���  \n �ڷΰ��÷��� 'back' �Է��ϼ��� ");
					String tmp = moviethread.recive();
					if (tmp.equals("back")) {
						loop = false;
						moviethread.stage_move(-1);
						break;
					}
					if (moviethread.isInteger(tmp)) {
						int num = Integer.parseInt(tmp);
						moviethread.saveKeyValue(rs, num);
						loop = false;
						selectSeat();
					} else
						moviethread.sendMsg("�� �� �Է��ϼ̽��ϴ�. �ٽ� �Է��� �ּ���..");
				}

			} else
				moviethread.sendMsg("\n�� ������ �ð��� �����ϴ�.\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/////////////////// �¼� ���� �޼ҵ� //////////////////////////////////////
	//////////////// ������ �Է��� ������ ���� ��ũ������ �������� //////////////////
	//////////// �� ��ũ������ �ش� �ϴ� �¼����� ���´�. ////////////////////////////

	void selectSeat() {
		clientinfo.setStage(34);
		// �¼� ���� �� �ҽ� ./////////////////////////////////////////
		///////////////////////////////////////////////////////////////
		String seat_name = "", str_empty = "", tmp_str = "";
		int seat_empty = 0;
		int querycount = 0;
		int ticketNum = 0;
		String[][] seatjpg = new String[1][3];
		ArrayList<Integer> seat_check;
		boolean loop = true, loop0 = true;
		seatjpg[0][0] = "";
		seatjpg[0][1] = "";
		seatjpg[0][2] = "";
		String screening_code = clientinfo.getScreening_code();
		String query = "SELECT seat_r_code,seat_name,seat_r_empty FROM seat_reserve"
				+ " NATURAL JOIN seat WHERE screening_code ='" + screening_code + "' ORDER BY seat_name ASC ";
		//////////////////////////////////////////////////////////////
		//////////// Ƽ�� ���� �� Ȯ�� ////////////////////////////////////
		try {
			while (loop0) {
				moviethread.sendMsg("���� ���� �Ͻðڽ��ϱ�? \n�ڷ� ���÷��� 'back'�� �Է��ϼ���");
				String tmp = br.readLine();
				if (tmp.equals("back")) {
					loop0 = false;
					moviethread.stage_move(-1);
				}
				if (moviethread.isInteger(tmp) && Integer.parseInt(tmp) > 0) {
					ticketNum = Integer.parseInt(tmp);
					int totalPrice = clientinfo.getMoviePrice() * ticketNum;
					clientinfo.setTotalPrice(totalPrice);
					loop0 = false;
					break;
				} else
					moviethread.sendMsg("0 �Ǵ� �� �� �Է� �ϼ̽��ϴ�.");

			}

			// SQL �Է��� �¼������� arraylist �ʱ� �뷮 ����
			rs = moviethread.returnRS(query);
			rs.last();
			// seat_check = new ArrayList<Integer>(rs.getRow());
			// moviethread.sendMsg(rs.getRow() + " array �ʱ� �뷮 �Դϴ�.");
			///////// �¼� �׸��� �ڵ� ///////////////////
			for (int i = 0; i < ticketNum; i++) {
				loop = true;
				rs = moviethread.returnRS(query);
				querycount = 0;
				moviethread.sendMsg("----------------------------------------------------------");
				moviethread.sendMsg("|                                                        |");
				moviethread.sendMsg("|       S       C       R       E       E       N        |");
				moviethread.sendMsg("|                                                        |");
				moviethread.sendMsg("----------------------------------------------------------\n");
				while (rs.next()) {

					// 5�� ���� ���� �ֱ� ���� ī����
					querycount++;
					seat_name = rs.getString(2);
					seat_empty = rs.getInt(3);
					// �¼� ���� ���� �ӽ� ����
					// seat_check.add(seat_empty);
					if (seat_empty == 1) {
						str_empty = "O";
					} else if (seat_empty == 0) {
						str_empty = "X";
					}

					seatjpg[0][0] += "|" + String.format("%02d", querycount) + ": " + seat_name + "|  ";
					seatjpg[0][1] += "|���డ��:" + str_empty + "|  ";
					seatjpg[0][2] += "----------  ";
					if (querycount % 5 == 0) {
						moviethread.sendMsg(seatjpg[0][0]);
						moviethread.sendMsg(seatjpg[0][1]);
						moviethread.sendMsg(seatjpg[0][2]);
						seatjpg[0][0] = "";
						seatjpg[0][1] = "";
						seatjpg[0][2] = "";
					}
				}

				//////////// ���� �ϴ� �޼ҵ� ////////////////////////////
				int nocount = 0;

				while (loop) {
					moviethread.sendMsg("���� �Ͻ� �¼��� ��ȣ�� ������ �ּ���\n�ڷΰ��÷��� 'back'�� �Է��ϼ��� ");
					tmp_str = moviethread.recive();
					if (tmp_str.equals("back")) {
						loop = false;
						seat_emptyReset();
						moviethread.stage_move(-1);
					}
					// �¼� Ȯ��
					if (moviethread.isInteger(tmp_str)) {
						int tmp = Integer.parseInt(tmp_str);
						rs = moviethread.returnRS(query);
						rs.absolute(tmp);
						if (rs.getInt(3) == 1) {
							loop = false;
							moviethread.saveKeyValue(rs, tmp);
							moviethread.sendMsg(clientinfo.getSeat_reserve().get(i).getSeat_name() + "�� �����ϼ̽��ϴ�.\n");
							//////////// ���� �Ϸ� clientinfo�� ���� ////////////////////////////
							//////////// SQL ������Ʈ ////////////////////////////////////////
							String getSeat_r_code = clientinfo.getSeat_reserve().get(i).getSeat_r_code();
							String updateQuery = "UPDATE seat_reserve SET SEAT_R_EMPTY = 0 WHERE SEAT_R_CODE='"
									+ getSeat_r_code + "'";
							pstmt = con.prepareStatement(updateQuery);
							pstmt.executeUpdate();
							// seat_check.clear();
							con.commit();
							break;
						} else {
							moviethread.sendMsg("�̹� ���õ� �¼��Դϴ�. �ٽ� �Է��� �ּ���");
						}
					} else {
						moviethread.sendMsg("�߸� �Է� �ϼ̽��ϴ�. �ٽ� �Է��� �ּ���");
					}
				}
			}
			finalCheck();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�¼� ���� ����");
			return;
		}

	}
	///////////////////////////////////////////////////////
	/////////////// final Check ///////////////////////////
	//////////// ���� Ȯ�� �޼ҵ� //////////////////////////////

	void finalCheck() {
		clientinfo.setStage(35);
		String strtmp;
		String cust_id, cust_name, cust_num;
		String movie_title, m_center_name, sc_center_name, screening_time, screening_code, seat_name = "";
		int totalprice, seat_reserve_size;
		try {
			cust_id = clientinfo.getCust_id();
			cust_name = clientinfo.getCust_name();
			cust_num = clientinfo.getCust_num();
			movie_title = clientinfo.getMovie_title();
			m_center_name = clientinfo.getM_center_name();
			sc_center_name = clientinfo.getSc_center_name();
			screening_code = clientinfo.getScreening_code();
			screening_time = clientinfo.getScreening_time();
			seat_reserve_size = clientinfo.getSeat_reserve().size();
			totalprice = clientinfo.getTotalPrice();
			for (int i = 0; i < seat_reserve_size; i++) {

				seat_name += " " + clientinfo.getSeat_reserve().get(i).getSeat_name();
				if (seat_reserve_size - 1 != i) {
					seat_name += ",";
				}
			}

			moviethread.sendMsg("���� Ȯ�� �ϰڽ��ϴ�.\n\n\n\n");
			moviethread.sendMsg("-------------------------------------------------");
			moviethread.sendMsg("| ** �� �� �� �� **                                  ");
			moviethread.sendMsg("|  �� ��ȣ :" + cust_num + "                          ");
			moviethread.sendMsg("|    ���̵� :" + cust_id + "                           ");
			moviethread.sendMsg("|     �� �� :" + cust_name + "                         ");
			moviethread.sendMsg("|                                                ");
			moviethread.sendMsg("| �� ȭ ����  :" + movie_title + "    ����:" + m_center_name);
			moviethread.sendMsg("|  �� �� ��   :" + sc_center_name + "                    ");
			moviethread.sendMsg("| �� �� �ð�  :" + screening_time + "                    ");
			moviethread.sendMsg("|    �ο�    :" + seat_reserve_size + " ��              �¼� : " + seat_name);
			moviethread.sendMsg("|    �� ��   :" + totalprice + "��");
			moviethread.sendMsg("-------------------------------------------------\n\n");
			moviethread.sendMsg(" �� ������ �½��ϱ�?  y/n \n Y�� �����ø� ������ �����ϰڽ��ϴ�.\n�ڷ� ���÷��� back�� ���� �ּ���");

			if (clientinfo.getCust_money() >= totalprice) {

				boolean loop = true;
				while (loop) {
					strtmp = moviethread.recive();
					if (strtmp.equals("back")) {
						loop = false;
						seat_emptyReset();
						moviethread.stage_move(-1);
					}
					if (strtmp.equals("y") || strtmp.equals("Y")) {
						String insertQuery = "INSERT INTO reservation VALUES('RE'||lpad(seat_seq.NEXTVAL,6,0)," + "'"
								+ cust_num + "','" + screening_code + "'," + seat_reserve_size + ",'" + seat_name + "',"
								+ totalprice + " ,'" + moviethread.getToday() + "' ) ";
						pstmt = con.prepareStatement(insertQuery);
						pstmt.executeUpdate();
						String seq = moviethread.returnSeq("reservation");
						for (int i = 0; i < clientinfo.getSeat_reserve().size(); i++) {
							insertQuery = "INSERT INTO seat_reser_list VALUES('" + seq.toUpperCase() + "','"
									+ clientinfo.getSeat_reserve().get(i).getSeat_r_code() + "' ) ";
							pstmt = con.prepareStatement(insertQuery);
							pstmt.executeUpdate();
							con.commit();
						}

						moviethread.updateMoney(totalprice * -1);

						moviethread.sendMsg("������ �Ϸ� �Ǿ����ϴ�.");
						moviethread.sendMsg("���� �ܾ��� : " + clientinfo.getCust_money());
						clientinfo.allreset();
						break;
					} else if (strtmp.equals("n") || strtmp.equals("N")) {
						seat_emptyReset();
						clientinfo.allreset();

						moviethread.sendMsg("��ٱ��ϸ� �ʱ�ȭ �Ͽ����ϴ�. ó������ �̵� �մϴ�.");
						break;
					} else
						moviethread.sendMsg("�߸� �Է� �ϼ̽��ϴ�. Y �Ǵ� N�� �Է� �� �ּ���");

				}
			} else {
				moviethread.sendMsg(" �ܾ��� ���� �մϴ�. ó������ ���� ���ϴ�.");
				seat_emptyReset();
				clientinfo.allreset();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void endPoint() {
		moviethread.sendMsg("ó������ ���� ���ϴ�.");
	}

}
