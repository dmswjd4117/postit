SET @@foreign_key_checks = 0;
TRUNCATE TABLE role;
INSERT INTO role(role_id, role_description, role_name) VALUES (1, "사용자", "ROLE_MEMBER");
INSERT INTO role(role_id, role_description, role_name) VALUES (2, "관리자", "ROLE_ADMIN");
SET @@foreign_key_checks = 1;