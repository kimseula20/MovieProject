--영화종류 INSERT문 
INSERT INTO Movie_kind
VALUES ('MK001', '어벤져스', 
'
 ASSEMBLE! 최강의 슈퍼히어로들이 모였다!
 지구의 운명을 건 거대한 전쟁이 시작된다!

 지구의 안보가 위협당하는 위기의 상황에서 슈퍼히어로들을 불러모아 세상을 구하는, 일명 [어벤져스] 작전. 
 에너지원 ‘큐브’를 이용한 적의 등장으로 인류가 위험에 처하자 
 국제평화유지기구인 쉴드 (S.H.I.E.L.D)의 국장 닉 퓨리(사무엘 L.잭슨)는 [어벤져스] 작전을 위해 
 전 세계에 흩어져 있던 슈퍼히어로들을 찾아나선다. 
 아이언맨(로버트 다우니 주니어)부터 토르(크리스 헴스워스), 헐크(마크 러팔로), 캡틴 아메리카(크리스 에반스)는 물론, 
 쉴드의 요원인 블랙 위도우(스칼렛 요한슨), 호크 아이(제레미 레너)까지, 
 최고의 슈퍼히어로들이 [어벤져스]의 멤버로 모이게 되지만, 
 각기 개성이 강한 이들의 만남은 예상치 못한 방향으로 흘러가는데… 
 지구의 운명을 건 거대한 전쟁 앞에 [어벤져스] 작전은 성공할 수 있을까?

', 
11000, 143, 4);
INSERT INTO Movie_kind
VALUES ('MK002', '어벤져스:에이지 오브 울트론', 
'
 어벤져스는 끝났다! 희망은 없다!

 쉴드의 숙적 히드라는 연구를 통해 새로운 능력자 막시모프 남매를 탄생시키고, 
 히드라의 기지를 공격하는 도중 토니 스타크는 완다 막시모프의 초능력으로 인해 자신이 
 가장 두려워하는 미래를 보게 된다.
 이에 "뉴욕전쟁" 때와 같은 사태가 벌어지지 않도록 스타크는 배너 박사와 함께 지구를 지킬 
 최강의 인공지능 울트론을 탄생시키게 되지만, 울트론은 예상과 다르게 지배를 벗어나 폭주하기 
 시작하는데...
 
', 
11000, 142, 3);
INSERT INTO Movie_kind
VALUES ('MK003', '어벤져스:인피니티 워', 
'
 새로운 조합을 이룬 어벤져스, 
 역대 최강 빌런 타노스에 맞서 세계의 운명이 걸린 
 인피니티 스톤을 향한 무한 대결이 펼쳐진다! 
  
 마블의 클라이맥스를 목격하라!
 
 ', 
 12000, 160, 2);
INSERT INTO Movie_kind
VALUES ('MK004', '어벤져스:엔드게임', 
'
 인피니티 워 이후 절반만 살아남은 지구
 마지막 희망이 된 어벤져스
 먼저 떠난 그들을 위해 모든 것을 걸었다!
 
 위대한 어벤져스
 운명을 바꿀 최후의 전쟁이 펼쳐진다!
 
 ', 
14000, 182, 1);

--지점 INSERT문
INSERT INTO Movie_center
VALUES ('MC001', '신촌지점', '서울 마포구 노고산동');
INSERT INTO Movie_center
VALUES ('MC002', '강남지점', '서울 강남구 역삼동');
INSERT INTO Movie_center
VALUES ('MC003', '홍대지점', '서울 마포구 동교동');

