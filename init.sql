CREATE TABLE account (id INT AUTO_INCREMENT PRIMARY KEY, lo VARCHAR(255), pass VARCHAR(255), is_employee TINYINT(1));
CREATE TABLE employee (id INT AUTO_INCREMENT PRIMARY KEY, position VARCHAR(255), id_account INT, name VARCHAR(255), surname VARCHAR(255), unique_code INT, FOREIGN KEY (id_account) REFERENCES account(id) ON DELETE CASCADE);
DELIMITER //
CREATE TRIGGER generate_unique_code
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
END;//
DELIMITER ;