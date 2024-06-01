CREATE TABLE FRIENDSHIP (
    user_id BIGINT NOT NULL,
    friend_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (friend_id) REFERENCES User(id)
);

insert into budget(budget, budget_Date, user_id) values (550000, "2024-04-11", 1);
insert into budget(budget, budget_Date, user_id) values (600000, "2024-05-11", 1);

INSERT INTO SCHEDULE (user_id, schedule_id, schedule_date, category, title, expense, income) VALUES
(1, 10, '2024-03-06', 'APPOINTMENT', '백제정육점', 31334, 0),
(1, 20, '2024-02-26', 'APPOINTMENT', '졸업식팟', 71860, 0);

INSERT INTO ACCOUNT (user_id, account_id, schedule_id, created_at, money_type, category, title, cost, inex, memo) VALUES
(1, 1, NULL, '2024-03-13 12:21', 'BANK', 'FOOD', '라이프밀크티-채은', 4000, 'EXPENSE', NULL),
(1, 2, NULL, '2024-03-13 12:21', 'BANK', 'FOOD', '사장님돈까스-채은', 9500, 'EXPENSE', NULL),
(1, 3, NULL, '2024-03-11 12:20', 'BANK', 'FOOD', '스튜디오웝--채은', 5000, 'EXPENSE', NULL),
(1, 4, NULL, '2024-03-11 12:20', 'BANK', 'FOOD', '존재의이유-채은', 8000, 'EXPENSE', NULL),
(1, 5, NULL, '2024-03-10 16:20', 'BANK', 'FOOD', '공대이마트', 2700, 'EXPENSE', NULL),
(1, 6, NULL, '2024-03-08 18:19', 'BANK', 'FOOD', '창신역 gs', 3500, 'EXPENSE', NULL),
(1, 7, NULL, '2024-03-06 23:19', 'BANK', 'FOOD', '오봉도시락-영애', 4500, 'EXPENSE', NULL),
(1, 8, 10, '2024-03-06 18:19', 'BANK', 'FOOD', '백제정육점-영수효정', 31334, 'EXPENSE', NULL),
(1, 9, NULL, '2024-03-06 16:15', 'BANK', 'FOOD', '공대이마트', 1500, 'EXPENSE', NULL),
(1, 11, NULL, '2024-03-05 12:17', 'BANK', 'OTHER', '해비타트', 10000, 'EXPENSE', NULL),
(1, 12, NULL, '2024-03-04 18:36', 'BANK', 'ENTERTAINMENT', '코노-영수비차', 4000, 'EXPENSE', NULL),
(1, 13, NULL, '2024-03-04 13:36', 'BANK', 'FOOD', '딸기골-영수딱지', 6500, 'EXPENSE', NULL),
(1, 14, NULL, '2024-03-01 18:36', 'BANK', 'ALLOWANCE', NULL, 300000, 'INCOME', NULL),
(1, 15, NULL, '2024-02-29 18:34', 'BANK', 'SALARY', NULL, 499230, 'INCOME', NULL),
(1, 16, NULL, '2024-02-29 18:34', 'BANK', 'FOOD', '맥도날드-지민', 14100, 'EXPENSE', NULL),
(1, 17, NULL, '2024-02-29 14:34', 'BANK', 'FOOD', '백엔드팀플카페', 4100, 'EXPENSE', NULL),
(1, 18, 20, '2024-02-27 18:33', 'BANK', 'FOOD', '안테이쿠-세원하린(26일)', 39860, 'EXPENSE', NULL),
(1, 19, 20, '2024-02-26 18:33', 'BANK', 'PRESENT', '민아 졸업식 꽃다발', 32000, 'EXPENSE', NULL),
(1, 21, NULL, '2024-02-22 18:32', 'BANK', 'FASHION', '미용실 매직', 110000, 'EXPENSE', NULL);

