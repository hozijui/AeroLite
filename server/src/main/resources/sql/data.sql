INSERT INTO `sys_user` (id, username, password, nickname, department)
VALUES (1, 'admin', '$2a$12$Tq.qUvKrHGW18t3NiG5VB.8JIiGvmf3cPgljfxoeiWcc2D/9wj.1K', '管理员', '系统运维 | 辅助决策系统');

INSERT INTO `sys_role` (id, role, name) VALUES (1, 'admin', '管理员');
INSERT INTO `sys_role` (id, role, name) VALUES (2, 'user', '普通用户');

INSERT INTO `sys_user_role` (user_id, role_id) VALUES (1, 1);

INSERT INTO `sys_permission` (id, permission, name, uri, method) VALUES (1, 'account:view', '个人信息-查看', '/api/account/info', 'GET');
INSERT INTO `sys_permission` (id, permission, name, uri, method) VALUES (2, 'account:edit', '个人信息-修改', '/api/account/info', 'PATCH');
INSERT INTO `sys_permission` (id, permission, name, uri, method) VALUES (3, 'account:password', '个人信息-修改密码', '/api/account/password', 'PUT');

INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (1, 1);
INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (1, 2);
INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (1, 3);
INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (2, 1);
INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (2, 2);
INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (2, 3);
