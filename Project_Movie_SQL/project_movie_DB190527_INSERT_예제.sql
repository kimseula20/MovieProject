INSERT INTO Customer
VALUES('CN'||lpad(reservation_seq.NEXTVAL,6,0),'CN001','CC101');
commit;

ALTER TABLE reservation ADD (ticketing_time VARCHAR(50));
--SELECT * FROM reservation;

--���� ���� INSERT��
INSERT INTO reservation
VALUES('RE'||lpad(reservation_seq.NEXTVAL,6,0),'CN001','CC101',11000 );
DELETE reservation;
commit;

--�������� INSERT��
INSERT INTO snack_sale 
VALUES ('SS'||lpad(snack_sales_seq.NEXTVAL,6,0),'MC001',30000,'05��27��07��34��');

--�ŷ�������� INSERT��
INSERT INTO snack_sales_list 
VALUES ('SS000001','SP001',4,6000);

--�¼����� INSERT��
INSERT INTO SEAT_RESERVE(seat_r_code) 
VALUES('SEAT'||lpad(seat_seq.NEXTVAL,6,0));

-- seat_reserve  ������ ��� ��� ���ڸ��� ���� seat�� �־� �ش�
INSERT INTO SEAT_RESERVE(seat_r_code) 
VALUES('SEAT'||lpad(seat_seq.NEXTVAL,6,0));

-- ��Ʈ ���� ��� --
-- WHERE screening_code ���� �ٲ��ָ� �ȴ�..
-- ���ο� ��ȭ�� ��� �ý� �� �ڵ带 �޾� ���� 
-- �� �ڵ忡 ���� �ð��� ���� ���� �ڵ����� ���� �ǰ� �Ѵ�.
INSERT INTO SEAT_RESERVE (SEAT_R_CODE,SEAT_NUM,SEAT_empty,screening_code)
SELECT 'SEAT'||lpad(seat_seq.NEXTVAL,6,0),seat_num,1,screening_code
FROM seat
NATURAL JOIN screening
WHERE screening_code ='CC209';

SELECT seat_num,seat_name,seat_empty FROM seat_reserve
NATURAL JOIN seat WHERE screening_code ='CC101' ORDER BY seat_name DESC;

-- �տ� seat���̴� ���
DELETE FROM seat_reserve;
SELECT 'seat' || lpad(seat_seq.nextval,6,0) FROM DUAL;

-- ���� ���ڿ� �߰��� ���
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