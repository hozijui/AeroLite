package cn.cdipcc.aerolite.server.dao;

import cn.cdipcc.aerolite.server.dto.RolePermission;
import cn.cdipcc.aerolite.server.entity.Role;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 角色表(Role)数据库访问层
 *
 * @author hozijui
 */
public interface RoleDao {
    Role queryById(Long id);

    List<Role> queryAll();

    long count(Role role);

    int insert(Role role);

    int insertBatch(@Param("entities") List<Role> entities);

    int insertOrUpdateBatch(@Param("entities") List<Role> entities);

    int update(Role role);

    int deleteById(Long id);

    RolePermission getPermissionByRole(String role);
}

