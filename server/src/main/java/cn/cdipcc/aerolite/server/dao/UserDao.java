package cn.cdipcc.aerolite.server.dao;

import cn.cdipcc.aerolite.server.dto.UserInfo;
import cn.cdipcc.aerolite.server.dto.UserRole;
import cn.cdipcc.aerolite.server.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户表(User)数据库访问层
 *
 * @author hozijui
 */
public interface UserDao {
    User queryById(Long id);

    User queryByUsername(String username);

    List<User> queryAll();

    long count(User user);

    int insert(User user);

    int insertBatch(@Param("entities") List<User> entities);

    int insertOrUpdateBatch(@Param("entities") List<User> entities);

    int update(User user);

    int deleteById(Long id);

    UserRole getRolesByUsername(String username);

    UserInfo getUserInfo(String username);
}

