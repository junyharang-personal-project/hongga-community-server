insert into member (member_no, email, password, name, nickname, phone_number, activated, about_me)
values (1, 'admin@hongga.com','$2a$10$M0M5qQHbmCZV81zCdLAF3uLkodBv4t6oyYO2h47UpX9VQnMGIl.uW', '만든이', 'admin', '010-2983-3939', 1, '안녕하세요!');

insert into authority (authority_name) values ('ROLE_GUEST');
insert into authority (authority_name) values ('ROLE_FAMILY');
insert into authority (authority_name) values ('ROLE_ADMIN');

insert into member_authority (member_no, authority_name) values (1, 'ROLE_GUEST');
insert into member_authority (member_no, authority_name) values (1, 'ROLE_FAMILY');
insert into member_authority (member_no, authority_name) values (1, 'ROLE_ADMIN');