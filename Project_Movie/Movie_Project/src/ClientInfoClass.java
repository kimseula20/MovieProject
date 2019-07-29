import java.io.PrintWriter;
import java.util.ArrayList;


public class ClientInfoClass {
	
	private String cust_id, cust_name, cust_num;
	private String movie_Code, m_center_code, sc_center_code, screening_code,snack_pos_code;
	private String movie_title, m_center_name, sc_center_name, screening_time,snack_pos_name;
	private int stage;
	private ArrayList<Snack_item> snack_shopping;
	private ArrayList<Seat_reserve> seat_reserve;
	private int moviePrice,totalPrice;
	private int snackPrice;
	private int cust_money;
	private PrintWriter pw;
	private boolean login;

	// �������� Ŭ���� ���� �ڵ�� �̸��� ���
	class Snack_item {
		// �����ڵ�
		private String snack_code;
		// ���� �̸�
		private String snack_name;
		// ���� ����
		private int snack_price;
		// ���� ���� ����
		private int snack_count;

		public String getSnack_code() {
			return snack_code;
		}

		public void setSnack_code(String snack_code) {
			this.snack_code = snack_code;
		}

		public String getSnack_name() {
			return snack_name;
		}

		public void setSnack_name(String snack_name) {
			this.snack_name = snack_name;
		}

		// ���� ���� get/set �߰�
		public int getSnack_count() {
			return snack_count;
		}

		public void setSnack_count(int snack_count) {
			this.snack_count = snack_count;
		}

		// ���� ���� get/set �߰�
		public int getSnack_price() {
			return snack_price;
		}

		public void setSnack_price(int snack_price) {
			this.snack_price = snack_price;
		}
	}

	// �¼� ���� Ŭ����
	class Seat_reserve {
		String seat_r_code;
		String seat_name;

		public String getSeat_r_code() {
			return seat_r_code;
		}

		public void setSeat_r_code(String seat_r_code) {
			this.seat_r_code = seat_r_code;
		}

		public String getSeat_name() {
			return seat_name;
		}

		public void setSeat_name(String seat_name) {
			this.seat_name = seat_name;
		}
	}

	ClientInfoClass(PrintWriter pw) {
		this.pw = pw;
		this.snack_shopping = new ArrayList<Snack_item>();
		this.seat_reserve = new ArrayList<Seat_reserve>();
		this.login = false;
	}

	public void allreset() {
		this.movie_Code = null;
		this.movie_title = null;
		this.m_center_code = null;
		this.m_center_name = null;
		this.sc_center_name = null;
		this.sc_center_code = null;
		this.screening_code= null;
		this.screening_time =null;
		this.totalPrice =0;			
		this.getSeat_reserve().clear();
	}

	public String getCust_id() {
		return cust_id;
	}

	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}

	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	public String getCust_num() {
		return cust_num;
	}

	public void setCust_num(String cust_num) {
		this.cust_num = cust_num;
	}

	public String getMovie_Code() {
		return movie_Code;
	}

	public void setMovie_Code(String movie_Code) {
		this.movie_Code = movie_Code;
	}

	public String getM_center_code() {
		return m_center_code;
	}

	public void setM_center_code(String m_center_code) {
		this.m_center_code = m_center_code;
	}

	public String getSc_center_code() {
		return sc_center_code;
	}

	public void setSc_center_code(String sc_center_code) {
		this.sc_center_code = sc_center_code;
	}

	public String getMovie_title() {
		return movie_title;
	}

	public void setMovie_title(String movie_title) {
		this.movie_title = movie_title;
	}

	public String getM_center_name() {
		return m_center_name;
	}

	public void setM_center_name(String m_center_name) {
		this.m_center_name = m_center_name;
	}

	public String getSc_center_name() {
		return sc_center_name;
	}

	public void setSc_center_name(String sc_center_name) {
		this.sc_center_name = sc_center_name;
	}

	public String resetSnack() {
		snack_shopping.clear();
		return "��ٱ��ϸ� ���̽��ϴ�.";
	}

	///////////////////////////////////////////
	// ArrayList ���� ��ٱ��Ͽ� �ڵ����� �߰� �Ǵ� �޼ҵ�
	public void setSnack_item(String snack_code, String snack_name, int snack_price) {
		Snack_item snack_item = new Snack_item();
		snack_item.setSnack_code(snack_code);
		snack_item.setSnack_name(snack_name);
		snack_item.setSnack_price(snack_price);
		snack_shopping.add(snack_item);
	}

	public ArrayList<Snack_item> getSnack_shopping() {
		return snack_shopping;
	}

	//////////////////////////////////////////
	// ArrayList�� �ڵ����� �߰� �Ǵ� �޼ҵ�
	public void setSeat_reserve(String seat_r_code, String seat_name) {
		Seat_reserve seat = new Seat_reserve();
		seat.setSeat_r_code(seat_r_code);
		seat.setSeat_name(seat_name);
		seat_reserve.add(seat);

	}
	public void resetSeat() {
		seat_reserve.clear();
	}

	public ArrayList<Seat_reserve> getSeat_reserve() {
		return seat_reserve;
	}
	
	public int getMoviePrice() {
		return moviePrice;
	}

	public void setMoviePrice(int moviePrice) {
		this.moviePrice = moviePrice;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getSnackPrice() {
		    return snackPrice;
	}
	public void setSnackPrice(int snackPrice) {
		    this.snackPrice = snackPrice;
	}

	public PrintWriter getPw() {
		return pw;
	}

	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}

	public boolean getLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public int getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

	public String getScreening_code() {
		return screening_code;
	}

	public void setScreening_code(String screening_code) {
		this.screening_code = screening_code;
	}

	public String getScreening_time() {
		return screening_time;
	}

	public void setScreening_time(String screening_time) {
		this.screening_time = screening_time;
	}

	public String getSnack_pos_code() {
		return snack_pos_code;
	}

	public void setSnack_pos_code(String snack_pos_code) {
		this.snack_pos_code = snack_pos_code;
	}

	public String getSnack_pos_name() {
		return snack_pos_name;
	}

	public void setSnack_pos_name(String snack_pos_name) {
		this.snack_pos_name = snack_pos_name;
	}

	public int getCust_money() {
		return cust_money;
	}

	public void setCust_money(int cust_money) {
		this.cust_money = cust_money;
	}

}
