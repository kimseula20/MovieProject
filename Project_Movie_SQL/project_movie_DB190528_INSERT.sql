--��ȭ���� INSERT�� 
INSERT INTO Movie_kind
VALUES ('MK001', '�����', 
'
 ASSEMBLE! �ְ��� ��������ε��� �𿴴�!
 ������ ����� �� �Ŵ��� ������ ���۵ȴ�!

 ������ �Ⱥ��� �������ϴ� ������ ��Ȳ���� ��������ε��� �ҷ���� ������ ���ϴ�, �ϸ� [�����] ����. 
 �������� ��ť�ꡯ�� �̿��� ���� �������� �η��� ���迡 ó���� 
 ������ȭ�����ⱸ�� ���� (S.H.I.E.L.D)�� ���� �� ǻ��(�繫�� L.�轼)�� [�����] ������ ���� 
 �� ���迡 ����� �ִ� ��������ε��� ã�Ƴ�����. 
 ���̾��(�ι�Ʈ �ٿ�� �ִϾ�)���� �丣(ũ���� �𽺿���), ��ũ(��ũ ���ȷ�), ĸƾ �Ƹ޸�ī(ũ���� ���ݽ�)�� ����, 
 ������ ����� �� ������(��Į�� ���ѽ�), ȣũ ����(������ ����)����, 
 �ְ��� ��������ε��� [�����]�� ����� ���̰� ������, 
 ���� ������ ���� �̵��� ������ ����ġ ���� �������� �귯���µ��� 
 ������ ����� �� �Ŵ��� ���� �տ� [�����] ������ ������ �� ������?

', 
11000, 143, 4);
INSERT INTO Movie_kind
VALUES ('MK002', '�����:������ ���� ��Ʈ��', 
'
 ������� ������! ����� ����!

 ������ ���� ������ ������ ���� ���ο� �ɷ��� ���ø��� ���Ÿ� ź����Ű��, 
 ������� ������ �����ϴ� ���� ��� ��Ÿũ�� �ϴ� ���ø����� �ʴɷ����� ���� �ڽ��� 
 ���� �η����ϴ� �̷��� ���� �ȴ�.
 �̿� "��������" ���� ���� ���°� �������� �ʵ��� ��Ÿũ�� ��� �ڻ�� �Բ� ������ ��ų 
 �ְ��� �ΰ����� ��Ʈ���� ź����Ű�� ������, ��Ʈ���� ����� �ٸ��� ���踦 ��� �����ϱ� 
 �����ϴµ�...
 
', 
11000, 142, 3);
INSERT INTO Movie_kind
VALUES ('MK003', '�����:���Ǵ�Ƽ ��', 
'
 ���ο� ������ �̷� �����, 
 ���� �ְ� ���� Ÿ�뽺�� �¼� ������ ����� �ɸ� 
 ���Ǵ�Ƽ ������ ���� ���� ����� ��������! 
  
 ������ Ŭ���̸ƽ��� ����϶�!
 
 ', 
 12000, 160, 2);
INSERT INTO Movie_kind
VALUES ('MK004', '�����:�������', 
'
 ���Ǵ�Ƽ �� ���� ���ݸ� ��Ƴ��� ����
 ������ ����� �� �����
 ���� ���� �׵��� ���� ��� ���� �ɾ���!
 
 ������ �����
 ����� �ٲ� ������ ������ ��������!
 
 ', 
14000, 182, 1);

--���� INSERT��
INSERT INTO Movie_center
VALUES ('MC001', '��������', '���� ������ ���굿');
INSERT INTO Movie_center
VALUES ('MC002', '��������', '���� ������ ���ﵿ');
INSERT INTO Movie_center
VALUES ('MC003', 'ȫ������', '���� ������ ������');

--�󿵰� INSERT��
INSERT INTO screen_center
VALUES ('SC001', '�ں��˰�', 'MC001');
INSERT INTO screen_center
VALUES ('SC002', '�������', 'MC001');
INSERT INTO screen_center
VALUES ('SC003', '�Ѱ��ΰ�', 'MC001');
INSERT INTO screen_center
VALUES ('SC004', '�������', 'MC002');
INSERT INTO screen_center
VALUES ('SC005', '������', 'MC002');
INSERT INTO screen_center
VALUES ('SC006', '�ں�����', 'MC002');
INSERT INTO screen_center
VALUES ('SC007', '��������', 'MC003');
INSERT INTO screen_center
VALUES ('SC008', '��ȿ����', 'MC003');
INSERT INTO screen_center
VALUES ('SC009', '�����', 'MC003');

--�󿵽ð� INSERT��
ALTER SESSION SET nls_date_format = 'YYYY-MM-DD HH24:MI';
commit;
--���ڵ�, ��ȭ�ڵ�, ��ȭ���۽ð�, �󿵰��ڵ�
--��ȭ1�� 5��1��/3��/5�� �ð� �������� �ں��˰�
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
--��ȭ2�� 5��2��/3��/4�� �ð� �������� �������
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
--��ȭ3�� 5��1��/2��/4�� �ð� �������� �Ѱ��ΰ�
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
--��ȭ4�� 5��2��/4��/1��/3�� ��������, �ں��˰�, �������, �Ѱ��ΰ�
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

