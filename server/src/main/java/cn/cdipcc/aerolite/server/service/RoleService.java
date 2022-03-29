package cn.cdipcc.aerolite.server.service;

import cn.cdipcc.aerolite.server.entity.Role;
import java.util.List;

/**
 * 角色表(Role)服务接口
 *
 * @author hozijui
 */
public interface RoleService {
    Role queryById(Long id);

    List<Role> queryAll();
}
