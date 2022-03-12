package cn.cdipcc.aerolite.server.service.impl;

import cn.cdipcc.aerolite.server.entity.Role;
import cn.cdipcc.aerolite.server.dao.RoleDao;
import cn.cdipcc.aerolite.server.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public PageInfo<Role> queryByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Role> roleList = this.roleDao.queryAll();
        return new PageInfo<>(roleList);
    }

    @Override
    public Role insert(Role role) {
        this.roleDao.insert(role);
        return role;
    }

    @Override
    public Role update(Role role) {
        this.roleDao.update(role);
        return this.queryById(role.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        return this.roleDao.deleteById(id) > 0;
    }
}