--상영관 INSERT문
INSERT INTO screen_center
VALUES ('SC001', '박보검관', 'MC001');
INSERT INTO screen_center
VALUES ('SC002', '차은우관', 'MC001');
INSERT INTO screen_center
VALUES ('SC003', '한가인관', 'MC001');
INSERT INTO screen_center
VALUES ('SC004', '김태희관', 'MC002');
INSERT INTO screen_center
VALUES ('SC005', '공유관', 'MC002');
INSERT INTO screen_center
VALUES ('SC006', '박보영관', 'MC002');
INSERT INTO screen_center
VALUES ('SC007', '조정석관', 'MC003');
INSERT INTO screen_center
VALUES ('SC008', '공효진관', 'MC003');
INSERT INTO screen_center
VALUES ('SC009', '고수관', 'MC003');

--상영시간 INSERT문
ALTER SESSION SET nls_date_format = 'YYYY-MM-DD HH24:MI';
commit;
--상영코드, 영화코드, 영화시작시간, 상영관코드
--영화1번 5월1일/3일/5일 시간 신촌지점 박보검관
INSERT INTO screening VALUES ('CC101', 'MK001', '2019-05-01 08:15', 'SC001');
INSERT INTO screening VALUES ('CC102', 'MK001', '2019-05-01 11:45', 'SC001');
INSERT INTO screening VALUES ('CC103', 'MK001', '2019-05-01 15:00', 'SC001');
INSERT INTO screening VALUES ('CC104', 'MK001', '2019-05-01 17:50', 'SC001');
INSERT INTO screening VALUES ('CC105', 'MK001', '2019-05-01 23:30', 'SC001');
INSERT INTO screening VALUES ('CC106', 'MK001', '2019-05-03 08:15', 'SC001');
INSERT INTO screening VALUES ('CC107', 'MK001', '2019-05-03 11:45', 'SC001');
INSERT INTO screening VALUES ('CC108', 'MK001', '2019-05-03 15:00', 'SC001');
INSERT INTO screening VALUES ('CC109', 'MK001', '2019-05-03 17:50', 'SC001');
INSERT INTO screening VALUES ('CC110', 'MK001', '2019-05-03 23:30', 'SC001');
INSERT INTO screening VALUES ('CC111', 'MK001', '2019-05-05 08:15', 'SC001');
INSERT INTO screening VALUES ('CC112', 'MK001', '2019-05-05 11:45', 'SC001');
INSERT INTO screening VALUES ('CC113', 'MK001', '2019-05-05 15:00', 'SC001');
INSERT INTO screening VALUES ('CC114', 'MK001', '2019-05-05 17:50', 'SC001');
INSERT INTO screening VALUES ('CC115', 'MK001', '2019-05-05 23:30', 'SC001');
--영화2번 5월2일/3일/4일 시간 신촌지점 차은우관
INSERT INTO screening VALUES ('CC201', 'MK002', '2019-05-02 09:35', 'SC002');
INSERT INTO screening VALUES ('CC202', 'MK002', '2019-05-02 13:55', 'SC002');
INSERT INTO screening VALUES ('CC203', 'MK002', '2019-05-02 16:15', 'SC002');
INSERT INTO screening VALUES ('CC204', 'MK002', '2019-05-02 20:40', 'SC002');
INSERT INTO screening VALUES ('CC205', 'MK002', '2019-05-02 23:55', 'SC002');
INSERT INTO screening VALUES ('CC206', 'MK002', '2019-05-03 09:35', 'SC002');
INSERT INTO screening VALUES ('CC207', 'MK002', '2019-05-03 13:55', 'SC002');
INSERT INTO screening VALUES ('CC208', 'MK002', '2019-05-03 16:15', 'SC002');
INSERT INTO screening VALUES ('CC209', 'MK002', '2019-05-03 20:40', 'SC002');
INSERT INTO screening VALUES ('CC210', 'MK002', '2019-05-03 23:55', 'SC002');
INSERT INTO screening VALUES ('CC211', 'MK002', '2019-05-04 09:35', 'SC002');
INSERT INTO screening VALUES ('CC212', 'MK002', '2019-05-04 13:55', 'SC002');
INSERT INTO screening VALUES ('CC213', 'MK002', '2019-05-04 16:15', 'SC002');
INSERT INTO screening VALUES ('CC214', 'MK002', '2019-05-04 20:40', 'SC002');
INSERT INTO screening VALUES ('CC215', 'MK002', '2019-05-04 23:55', 'SC002');
--영화3번 5월1일/2일/4일 시간 신촌지점 한가인관
INSERT INTO screening VALUES ('CC301', 'MK003', '2019-05-01 11:35', 'SC003');
INSERT INTO screening VALUES ('CC302', 'MK003', '2019-05-01 16:20', 'SC003');
INSERT INTO screening VALUES ('CC303', 'MK003', '2019-05-01 19:50', 'SC003');
INSERT INTO screening VALUES ('CC304', 'MK003', '2019-05-01 22:10', 'SC003');
INSERT INTO screening VALUES ('CC305', 'MK003', '2019-05-02 11:35', 'SC003');
INSERT INTO screening VALUES ('CC306', 'MK003', '2019-05-02 16:20', 'SC003');
INSERT INTO screening VALUES ('CC307', 'MK003', '2019-05-02 19:50', 'SC003');
INSERT INTO screening VALUES ('CC308', 'MK003', '2019-05-02 22:10', 'SC003');
INSERT INTO screening VALUES ('CC309', 'MK003', '2019-05-04 11:35', 'SC003');
INSERT INTO screening VALUES ('CC310', 'MK003', '2019-05-04 16:20', 'SC003');
INSERT INTO screening VALUES ('CC311', 'MK003', '2019-05-04 19:50', 'SC003');
INSERT INTO screening VALUES ('CC312', 'MK003', '2019-05-04 22:10', 'SC003');
--영화4번 5월2일/4일/1일/3일 신촌지점, 박보검관, 차은우관, 한가인관
INSERT INTO screening VALUES ('CC116', 'MK004', '2019-05-02 08:15', 'SC001');
INSERT INTO screening VALUES ('CC117', 'MK004', '2019-05-02 11:45', 'SC001');
INSERT INTO screening VALUES ('CC118', 'MK004', '2019-05-02 15:00', 'SC001');
INSERT INTO screening VALUES ('CC119', 'MK004', '2019-05-04 08:15', 'SC001');
INSERT INTO screening VALUES ('CC120', 'MK004', '2019-05-04 11:45', 'SC001');
INSERT INTO screening VALUES ('CC121', 'MK004', '2019-05-04 15:00', 'SC001');
INSERT INTO screening VALUES ('CC216', 'MK004', '2019-05-01 09:35', 'SC002');
INSERT INTO screening VALUES ('CC217', 'MK004', '2019-05-01 13:55', 'SC002');
INSERT INTO screening VALUES ('CC218', 'MK004', '2019-05-01 16:15', 'SC002');
INSERT INTO screening VALUES ('CC313', 'MK004', '2019-05-03 16:20', 'SC003');
INSERT INTO screening VALUES ('CC314', 'MK004', '2019-05-03 19:50', 'SC003');
INSERT INTO screening VALUES ('CC315', 'MK004', '2019-05-03 22:10', 'SC003');

