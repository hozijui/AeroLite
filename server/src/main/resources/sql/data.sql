INSERT INTO `sys_user` (id, username, password, nickname) VALUES (1, 'admin', '$2a$12$Tq.qUvKrHGW18t3NiG5VB.8JIiGvmf3cPgljfxoeiWcc2D/9wj.1K', '管理员');

INSERT INTO `sys_role` (id, role, name) VALUES (1, 'admin', '管理员');
INSERT INTO `sys_role` (id, role, name) VALUES (2, 'user', '普通用户');

INSERT INTO `sys_user_role` (user_id, role_id) VALUES (1, 1);

INSERT INTO `sys_permission` (id, permission, name, uri, method) VALUES (1, 'user_info', '用户信息', '/api/user/info', 'GET');

INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (1, 1);
INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (2, 1);
