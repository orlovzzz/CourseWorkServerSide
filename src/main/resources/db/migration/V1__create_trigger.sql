CREATE TRIGGER IF NOT EXISTS generate_unique_code
BEFORE INSERT ON employee
FOR EACH ROW
BEGIN
    DECLARE random_code INT;
    DECLARE code_exists INT;

    SET code_exists = 1;

    WHILE code_exists > 0 DO
        SET random_code = FLOOR(RAND() * 9000) + 1000;
        SELECT COUNT(*) INTO code_exists FROM employee WHERE unique_code = random_code;
    END WHILE;

    SET NEW.unique_code = random_code;
END;