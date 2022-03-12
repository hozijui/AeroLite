package cn.cdipcc.aerolite.server.service.impl;

import cn.cdipcc.aerolite.server.entity.Permission;
import cn.cdipcc.aerolite.server.dao.PermissionDao;
import cn.cdipcc.aerolite.server.service.PermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限表(Permission)服务实现类
 *
 * @author hozijui
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private PermissionDao permissionDao;

    @Override
    public Permission queryById(Long id) {
        return this.permissionDao.queryById(id);
    }

    @Override
    public PageInfo<Permission> queryByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Permission> permissionList = this.permissionDao.queryAll();
        return new PageInfo<>(permissionList);
    }

    @Override
    public Permission insert(Permission permission) {
        this.permissionDao.insert(permission);
        return permission;
    }

    @Override
    public Permission update(Permission permission) {
        this.permissionDao.update(permission);
        return this.queryById(permission.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        return this.permissionDao.deleteById(id) > 0;
    }
}
