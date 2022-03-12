package cn.cdipcc.aerolite.server.service;

import cn.cdipcc.aerolite.server.entity.Permission;
import com.github.pagehelper.PageInfo;

/**
 * 权限表(Permission)服务接口
 *
 * @author hozijui
 */
public interface PermissionService {
    Permission queryById(Long id);

    PageInfo<Permission> queryByPage(Integer page, Integer limit);

    Permission insert(Permission permission);

    Permission update(Permission permission);

    boolean deleteById(Long id);
}
