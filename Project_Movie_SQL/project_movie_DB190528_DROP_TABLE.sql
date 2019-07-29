--매점테이블 삭제
DROP TABLE snack_menu;
--예매정보 테이블 삭제
DROP TABLE reservation;
--좌석테이블 삭제
DROP TABLE seat;
--매점매출 삭제
DROP TABLE snack_sales;
--거래내역목록 테이블 삭제
DROP TABLE snack_sales_list;
--상영시간 테이블 삭제
DROP TABLE screening;
--상영관테이블 삭제
DROP TABLE screen_center;
--영화종류 및 영화정보 테이블 삭제
DROP TABLE Movie_kind;
--지점테이블 삭제
DROP TABLE Movie_center;
--고객정보테이블 삭제
DROP TABLE Customer_Info;
--고객테이블 삭제
DROP TABLE Customer;
--좌석예매 테이블 삭제
--DROP TABLE seat_reserve;
--예매정보 취소 테이블 삭제
DROP TABLE cancel_reser;
--매점매출 취소테이블
DROP TABLE cancel_snack_sales;
--매점거래내역목록 취소테이블
DROP TABLE cancel_snack_sales_list;
--좌석예매 코드저장 취소테이블
DROP TABLE seat_reser_list;

DROP VIEW cust_movie_list;
DROP VIEW sn_purchase_list;

COMMIT;