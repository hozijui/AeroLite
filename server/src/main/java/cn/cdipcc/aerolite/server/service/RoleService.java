package cn.cdipcc.aerolite.server.service;

import cn.cdipcc.aerolite.server.entity.Role;
import com.github.pagehelper.PageInfo;

/**
 * 角色表(Role)服务接口
 *
 * @author hozijui
 */
public interface RoleService {
    Role queryById(Long id);

    PageInfo<Role> queryByPage(Integer page, Integer limit);

    Role insert(Role role);

    Role update(Role role);

    boolean deleteById(Long id);
}
