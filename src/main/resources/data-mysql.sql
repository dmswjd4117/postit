--role
INSERT INTO role(role_description, role_name) VALUES('관리자 권한', 'ROLE_ADMIN');
INSERT INTO role(role_description, role_name) VALUES('사용자 권한', 'ROLE_MEMBER');

--사용자
INSERT INTO member(email, name, password) VALUES('admin@gmail.com', 'admin', '$2a$10$mCDr0Dz9SbSrpEb4EwwsueQUMAA6Pm.1XqnhZMa2qtl3QW4f6J7AS');
INSERT INTO member(email, name, password) VALUES('member@gmail.com', 'member', '$2a$10$mCDr0Dz9SbSrpEb4EwwsueQUMAA6Pm.1XqnhZMa2qtl3QW4f6J7AS');

--사용자가 가진 권한들
INSERT INTO member_role(member_id, role_id) VALUES(1, 1);
INSERT INTO member_role(member_id, role_id) VALUES(2, 2);
