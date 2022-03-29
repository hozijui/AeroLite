package cn.cdipcc.aerolite.server.service;

import cn.cdipcc.aerolite.server.dto.UserInfo;
import cn.cdipcc.aerolite.server.entity.User;
import com.github.pagehelper.PageInfo;

/**
 * 用户表(User)服务接口
 *
 * @author hozijui
 */
public interface UserService {
    User queryById(Long id);

    User queryByUsername(String username);

    PageInfo<UserInfo> queryByPage(Integer page, Integer limit, String orderBy, User user);

    User insert(User user, Long roleId);

    User update(User user, Long roleId);

    void updatePassword(Long id, String password, String confirm);

    boolean deleteById(Long id);

    UserInfo getUserInfo();

    UserInfo updateInfo(User user);

    void updatePassword(String origin, String password, String confirm);
}
