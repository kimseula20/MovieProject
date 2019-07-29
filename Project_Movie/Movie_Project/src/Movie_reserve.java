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
			moviethread.sendMsg("상세 하게 보실 영화를 선택해 주세요");
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

	// 현재 상영 중 영화를 보여 주는 메소드
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
			moviethread.sendMsg("제목 : " + rs.getString(1) + "\n\n");
			moviethread.sendMsg("줄거리 : " + rs.getString(2));

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
				System.out.println("seat_empty 예외처리 발생");
			}

		}
	}

	// 영화를 선택 하는 메소드
	void movieChoice() {
		clientinfo.setStage(31);
		boolean loop = true;
		// 영화 목록 뷰
		movieshow(false);
		// 영화를 선택했는지 유무
		if (clientinfo.getMovie_Code() != null) {
			moviethread.sendMsg("********************************************\n");
			moviethread.sendMsg("현재 선택 하신 영화는 : " + clientinfo.getMovie_title() + "입니다.");
			moviethread.sendMsg("\n******************************************\n\n\n");
		}
		String query = "SELECT movie_code,movie_title,movie_price FROM movie_kind ";
		while (loop) {
			moviethread.sendMsg("영화를 선택해 주세요\n뒤로가시려면 'back' 입력하세요 ");
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
				moviethread.sendMsg(" 잘못 입력 하셨습니다. 다시 입력해 주세요");
			}
		}
		System.out.println("영화 선택 완료 ---------------- 출력 ");
		System.out.println(clientinfo.getMovie_Code() + "movie_code\n" + clientinfo.getMovie_title() + " movie 이름");
		movie_centerChoice();

	}

	//////////// 영화관 지점 선택 //////////////////////////////////////////
	void movie_centerChoice() {
		clientinfo.setStage(32);
		int count = 0;
		boolean loop = true;
		String tmp, query, already = "";
		;
		query = "SELECT m_center_code, m_center_name,m_center_addr FROM movie_center ";
		rs = moviethread.returnRS(query);
		moviethread.sendMsg("-----------영화관 선택-----------");
		try {
			count = 0;
			while (rs.next()) {
				count++;
				moviethread.sendMsg(count + " : " + rs.getString(2) + "\t 위치" + rs.getString(3));
			}
			while (loop) {
				moviethread.sendMsg("\n상영 하실 영화관을 선택해 주세요\n뒤로가시려면 'back' 입력하세요 ");
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
					moviethread.sendMsg("잘못 입력 하셨습니다.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		moviethread.sendMsg(already + " 영화관 : " + clientinfo.getM_center_name() + " 지점을 선택 하셨습니다.");
		daytimeselect();
		System.out.println(clientinfo.getMovie_Code() + " clientinfo.getMovie_Code()" + clientinfo.getM_center_code()
				+ " clientinfo.getM_center_code()");
	}

	////////// 날짜와 시간을 선택 하는 메소드 ///////////////////////////////////
	void daytimeselect() {
		clientinfo.setStage(33);
		int indexnum = 0;
		boolean loop = true;
		pw.println("\n\n상영가능한 영화 날짜 시간\n\n");
		pw.flush();
		String query, movie_code, m_center_code;
		movie_code = clientinfo.getMovie_Code();
		m_center_code = clientinfo.getM_center_code();
		query = "SELECT screening_code, TO_CHAR(screening_time,'MM/DD:HH:MI'),sc_center_name,sc_center_code 날짜 FROM screening JOIN screen_center USING(sc_center_code) "
				+ "WHERE m_center_code='" + m_center_code + "'" + " AND movie_code='" + movie_code
				+ "' ORDER By 날짜 ASC ";
		rs = moviethread.returnRS(query);
		try {
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					indexnum++;
					String tmp = rs.getString(2);
					moviethread.sendMsg(indexnum + " ) " + tmp.substring(0, 2) + "월" + tmp.substring(3, 5) + "일 "
							+ tmp.substring(6, 8) + "시" + tmp.substring(9, tmp.length()) + "분\n");
				}
				while (loop) {
					moviethread.sendMsg("원하시는 날짜를 선택해 주세요  \n 뒤로가시려면 'back' 입력하세요 ");
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
						moviethread.sendMsg("잘 못 입력하셨습니다. 다시 입력해 주세요..");
				}

			} else
				moviethread.sendMsg("\n상영 가능한 시간이 없습니다.\n");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/////////////////// 좌석 선택 메소드 //////////////////////////////////////
	//////////////// 본인이 입력한 정보에 따라 스크린관이 정해지고 //////////////////
	//////////// 그 스크린관에 해당 하는 좌석들이 나온다. ////////////////////////////

	void selectSeat() {
		clientinfo.setStage(34);
		// 좌석 쿼리 뷰 소스 ./////////////////////////////////////////
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
		//////////// 티켓 예매 수 확인 ////////////////////////////////////
		try {
			while (loop0) {
				moviethread.sendMsg("몇장 예매 하시겠습니까? \n뒤로 가시려면 'back'을 입력하세요");
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
					moviethread.sendMsg("0 또는 잘 못 입력 하셨습니다.");

			}

			// SQL 입력한 좌석개수를 arraylist 초기 용량 정함
			rs = moviethread.returnRS(query);
			rs.last();
			// seat_check = new ArrayList<Integer>(rs.getRow());
			// moviethread.sendMsg(rs.getRow() + " array 초기 용량 입니다.");
			///////// 좌석 그리는 코드 ///////////////////
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

					// 5개 마다 끊어 주기 위한 카운터
					querycount++;
					seat_name = rs.getString(2);
					seat_empty = rs.getInt(3);
					// 좌석 가능 유무 임시 저장
					// seat_check.add(seat_empty);
					if (seat_empty == 1) {
						str_empty = "O";
					} else if (seat_empty == 0) {
						str_empty = "X";
					}

					seatjpg[0][0] += "|" + String.format("%02d", querycount) + ": " + seat_name + "|  ";
					seatjpg[0][1] += "|예약가능:" + str_empty + "|  ";
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

				//////////// 선택 하는 메소드 ////////////////////////////
				int nocount = 0;

				while (loop) {
					moviethread.sendMsg("예약 하실 좌석의 번호를 선택해 주세요\n뒤로가시려면 'back'을 입력하세요 ");
					tmp_str = moviethread.recive();
					if (tmp_str.equals("back")) {
						loop = false;
						seat_emptyReset();
						moviethread.stage_move(-1);
					}
					// 좌석 확인
					if (moviethread.isInteger(tmp_str)) {
						int tmp = Integer.parseInt(tmp_str);
						rs = moviethread.returnRS(query);
						rs.absolute(tmp);
						if (rs.getInt(3) == 1) {
							loop = false;
							moviethread.saveKeyValue(rs, tmp);
							moviethread.sendMsg(clientinfo.getSeat_reserve().get(i).getSeat_name() + "을 선택하셨습니다.\n");
							//////////// 선택 완료 clientinfo에 저장 ////////////////////////////
							//////////// SQL 업데이트 ////////////////////////////////////////
							String getSeat_r_code = clientinfo.getSeat_reserve().get(i).getSeat_r_code();
							String updateQuery = "UPDATE seat_reserve SET SEAT_R_EMPTY = 0 WHERE SEAT_R_CODE='"
									+ getSeat_r_code + "'";
							pstmt = con.prepareStatement(updateQuery);
							pstmt.executeUpdate();
							// seat_check.clear();
							con.commit();
							break;
						} else {
							moviethread.sendMsg("이미 선택된 좌석입니다. 다시 입력해 주세요");
						}
					} else {
						moviethread.sendMsg("잘못 입력 하셨습니다. 다시 입력해 주세요");
					}
				}
			}
			finalCheck();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("좌석 선택 에러");
			return;
		}

	}
	///////////////////////////////////////////////////////
	/////////////// final Check ///////////////////////////
	//////////// 최종 확인 메소드 //////////////////////////////

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

			moviethread.sendMsg("최종 확인 하겠습니다.\n\n\n\n");
			moviethread.sendMsg("-------------------------------------------------");
			moviethread.sendMsg("| ** 예 매 내 역 **                                  ");
			moviethread.sendMsg("|  고객 번호 :" + cust_num + "                          ");
			moviethread.sendMsg("|    아이디 :" + cust_id + "                           ");
			moviethread.sendMsg("|     성 함 :" + cust_name + "                         ");
			moviethread.sendMsg("|                                                ");
			moviethread.sendMsg("| 영 화 제목  :" + movie_title + "    지점:" + m_center_name);
			moviethread.sendMsg("|  상 영 관   :" + sc_center_name + "                    ");
			moviethread.sendMsg("| 상 영 시간  :" + screening_time + "                    ");
			moviethread.sendMsg("|    인원    :" + seat_reserve_size + " 명              좌석 : " + seat_name);
			moviethread.sendMsg("|    금 액   :" + totalprice + "원");
			moviethread.sendMsg("-------------------------------------------------\n\n");
			moviethread.sendMsg(" 위 사항이 맞습니까?  y/n \n Y를 누르시면 결제로 진행하겠습니다.\n뒤로 가시려면 back을 눌러 주세요");

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

						moviethread.sendMsg("결제가 완료 되었습니다.");
						moviethread.sendMsg("현재 잔액은 : " + clientinfo.getCust_money());
						clientinfo.allreset();
						break;
					} else if (strtmp.equals("n") || strtmp.equals("N")) {
						seat_emptyReset();
						clientinfo.allreset();

						moviethread.sendMsg("장바구니를 초기화 하였습니다. 처음으로 이동 합니다.");
						break;
					} else
						moviethread.sendMsg("잘못 입력 하셨습니다. Y 또는 N을 입력 해 주세요");

				}
			} else {
				moviethread.sendMsg(" 잔액이 부족 합니다. 처음으로 돌아 감니다.");
				seat_emptyReset();
				clientinfo.allreset();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void endPoint() {
		moviethread.sendMsg("처음으로 돌아 감니다.");
	}

}
