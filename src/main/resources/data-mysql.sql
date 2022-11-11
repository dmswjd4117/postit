--role
INSERT INTO role(role_description, role_name) VALUES('관리자 권한', 'ROLE_ADMIN');
INSERT INTO role(role_description, role_name) VALUES('사용자 권한', 'ROLE_MEMBER');

INSERT INTO member(member_id, email, name, password) VALUES(1, 'member0@gmail.com', 'member0', '$2a$10$mCDr0Dz9SbSrpEb4EwwsueQUMAA6Pm.1XqnhZMa2qtl3QW4f6J7AS');
INSERT INTO member(member_id, email, name, password) VALUES(2, 'member1@gmail.com', 'member1', '$2a$10$mCDr0Dz9SbSrpEb4EwwsueQUMAA6Pm.1XqnhZMa2qtl3QW4f6J7AS');
INSERT INTO member(member_id, email, name, password) VALUES(3, 'member2@gmail.com', 'member2', '$2a$10$mCDr0Dz9SbSrpEb4EwwsueQUMAA6Pm.1XqnhZMa2qtl3QW4f6J7AS');
INSERT INTO member(member_id, email, name, password) VALUES(4, 'member3@gmail.com', 'member3', '$2a$10$mCDr0Dz9SbSrpEb4EwwsueQUMAA6Pm.1XqnhZMa2qtl3QW4f6J7AS');

--사용자가 가진 권한들
INSERT INTO member_role(member_id, role_id) VALUES(1, 2);
INSERT INTO member_role(member_id, role_id) VALUES(2, 2);
INSERT INTO member_role(member_id, role_id) VALUES(3, 2);
INSERT INTO member_role(member_id, role_id) VALUES(4, 2);

-- member_id 가 target_member_id를 팔로우 한다.
INSERT INTO connections(member_id, target_member_id) VALUES(2, 1);
INSERT INTO connections(member_id, target_member_id) VALUES(2, 3);
INSERT INTO connections(member_id, target_member_id) VALUES(2, 4);

--포스트
INSERT INTO post(post_id, title, body, member_id) VALUES(1, 'first', 'post!!', 2);
INSERT INTO post(post_id, title, body, member_id) VALUES(2, 'second', 'post!!', 2);
INSERT INTO post(post_id, title, body, member_id) VALUES(3, 'third', 'post!!', 2);

--태그
INSERT INTO tag(tag_id, tag_name) VALUES(1, "tag-1");
INSERT INTO tag(tag_id, tag_name) VALUES(2, "tag-2");
INSERT INTO tag(tag_id, tag_name) VALUES(3, "tag-3");
INSERT INTO tag(tag_id, tag_name) VALUES(4, "tag-4");
INSERT INTO tag(tag_id, tag_name) VALUES(5, "tag-5");
INSERT INTO tag(tag_id, tag_name) VALUES(6, "tag-6");

--포스트 <-> 태그
INSERT INTO post_tag(post_tag_id, post_id, tag_id) VALUES(1, 1, 1);
INSERT INTO post_tag(post_tag_id, post_id, tag_id) VALUES(2, 1, 2);
INSERT INTO post_tag(post_tag_id, post_id, tag_id) VALUES(3, 1, 3);
INSERT INTO post_tag(post_tag_id, post_id, tag_id) VALUES(4, 2, 1);

--이미지
INSERT INTO image(image_path, post_id) VALUES('https://i.pinimg.com/736x/ee/7a/4c/ee7a4c39dd2a5790acc6e24b5d710e44.jpg',  1);
INSERT INTO image(image_path, post_id) VALUES('https://ibb.co/B4Zywkp',  1);
