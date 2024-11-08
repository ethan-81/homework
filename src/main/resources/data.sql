INSERT INTO POINT_TYPE (code, name, max_earn_amount, min_earn_amount, max_holding_amount, valid_day, active)
VALUES
    ('FREE_POINT', '무료 구매 지급 포인트', 1000, 1, 10000, 365, true),
    ('FREE_POINT', '무료 구매 지급 포인트', 1000, 1, 10000, 100, false),
    ('MANUAL_POINT', '관리자 수기 지급 포인트', 10000, 1, 1000000, 50, true);