--영화1번 5월1일/3일/5일 시간 강남지점 김태희관
INSERT INTO screening VALUES ('CC401', 'MK001', '2019-05-01 08:15', 'SC004');
INSERT INTO screening VALUES ('CC402', 'MK001', '2019-05-01 11:45', 'SC004');
INSERT INTO screening VALUES ('CC403', 'MK001', '2019-05-01 15:00', 'SC004');
INSERT INTO screening VALUES ('CC404', 'MK001', '2019-05-01 17:50', 'SC004');
INSERT INTO screening VALUES ('CC405', 'MK001', '2019-05-01 23:30', 'SC004');
INSERT INTO screening VALUES ('CC406', 'MK001', '2019-05-03 08:15', 'SC004');
INSERT INTO screening VALUES ('CC407', 'MK001', '2019-05-03 11:45', 'SC004');
INSERT INTO screening VALUES ('CC408', 'MK001', '2019-05-03 15:00', 'SC004');
INSERT INTO screening VALUES ('CC409', 'MK001', '2019-05-03 17:50', 'SC004');
INSERT INTO screening VALUES ('CC410', 'MK001', '2019-05-03 23:30', 'SC004');
INSERT INTO screening VALUES ('CC411', 'MK001', '2019-05-05 08:15', 'SC004');
INSERT INTO screening VALUES ('CC412', 'MK001', '2019-05-05 11:45', 'SC004');
INSERT INTO screening VALUES ('CC413', 'MK001', '2019-05-05 15:00', 'SC004');
INSERT INTO screening VALUES ('CC414', 'MK001', '2019-05-05 17:50', 'SC004');
INSERT INTO screening VALUES ('CC415', 'MK001', '2019-05-05 23:30', 'SC004');
--영화2번 5월2일/3일/4일 시간 강남지점 공유관
INSERT INTO screening VALUES ('CC501', 'MK002', '2019-05-02 09:35', 'SC005');
INSERT INTO screening VALUES ('CC502', 'MK002', '2019-05-02 13:55', 'SC005');
INSERT INTO screening VALUES ('CC503', 'MK002', '2019-05-02 16:15', 'SC005');
INSERT INTO screening VALUES ('CC504', 'MK002', '2019-05-02 20:40', 'SC005');
INSERT INTO screening VALUES ('CC505', 'MK002', '2019-05-02 23:55', 'SC005');
INSERT INTO screening VALUES ('CC506', 'MK002', '2019-05-03 09:35', 'SC005');
INSERT INTO screening VALUES ('CC507', 'MK002', '2019-05-03 13:55', 'SC005');
INSERT INTO screening VALUES ('CC508', 'MK002', '2019-05-03 16:15', 'SC005');
INSERT INTO screening VALUES ('CC509', 'MK002', '2019-05-03 20:40', 'SC005');
INSERT INTO screening VALUES ('CC510', 'MK002', '2019-05-03 23:55', 'SC005');
INSERT INTO screening VALUES ('CC511', 'MK002', '2019-05-04 09:35', 'SC005');
INSERT INTO screening VALUES ('CC512', 'MK002', '2019-05-04 13:55', 'SC005');
INSERT INTO screening VALUES ('CC513', 'MK002', '2019-05-04 16:15', 'SC005');
INSERT INTO screening VALUES ('CC514', 'MK002', '2019-05-04 20:40', 'SC005');
INSERT INTO screening VALUES ('CC515', 'MK002', '2019-05-04 23:55', 'SC005');
--영화3번 5월1일/2일/4일 시간 강남지점 박보영관
INSERT INTO screening VALUES ('CC601', 'MK003', '2019-05-01 11:35', 'SC006');
INSERT INTO screening VALUES ('CC602', 'MK003', '2019-05-01 16:20', 'SC006');
INSERT INTO screening VALUES ('CC603', 'MK003', '2019-05-01 19:50', 'SC006');
INSERT INTO screening VALUES ('CC604', 'MK003', '2019-05-01 22:10', 'SC006');
INSERT INTO screening VALUES ('CC605', 'MK003', '2019-05-02 11:35', 'SC006');
INSERT INTO screening VALUES ('CC606', 'MK003', '2019-05-02 16:20', 'SC006');
INSERT INTO screening VALUES ('CC607', 'MK003', '2019-05-02 19:50', 'SC006');
INSERT INTO screening VALUES ('CC608', 'MK003', '2019-05-02 22:10', 'SC006');
INSERT INTO screening VALUES ('CC609', 'MK003', '2019-05-04 11:35', 'SC006');
INSERT INTO screening VALUES ('CC610', 'MK003', '2019-05-04 16:20', 'SC006');
INSERT INTO screening VALUES ('CC611', 'MK003', '2019-05-04 19:50', 'SC006');
INSERT INTO screening VALUES ('CC612', 'MK003', '2019-05-04 22:10', 'SC006');
--영화4번 5월2일/4일/1일/3일 강남지점 김태희, 공유, 박보영관
INSERT INTO screening VALUES ('CC416', 'MK004', '2019-05-02 08:15', 'SC004');
INSERT INTO screening VALUES ('CC417', 'MK004', '2019-05-02 11:45', 'SC004');
INSERT INTO screening VALUES ('CC418', 'MK004', '2019-05-02 15:00', 'SC004');
INSERT INTO screening VALUES ('CC419', 'MK004', '2019-05-04 08:15', 'SC004');
INSERT INTO screening VALUES ('CC420', 'MK004', '2019-05-04 11:45', 'SC004');
INSERT INTO screening VALUES ('CC421', 'MK004', '2019-05-04 15:00', 'SC004');
INSERT INTO screening VALUES ('CC516', 'MK004', '2019-05-01 09:35', 'SC005');
INSERT INTO screening VALUES ('CC517', 'MK004', '2019-05-01 13:55', 'SC005');
INSERT INTO screening VALUES ('CC518', 'MK004', '2019-05-01 16:15', 'SC005');
INSERT INTO screening VALUES ('CC613', 'MK004', '2019-05-03 16:20', 'SC006');
INSERT INTO screening VALUES ('CC614', 'MK004', '2019-05-03 19:50', 'SC006');
INSERT INTO screening VALUES ('CC615', 'MK004', '2019-05-03 22:10', 'SC006');

