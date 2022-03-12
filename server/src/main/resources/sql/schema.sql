DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `username` varchar(32) NOT NULL COMMENT '用户名',
    `password` varchar(100) NOT NULL COMMENT '密码',
    `nickname` varchar(100) NOT NULL COMMENT '昵称',
    `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
    `last_login` datetime DEFAULT NULL COMMENT '最近登录时间',
    `enabled` tinyint(1) DEFAULT '1' COMMENT '是否启用',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_sys_user_username` (`username`)
) COMMENT='用户表';


DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `role` varchar(32) NOT NULL COMMENT '唯一标识',
    `name` varchar(100) NOT NULL COMMENT '角色名称',
    `description` varchar(255) DEFAULT NULL COMMENT '角色描述',
    `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
    `enabled` tinyint(1) DEFAULT '1' COMMENT '是否启用',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_sys_role_role` (`role`)
) COMMENT='角色表';


DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `permission` varchar(32) NOT NULL COMMENT '唯一标识',
    `name` varchar(100) NOT NULL COMMENT '权限名称',
    `uri` varchar(255) DEFAULT NULL COMMENT '可访问的地址',
    `method` varchar(20) DEFAULT NULL COMMENT '允许的请求方法',
    `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
    `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
    `enabled` tinyint(1) DEFAULT '1' COMMENT '是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_sys_permission_permission` (`permission`)
) COMMENT='权限表';

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) DEFAULT NULL,
    `role_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
) COMMENT='用户和角色关系表';

DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id` bigint(20) DEFAULT NULL,
    `permission_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
) COMMENT='角色和权限关系表';
