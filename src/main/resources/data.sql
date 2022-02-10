insert into member (member_id, email, password, name, nickname, phone_number, activated, about_me)
values (1, 'admin@hongga.com','$2a$08$ldnHPz7eUkSi6ao14Twugu08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', '만든이', 'admin', '010-2983-3939', 1, '안녕하세요!');

insert into authority (authority_name) values ('ROLE_GUEST');
insert into authority (authority_name) values ('ROLE_FAMILY');
insert into authority (authority_name) values ('ROLE_ADMIN');

insert into member_authority (member_id, authority_name) values (1, 'ROLE_GUEST');
insert into member_authority (member_id, authority_name) values (1, 'ROLE_FAMILY');
insert into member_authority (member_id, authority_name) values (1, 'ROLE_ADMIN');