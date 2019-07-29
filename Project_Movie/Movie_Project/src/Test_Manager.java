import java.util.ArrayList;
import java.util.Scanner;

public class Test_Manager {
	public static void main(String[] args) {
		Manager_start m_start = new Manager_start();
		
		m_start.subMain();

	}
}

class Manager_start {

	Scanner scan = new Scanner(System.in);
	Info info;

	Manager_start() {
		this.info = new Info();

	}

	void subMain() {

		while (true) {
			int choice = 0;
			System.out.println("���� ����");
			choice = scan.nextInt();
			switch (choice) {

			case 1:
				movieChoice();
				break;

			case 2:

				break;

			case 3:

				break;

			}

		}
	}

	void movieChoice() {

		if (info.getMovie()==null) {
			System.out.println("��ȭ�� �����ϼ���");
			System.out.println("��ȭ1");
			System.out.println("��ȭ2");
			System.out.println("��ȭ3");
			System.out.println("��ȭ4");
			System.out.println("��ȭ5");

			String movie = scan.next();
			System.out.println(movie + " ���� �ϼ̽��ϴ�.");
			info.setMovie(movie);
			
			} 
		if(info.getMovie_center()==null){
				movieCenter();
		}		
		else{
			System.out.println("�̹� ��ȭ�� ���� �ϼ̽��ϴ�.");
			movieDayTime();
		}
		}

	void movieCenter() {
		if(info.getMovie_center() == null) {
		System.out.println("��ȭ���� �����ϼ���");
		System.out.println("��ȭ��1");
		System.out.println("��ȭ��2");
		System.out.println("��ȭ��3");
		System.out.println("��ȭ��4");
		System.out.println("��ȭ��5");

		String movie = scan.next();
		System.out.println(movie + " ���� �ϼ̽��ϴ�.");
		info.setMovie_center(movie);
		}
		if(info.getMovie()==null) {
			movieChoice();
		}
		else {
			System.out.println("�������� �Ѿ� ���ϴ�.");
			movieDayTime();
		}
			
	}

	void movieDayTime() {

		
		if(info.getMovie_center()==null || 
				info.getMovie()==null) {
			movieChoice();
			
		}
		
		if(info.getMovie() !=null && info.getMovie_center() !=null) {
		System.out.println("��ȭ��¥�� �����ϼ���");
		System.out.println("11��");
		System.out.println("12��");
		System.out.println("13��");
		String movie = scan.next();
		System.out.println(movie + " ���� �ϼ̽��ϴ�.");
		info.setDay(movie);

		System.out.println("��ȭ �ð���  �����ϼ���");
		System.out.println("1��");
		System.out.println("2��");
		System.out.println("3��");
		movie = scan.next();
		System.out.println(movie + " ���� �ϼ̽��ϴ�.");
		info.setTime(movie);
		
		seatNum();
		}
		
			

	}

	void seatNum() {
		System.out.println("�¼��� �����ϼ���");
		System.out.println("1-1");
		System.out.println("1-2");
		System.out.println("1-3");
		String movie = scan.next();
		System.out.println(movie + " ���� �ϼ̽��ϴ�.");
		info.setSeat_num(movie);
	}

}

class Info {

	String name;
	String movie;
	String day;
	String time;
	String movie_center;
	String seat_num;
	int peopleCount;
	int price;

	Info(){
		this.name = null;
		this.movie = null;
		this.day = null;
		this.time = null;
		this.movie_center  = null;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMovie() {
		return movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMovie_center() {
		return movie_center;
	}

	public void setMovie_center(String movie_center) {
		this.movie_center = movie_center;
	}

	public String getSeat_num() {
		return seat_num;
	}

	public void setSeat_num(String seat_num) {
		this.seat_num = seat_num;
	}

	public int getPeopleCount() {
		return peopleCount;
	}

	public void setPeopleCount(int peopleCount) {
		this.peopleCount = peopleCount;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}