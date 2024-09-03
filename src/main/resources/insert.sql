-- 用户表
CREATE TABLE common_table (
  id int AUTO_INCREMENT PRIMARY KEY,
  content varchar(200),
);
-- 测试数据
INSERT INTO common_table(content) VALUES
('primary_db数据');

-- 数据库secondary_db
INSERT INTO common_table(content) VALUES
('secondary_db数据');