INSERT INTO ACCOUNT (user_id, schedule_id, created_at, money_type, category, title, cost, inex, memo) VALUES
(1, NULL, '2024-05-20 11:59:56', 'BANK', 'FOOD', '공대학식-영애', 5500, 'EXPENSE', NULL),
(1, NULL, '2024-05-19 11:55:32', 'BANK', 'ENTERTAINMENT', '리디충전', 5000, 'EXPENSE', NULL),
(1, NULL, '2024-05-19 11:55:32', 'BANK', 'ENTERTAINMENT', '피너*', 9900, 'EXPENSE', NULL),
(1, NULL, '2024-05-19 11:55:32', 'BANK', 'ENTERTAINMENT', '리디충전', 10000, 'EXPENSE', NULL),
(1, NULL, '2024-05-19 11:55:32', 'BANK', 'ENTERTAINMENT', '플로결제', 1580, 'EXPENSE', NULL),
(1, NULL, '2024-05-18 11:53:27', 'BANK', 'FOOD', '안국역-채은', 20200, 'EXPENSE', NULL),
(1, NULL, '2024-05-18 11:53:27', 'BANK', 'PRESENT', '인사동', 5000, 'EXPENSE', NULL),
(1, NULL, '2024-05-17 11:52:25', 'BANK', 'FOOD', '딸기골-채은', 6000, 'EXPENSE', NULL),
(1, NULL, '2024-05-17 11:52:25', 'BANK', 'FOOD', '공대이마트-졸프', 2500, 'EXPENSE', NULL),
(1, NULL, '2024-05-17 11:52:25', 'BANK', 'FOOD', '존재의이유-채은', 8000, 'EXPENSE', NULL),
(1, NULL, '2024-05-14 11:52:10', 'BANK', 'TRANSPORTATION', '후불교통카드', 44800, 'EXPENSE', NULL),
(1, NULL, '2024-05-13 11:51:32', 'BANK', 'FOOD', '스튜디오웝--채은', 4500, 'EXPENSE', NULL),
(1, NULL, '2024-05-13 11:51:32', 'BANK', 'FOOD', '투썸아박-채은', 3900, 'EXPENSE', NULL),
(1, NULL, '2024-05-13 11:51:32', 'BANK', 'FOOD', '존재의이유-채은', 8000, 'EXPENSE', NULL),
(1, NULL, '2024-05-12 11:50:32', 'BANK', 'FOOD', '대동제뒤풀이(10일)', 10410, 'EXPENSE', NULL),
(1, NULL, '2024-05-12 11:50:32', 'BANK', 'FOOD', '북카페파오-라락', 5500, 'EXPENSE', NULL),
(1, NULL, '2024-05-11 11:48:44', 'BANK', 'ENTERTAINMENT', '넷플', 4250, 'EXPENSE', NULL),
(1, NULL, '2024-05-11 11:48:44', 'BANK', 'EDUCATION', 'SQLD 교재', 15000, 'EXPENSE', NULL),
(1, NULL, '2024-05-11 11:48:44', 'BANK', 'EDUCATION', '토익 48000', 45040, 'EXPENSE', NULL),
(1, NULL, '2024-05-10 10:58:38', 'BANK', 'FOOD', '탕화쿵푸-라락', 9000, 'EXPENSE', NULL),
(1, NULL, '2024-05-10 10:58:38', 'BANK', 'FOOD', '대동제', 4000, 'EXPENSE', NULL),
(1, NULL, '2024-05-08 10:57:51', 'BANK', 'FOOD', '대동제', 7500, 'EXPENSE', NULL),
(1, NULL, '2024-05-08 10:57:51', 'BANK', 'FOOD', '서브웨이-채은', 6700, 'EXPENSE', NULL),
(1, NULL, '2024-05-07 10:57:35', 'BANK', 'OTHER', '해비타트', 10000, 'EXPENSE', NULL),
(1, NULL, '2024-05-01', 'BANK', 'ALLOWANCE', '', 300000, 'INCOME', NULL);


insert into asratio(user_id, created_at, first, second, third, fourth, ratio_type) values(1, "2024-05-25", "FOOD,40", "EDUCATION,22", "TRANSPORTATION,19", "ENTERTAINMENT,11", 1);