--영화1번 5월1일/3일/5일 시간 홍대지점 조정석관
INSERT INTO screening VALUES ('CC701', 'MK001', '2019-05-01 08:15', 'SC007');
INSERT INTO screening VALUES ('CC702', 'MK001', '2019-05-01 11:45', 'SC007');
INSERT INTO screening VALUES ('CC703', 'MK001', '2019-05-01 15:00', 'SC007');
INSERT INTO screening VALUES ('CC704', 'MK001', '2019-05-01 17:50', 'SC007');
INSERT INTO screening VALUES ('CC705', 'MK001', '2019-05-01 23:30', 'SC007');
INSERT INTO screening VALUES ('CC706', 'MK001', '2019-05-03 08:15', 'SC007');
INSERT INTO screening VALUES ('CC707', 'MK001', '2019-05-03 11:45', 'SC007');
INSERT INTO screening VALUES ('CC708', 'MK001', '2019-05-03 15:00', 'SC007');
INSERT INTO screening VALUES ('CC709', 'MK001', '2019-05-03 17:50', 'SC007');
INSERT INTO screening VALUES ('CC710', 'MK001', '2019-05-03 23:30', 'SC007');
INSERT INTO screening VALUES ('CC711', 'MK001', '2019-05-05 08:15', 'SC007');
INSERT INTO screening VALUES ('CC712', 'MK001', '2019-05-05 11:45', 'SC007');
INSERT INTO screening VALUES ('CC713', 'MK001', '2019-05-05 15:00', 'SC007');
INSERT INTO screening VALUES ('CC714', 'MK001', '2019-05-05 17:50', 'SC007');
INSERT INTO screening VALUES ('CC715', 'MK001', '2019-05-05 23:30', 'SC007');
--영화2번 5월2일/3일/4일 시간 홍대지점 공효진관
INSERT INTO screening VALUES ('CC801', 'MK002', '2019-05-02 09:35', 'SC008');
INSERT INTO screening VALUES ('CC802', 'MK002', '2019-05-02 13:55', 'SC008');
INSERT INTO screening VALUES ('CC803', 'MK002', '2019-05-02 16:15', 'SC008');
INSERT INTO screening VALUES ('CC804', 'MK002', '2019-05-02 20:40', 'SC008');
INSERT INTO screening VALUES ('CC805', 'MK002', '2019-05-02 23:55', 'SC008');
INSERT INTO screening VALUES ('CC806', 'MK002', '2019-05-03 09:35', 'SC008');
INSERT INTO screening VALUES ('CC807', 'MK002', '2019-05-03 13:55', 'SC008');
INSERT INTO screening VALUES ('CC808', 'MK002', '2019-05-03 16:15', 'SC008');
INSERT INTO screening VALUES ('CC809', 'MK002', '2019-05-03 20:40', 'SC008');
INSERT INTO screening VALUES ('CC810', 'MK002', '2019-05-03 23:55', 'SC008');
INSERT INTO screening VALUES ('CC811', 'MK002', '2019-05-04 09:35', 'SC008');
INSERT INTO screening VALUES ('CC812', 'MK002', '2019-05-04 13:55', 'SC008');
INSERT INTO screening VALUES ('CC813', 'MK002', '2019-05-04 16:15', 'SC008');
INSERT INTO screening VALUES ('CC814', 'MK002', '2019-05-04 20:40', 'SC008');
INSERT INTO screening VALUES ('CC815', 'MK002', '2019-05-04 23:55', 'SC008');
--영화3번 5월1일/2일/4일 시간 홍대지점 고수관
INSERT INTO screening VALUES ('CC901', 'MK003', '2019-05-01 11:35', 'SC009');
INSERT INTO screening VALUES ('CC902', 'MK003', '2019-05-01 16:20', 'SC009');
INSERT INTO screening VALUES ('CC903', 'MK003', '2019-05-01 19:50', 'SC009');
INSERT INTO screening VALUES ('CC904', 'MK003', '2019-05-01 22:10', 'SC009');
INSERT INTO screening VALUES ('CC905', 'MK003', '2019-05-02 11:35', 'SC009');
INSERT INTO screening VALUES ('CC906', 'MK003', '2019-05-02 16:20', 'SC009');
INSERT INTO screening VALUES ('CC907', 'MK003', '2019-05-02 19:50', 'SC009');
INSERT INTO screening VALUES ('CC908', 'MK003', '2019-05-02 22:10', 'SC009');
INSERT INTO screening VALUES ('CC909', 'MK003', '2019-05-04 11:35', 'SC009');
INSERT INTO screening VALUES ('CC910', 'MK003', '2019-05-04 16:20', 'SC009');
INSERT INTO screening VALUES ('CC911', 'MK003', '2019-05-04 19:50', 'SC009');
INSERT INTO screening VALUES ('CC912', 'MK003', '2019-05-04 22:10', 'SC009');
--영화4번 5월2일/4일/1일/3일 홍대지점 조정석, 공효진, 고수관
INSERT INTO screening VALUES ('CC716', 'MK004', '2019-05-02 08:15', 'SC007');
INSERT INTO screening VALUES ('CC717', 'MK004', '2019-05-02 11:45', 'SC007');
INSERT INTO screening VALUES ('CC718', 'MK004', '2019-05-02 15:00', 'SC007');
INSERT INTO screening VALUES ('CC719', 'MK004', '2019-05-04 08:15', 'SC007');
INSERT INTO screening VALUES ('CC720', 'MK004', '2019-05-04 11:45', 'SC007');
INSERT INTO screening VALUES ('CC721', 'MK004', '2019-05-04 15:00', 'SC007');
INSERT INTO screening VALUES ('CC816', 'MK004', '2019-05-01 09:35', 'SC008');
INSERT INTO screening VALUES ('CC817', 'MK004', '2019-05-01 13:55', 'SC008');
INSERT INTO screening VALUES ('CC818', 'MK004', '2019-05-01 16:15', 'SC008');
INSERT INTO screening VALUES ('CC913', 'MK004', '2019-05-03 16:20', 'SC009');
INSERT INTO screening VALUES ('CC914', 'MK004', '2019-05-03 19:50', 'SC009');
INSERT INTO screening VALUES ('CC915', 'MK004', '2019-05-03 22:10', 'SC009');

