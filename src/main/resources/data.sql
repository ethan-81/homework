INSERT INTO POINT_TYPE (code, name, max_earn_amount, min_earn_amount, max_holding_amount, valid_day, active, use_priority)
VALUES
    ('FREE_POINT', '무료 구매 지급 포인트', 1000, 1, 10000, 365, true, 2),
    ('FREE_POINT', '무료 구매 지급 포인트', 1000, 1, 10000, 100, false, 2),
    ('MANUAL_POINT', '관리자 수기 지급 포인트', 10000, 1, 1000000, 50, true, 1),
    ('ALTERNATIVE_POINT', '만료 된 포인트 사용 취소에 의한 대체 지급 포인트', 10000, 1, 1000000, 365, true, 1);
