package cn.cdipcc.aerolite.server.service.impl;

import cn.cdipcc.aerolite.server.entity.Role;
import cn.cdipcc.aerolite.server.dao.RoleDao;
import cn.cdipcc.aerolite.server.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色表(Role)服务实现类
 *
 * @author hozijui
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;

    @Override
    public Role queryById(Long id) {
        return this.roleDao.queryById(id);
    }

    @Override
    public List<Role> queryAll() {
        return this.roleDao.queryAll();
    }
}
