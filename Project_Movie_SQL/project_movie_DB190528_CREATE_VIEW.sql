--영화예매 뷰
CREATE VIEW cust_movie_list (예매번호, 영화, 상영시간, 지점, 상영관, 가격)
AS (SELECT r.reser_code, mk.movie_title, s.screening_time, mc.m_center_name, sc.sc_center_name, r.price
    FROM reservation r
    JOIN screening s ON (r.screening_code = s.screening_code)
    JOIN screen_center sc ON (s.sc_center_code = sc.sc_center_code)
    JOIN movie_center mc ON (sc.m_center_code = mc.m_center_code)
    JOIN movie_kind mk ON (s.movie_code = mk.movie_code)
    );
    
--매점거래명세 뷰
CREATE VIEW sn_purchase_list
AS(SELECT snack_sales.snack_sales_code,snack_sales.cust_id,snack_sales_list.snack_name,
snack_sales_list.snack_code, snack_sales_list.snack_count, snack_price,snack_sales.m_center_code, movie_center.m_center_name, snack_sales.sales_totalprice
  FROM snack_sales
  FULL JOIN snack_sales_list ON (snack_sales_list.snack_sales_code=snack_sales.snack_sales_code)
  FULL JOIN movie_center ON (snack_sales.m_center_code=movie_center.m_center_code));
  
COMMIT;