--매점테이블 (pos코드, 스낵코드, 스낵이름, 스낵가격)
INSERT INTO snack_menu
VALUES ('SP001', '팝콘 오리지널 중', 4500);
INSERT INTO snack_menu
VALUES ('SP002', '팝콘 오리지널 대', 5000);
INSERT INTO snack_menu
VALUES ('SP003', '팝콘 달콤한맛 중', 5000);
INSERT INTO snack_menu
VALUES ('SP004', '팝콘 달콤한맛 대', 5500);
INSERT INTO snack_menu
VALUES ('SP005', '팝콘 어니언맛 중', 5500);
INSERT INTO snack_menu
VALUES ('SP006', '팝콘 어니언맛 대', 6000);
INSERT INTO snack_menu
VALUES ('SP007', '팝콘 더블치즈 중', 5500);
INSERT INTO snack_menu
VALUES ('SP008', '팝콘 더블치즈 대', 6000);
INSERT INTO snack_menu
VALUES ('SP009', '팝콘 콘소메맛 중', 5500);
INSERT INTO snack_menu
VALUES ('SP010', '팝콘 콘소메맛 대', 6000);
INSERT INTO snack_menu
VALUES ('SD011', '코카콜라', 2500);
INSERT INTO snack_menu
VALUES ('SD012', '칠성사이다', 2500);
INSERT INTO snack_menu
VALUES ('SD013', '자몽에이드', 3500);
INSERT INTO snack_menu
VALUES ('SD014', '레몬에이드', 3500);
INSERT INTO snack_menu
VALUES ('SD015', '블루레몬에이드', 3500);
INSERT INTO snack_menu
VALUES ('SD016', '오렌지에이드', 3500);
INSERT INTO snack_menu
VALUES ('SD017', 'ice아메리카노', 4000);
INSERT INTO snack_menu
VALUES ('SD018', '핫아메리카노', 3500);
INSERT INTO snack_menu
VALUES ('SD019', 'ice초코', 4000);
INSERT INTO snack_menu
VALUES ('SD020', '핫초코', 3500);
INSERT INTO snack_menu
VALUES ('SD021', '아이리스', 2000);
INSERT INTO snack_menu
VALUES ('SD022', '삼다수', 2000);
INSERT INTO snack_menu
VALUES ('SN031', '칠리치즈나쵸', 4500);
INSERT INTO snack_menu
VALUES ('SN032', '치킨스틱나쵸', 5000);
INSERT INTO snack_menu
VALUES ('SH033', '크림갈릭핫도그', 4500);
INSERT INTO snack_menu
VALUES ('SH034', '플레인핫도그', 4000);
INSERT INTO snack_menu
VALUES ('SH035', '칠리치즈핫도그', 4500);
INSERT INTO snack_menu
VALUES ('SH036', '불닭핫도그', 5500);
INSERT INTO snack_menu
VALUES ('SN037', '허니버터오징어', 4000);
INSERT INTO snack_menu
VALUES ('SI041', 'icecream초코', 2500);
INSERT INTO snack_menu
VALUES ('SI043', 'icecream딸기', 2500);
INSERT INTO snack_menu
VALUES ('SI044', 'icecream바닐라', 2500);
INSERT INTO snack_menu
VALUES ('SN039', '즉석오징어 몸통', 4500);
INSERT INTO snack_menu
VALUES ('SN040', '즉석오징어 다리', 4500);
INSERT INTO snack_menu
VALUES ('SN041', '즉석오징어 전체', 8500);
INSERT INTO snack_menu
VALUES ('SN042', '츄로스', 3000);
INSERT INTO snack_menu
VALUES ('SN043', 'ice츄로스', 4000);

COMMIT;