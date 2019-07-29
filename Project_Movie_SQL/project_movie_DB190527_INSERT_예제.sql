INSERT INTO Customer
VALUES('CN'||lpad(reservation_seq.NEXTVAL,6,0),'CN001','CC101');
commit;

ALTER TABLE reservation ADD (ticketing_time VARCHAR(50));
--SELECT * FROM reservation;

--예매 정보 INSERT문
INSERT INTO reservation
VALUES('RE'||lpad(reservation_seq.NEXTVAL,6,0),'CN001','CC101',11000 );
DELETE reservation;
commit;

--매점매출 INSERT문
INSERT INTO snack_sale 
VALUES ('SS'||lpad(snack_sales_seq.NEXTVAL,6,0),'MC001',30000,'05월27일07시34분');

--거래내역목록 INSERT문
INSERT INTO snack_sales_list 
VALUES ('SS000001','SP001',4,6000);

--좌석예매 INSERT문
INSERT INTO SEAT_RESERVE(seat_r_code) 
VALUES('SEAT'||lpad(seat_seq.NEXTVAL,6,0));

-- seat_reserve  시퀀스 사용 방법 앞자리에 문자 seat을 넣어 준다
INSERT INTO SEAT_RESERVE(seat_r_code) 
VALUES('SEAT'||lpad(seat_seq.NEXTVAL,6,0));

-- 시트 생성 양식 --
-- WHERE screening_code 값만 바꿔주면 된다..
-- 새로운 영화가 들어 올시 그 코드를 받아 오면 
-- 그 코드에 대한 시간이 정해 지면 자동으로 생성 되게 한다.
INSERT INTO SEAT_RESERVE (SEAT_R_CODE,SEAT_NUM,SEAT_empty,screening_code)
SELECT 'SEAT'||lpad(seat_seq.NEXTVAL,6,0),seat_num,1,screening_code
FROM seat
NATURAL JOIN screening
WHERE screening_code ='CC209';

SELECT seat_num,seat_name,seat_empty FROM seat_reserve
NATURAL JOIN seat WHERE screening_code ='CC101' ORDER BY seat_name DESC;

-- 앞에 seat붙이는 방법
DELETE FROM seat_reserve;
SELECT 'seat' || lpad(seat_seq.nextval,6,0) FROM DUAL;

-- 사용법 문자열 추가로 사용
SELECT 'SEAT'||lpad(seat_seq.NEXTVAL,6,0),seat_num,1,screening_code
FROM seat
NATURAL JOIN screening
WHERE screening_code ='CC209';

--SELECT * FROM seat_reserve;

SELECT * FROM seat;
INSERT INTO seat
VALUES ('M01S02001','A-01',1,'SC001');
ROLLBACK;

SELECT * FROM seat;
SELECT seat_num,seat_name,seat_empty 
FROM seat 
NATURAL JOIN screen_center 
WHERE sc_center_code ='SC001';