--��ȭ1�� 5��1��/3��/5�� �ð� �������� �������
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
--��ȭ2�� 5��2��/3��/4�� �ð� �������� ������
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
--��ȭ3�� 5��1��/2��/4�� �ð� �������� �ں�����
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
--��ȭ4�� 5��2��/4��/1��/3�� �������� ������, ����, �ں�����
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

--��ȭ1�� 5��1��/3��/5�� �ð� ȫ������ ��������
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
--��ȭ2�� 5��2��/3��/4�� �ð� ȫ������ ��ȿ����
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
--��ȭ3�� 5��1��/2��/4�� �ð� ȫ������ �����
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
--��ȭ4�� 5��2��/4��/1��/3�� ȫ������ ������, ��ȿ��, �����
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

--�������̺� (pos�ڵ�, �����ڵ�, �����̸�, ��������)
INSERT INTO snack_menu
VALUES ('SP001', '���� �������� ��', 4500);
INSERT INTO snack_menu
VALUES ('SP002', '���� �������� ��', 5000);
INSERT INTO snack_menu
VALUES ('SP003', '���� �����Ѹ� ��', 5000);
INSERT INTO snack_menu
VALUES ('SP004', '���� �����Ѹ� ��', 5500);
INSERT INTO snack_menu
VALUES ('SP005', '���� ��Ͼ�� ��', 5500);
INSERT INTO snack_menu
VALUES ('SP006', '���� ��Ͼ�� ��', 6000);
INSERT INTO snack_menu
VALUES ('SP007', '���� ����ġ�� ��', 5500);
INSERT INTO snack_menu
VALUES ('SP008', '���� ����ġ�� ��', 6000);
INSERT INTO snack_menu
VALUES ('SP009', '���� �ܼҸ޸� ��', 5500);
INSERT INTO snack_menu
VALUES ('SP010', '���� �ܼҸ޸� ��', 6000);
INSERT INTO snack_menu
VALUES ('SD011', '��ī�ݶ�', 2500);
INSERT INTO snack_menu
VALUES ('SD012', 'ĥ�����̴�', 2500);
INSERT INTO snack_menu
VALUES ('SD013', '�ڸ����̵�', 3500);
INSERT INTO snack_menu
VALUES ('SD014', '�����̵�', 3500);
INSERT INTO snack_menu
VALUES ('SD015', '��緹���̵�', 3500);
INSERT INTO snack_menu
VALUES ('SD016', '���������̵�', 3500);
INSERT INTO snack_menu
VALUES ('SD017', 'ice�Ƹ޸�ī��', 4000);
INSERT INTO snack_menu
VALUES ('SD018', '�־Ƹ޸�ī��', 3500);
INSERT INTO snack_menu
VALUES ('SD019', 'ice����', 4000);
INSERT INTO snack_menu
VALUES ('SD020', '������', 3500);
INSERT INTO snack_menu
VALUES ('SD021', '���̸���', 2000);
INSERT INTO snack_menu
VALUES ('SD022', '��ټ�', 2000);
INSERT INTO snack_menu
VALUES ('SN031', 'ĥ��ġ���', 4500);
INSERT INTO snack_menu
VALUES ('SN032', 'ġŲ��ƽ����', 5000);
INSERT INTO snack_menu
VALUES ('SH033', 'ũ�������ֵ���', 4500);
INSERT INTO snack_menu
VALUES ('SH034', '�÷����ֵ���', 4000);
INSERT INTO snack_menu
VALUES ('SH035', 'ĥ��ġ���ֵ���', 4500);
INSERT INTO snack_menu
VALUES ('SH036', '�Ҵ��ֵ���', 5500);
INSERT INTO snack_menu
VALUES ('SN037', '��Ϲ��Ϳ�¡��', 4000);
INSERT INTO snack_menu
VALUES ('SI041', 'icecream����', 2500);
INSERT INTO snack_menu
VALUES ('SI043', 'icecream����', 2500);
INSERT INTO snack_menu
VALUES ('SI044', 'icecream�ٴҶ�', 2500);
INSERT INTO snack_menu
VALUES ('SN039', '�Ｎ��¡�� ����', 4500);
INSERT INTO snack_menu
VALUES ('SN040', '�Ｎ��¡�� �ٸ�', 4500);
INSERT INTO snack_menu
VALUES ('SN041', '�Ｎ��¡�� ��ü', 8500);
INSERT INTO snack_menu
VALUES ('SN042', '��ν�', 3000);
INSERT INTO snack_menu
VALUES ('SN043', 'ice��ν�', 4000);

COMMIT;