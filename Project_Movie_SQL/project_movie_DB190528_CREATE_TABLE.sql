--고객테이블
CREATE TABLE Customer(
  cust_num VARCHAR2(20),
  cust_name VARCHAR2(50) NOT NULL,
  cust_phone VARCHAR2(50) NOT NULL,
  cust_money NUMBER NOT NULL,
  CONSTRAINT Customer_pk_cust_num PRIMARY KEY (cust_num)
);

--고객정보테이블
CREATE TABLE Customer_Info(
 cust_ID VARCHAR2(16),
 cust_PW VARCHAR2(50) NOT NULL CHECK(LENGTH(cust_PW)>3),
 cust_num VARCHAR2(20),
 CONSTRAINT C_Info_pk_cust_ID PRIMARY KEY (cust_ID),
 CONSTRAINT C_Info_fk_cust_num FOREIGN KEY (cust_num)
                               REFERENCES Customer(cust_num)
);

--영화종류 및 정보 테이블
CREATE TABLE Movie_kind(
 movie_code VARCHAR2(20),
 movie_title VARCHAR2(50) NOT NULL,
 movie_Info VARCHAR2(2000) NOT NULL,
 movie_price NUMBER NOT NULL CHECK(movie_price > 0),
 running_time NUMBER NOT NULL CHECK(running_time > 0 ),
 movie_rank NUMBER NOT NULL,
 CONSTRAINT Movie_kind_uk_movie_rank UNIQUE (movie_rank),
 CONSTRAINT Movie_kind_pk_movie_code PRIMARY KEY (movie_code)
);

--지점 테이블
CREATE TABLE Movie_center(
 m_center_code VARCHAR2(20),
 m_center_name VARCHAR2(50) NOT NULL,
 m_center_addr VARCHAR2(50) NOT NULL,
 CONSTRAINT Movie_center_pk_m_code PRIMARY KEY (m_center_code)
);

--영화관 안에 있는 상영관테이블
CREATE TABLE screen_center(
 sc_center_code VARCHAR2(20),
 sc_center_name VARCHAR2(50) NOT NULL,
 m_center_code VARCHAR2(20),
 CONSTRAINT sc_pk_center_code PRIMARY KEY (sc_center_code),
 CONSTRAINT sc_fk_m_center_code FOREIGN KEY (m_center_code)
                                REFERENCES Movie_center(m_center_code)
);

--상영시간 테이블
CREATE TABLE screening(
 screening_code VARCHAR2(20),
 movie_code VARCHAR2(20),
 screening_time DATE NOT NULL,
 sc_center_code VARCHAR2(20),
 CONSTRAINT screening_pk_screening_code PRIMARY KEY (screening_code),
 CONSTRAINT screening_fk_movie_code FOREIGN KEY (movie_code)
                                        REFERENCES Movie_kind(movie_code),
 CONSTRAINT screening_fk_sc_center_code FOREIGN KEY (sc_center_code)
                                        REFERENCES screen_center(sc_center_code)
);

--매점 테이블
CREATE TABLE snack_menu(
 snack_code VARCHAR2(20) NOT NULL,
 snack_name VARCHAR2(50) NOT NULL,
 snack_price NUMBER NOT NULL CHECK(snack_price > 0),
 CONSTRAINT snack_menu_pk_snack_code PRIMARY KEY (snack_code)
);

--예매정보 테이블
CREATE TABLE reservation(
 reser_code VARCHAR2(20),
 cust_num VARCHAR2(20),
 screening_code VARCHAR2(10),
 cust_count NUMBER NOT NULL,
 seat_re_name VARCHAR(50),
 price NUMBER CHECK(price >= 0),
 CONSTRAINT reservation_pk_reser_code PRIMARY KEY (reser_code),
 CONSTRAINT reservation_fk_cust_num FOREIGN KEY (cust_num)
                                    REFERENCES Customer(cust_num),
 CONSTRAINT reservation_fk_screening_code FOREIGN KEY (screening_code)
                                    REFERENCES screening(screening_code)
);

--좌석 테이블
CREATE TABLE seat(
 seat_num VARCHAR2(20),
 seat_name VARCHAR2(10)NOT NULL,
 seat_empty NUMBER NOT NULL ,
 sc_center_code VARCHAR2(20),
 CONSTRAINT seat_fk_seat_num PRIMARY KEY (seat_num),
 CONSTRAINT seat_fk_sc_center_code FOREIGN KEY (sc_center_code)
                                   REFERENCES screen_center(sc_center_code)
);

--매점매출 테이블
CREATE TABLE snack_sales(
 snack_sales_code VARCHAR2(20) PRIMARY KEY,
 cust_id VARCHAR(20) NOT NULL,
 m_center_code VARCHAR2(20) NOT NULL,
 sales_totalprice NUMBER NOT NULL CHECK(sales_totalprice > 0),
 sales_date VARCHAR(50) NOT NULL,
CONSTRAINT snack_cust_id FOREIGN KEY (cust_id)
                         REFERENCES customer_info(cust_id)
);

-- 거래내역 목록 테이블 위에 목록을 참조하여 거래번호를 이용 하여 목록들을 저장
CREATE TABLE snack_sales_list(
snack_sales_code VARCHAR(20) NOT NULL,
snack_code VARCHAR(20) NOT NULL,
snack_name VARCHAR(50) NOT NULL,
snack_count NUMBER NOT NULL CHECK(snack_count > 0),
snack_price NUMBER NOT NULL CHECK(snack_price > 0)
);

--예매정보 취소테이블
CREATE TABLE cancel_reser(
 reser_code VARCHAR2(20),
 cust_num VARCHAR2(20),
 screening_code VARCHAR2(10),
 cust_count NUMBER NOT NULL,
 seat_re_name VARCHAR(50),
 price NUMBER CHECK(price >= 0)
);

--매점거래내역목록 취소테이블
CREATE TABLE cancel_snack_sales_list(
snack_sales_code VARCHAR(20) NOT NULL,
snack_code VARCHAR(20) NOT NULL,
snack_name VARCHAR(50) NOT NULL,
snack_count NUMBER NOT NULL CHECK(snack_count > 0),
snack_price NUMBER NOT NULL CHECK(snack_price > 0)
);

--매점매출 취소테이블
CREATE TABLE cancel_snack_sales(
 snack_sales_code VARCHAR2(20) PRIMARY KEY,
 cust_id VARCHAR(20) NOT NULL,
 m_center_code VARCHAR2(20) NOT NULL,
 sales_totalprice NUMBER NOT NULL CHECK(sales_totalprice > 0),
 sales_date VARCHAR(50) NOT NULL
);

--좌석예매 테이블
CREATE TABLE seat_reserve(
seat_r_code VARCHAR(20) NOT NULL PRIMARY KEY,
seat_num VARCHAR2(20),
seat_empty NUMBER CONSTRAINT seat_re_check CHECK(seat_empty IN(0,1)),
screening_code VARCHAR2(20),
CONSTRAINT seat_re_code_fk FOREIGN KEY(seat_num)
                      REFERENCES seat(seat_num),
CONSTRAINT seat_Re_sccode_fk FOREIGN KEY(screening_code)
                      REFERENCES screening(screening_code)
);

--좌석 예매 리스트 코드저장 테이블
CREATE TABLE seat_reser_list(
reser_code VARCHAR2(20),
seat_r_code VARCHAR2(20)
);
COMMIT;