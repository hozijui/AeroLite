package cn.cdipcc.aerolite.server.dao;

import cn.cdipcc.aerolite.server.entity.Permission;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 权限表(Permission)数据库访问层
 *
 * @author hozijui
 */
public interface PermissionDao {
    Permission queryById(Long id);

    List<Permission> queryAll();

    long count(Permission permission);

    int insert(Permission permission);

    int insertBatch(@Param("entities") List<Permission> entities);

    int insertOrUpdateBatch(@Param("entities") List<Permission> entities);

    int update(Permission permission);

    int deleteById(Long id);
}

