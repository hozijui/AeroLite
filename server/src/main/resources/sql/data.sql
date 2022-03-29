INSERT INTO `sys_user` (id, username, password, nickname, department)
VALUES (1, 'admin', '$2a$12$Tq.qUvKrHGW18t3NiG5VB.8JIiGvmf3cPgljfxoeiWcc2D/9wj.1K', '管理员', '系统运维 | 辅助决策系统');

INSERT INTO `sys_role` (id, role, name) VALUES (1, 'admin', '管理员');
INSERT INTO `sys_role` (id, role, name) VALUES (2, 'user', '普通用户');

INSERT INTO `sys_user_role` (user_id, role_id) VALUES (1, 1);

INSERT INTO `sys_permission` (id, permission, name, uri, method) VALUES (1, 'account:view', '账户信息-查看', '/api/account/info', 'GET');
INSERT INTO `sys_permission` (id, permission, name, uri, method) VALUES (2, 'account:edit', '账户信息-修改', '/api/account/info', 'PATCH');
INSERT INTO `sys_permission` (id, permission, name, uri, method) VALUES (3, 'account:password', '账户信息-修改密码', '/api/account/password', 'PUT');
INSERT INTO `sys_permission` (id, permission, name, uri, method) VALUES (4, 'user:list', '用户管理-查看', '/api/user', 'GET');
INSERT INTO `sys_permission` (id, permission, name, uri, method) VALUES (5, 'user:create', '用户管理-创建', '/api/user', 'POST');
INSERT INTO `sys_permission` (id, permission, name, uri, method) VALUES (6, 'user:edit', '用户管理-修改', '/api/user/{id}', 'PATCH');
INSERT INTO `sys_permission` (id, permission, name, uri, method) VALUES (7, 'user:password', '用户管理-修改密码', '/api/user/{id}/password', 'PUT');
INSERT INTO `sys_permission` (id, permission, name, uri, method) VALUES (8, 'user:delete', '用户管理-删除', '/api/user/{id}', 'DELETE');
INSERT INTO `sys_permission` (id, permission, name, uri, method) VALUES (9, 'role:list', '角色管理-查看', '/api/role', 'GET');

INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (1, 1);
INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (1, 2);
INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (1, 3);
INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (1, 4);
INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (1, 5);
INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (1, 6);
INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (1, 7);
INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (1, 8);
INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (1, 9);
INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (2, 1);
INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (2, 2);
INSERT INTO `sys_role_permission` (role_id, permission_id) VALUES (2, 3);
