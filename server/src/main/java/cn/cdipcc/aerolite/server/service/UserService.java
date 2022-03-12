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

    PageInfo<User> queryByPage(Integer page, Integer limit);

    User insert(User user);

    User update(User user);

    boolean deleteById(Long id);

    UserInfo getUserInfo();
}
