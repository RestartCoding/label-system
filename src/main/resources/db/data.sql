insert into label(name, code, parent_code)
values('标签', '0', null);

insert into user(username, password, first_name, last_name)
values ('admin', '$2y$10$wbpSrzm0aT/aUObSxaQ.aOABKahDOlG0IQmgDTsarKWkh3uR864Ja', 'Jack', 'Sha');

insert into role(code, name, description)
values ('admin', '管理员', '管理系统的角色，拥有至高无上的权限');

insert into operation(code, name, description) values
('label:import', '标签导入', '标签导入的操作'),
('label:export', '标签导出', '标签导出的操作'),
('label:create', '标签创建', '标签创建的操作'),
('label:page', '标签分页查询', '标签分页查询的操作');

insert into user_role(username, role_code)
values ('admin', 'admin');

insert into role_operation(role_code, operation_code) values
('admin', 'label:import'),
('admin', 'label:export'),
('admin', 'label:create'),
('admin', 'label:page');
